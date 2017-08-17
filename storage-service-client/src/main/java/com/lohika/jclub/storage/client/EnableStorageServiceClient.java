package com.lohika.jclub.storage.client;

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
@Import({FeignMappingDefaultConfiguration.class, StorageServiceClientConfiguration.class})
public @interface EnableStorageServiceClient {
}
