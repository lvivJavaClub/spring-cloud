package com.lohika.jclub.realtor;

import feign.Headers;
import feign.RequestLine;

/**
 * Created by omuliarevych on 6/8/17.
 */
public interface ApartmentRecordClient {

    @RequestLine("POST /apartmentRecords")
    @Headers("Content-Type: application/json")
    void storeApartment(ApartmentRecord apartmentRecord);
}
