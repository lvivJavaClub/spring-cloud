package com.lohika.jclub.hackster.client;

import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Import;
import org.springframework.hateoas.config.EnableHypermediaSupport;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented

@EnableHypermediaSupport(type = EnableHypermediaSupport.HypermediaType.HAL)
@EnableFeignClients(clients = {HacksterServiceClient.class})
@Import({FeignMappingDefaultConfiguration.class, HacksterServiceClientConfiguration.class})
@EnableDiscoveryClient
public @interface EnableHacksterServiceClient {
}
