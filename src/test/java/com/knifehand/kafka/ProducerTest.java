package com.knifehand.kafka;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutionException;


class ProducerTest {
    @Test
    void producerTest() throws ExecutionException, InterruptedException {
        org.acme.Producer.setProducerProperties();
    }
}
