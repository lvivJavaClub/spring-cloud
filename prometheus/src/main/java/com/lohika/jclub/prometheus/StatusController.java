package com.lohika.jclub.prometheus;

import io.prometheus.client.Counter;
import io.prometheus.client.spring.boot.EnablePrometheusEndpoint;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnablePrometheusEndpoint
public class StatusController {

    private final Counter counter = Counter.build()
            .name("http_total_request")
            .help("All http calls")
            .register();

    @GetMapping("/status/{status}/{content}")
    public ResponseEntity<String> getStatus(@PathVariable int status, @PathVariable String content) {
        counter.inc();
        return new ResponseEntity<>(content, HttpStatus.resolve(status));
    }
}
