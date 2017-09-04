package com.lohika.jclub.realtor;

import lombok.extern.slf4j.Slf4j;

import com.lohika.jclub.storage.client.Apartment;
import com.lohika.jclub.storage.client.StorageServiceClient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import javax.annotation.PostConstruct;

@Slf4j
@RestController
public class RealtorController {

    @Autowired
    private DiscoveryClient discoveryClient;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private StorageServiceClient storageServiceClient;

    @PostConstruct
    public void warmUp() {
        storageServiceClient.list();
    }

    @PostMapping("/apartments")
    public void addApartment(@RequestBody ApartmentRecord apartmentRecord) {
        ResponseEntity<Boolean> isHackster =
                restTemplate.exchange("http://hackster-service/hackster/{phone}",
                        HttpMethod.GET, null, Boolean.class, apartmentRecord.getPhone());
        log.info("Is hackster " + isHackster);
    }

    @PostMapping("/storeApartments")
    @ResponseBody
    public Apartment storeApartment(@RequestBody ApartmentRecord apartmentRecord) {

        Apartment newApartment = Apartment.builder()
            .location(apartmentRecord.getLocation())
            .mail(apartmentRecord.getMail())
            .phone(apartmentRecord.getPhone())
            .price(apartmentRecord.getPrice())
            .realtorName(apartmentRecord.getRealtorName())
            .sqft(apartmentRecord.getSqft())
            .build();

        Apartment apartment = storageServiceClient.create(newApartment);
        /*ApartmentRecordClient apartmentRecordClient = Feign.builder().encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder()).target(ApartmentRecordClient.class, "http://storage-service");
        apartmentRecordClient.storeApartment(apartmentRecord);*/
        log.info("Stored, {}", apartment);
        return apartment;
    }

    @RequestMapping("/service-instances/{applicationName}")
    public List<ServiceInstance> serviceInstancesByApplicationName(@PathVariable String applicationName) {
        return this.discoveryClient.getInstances(applicationName);
    }
}
