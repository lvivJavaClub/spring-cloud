package com.lohika.jclub.dsl.service;

import groovy.lang.GroovyShell;
import groovy.util.DelegatingScript;

import com.lohika.jclub.dsl.core.MyDsl;
import com.lohika.jclub.rating.client.RatingServiceClient;
import com.lohika.jclub.storage.client.StorageServiceClient;

import org.springframework.cloud.sleuth.Span;
import org.springframework.cloud.sleuth.Tracer;

import org.codehaus.groovy.control.CompilerConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.sleuth.sampler.AlwaysSampler;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.annotation.PostConstruct;

@Service
public class DslService {
  private static final String DSL_EXTENSION = ".mydsl";

  @Value("${dsl.basepath}")
  private String basepath;

  @Autowired
  private Tracer tracer;

  @Autowired
  private StorageServiceClient storageServiceClient;

  @Autowired
  private RatingServiceClient ratingServiceClient;

  @PostConstruct
  public void warmUp() {
    storageServiceClient.list();
  }

  public MyDsl runScript(String scriptName) throws IOException {
    String script = getScriptByName(scriptName);
    return run(getDelegatingScript(script));
  }

  private String getScriptByName(String scriptName) throws IOException {
    this.tracer.addTag("script.name", scriptName);
    File file = new File(basepath + scriptName + DSL_EXTENSION);
    this.tracer.addTag("script.file", file.getAbsolutePath());

    Span span = this.tracer.createSpan("load script");
    try {
      String content = new String(Files.readAllBytes(Paths.get(file.getPath())));
      this.tracer.addTag("script.length", String.valueOf(content.length()));
      return content;
    } finally {
      this.tracer.close(span);
    }
  }

  private MyDsl run(DelegatingScript delegatingScript) {
    Span run = this.tracer.createSpan("Run script");
    delegatingScript.run();
    this.tracer.close(run);

    return (MyDsl) delegatingScript.getDelegate();
  }

  private DelegatingScript getDelegatingScript(String script) {
    Span prepare = this.tracer.createSpan("Prepare script");

    MyDsl dsl = new MyDsl(ratingServiceClient, storageServiceClient);

    CompilerConfiguration configuration = new CompilerConfiguration();
    configuration.setScriptBaseClass(DelegatingScript.class.getName());

    GroovyShell groovy = new GroovyShell(configuration);

    DelegatingScript delegatingScript = (DelegatingScript) groovy.parse(script);
    delegatingScript.setDelegate(dsl);
    this.tracer.close(prepare);

    return delegatingScript;
  }
}
