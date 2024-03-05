package it.poste.patrimonio.batch;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.batch.JobLauncherApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication//(exclude={DataSourceAutoConfiguration.class})
@EnableBatchProcessing
@ComponentScan(basePackages = "it.poste.patrimonio")
@EnableMongoRepositories(basePackages = "it.poste.patrimonio")
//@EnableRetry
public class PatrimonioBatchApplication/* implements CommandLineRunner*/{
	
	@Autowired
	private JobLauncher jobLauncher;
	
	@Autowired
	private JobExplorer jobExplorer;
	
	@Autowired
	private JobRepository jobRepository;


	/*@Autowired
	private Job job;*/

	public static void main(String[] args) {
		SpringApplication.run(PatrimonioBatchApplication.class, args);
	}

	/*@Override
	public void run(String... args) throws Exception {
		
		log.info("Starting");

		JobParameters jobParameters = new JobParametersBuilder()
				.addString("name", "foo")
				.addLocalDateTime("runtime", LocalDateTime.now())
				.toJobParameters();

		
		JobExecution jobExecution = jobLauncher.run(job, jobParameters);
		
		log.info("COMPLETED "+jobExecution.getExitStatus());
		
	}*/
	
	@Bean
	public JobLauncherApplicationRunner jobLauncherApplicationRunner() {
		
		return new JobLauncherApplicationRunner(jobLauncher, jobExplorer, jobRepository);
	}
	
}
