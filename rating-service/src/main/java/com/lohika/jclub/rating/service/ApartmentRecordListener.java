package com.lohika.jclub.rating.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigDecimal;

/**
 * @author Andriy Levchenko
 */
@Slf4j
@Component
@EnableKafka
public class ApartmentRecordListener {

    @Autowired
    private RatingService ratingService;

    @Autowired
    private ObjectMapper objectMapper;

    @KafkaListener(topics = "${kafka.topic.boot}")
    public void receive(ConsumerRecord<String, String> record) throws IOException {
        log.info("Received message: " + record);
        Apartment apartment = objectMapper.readValue(record.value(), Apartment.class);
        BigDecimal rating = ratingService.calculateRating(apartment);
        log.info("Rating : {}", rating);
    }
}
