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

import java.util.HashMap;
import java.util.Map;

@Configuration
public class GpmFoeBusinessEventConfig {

    @Autowired
    DefaultSslBundleRegistry defaultSslBundleRegistry;

    @Bean(name="GpmFoeBusinessEventKTFactory")
    public KafkaTemplate<String, IGpmFoeBusinessEvent> GpmFoeBusinessEventKTFactory(KafkaProperties defaultProperties) {
        return new KafkaTemplate<>(producerFactory(defaultProperties));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Object> GpmFoeBusinessEventKLCFactory(KafkaProperties defaultProperties) {
        ConcurrentKafkaListenerContainerFactory<String, Object> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory(defaultProperties));
        return factory;
    }


    public ProducerFactory<String, IGpmFoeBusinessEvent> producerFactory(KafkaProperties properties) {
        Map<String, Object> configProps = properties.buildProducerProperties(defaultSslBundleRegistry);
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        //configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        configProps.put(JsonSerializer.ADD_TYPE_INFO_HEADERS, true);
        configProps.put(JsonSerializer.TYPE_MAPPINGS, EventMapping.GPEFOE_TYPE_MAPPINGS);
        return new DefaultKafkaProducerFactory<>(configProps,new StringSerializer(), new JsonSerializer<>());
    }

    private ConsumerFactory<String, Object> consumerFactory(KafkaProperties properties) {

        Map<String, Object> configProps = properties.buildConsumerProperties(defaultSslBundleRegistry);
        //configProps.put(ConsumerConfig.GROUP_ID_CONFIG, "my-group-id");
        configProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        configProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        configProps.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
        configProps.put(JsonSerializer.TYPE_MAPPINGS, EventMapping.GPEFOE_TYPE_MAPPINGS );
        return new DefaultKafkaConsumerFactory<>(configProps);
    }



}
