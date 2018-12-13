package com.lohika.jclub.prometheus;

import java.io.IOException;
import java.io.Writer;
import io.prometheus.client.CollectorRegistry;
import io.prometheus.client.Counter;
import io.prometheus.client.exporter.common.TextFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
//@EnablePrometheusEndpoint
public class StatusController {

    private final Counter counter = Counter.build()
            .name("http_total_request")
            .labelNames("status")
            .help("All http calls")
            .register();

    @GetMapping("/status/{status}/{content}")
    public ResponseEntity<String> getStatus(@PathVariable int status, @PathVariable String content) {
        counter.labels(String.valueOf(status)).inc();
        return new ResponseEntity<>(content, HttpStatus.resolve(status));
    }

    @GetMapping("/metrics")
    public void metrics(Writer writer) throws IOException {
        TextFormat.write004(writer, CollectorRegistry.defaultRegistry.metricFamilySamples());
        writer.close();
    }
}
