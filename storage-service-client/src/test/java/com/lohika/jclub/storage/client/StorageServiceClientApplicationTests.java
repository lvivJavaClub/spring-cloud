package com.lohika.jclub.storage.client;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = StorageServiceClientTestApplication.class)
public class StorageServiceClientApplicationTests {

  @Autowired
  private StorageServiceClientFallback storageServiceClientFallback;

  @Test
  public void contextLoads() {
  }
}
