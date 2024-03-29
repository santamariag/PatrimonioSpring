package it.poste.patrimonio.kconsumer.titoli.config;


import it.poste.patrimonio.event.business.model.EventMapping;
import it.poste.patrimonio.event.business.model.ITitoliBusinessEvent;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.boot.ssl.DefaultSslBundleRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.Map;

@Configuration
public class TitoliBusinessEventConfig {

    public ProducerFactory<String, ITitoliBusinessEvent> producerFactory(KafkaProperties defaultProperties, DefaultSslBundleRegistry defaultSslBundleRegistry) {
        Map<String,Object> configProps = defaultProperties.buildConsumerProperties(defaultSslBundleRegistry);
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        configProps.put(JsonSerializer.ADD_TYPE_INFO_HEADERS, true);
        configProps.put(JsonSerializer.TYPE_MAPPINGS, EventMapping.TITOLI_BUSINESS_EVENT_TYPE_MAPPINGS);
        return new DefaultKafkaProducerFactory<>(configProps,new StringSerializer(), new JsonSerializer<>());
    }

    private ConsumerFactory<String, ITitoliBusinessEvent> consumerFactory(KafkaProperties defaultProperties, DefaultSslBundleRegistry defaultSslBundleRegistry) {
        Map<String,Object> configProps = defaultProperties.buildConsumerProperties(defaultSslBundleRegistry);
        configProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        configProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        configProps.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
        configProps.put(JsonSerializer.TYPE_MAPPINGS, EventMapping.TITOLI_BUSINESS_EVENT_TYPE_MAPPINGS);
        return new DefaultKafkaConsumerFactory<>(configProps);
    }


    @Bean(name="TitoliBusinessEventKTFactory")
    public KafkaTemplate<String, ITitoliBusinessEvent> TitoliBusinessEventKTFactory(KafkaProperties defaultProperties, DefaultSslBundleRegistry defaultSslBundleRegistry) {
        return new KafkaTemplate<>(producerFactory(defaultProperties, defaultSslBundleRegistry));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, ITitoliBusinessEvent> TitoliBusinessEventKLCFactory(KafkaProperties defaultProperties, DefaultSslBundleRegistry defaultSslBundleRegistry) {
        ConcurrentKafkaListenerContainerFactory<String, ITitoliBusinessEvent> factory = new ConcurrentKafkaListenerContainerFactory<>();
        //factory.setConcurrency(32);
        factory.setConsumerFactory(consumerFactory(defaultProperties, defaultSslBundleRegistry));
        return factory;
    }

}
