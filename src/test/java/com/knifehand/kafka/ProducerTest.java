package org.acme;

import com.boeing.bds.ptes.mms.event.classified.svc.producer.Producer;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutionException;


class ProducerTest {
    @Test
    void producerTest() throws ExecutionException, InterruptedException {
        Producer.setProducerProperties();
    }
}
