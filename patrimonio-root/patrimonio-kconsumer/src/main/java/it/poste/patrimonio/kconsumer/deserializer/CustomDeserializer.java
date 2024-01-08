package it.poste.patrimonio.kconsumer.deserializer;

import java.util.Map;

import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;


import com.fasterxml.jackson.databind.ObjectMapper;

import it.poste.patrimonio.itf.model.GpmDTO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomDeserializer implements Deserializer<GpmDTO> {
    
    private final ObjectMapper objectMapper= new ObjectMapper();

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
    }

    @Override
    public GpmDTO deserialize(String topic, byte[] data) {
        try {
            if (data == null){
                log.error("Null received at deserializing");
                return null;
            }
            log.info("Deserializing...");
            return objectMapper.readValue(new String(data, "UTF-8"), GpmDTO.class);
        } catch (Exception e) {
            throw new SerializationException("Error when deserializing byte[] to PositionDTO");
        }
    }

    @Override
    public void close() {
    }
}