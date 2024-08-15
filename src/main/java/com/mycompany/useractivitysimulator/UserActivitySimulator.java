package com.mycompany.useractivitysimulator;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord; 
import org.apache.kafka.common.serialization.StringSerializer;  
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class UserActivitySimulator {

    private static volatile String strUserName;
    private static volatile String strUserEvent;

    public static void main(String[] args) throws InterruptedException {

        AtomicInteger intCounter = new AtomicInteger();

        Runnable newUser = () -> {
            for (int i = 1; i <= 12; i++) {
                intCounter.incrementAndGet();
                strUserName = "User" + intCounter.get();

                System.out.println("User " + strUserName + " is instantiated");
            }
        };

        Thread myThread = new Thread(newUser);

        myThread.start();

        Thread.sleep(2000);

        myThread.join();

        SelectActivity(myThread);
    }

    private static void SelectActivity(Thread thisThread) {
        String strUserEvent = "this happened";

        System.out.println("Thread-" + thisThread.getName() + " " + strUserEvent);

        thisThread.interrupt();

    }
}

class  myProducer  {

    public static void main(String[] args) {
        CreateProducer();
    }

    private static void  CreateProducer() {
        Properties props = new Properties();

        props.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.setProperty(ProducerConfig.COMPRESSION_TYPE_CONFIG, "snappy");
        props.setProperty(ProducerConfig.LINGER_MS_CONFIG, "20");
        props.setProperty(ProducerConfig.BATCH_SIZE_CONFIG, Integer.toString(32*1024));

        Producer<String, String> producer = new KafkaProducer
                <String, String>(props);
        
        producer.send(new ProducerRecord<String, String>("UserActivitySimulator", "hello from the producer"));
        producer.close();
    }
}

