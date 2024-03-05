package it.poste.patrimonio.batch.configuration;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.experimental.core.explore.support.MongoJobExplorerFactoryBean;
import org.springframework.batch.experimental.core.repository.support.MongoJobRepositoryFactoryBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.mongodb.ReadConcern;
import com.mongodb.ReadPreference;
import com.mongodb.TransactionOptions;
import com.mongodb.WriteConcern;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

@Configuration
@EnableBatchProcessing
@EnableTransactionManagement
public class DataSourceConfig {
		
		@Value("${spring.data.mongodb.uri}")
		private String uri;
		
		@Value("${spring.data.mongodb.dbname}")
		private String dbName;

		
		@Bean
		public JobRepository jobRepository(MongoTemplate mongoTemplate, MongoTransactionManager transactionManager) throws Exception {
		    MongoJobRepositoryFactoryBean jobRepositoryFactoryBean = new MongoJobRepositoryFactoryBean();
		    jobRepositoryFactoryBean.setMongoOperations(mongoTemplate);
		    jobRepositoryFactoryBean.setTransactionManager(transactionManager);
		    jobRepositoryFactoryBean.afterPropertiesSet();
		    return jobRepositoryFactoryBean.getObject();
		}
		
		@Bean
		public MongoTemplate mongoTemplate(MongoDatabaseFactory mongoDatabaseFactory) {
			MongoTemplate template = new MongoTemplate(mongoDatabaseFactory);
			MappingMongoConverter converter = (MappingMongoConverter) template.getConverter();
			converter.setMapKeyDotReplacement(".");
			return template;
		}
		
		
		@Bean
		public MongoTransactionManager transactionManager(MongoDatabaseFactory mongoDatabaseFactory) {
			TransactionOptions transactionalOptions = TransactionOptions.builder()
		            .readConcern(ReadConcern.MAJORITY)
		            .readPreference(ReadPreference.primary())
		            .writeConcern(WriteConcern.MAJORITY.withJournal(true))
		            .build();
			MongoTransactionManager mongoTransactionManager = new MongoTransactionManager();
			mongoTransactionManager.setDbFactory(mongoDatabaseFactory);
			mongoTransactionManager.setOptions(transactionalOptions);
			mongoTransactionManager.afterPropertiesSet();
			return mongoTransactionManager;
		}
		
		@Bean
		public JobExplorer jobExplorer(MongoTemplate mongoTemplate, MongoTransactionManager transactionManager) throws Exception {
			MongoJobExplorerFactoryBean jobExplorerFactoryBean = new MongoJobExplorerFactoryBean();
			jobExplorerFactoryBean.setMongoOperations(mongoTemplate);
			jobExplorerFactoryBean.setTransactionManager(transactionManager);
			jobExplorerFactoryBean.afterPropertiesSet();
			return jobExplorerFactoryBean.getObject();
		}

		@Bean
		@Primary
		public MongoDatabaseFactory mongoDatabaseFactory() {
			MongoClient mongoClient = MongoClients.create(uri);
			return new SimpleMongoClientDatabaseFactory(mongoClient, dbName);
		}

}