package com.lohika.jclub;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Slf4j
@RestController()
public class RealtorController {

    @Autowired
    private DiscoveryClient discoveryClient;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private RealtorService realtorService;

    @PostMapping("/apartments")
    public void addApartment(@RequestBody ApartmentRecord apartmentRecord) {
        ResponseEntity<Boolean> isHackster =
                restTemplate.exchange("http://hackster-service/hackster/{phone}",
                        HttpMethod.GET, null, Boolean.class, apartmentRecord.getPhone());
        log.info("Is hackster " + isHackster);
    }

    @PostMapping("/storeApartments")
    public void storeApartment(@RequestBody ApartmentRecord apartmentRecord) {
        realtorService.storeApartment(apartmentRecord);
        /*ApartmentRecordClient apartmentRecordClient = Feign.builder().encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder()).target(ApartmentRecordClient.class, "http://storage-service");
        apartmentRecordClient.storeApartment(apartmentRecord);*/
        log.info("Stored");
    }

    @RequestMapping("/service-instances/{applicationName}")
    public List<ServiceInstance> serviceInstancesByApplicationName(@PathVariable String applicationName) {
        return this.discoveryClient.getInstances(applicationName);
    }
}
