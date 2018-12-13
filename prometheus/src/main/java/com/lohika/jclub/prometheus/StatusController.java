package com.lohika.jclub.prometheus;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StatusController {

    @GetMapping("/status/{status}/{content}")
    public ResponseEntity<String> getStatus(@PathVariable int status, @PathVariable String content) {
        return new ResponseEntity<>(content, HttpStatus.resolve(status));
    }
}
