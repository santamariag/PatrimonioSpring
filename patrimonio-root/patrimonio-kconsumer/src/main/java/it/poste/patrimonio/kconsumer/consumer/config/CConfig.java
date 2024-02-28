package it.poste.patrimonio.kconsumer.consumer.config;

import java.util.HashMap;
import java.util.Map;

import it.poste.patrimonio.kconsumer.config.KafkaConfig;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.AfterRollbackProcessor;
import org.springframework.kafka.listener.CommonErrorHandler;
import org.springframework.kafka.listener.ContainerProperties.AckMode;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.DefaultAfterRollbackProcessor;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.util.backoff.FixedBackOff;

import it.poste.patrimonio.itf.model.KeyDTO;
import it.poste.patrimonio.itf.model.ValueDTO;
import it.poste.patrimonio.kconsumer.deserializer.CustomDeserializerKeyDTO;
import it.poste.patrimonio.kconsumer.deserializer.CustomDeserializerValueDTO;


@EnableKafka
@Configuration
public class CConfig<TKey, TEvent> {

	@Autowired
	private KafkaConfig conf;

    @Bean
    @Profile("dev")
    ConsumerFactory<String, Object> consumerFactory() {
        Map<String, Object> properties = new HashMap<>();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, conf.getBootstrapAddress());
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, conf.getGroupId());
        /*properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
    	properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, CustomDeserializer.class);*/

       /* properties.put(ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG, 1000);
        properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);*/
        
        JsonDeserializer<Object> deserializer = new JsonDeserializer<>();
        deserializer.addTrustedPackages("*");
        //Set these if using SASL authentication or Confluent Cloud
        /*properties.put("security.protocol", "SASL_SSL");
    properties.put("sasl.mechanism", "PLAIN");
    properties.put("sasl.jaas.config", jaas);*/
        return new DefaultKafkaConsumerFactory<String, Object>(properties, new StringDeserializer(), deserializer);
    }
    
    @Bean
    @Profile("poste")
    ConsumerFactory<KeyDTO, ValueDTO> consumerFactoryTest() {
        Map<String, Object> properties = new HashMap<>();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, conf.getBootstrapAddress());
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, conf.getGroupId());
        /*properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
    	properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, CustomDeserializer.class);*/

       /* properties.put(ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG, 1000);
        properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);*/
        CustomDeserializerKeyDTO keyDeserializer = new CustomDeserializerKeyDTO();
        CustomDeserializerValueDTO valueDeserializer = new CustomDeserializerValueDTO();
       /*JsonDeserializer<KeyDTO> keyDeserializer = new JsonDeserializer<>();
        keyDeserializer.addTrustedPackages("*");
        JsonDeserializer<ValueDTO> valueDeserializer = new JsonDeserializer<>();
        valueDeserializer.addTrustedPackages("*");*/
        //Set these if using SASL authentication or Confluent Cloud
        /*properties.put("security.protocol", "SASL_SSL");
    properties.put("sasl.mechanism", "PLAIN");
    properties.put("sasl.jaas.config", jaas);*/
        return new DefaultKafkaConsumerFactory<>(properties, keyDeserializer, valueDeserializer);
    }

    @Bean
    @Profile("dev")
    ConcurrentKafkaListenerContainerFactory<String, Object> kafkaListenerContainerFactory(KafkaTemplate<TKey, TEvent> kafkaTemplate) {
        ConcurrentKafkaListenerContainerFactory<String, Object> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        factory.setCommonErrorHandler(errorHandler(kafkaTemplate));
        //factory.setAfterRollbackProcessor(afterRollbackProcessor(kafkaTemplate)); //Use with kafka transactions
        factory.getContainerProperties().setAckMode(AckMode.RECORD);
        
        return factory;
    }
    
    @Bean
    @Profile("poste")
    ConcurrentKafkaListenerContainerFactory<KeyDTO, ValueDTO> kafkaListenerContainerFactoryTest(KafkaTemplate<TKey, TEvent> kafkaTemplate) {
        ConcurrentKafkaListenerContainerFactory<KeyDTO, ValueDTO> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactoryTest());
        factory.setCommonErrorHandler(errorHandler(kafkaTemplate));
        //factory.setAfterRollbackProcessor(afterRollbackProcessor(kafkaTemplate)); //Use with kafka transactions
        factory.getContainerProperties().setAckMode(AckMode.RECORD);
        
        return factory;
    }
    
    @Bean
    public DeadLetterPublishingRecoverer recoverer(KafkaTemplate<TKey, TEvent> kafkaTemplate) {
    	DeadLetterPublishingRecoverer recoverer = new DeadLetterPublishingRecoverer(kafkaTemplate,
    			(r, e) -> {

    				return new TopicPartition(r.topic() + ".failures", r.partition());

    			});
    	
    	return recoverer;
    }
	
    
    @Bean
    public CommonErrorHandler errorHandler(KafkaTemplate<TKey, TEvent> kafkaTemplate) {
    	    	
    	DefaultErrorHandler handler= new DefaultErrorHandler(recoverer(kafkaTemplate),
    			new FixedBackOff(conf.getConsumer().getBackOff().getInterval(), conf.getConsumer().getBackOff().getMaxAttempts()));
 
    	return handler;
   }
    
    public AfterRollbackProcessor<String, Object> afterRollbackProcessor(KafkaTemplate<TKey, TEvent> kafkaTemplate) {
	
    	DefaultAfterRollbackProcessor<String, Object> processor= new DefaultAfterRollbackProcessor<String, Object>(recoverer(kafkaTemplate),
    			new FixedBackOff(conf.getConsumer().getBackOff().getInterval(), conf.getConsumer().getBackOff().getMaxAttempts()));
    	
    	return processor;
   }
    
}