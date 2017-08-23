package com.lohika.jclub.dsl.service;

import groovy.lang.GroovyShell;
import groovy.util.DelegatingScript;

import com.lohika.jclub.dsl.MyDsl;
import com.lohika.jclub.rating.client.RatingServiceClient;
import com.lohika.jclub.storage.client.StorageServiceClient;

import org.codehaus.groovy.control.CompilerConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
public class DslService {
  private static final String DSL_EXTENSION = ".mydsl";

  @Value("${dsl.basepath}")
  private String basepath;

  @Autowired
  private StorageServiceClient storageServiceClient;

  @Autowired
  private RatingServiceClient ratingServiceClient;

  public MyDsl runScript(String scriptName) throws IOException {
    String script = getScriptByName(scriptName);
    return run(script);
  }

  private String getScriptByName(String scriptName) throws IOException {
    File file = new File(basepath + scriptName + DSL_EXTENSION);
    return new String(Files.readAllBytes(Paths.get(file.getPath())));
  }

  private MyDsl run(String script) {
    MyDsl dsl = new MyDsl(ratingServiceClient, storageServiceClient);

    CompilerConfiguration configuration = new CompilerConfiguration();
    configuration.setScriptBaseClass(DelegatingScript.class.getName());

    GroovyShell groovy = new GroovyShell(configuration);

    DelegatingScript delegatingScript = (DelegatingScript) groovy.parse(script);
    delegatingScript.setDelegate(dsl);
    delegatingScript.run();

    return dsl;
  }
}
