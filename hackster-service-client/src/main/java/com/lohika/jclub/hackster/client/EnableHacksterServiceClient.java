package com.lohika.jclub.hackster.client;

import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Import;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented

@EnableFeignClients(clients = {HacksterServiceClient.class})
@Import({FeignMappingDefaultConfiguration.class, HacksterServiceClientConfiguration.class})
public @interface EnableHacksterServiceClient {
}
