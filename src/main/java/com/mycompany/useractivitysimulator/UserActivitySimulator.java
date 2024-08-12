package com.mycompany.useractivitysimulator;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord; 
import org.apache.kafka.common.serialization.StringSerializer;  
import java.util.*; 
 
public class UserActivitySimulator {

    public static void main(String[] args) {

        final List<String> lstUsers = Arrays.asList(
                "User1",
                "User2",
                "User3",
                "User4",
                "User5",
                "User6"
        );
        
        for (int i = 0; i <= lstUsers.size() - 1; i++) {
            myUser newUser = new myUser();
            newUser.UserName = lstUsers.get(i);
            newUser.start();
        }
    }
}

class myUser extends Thread {
    String UserName;

    @Override
    public void run() {
        System.out.println(STR."User is \{UserName}");
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
        //System.out.println('“'Message sent successfully”)
        producer.close();
    }
}

