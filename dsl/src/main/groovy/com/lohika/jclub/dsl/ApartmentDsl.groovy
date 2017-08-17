package com.lohika.jclub.dsl

import com.lohika.jclub.storage.client.Apartment

//@Builder(prefix = "", builderStrategy = SimpleStrategy.class)
class ApartmentDsl {
    String location ="location"
    Integer price =2
    Integer sqft =2
    String phone ='phone'
    String realtorName ='realtorName'
    String mail ='mail'

    void location(String location) {
        this.location = location
    }

    void price(Integer price) {
        this.price = price
    }

    void sqft(Integer sqft) {
        this.sqft = sqft
    }

    void phone(String phone) {
        this.phone = phone
    }

    void realtorName(String realtorName) {
        this.realtorName = realtorName
    }

    void mail(String mail) {
        this.mail = mail
    }

    def toEntity() {
        Apartment.builder()
        .location(location)
        .price(price)
        .sqft(sqft)
        .phone(phone)
        .realtorName(realtorName)
        .mail(mail)
        .build()
    }
}
