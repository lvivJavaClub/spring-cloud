package com.lohika.jclub.integration;

import org.junit.ClassRule;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.testcontainers.containers.DockerComposeContainer;

import java.io.File;

@RunWith(Suite.class)
@Suite.SuiteClasses({
    GetWayIntegrationTest.class,
    DslIntegrationTest.class
})
public class EntToEndIntegrationTestSuite {

  private static final String COMPOSE = GetWayIntegrationTest.class.getClassLoader()
      .getResource("./docker-compose.yml").getPath();

  @ClassRule
  public static DockerComposeContainer environment = new DockerComposeContainer(new File(COMPOSE))
      .withPull(false)
      .withEnv("TestSuite", "true");
}
