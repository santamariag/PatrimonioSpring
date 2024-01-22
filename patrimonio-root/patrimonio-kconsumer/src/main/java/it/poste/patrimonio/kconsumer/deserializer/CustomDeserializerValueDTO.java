package it.poste.patrimonio.kconsumer.deserializer;

import java.util.Map;

import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import it.poste.patrimonio.itf.model.ValueDTO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomDeserializerValueDTO implements Deserializer<ValueDTO> {
    
    private final ObjectMapper objectMapper= new ObjectMapper();

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
    	
    	objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    }

    @Override
    public ValueDTO deserialize(String topic, byte[] data) {
        try {
            if (data == null){
                log.error("Null received at deserializing");
                return null;
            }
            log.trace("Deserializing...");
            return objectMapper.readValue(new String(data, "UTF-8"), ValueDTO.class);
        } catch (Exception e) {
            throw new SerializationException("Error when deserializing byte[]");
        }
    }

    @Override
    public void close() {
    }
}