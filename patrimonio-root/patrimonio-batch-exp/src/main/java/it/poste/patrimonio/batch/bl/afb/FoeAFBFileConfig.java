package it.poste.patrimonio.batch.bl.afb;

import it.poste.patrimonio.batch.bl.config.JobNameConfig;
import it.poste.patrimonio.batch.bl.config.WorkerConfig;
import it.poste.patrimonio.batch.bl.util.BatchConstants;
import it.poste.patrimonio.db.model.Foe;
import it.poste.patrimonio.itf.model.AFBBalanceDTO;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.configuration.support.JobRegistryBeanPostProcessor;
import org.springframework.batch.core.job.builder.JobBuilder;
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
import org.springframework.data.mongodb.MongoTransactionManager;

import java.util.List;

import static it.poste.patrimonio.batch.bl.afb.FoeAFBBusinessConfig.*;


@Configuration
@Profile("foe-afb")
public class FoeAFBFileConfig {

	@Autowired
	private WorkerConfig workerConfig;
	
	@Autowired
	private JobNameConfig jobNameConfig;

	@Bean
	public Step loadCsvStep(JobRepository jobRepository, MongoTransactionManager transactionManager, 
			@Qualifier(AFB_FILE_CSV_READER_QUALIF) ItemReader<AFBBalanceDTO> itemReader,
			@Qualifier(AFB_FILE_PROCESSOR_QUALIF) ItemProcessor<AFBBalanceDTO, List<Foe>> itemProcessor,
			@Qualifier(AFB_FILE_WRITER_QUALIF) ItemWriter<List<Foe>> itemWriter){
		return new StepBuilder(BatchConstants.LOAD_AFB_STEP, jobRepository)
				.<AFBBalanceDTO, List<Foe>>chunk(workerConfig.getChunkSize(), transactionManager)
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

	@Bean
	public Job loadCsvFileJob(JobRepository jobRepository, MongoTransactionManager transactionManager,
			@Qualifier(MOVE_FILE_LISTENER_QUALIF) JobExecutionListener jobListener,
			@Qualifier(AFB_FILE_NAME_LISTENER_QUALIF) JobExecutionListener fileNameListener) {
			
		JobBuilder priceJob=null;
		if (jobNameConfig.isAlways())
			priceJob= new JobBuilder(BatchConstants.LOAD_AFB_FILE_JOB+System.currentTimeMillis(), jobRepository);
					
		else if (jobNameConfig.isAllowed())
			priceJob= new JobBuilder(BatchConstants.LOAD_AFB_FILE_JOB+jobNameConfig.getNameSuffix(), jobRepository);
			
		else
			priceJob= new JobBuilder(BatchConstants.LOAD_AFB_FILE_JOB, jobRepository);
			
		return priceJob
				.start(loadCsvStep(null, null, null, null, null))
				.listener(fileNameListener)
				.listener(jobListener)
				.build();
		
		
	}

}
