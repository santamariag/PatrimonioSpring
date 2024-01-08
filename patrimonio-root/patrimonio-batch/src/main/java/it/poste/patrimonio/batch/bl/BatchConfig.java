package it.poste.patrimonio.batch.bl;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.configuration.support.JobRegistryBeanPostProcessor;
import org.springframework.batch.core.partition.PartitionHandler;
import org.springframework.batch.core.partition.support.TaskExecutorPartitionHandler;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

import it.poste.patrimonio.batch.bl.partitioner.CsvStepPartitioner;
import it.poste.patrimonio.batch.bl.processor.PriceDTOToPriceProcessor;
import it.poste.patrimonio.batch.bl.util.BatchConstants;
import it.poste.patrimonio.batch.bl.writer.PriceItemWriter;
import it.poste.patrimonio.db.model.Price;
import it.poste.patrimonio.db.repository.IGpmRepository;
import it.poste.patrimonio.db.repository.IPriceRepository;
import it.poste.patrimonio.itf.mapper.PriceMapper;
import it.poste.patrimonio.itf.model.PriceDTO;
import lombok.extern.slf4j.Slf4j;


@Configuration
@Slf4j
public class BatchConfig {

    public static final Long LONG_OVERRIDE = null;

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final IGpmRepository gpmRepository;
    private final IPriceRepository priceRepository;
    private final PriceMapper priceMapper;
    private final PlatformTransactionManager platformTransactionManager;
   
    
    public BatchConfig(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory,
			IGpmRepository gpmRepository, IPriceRepository priceRepository, PriceMapper priceMapper, PlatformTransactionManager platformTransactionManager) {
		this.jobBuilderFactory = jobBuilderFactory;
		this.stepBuilderFactory = stepBuilderFactory;
		this.gpmRepository = gpmRepository;
		this.priceRepository = priceRepository;
		this.priceMapper = priceMapper;
		this.platformTransactionManager = platformTransactionManager;
	}

	@Bean
    public Job loadCsvFileJob() {
        return this.jobBuilderFactory.get(BatchConstants.LOAD_CSV_FILE_JOB+System.currentTimeMillis())
                .start(loadCsvStepPartitioner())
                .build();
    }
	
	/*@Bean
    public Job loadCsvFileJob() {
        return this.jobBuilderFactory.get(BatchConstants.LOAD_CSV_FILE_JOB+System.currentTimeMillis())
                .start(loadCsvStep())
                .build();
    }*/

    private Step loadCsvStepPartitioner() {
        return stepBuilderFactory.get(BatchConstants.LOAD_CSV_STEP_PARTITIONER)
                .partitioner(loadCsvStep().getName(), csvStepPartitioner())
                .partitionHandler(loadCsvFileStepPartitionHandler(loadCsvStep(), BatchConstants.GRID_SIZE))
                .build();
    }

    private CsvStepPartitioner csvStepPartitioner() {
        return new CsvStepPartitioner();
    }

    private PartitionHandler loadCsvFileStepPartitionHandler(final Step step,
                                                             final int gridSize) {
        TaskExecutorPartitionHandler taskExecutorPartitionHandler =
                new TaskExecutorPartitionHandler();
        taskExecutorPartitionHandler.setTaskExecutor(taskExecutor());
        taskExecutorPartitionHandler.setStep(step);
        taskExecutorPartitionHandler.setGridSize(gridSize);
        return taskExecutorPartitionHandler;
    }

    public TaskExecutor taskExecutor() {
        SimpleAsyncTaskExecutor asyncTaskExecutor =
                new SimpleAsyncTaskExecutor(BatchConstants.THREAD_NAME_PREFIX);
        asyncTaskExecutor.setThreadNamePrefix(BatchConstants.SLAVE_THREAD);
        asyncTaskExecutor.setConcurrencyLimit(BatchConstants.CONCURRENCY_LIMIT);
        return asyncTaskExecutor;
    }

    public Step loadCsvStep() {
        return this.stepBuilderFactory.get(BatchConstants.LOAD_CSV_STEP)
                .<PriceDTO, Price>chunk(BatchConstants.CHUNK_SIZE)
                .reader(flatFileItemReader(LONG_OVERRIDE, LONG_OVERRIDE,
                        LONG_OVERRIDE))
                .processor(priceProcessor(priceMapper, priceRepository))
                .writer(writer())
                //.transactionManager(platformTransactionManager)
                .build();
    }
    
    @Bean
	public PriceDTOToPriceProcessor priceProcessor(PriceMapper priceMapper, IPriceRepository priceRepository) {
		return new PriceDTOToPriceProcessor(priceMapper, priceRepository);
    }

    @Bean
    public JobRegistryBeanPostProcessor jobRegistryBeanPostProcessor(JobRegistry jobRegistry) {
        JobRegistryBeanPostProcessor jobRegistryBeanPostProcessor = new JobRegistryBeanPostProcessor();
        jobRegistryBeanPostProcessor.setJobRegistry(jobRegistry);
        return jobRegistryBeanPostProcessor;
    }


    public PriceItemWriter writer() {
        return new PriceItemWriter(priceRepository, gpmRepository);
    }

    @Bean
    @StepScope
    public FlatFileItemReader<PriceDTO> flatFileItemReader(
            @Value("#{stepExecutionContext[partition_number]}") final Long partitionNumber,
            @Value("#{stepExecutionContext[first_line]}") final Long firstLine,
            @Value("#{stepExecutionContext[last_line]}") final Long lastLine) {

        log.info("Partition Number : {}, Reading file from line : {}, to line: {} ", partitionNumber, firstLine, lastLine);

        FlatFileItemReader<PriceDTO> reader = new FlatFileItemReader<>();
        reader.setLinesToSkip(Math.toIntExact(firstLine));
                                                                                         reader.setMaxItemCount(Math.toIntExact(lastLine));
        reader.setResource(new ClassPathResource(BatchConstants.FILENAME));
        reader.setLineMapper(new DefaultLineMapper<PriceDTO>() {
            {
               setLineTokenizer(new DelimitedLineTokenizer() {
                    {
                        setNames("isin", "price");
                    }
                });

                setFieldSetMapper(new BeanWrapperFieldSetMapper<PriceDTO>() {
                    {
                        setTargetType(PriceDTO.class);
                    }
                });

            }
        });
        return reader;
    }
}