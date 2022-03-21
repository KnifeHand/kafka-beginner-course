package com.knifehand.kafka;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

public class Producer {
    private static final String bootstrapServers = "localhost:9092";
    private static final Logger logger = LoggerFactory.getLogger(Producer.class);
    private static final String TOPIC = "first_topic";
    private static String VALUE = "Hiddee Ho!";
    private static String KEY = "id_";


    // create Producer properties
    public static void setProducerProperties() throws ExecutionException, InterruptedException {

        // create the producer
        for(int i = 0; i < 10; i++){
            getProducer();
            VALUE = VALUE + Integer.toString(i);
            KEY = KEY + Integer.toString(i);

            logger.info("Key: " + KEY); // log the key
            // create a producer record
            ProducerRecord<String, String> record = getProducerRecord();

            // send data - asynchronous
//            getProducer().send(record);
            callback();

            // flush data
            getProducer().flush();

            // flush and close producer
            getProducer().close();
        }
    }

    public static ProducerRecord<String, String> getProducerRecord() {
        return new ProducerRecord<String, String>(TOPIC, KEY, VALUE);
    }

    public static Properties getProperties() {
        Properties properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        return properties;
    }

    public static KafkaProducer<String, String> getProducer(){
        return new KafkaProducer<String, String>(getProperties());
    }

    public static void callback() throws ExecutionException, InterruptedException {
        getProducer().send(getProducerRecord(), new Callback() {
            @Override
            public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                // executes every time a record is successfully sent or an exception is thrown
                if (e == null){
                    // the record was successfully sent
                    logger.info("\n***Recieved new metadata***\n"
                            + "Topic: " +recordMetadata.topic() + "\n"
                            + "Partition: " + recordMetadata.partition() +"\n"
                            + "Offset: " + recordMetadata.offset() + "\n"
                            + "Timestamp: " + recordMetadata.timestamp());
                } else {
                    logger.error("Error while producing", e);
                }
            }
        }).get(); // block the .send() to make it synchronous - don't do in production
    }

}

