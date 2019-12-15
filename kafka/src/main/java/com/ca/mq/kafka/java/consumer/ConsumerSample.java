package com.ca.mq.kafka.java.consumer;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.Arrays;
import java.util.Properties;

/**
 * 先运行consumer
 */
public class ConsumerSample {

    public static void main(String[] args) {
        String topic = "test-topic";

        Properties props = new Properties();
        props.put("bootstrap.servers", "39.100.121.226:9092");
        props.put("group.id", "testGroup1");
        //Consumer的offset是否自动提交
        props.put("enable.auto.commit", "true");
        //设置自动提交offset到zookeeper的时间间隔，单位是毫秒
        props.put("auto.commit.interval.ms", "1000");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        Consumer<String, String> consumer = new KafkaConsumer(props);
        consumer.subscribe(Arrays.asList(topic));
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(100);
            for (ConsumerRecord<String, String> record : records)
                System.out.printf("partition = %d, offset = %d, key = %s, value = %s%n", record.partition(), record.offset(), record.key(), record.value());
        }

    }
}
