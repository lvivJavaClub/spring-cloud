package com.lohika.jclub;

import java.util.Random;
import io.prometheus.client.CollectorRegistry;
import io.prometheus.client.Gauge;
import io.prometheus.client.exporter.PushGateway;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) throws Exception {
        CollectorRegistry registry = new CollectorRegistry();
        Gauge duration = Gauge.build()
                .name("batch_job_duration")
                .help("Batch job duration")
                .register(registry);
        Gauge.Timer timer = duration.startTimer();

        System.out.println("Doing something important....");
        Thread.sleep(new Random().nextInt(5000));

        timer.setDuration();
        PushGateway pg = new PushGateway("127.0.0.1:9091");
        pg.pushAdd(registry, "batch_job");
    }
}
