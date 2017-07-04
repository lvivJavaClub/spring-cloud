package com.lohika.jclub.realtor;

import feign.Headers;
import feign.RequestLine;

public interface ApartmentRecordClient {

    @RequestLine("POST /apartmentRecords")
    @Headers("Content-Type: application/json")
    void storeApartment(ApartmentRecord apartmentRecord);
}