package it.poste.patrimonio.batch.configuration;

import java.math.BigDecimal;
import java.util.Arrays;

import org.bson.Document;
import org.bson.types.Decimal128;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.experimental.core.explore.support.MongoJobExplorerFactoryBean;
import org.springframework.batch.experimental.core.repository.support.MongoJobRepositoryFactoryBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
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
			converter.setCustomConversions(customConversions());
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
		
		MongoCustomConversions customConversions() {
		    return new MongoCustomConversions(Arrays.asList(
		        // writing converter, reader converter
		        new DocumentDecimal128Converter(), new Decimal128DocumentConverter()
		    ));
		  }
		
		@WritingConverter
		public static class DocumentDecimal128Converter implements Converter<Document, Decimal128> {

			@Override
			public Decimal128 convert(Document source) {
				return new Decimal128((BigDecimal)source.get("patrimonioOld.posizioni.qqta"));
			}
		}

		@ReadingConverter
		public static class Decimal128DocumentConverter implements Converter<Decimal128, Document> {

			@Override
			public Document convert(Decimal128 source) {
				return  new Document("patrimonioOld.posizioni.qqta",source.bigDecimalValue());
			}
		}

}