package com.lohika.jclub.client;

import com.lohika.jclub.storage.client.Apartment;
import com.lohika.jclub.storage.client.StorageServiceClient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.PagedResources;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class ClientController {

    @Autowired
    private StorageServiceClient storageServiceClient;

    @GetMapping("/apartments")
    public PagedResources<Apartment> getApartments() {
        PagedResources<Apartment> list = storageServiceClient.list();
        return new PagedResources<>(list.getContent(), list.getMetadata());
    }

}