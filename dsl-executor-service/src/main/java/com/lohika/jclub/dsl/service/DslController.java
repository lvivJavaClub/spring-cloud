package com.lohika.jclub.dsl.service;

import com.lohika.jclub.dsl.core.MyDsl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping(path = "/dsl")
public class DslController {

  @Autowired
  private DslService dslService;

  @GetMapping(path = "/{scriptName}")
  public MyDsl runScript(@PathVariable(name = "scriptName") String scriptName) throws IOException {
    return dslService.runScript(scriptName);
  }
}
