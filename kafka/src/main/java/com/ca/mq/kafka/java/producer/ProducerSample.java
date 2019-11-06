package com.ca.mq.kafka.java.producer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.HashMap;
import java.util.Map;

public class ProducerSample {

    public static void main(String[] args) {
        Map<String, Object> props = new HashMap<String, Object>();
        //bootstrap.servers Kafka集群，多台机器逗号分隔
        props.put("bootstrap.servers", "localhost:9042");
        //key.serializer value.serializer消息序列化类型
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        //key.deserializer value.deserializer 反序列化类型
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeSerializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeSerializer");

        //zk.connect zk服务器
        props.put("zk.connect", "127.0.0.1:2181");

        String topic = "test-topic";
        System.out.println("start");
        Producer<String, String> producer = new KafkaProducer<String, String>(props);
        producer.send(new ProducerRecord(topic, "idea-key2", "java-message 1"));
        producer.send(new ProducerRecord(topic, "idea-key2", "java-message 2"));
        producer.send(new ProducerRecord(topic, "idea-key2", "java-message 3"));

        producer.close();
        System.out.println("end");
    }

}
