package com.lohika.jclub.dsl.service;

import groovy.lang.GroovyShell;
import groovy.util.DelegatingScript;

import com.lohika.jclub.dsl.MyDsl;
import com.lohika.jclub.rating.client.RatingServiceClient;
import com.lohika.jclub.storage.client.StorageServiceClient;

import org.codehaus.groovy.control.CompilerConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@RestController
@RequestMapping(path = "/dsl")
public class DslController {

  @Value("${dsl.basepath}")
  private String basepath;

  @Autowired
  private StorageServiceClient storageServiceClient;

  @Autowired
  private RatingServiceClient ratingServiceClient;

  @GetMapping(path = "/{scriptName}")
  public Object runScript(@PathVariable(name = "scriptName") String scriptName) throws IOException {
    File file = new File(basepath + scriptName + ".groovy");
    String script = new String(Files.readAllBytes(Paths.get(file.getPath())));

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
