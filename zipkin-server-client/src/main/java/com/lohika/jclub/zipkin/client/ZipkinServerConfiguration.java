package com.lohika.jclub.zipkin.client;


import lombok.extern.log4j.Log4j;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.sleuth.Sampler;
import org.springframework.cloud.sleuth.Span;
import org.springframework.cloud.sleuth.SpanNamer;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.cloud.sleuth.metric.SpanMetricReporter;
import org.springframework.cloud.sleuth.zipkin.HttpZipkinSpanReporter;
import org.springframework.cloud.sleuth.zipkin.ZipkinProperties;
import org.springframework.cloud.sleuth.zipkin.ZipkinSpanReporter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.Callable;

import static org.apache.commons.lang.StringUtils.isEmpty;

@Log4j
@Configuration
public class ZipkinServerConfiguration {

  @Autowired(required = false)
  private EurekaClient eurekaClient;

  @Autowired
  private SpanMetricReporter spanMetricReporter;

  @Autowired
  private ZipkinProperties zipkinProperties;

  @Value(value = "${spring.sleuth.web.skip-pattern:}")
  private String skipPattern;

  @Value(value = "${spring.sleuth.serverName:ZIPKIN-SERVER}")
  private String ziplikServerName;

  @Autowired
  @Qualifier("zipkinRestTemplate")
  private RestTemplate zipkinRestTemplate;

  @Bean
  @ConditionalOnProperty(value = "spring.sleuth.enabled", havingValue = "true")
  public ZipkinSpanReporter zipkinSpanReporter() {
    return new ZipkinSpanReporter() {

      private HttpZipkinSpanReporter delegate;
      private String baseUrl;

      @Override
      public void report(zipkin.Span span) {
        if (baseUrl == null || delegate == null) {
          InstanceInfo instance = eurekaClient.getNextServerFromEureka(ziplikServerName, false);
          log.info("InstanceInfo: " + instance);

          baseUrl = instance.getHomePageUrl();
          delegate = new HttpZipkinSpanReporter(zipkinRestTemplate, baseUrl, zipkinProperties.getFlushInterval(), spanMetricReporter);
        }

        if (isEmpty(skipPattern) || !span.name.matches(skipPattern)) {
          delegate.report(span);
        }
      }
    };
  }

  @Bean
  @Qualifier("zipkinRestTemplate")
  public RestTemplate zipkinRestTemplate() {
    return new RestTemplate();
  }

  @Bean
  @ConditionalOnMissingBean(EurekaClient.class)
  @ConditionalOnProperty(value = "spring.sleuth.enabled", havingValue = "false")
  public ZipkinSpanReporter emptyZipkinSpanReporter() {
    return span -> log.info(String.format("Reporting span [%s]", span));
  }

  @Bean
  @ConditionalOnProperty(value = "spring.sleuth.enabled", havingValue = "false")
  public SpanNamer spanNamer() {
    return (object, defaultValue) -> null;
  }

  @Bean
  @ConditionalOnProperty(value = "spring.sleuth.enabled", havingValue = "false")
  public Tracer tracer() {
    return new Tracer() {
      @Override
      public Span createSpan(String name) {
        return Span.builder().build();
      }

      @Override
      public Span createSpan(String name, Span parent) {
        return Span.builder().build();
      }

      @Override
      public Span createSpan(String name, Sampler sampler) {
        return Span.builder().build();
      }

      @Override
      public Span continueSpan(Span span) {
        return Span.builder().build();
      }

      @Override
      public void addTag(String key, String value) {
      }

      @Override
      public Span detach(Span span) {
        return Span.builder().build();
      }

      @Override
      public Span close(Span span) {
        return Span.builder().build();
      }

      @Override
      public <V> Callable<V> wrap(Callable<V> callable) {
        return null;
      }

      @Override
      public Runnable wrap(Runnable runnable) {
        return null;
      }

      @Override
      public Span getCurrentSpan() {
        return Span.builder().build();
      }

      @Override
      public boolean isTracing() {
        return false;
      }
    };
  }
}
