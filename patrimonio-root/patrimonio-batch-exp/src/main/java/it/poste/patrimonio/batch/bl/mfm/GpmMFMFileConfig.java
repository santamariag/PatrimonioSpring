package it.poste.patrimonio.batch.bl.mfm;

import static it.poste.patrimonio.batch.bl.mfm.GpmMFMBusinessConfig.MFM_FILE_CSV_READER_QUALIF;
import static it.poste.patrimonio.batch.bl.mfm.GpmMFMBusinessConfig.MFM_FILE_NAME_LISTENER_QUALIF;
import static it.poste.patrimonio.batch.bl.mfm.GpmMFMBusinessConfig.MFM_FILE_PROCESSOR_QUALIF;
import static it.poste.patrimonio.batch.bl.mfm.GpmMFMBusinessConfig.MFM_FILE_WRITER_QUALIF;
import static it.poste.patrimonio.batch.bl.mfm.GpmMFMBusinessConfig.MOVE_FILE_LISTENER_QUALIF;
import static it.poste.patrimonio.batch.bl.mfm.GpmMFMBusinessConfig.STEP_CONTEXT_LISTENER;

import java.util.List;

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

import it.poste.patrimonio.batch.bl.config.JobNameConfig;
import it.poste.patrimonio.batch.bl.config.WorkerConfig;
import it.poste.patrimonio.batch.bl.util.BatchConstants;
import it.poste.patrimonio.db.model.Gpm;
import it.poste.patrimonio.itf.model.MFMBalanceDTO;



@Configuration
@Profile("gpm-mfm")
public class GpmMFMFileConfig {

	@Autowired
	private WorkerConfig workerConfig;
	
	@Autowired
	private JobNameConfig jobNameConfig;

	@Bean
	public Step loadCsvStep(JobRepository jobRepository, MongoTransactionManager transactionManager, 
			@Qualifier(MFM_FILE_CSV_READER_QUALIF) ItemReader<MFMBalanceDTO> itemReader,
			@Qualifier(MFM_FILE_PROCESSOR_QUALIF) ItemProcessor<MFMBalanceDTO, List<Gpm>> itemProcessor,
			@Qualifier(MFM_FILE_WRITER_QUALIF) ItemWriter<List<Gpm>> itemWriter,
			@Qualifier(STEP_CONTEXT_LISTENER) StepExecutionListener listener) {
		return new StepBuilder(BatchConstants.LOAD_CSV_MFM_STEP, jobRepository)
				.<MFMBalanceDTO, List<Gpm>>chunk(workerConfig.getChunkSize(), transactionManager)
				.listener(listener)
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
			@Qualifier(MFM_FILE_NAME_LISTENER_QUALIF) JobExecutionListener fileNameListener) {
			
		JobBuilder priceJob=null;
		if (jobNameConfig.isAlways())
			priceJob= new JobBuilder(BatchConstants.LOAD_CSV_MFM_FILE_JOB+System.currentTimeMillis(), jobRepository);
					
		else if (jobNameConfig.isAllowed())
			priceJob= new JobBuilder(BatchConstants.LOAD_CSV_MFM_FILE_JOB+jobNameConfig.getNameSuffix(), jobRepository);
			
		else
			priceJob= new JobBuilder(BatchConstants.LOAD_CSV_MFM_FILE_JOB, jobRepository);
			
		return priceJob
				.start(loadCsvStep(null, null, null, null, null, null))
				.listener(fileNameListener)
				.listener(jobListener)
				.build();
		
		
	}

}
