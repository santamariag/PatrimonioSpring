package it.poste.patrimonio.kconsumer.producer.config;

import java.util.HashMap;
import java.util.Map;

import it.poste.patrimonio.kconsumer.config.KafkaConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.kafka.transaction.KafkaTransactionManager;

import com.mongodb.ReadConcern;
import com.mongodb.ReadPreference;
import com.mongodb.TransactionOptions;
import com.mongodb.WriteConcern;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

@Configuration
public class PConfig<TKey, TEvent> {

	@Autowired
	private KafkaConfig conf;
	
	@Value("${spring.data.mongodb.uri}")
	private String uri;
	
	@Value("${spring.data.mongodb.dbname}")
	private String dbName;
	
	@Bean
	MongoTransactionManager transactionManager(MongoDatabaseFactory mongoDatabaseFactory) {
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
	public MongoDatabaseFactory mongoDatabaseFactory() {
		MongoClient mongoClient = MongoClients.create(uri);
		return new SimpleMongoClientDatabaseFactory(mongoClient, dbName);
	}
	
	
	@Bean
	public KafkaTransactionManager<TKey, TEvent> kafkaTransactionManager(final ProducerFactory<TKey, TEvent> producerFactory) {
		
		return new KafkaTransactionManager<>(producerFactory);
	}

    @Bean
    ProducerFactory<TKey, TEvent> producerFactory() {
        Map<String, Object> properties = new HashMap<>();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, conf.getBootstrapAddress());
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        
        properties.put(ProducerConfig.TRANSACTIONAL_ID_CONFIG, "transaction-id");
        properties.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, true);

        //Set these if using SASL authentication or Confluent Cloud
        /*properties.put("security.protocol", "SASL_SSL");
    properties.put("sasl.mechanism", "PLAIN");
    properties.put("sasl.jaas.config", jaas);
    properties.put("acks", "all");*/
        return new DefaultKafkaProducerFactory<>(properties);
    }

    @Bean
	@Primary
    KafkaTemplate<TKey, TEvent> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }
}