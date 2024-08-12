package com.mycompany.useractivitysimulator;

import org.apache.kafka.clients.producer.KafkaProducer; 
import org.apache.kafka.clients.producer.ProducerConfig; 
import org.apache.kafka.clients.producer.ProducerRecord; 
import org.apache.kafka.common.serialization.StringSerializer;  
import java.util.*; 
 
public class UserActivitySimulator {
    
    public String UserId;

    static List<String> lstUsers = Arrays.asList(
            "User1", 
            "User2",
            "User3",
            "User4",
            "User5",
            "User6"
    );
    
    public static void main(String[] args) {
        
        //create producer on a new thread each time we create a new user
        
        for (int i = 0; i <= lstUsers.size(); i++) {
            User.CreateUser();
        }
    }
}

class User {
    
    static void CreateUser() {
        
        SpawnProducer();
        
        CreateNewUser();
        
        
        
    }
    
    
   static void CreateNewUser(){
       
       
   } 
    
   static KafkaProducer<String, String> SpawnProducer() {
        Properties properties = new Properties(); 
        
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092"); 
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName()); 
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName()); 
        properties.setProperty(ProducerConfig.COMPRESSION_TYPE_CONFIG, "snappy"); 
        properties.setProperty(ProducerConfig.LINGER_MS_CONFIG, "20"); 
        properties.setProperty(ProducerConfig.BATCH_SIZE_CONFIG, Integer.toString(32*1024)); 
  
        KafkaProducer<String, String> producer = new KafkaProducer<>(properties); 
  
        ProducerRecord<String, String> record = new ProducerRecord<>("UserActivitySimulator", "hello from the producer"); 
           
        producer.send(record);
        producer.flush(); 
        
        return producer;
    }
}