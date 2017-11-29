package com.lohika.jclub.storage.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.event.AbstractRepositoryEventListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * @author Andriy Levchenko
 */
@Slf4j
@Component
public class ApartmentRepositoryEventListener extends AbstractRepositoryEventListener<ApartmentRecord> {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    protected void onAfterCreate(ApartmentRecord apartmentRecord) {
        log.info("Creating message for apartmentRecord " + apartmentRecord.toString());
        String json;
        try {
            json = objectMapper.writeValueAsString(apartmentRecord);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error", e);
        }
        kafkaTemplate.send("apartments", json);
        log.info("Message sent for apartmentRecord " + apartmentRecord.toString());
    }
}
