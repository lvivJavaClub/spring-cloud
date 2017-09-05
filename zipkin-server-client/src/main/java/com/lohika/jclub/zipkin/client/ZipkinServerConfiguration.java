package com.lohika.jclub.zipkin.client;


import lombok.extern.log4j.Log4j;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;

import org.springframework.beans.factory.annotation.Autowired;
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

@Log4j
@Configuration
public class ZipkinServerConfiguration {

  @Autowired(required = false)
  private EurekaClient eurekaClient;

  @Autowired
  private SpanMetricReporter spanMetricReporter;

  @Autowired
  private ZipkinProperties zipkinProperties;

  @Value(value = "${spring.sleuth.web.skip-pattern:#{null}}")
  private String skipPattern;

  @Value(value = "${spring.sleuth.serverName:#{null}}")
  private String ziplikServerName;

  @Autowired
  private RestTemplate restTemplate;

  @Bean
  public RestTemplate restTemplate() {
    return new RestTemplate();
  }

  @Bean
  @ConditionalOnProperty(value = "spring.sleuth.enabled", havingValue = "true")
  public ZipkinSpanReporter zipkinSpanReporter() {
    return new ZipkinSpanReporter() {

      private HttpZipkinSpanReporter delegate;
      private String baseUrl;

      @Override
      public void report(zipkin.Span span) {
        InstanceInfo instance = eurekaClient.getNextServerFromEureka(ziplikServerName, false);
        if (!(baseUrl != null && instance.getHomePageUrl().equals(baseUrl))) {
          baseUrl = instance.getHomePageUrl();
          delegate = new HttpZipkinSpanReporter(restTemplate, baseUrl, zipkinProperties.getFlushInterval(), spanMetricReporter);
          if (!span.name.matches(skipPattern)) {
            delegate.report(span);
          }
        }
      }
    };
  }

  @Bean
  @ConditionalOnMissingBean(EurekaClient.class)
  @ConditionalOnProperty(value = "spring.sleuth.enabled", havingValue = "false")
  public ZipkinSpanReporter emptyZipkinSpanReporter() {
    return span -> log.info(String.format("Reporting span [%s]", span));
  }

  @Bean
  @ConditionalOnMissingBean(SpanNamer.class)
  public SpanNamer spanNamer() {
    return (object, defaultValue) -> null;
  }

  @Bean
  @ConditionalOnMissingBean(Tracer.class)
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
