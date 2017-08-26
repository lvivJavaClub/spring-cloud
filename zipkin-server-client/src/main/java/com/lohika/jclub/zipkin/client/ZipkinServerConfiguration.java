package com.lohika.jclub.zipkin.client;


import lombok.extern.log4j.Log4j;
import zipkin.Span;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.sleuth.metric.SpanMetricReporter;
import org.springframework.cloud.sleuth.zipkin.HttpZipkinSpanReporter;
import org.springframework.cloud.sleuth.zipkin.ZipkinProperties;
import org.springframework.cloud.sleuth.zipkin.ZipkinSpanReporter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Log4j
@Configuration
public class ZipkinServerConfiguration {

  @Autowired
  private EurekaClient eurekaClient;

  @Autowired
  private SpanMetricReporter spanMetricReporter;

  @Autowired
  private ZipkinProperties zipkinProperties;

  @Value(value = "${spring.sleuth.web.skip-pattern:#{null}}")
  private String skipPattern;

  @Value(value = "${spring.sleuth.serverName}")
  private String ziplikServerName;

  @Autowired
  private RestTemplate restTemplate;

  @Bean
  public RestTemplate restTemplate() {
    return new RestTemplate();
  }

  @Bean
  @ConditionalOnProperty(value = "sample.zipkin.enabled", havingValue = "true")
  public ZipkinSpanReporter makeZipkinSpanReporter() {
    return new ZipkinSpanReporter() {

      private HttpZipkinSpanReporter delegate;
      private String baseUrl;

      @Override
      public void report(Span span) {
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
  @ConditionalOnProperty(value = "sample.zipkin.enabled", havingValue = "false")
  public ZipkinSpanReporter spanCollector() {
    return span -> log.info(String.format("Reporting span [%s]", span));
  }
}
