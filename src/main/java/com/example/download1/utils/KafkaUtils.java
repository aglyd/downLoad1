package com.example.download1.utils;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.kafka.support.SendResult;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

//@Component
//@RequiredArgsConstructor
public class KafkaUtils {

//    private final KafkaTemplate<String, String> kafkaTemplate;

    private static Logger log = LoggerFactory.getLogger(KafkaUtils.class);
    /**
     * 发送消息
     */
//    public void kafkaSendMsg(String topicName, String msg){
//        log.info("kafkaTemplate:/"+kafkaTemplate);
//        SendResult<String, String> metadata = null;
//        try {
//            metadata = kafkaTemplate.send(new ProducerRecord<>(topicName, msg)).get();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        }
//        log.info("kafka成功发送消息给：" + topicName + "，内容为：" + msg);
//        ProducerRecord<String, String> producerRecord = metadata.getProducerRecord();
//        RecordMetadata recordMetadata = metadata.getRecordMetadata();
//        log.info("同步返回：" + metadata + "，producerRecord内容为：" +producerRecord.topic()+"//"+producerRecord.partition()+"//recordMetadata:"+"//"+recordMetadata.topic()+"//"+recordMetadata.serializedKeySize()+"//"+recordMetadata.serializedValueSize());
//    }

    /**
     * 监听消息
     */
//    @KafkaListener(topics = {"bxyq1"}, groupId = "bxyq1",containerFactory="kafkaListenerContainerFactory")
    public void kafkaListener(ConsumerRecord<?, ?> record, Acknowledgment ack, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {

        log.info("这是消费者在消费消息：" + record.topic() + "----" + record.partition() + "----" + record.value());

        ack.acknowledge();
    }

    public static void testSend(){
        Properties props = new Properties();
        // 必须
        props.put("bootstrap.servers","10.251.22.122:9092");
        // 被发送到broker的任何消息的格式都必须是字节数组
        props.put("key.serializer","org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer","org.apache.kafka.common.serialization.StringSerializer");
        // 非必须参数配置
        // acks=0表明producer完全不管发送结果；
        // acks=all或-1表明producer会等待ISR所有节点均写入后的响应结果；
        // acks=1，表明producer会等待leader写入后的响应结果
        props.put("acks","-1");
        // 发生可重试异常时的重试次数
        props.put("retries",3);
        // producer会将发往同一分区的多条消息封装进一个batch中，
        // 当batch满了的时候，发送其中的所有消息,不过并不总是等待batch满了才发送消息；
        props.put("batch.size",323840);
        // 控制消息发送延时，默认为0，即立即发送，无需关心batch是否已被填满。
        props.put("linger.ms",10);
        // 指定了producer用于缓存消息的缓冲区大小，单位字节，默认32MB
        // producer启动时会首先创建一块内存缓冲区用于保存待发送的消息，然后由另一个专属线程负责从缓冲区中读取消息执行真正的发送
        props.put("buffer.memory",33554432);
        // 设置producer能发送的最大消息大小
        props.put("max.request.size",10485760);
        // 设置是否压缩消息，默认none
        props.put("compression.type","lz4");
        // 设置消息发送后，等待响应的最大时间
        props.put("request.timeout.ms",30);

        Producer<String,String> producer = new KafkaProducer<String, String>(props);
        for(int i = 0;i<1;i++){
            producer.send(new ProducerRecord<>("bxyq","key"+i,"value"+i));
        }

        producer.close();
    }


    public static void main(String[] args) {
        testSend();
    }
}
