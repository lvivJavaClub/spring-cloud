package com.lohika.jclub.dsl.service;

import com.lohika.jclub.dsl.MyDsl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping(path = "/dsl")
public class DslController {

  @Value("${dsl.basepath}")
  private String basepath;

  @Autowired
  private DslService dslService;

  @GetMapping(path = "/{scriptName}")
  public MyDsl runScript(@PathVariable(name = "scriptName") String scriptName) throws IOException {
    return dslService.runScript(scriptName);
  }
}
