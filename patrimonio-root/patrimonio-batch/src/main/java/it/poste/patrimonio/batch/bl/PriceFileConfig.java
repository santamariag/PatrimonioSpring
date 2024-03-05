package it.poste.patrimonio.batch.bl;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.configuration.support.JobRegistryBeanPostProcessor;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.partition.PartitionHandler;
import org.springframework.batch.core.partition.support.TaskExecutorPartitionHandler;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.data.mongodb.MongoTransactionManager;

import it.poste.patrimonio.batch.bl.config.JobNameConfig;
import it.poste.patrimonio.batch.bl.config.WorkerConfig;
import it.poste.patrimonio.batch.bl.partitioner.CsvStepPartitioner;
import it.poste.patrimonio.batch.bl.util.BatchConstants;
import it.poste.patrimonio.db.model.Price;
import it.poste.patrimonio.itf.model.PriceDTO;

import static it.poste.patrimonio.batch.bl.PriceBusinessConfig.PRICE_FILE_CSV_READER_QUALIF;
import static it.poste.patrimonio.batch.bl.PriceBusinessConfig.PRICE_FILE_FIXED_READER_QUALIF;
import static it.poste.patrimonio.batch.bl.PriceBusinessConfig.PRICE_FILE_PROCESSOR_QUALIF;
import static it.poste.patrimonio.batch.bl.PriceBusinessConfig.PRICE_FILE_WRITER_QUALIF;
import static it.poste.patrimonio.batch.bl.PriceBusinessConfig.PRICE_FILE_SYNC_WRITER_QUALIF;
import static it.poste.patrimonio.batch.bl.PriceBusinessConfig.MOVE_FILE_LISTENER_QUALIF;



@Configuration
@Profile("price")
public class PriceFileConfig {

	@Autowired
	private WorkerConfig workerConfig;
	
	@Autowired
	private JobNameConfig jobNameConfig;

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
		asyncTaskExecutor.setConcurrencyLimit(workerConfig.getMaxNumber().intValue());
		return asyncTaskExecutor;
	}

	@Bean
	public Step loadCsvStep(JobRepository jobRepository, MongoTransactionManager transactionManager, 
			@Qualifier(PRICE_FILE_FIXED_READER_QUALIF) ItemReader<PriceDTO> itemReader,
			@Qualifier(PRICE_FILE_PROCESSOR_QUALIF) ItemProcessor<PriceDTO, Price> itemProcessor,
			@Qualifier(PRICE_FILE_WRITER_QUALIF) ItemWriter<Price> itemWriter) {
		return new StepBuilder(BatchConstants.LOAD_CSV_STEP, jobRepository)
				.<PriceDTO, Price>chunk(workerConfig.getChunkSize(), transactionManager)
				.reader(itemReader)
				.processor(itemProcessor)
				.writer(itemWriter)
				.build();
	}

	@Bean
    public JobRegistryBeanPostProcessor jobRegistryBeanPostProcessor(JobRegistry jobRegistry) {
        JobRegistryBeanPostProcessor jobRegistryBeanPostProcessor = new JobRegistryBeanPostProcessor();
        jobRegistryBeanPostProcessor.setJobRegistry(jobRegistry);
        return jobRegistryBeanPostProcessor;
    }

	/*@Bean
    public Job loadCsvFileJob(JobRepository jobRepository, MongoTransactionManager transactionManager) {
        return new JobBuilder(BatchConstants.LOAD_CSV_FILE_JOB+System.currentTimeMillis(), jobRepository)
        		.start(loadCsvStepPartitioner(jobRepository, transactionManager))
                .build();
    }*/

	@Bean
	public Job loadCsvFileJob(JobRepository jobRepository, MongoTransactionManager transactionManager,
			@Qualifier(PRICE_FILE_CSV_READER_QUALIF) ItemReader<PriceDTO> itemReader,
			@Qualifier(PRICE_FILE_PROCESSOR_QUALIF) ItemProcessor<PriceDTO, Price> itemProcessor,
			@Qualifier(PRICE_FILE_WRITER_QUALIF) ItemWriter<Price> itemWriter,
			@Qualifier(MOVE_FILE_LISTENER_QUALIF) JobExecutionListener jobListener) {
		
		
		JobBuilder priceJob=null;
		if (jobNameConfig.isAlways())
			priceJob= new JobBuilder(BatchConstants.LOAD_CSV_FILE_JOB+System.currentTimeMillis(), jobRepository);
					
		else if (jobNameConfig.isAllowed())
			priceJob= new JobBuilder(BatchConstants.LOAD_CSV_FILE_JOB+jobNameConfig.getNameSuffix(), jobRepository);
			
		else
			priceJob= new JobBuilder(BatchConstants.LOAD_CSV_FILE_JOB, jobRepository);
			
		return priceJob
				.start(loadCsvStep(null, null, null, null, null))
				.listener(jobListener)
				.build();
		
		
	}

	@SuppressWarnings("unused")
	private Step loadCsvStepPartitioner(JobRepository jobRepository, MongoTransactionManager transactionManager,
			@Qualifier(PRICE_FILE_CSV_READER_QUALIF) ItemReader<PriceDTO> itemReader,
			@Qualifier(PRICE_FILE_PROCESSOR_QUALIF) ItemProcessor<? super Object, ? extends Object> itemProcessor,
			@Qualifier(PRICE_FILE_WRITER_QUALIF) ItemWriter<? super Object> itemWriter,
			@Qualifier(MOVE_FILE_LISTENER_QUALIF) JobExecutionListener jobListener) {
		return new StepBuilder(BatchConstants.LOAD_CSV_STEP_PARTITIONER, jobRepository)
				.partitioner(loadCsvStep(null, null, null, null, null).getName(),csvStepPartitioner())
				.partitionHandler(loadCsvFileStepPartitionHandler(loadCsvStep(null, null, null, null, null), workerConfig.getChunkSize()))
				.build();
	}

	private CsvStepPartitioner csvStepPartitioner() {
		return new CsvStepPartitioner();
	}


}
