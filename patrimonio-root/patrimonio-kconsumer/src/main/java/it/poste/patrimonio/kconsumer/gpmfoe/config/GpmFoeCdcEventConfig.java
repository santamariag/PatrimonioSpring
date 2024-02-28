package it.poste.patrimonio.kconsumer.gpmfoe.config;

import it.poste.patrimonio.event.business.model.EventMapping;
import it.poste.patrimonio.event.business.model.IGpmFoeBusinessEvent;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
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
public class GpmFoeCdcEventConfig {

    @Autowired
    DefaultSslBundleRegistry defaultSslBundleRegistry;

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> GpmFoeCdcEventKLCFactory(KafkaProperties defaultProperties) {
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory(defaultProperties));
        return factory;
    }

    private ConsumerFactory<String, String> consumerFactory(KafkaProperties properties) {
        Map<String,Object> configProps = properties.buildConsumerProperties(defaultSslBundleRegistry);
        configProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        return new DefaultKafkaConsumerFactory<>(configProps);
    }

}
