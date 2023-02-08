package com.example.download1.config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

//@Configuration
public class KafkaMQConfig {
    // 此处配置代替zk
    @Value("${spring.kafka.bootstrap-servers}")
    private String servers;
    // 消费组标识
    @Value("${spring.kafka.consumer.group-id}")
    private String groupId;
    // 偏移量的起始点
    @Value("${spring.kafka.consumer.auto-offset-reset}")
    private String autoOffsetReset;
    // 偏移量的提交方式
    @Value("${spring.kafka.consumer.enable-auto-commit}")
    private String enableAutoCommit;
    // 一次从kafka服务拉取的数据量
    @Value("${spring.kafka.consumer.max-poll-records}")
    private String maxPollRecords;
    // 监测消费端心跳时间
    @Value("${spring.kafka.consumer.heartbeat-interval}")
    private String heartbeatInterval;
    // 两次拉取数据的最大时间间隔
    @Value("${spring.kafka.consumer.max.poll.interval.ms}")
    private String maxPollIntervalMs;
    //生产者相关配置
//    @Bean
    public KafkaProducer kafkaProducer() {
        Properties props = new Properties();
        // 设置接入点，请通过控制台获取对应 Topic 的接入点
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, servers);
        // Kafka 消息的序列化方式
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        // 请求的最长等待时间
        props.put(ProducerConfig.MAX_BLOCK_MS_CONFIG, 30 * 1000);
        // 构造 Producer 对象，注意，该对象是线程安全的
        // 一般来说，一个进程内一个 Producer 对象即可
        // 如果想提高性能，可构造多个对象，但最好不要超过 5 个
        return new KafkaProducer<String, String>(props);
    }
    // 消费端相关配置
//    @Bean
    public Map<String, Object> consumerConfigs() {
        System.out.println("sercers:"+servers+"//"+groupId+"//"+maxPollIntervalMs);
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, servers);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, enableAutoCommit);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, autoOffsetReset);
        props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, maxPollRecords);
        props.put(ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG, maxPollIntervalMs);
        props.put(ConsumerConfig.HEARTBEAT_INTERVAL_MS_CONFIG, heartbeatInterval);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        return props;
    }
//    @Bean
    public ConsumerFactory<String, String> consumerFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerConfigs());
    }
//    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL_IMMEDIATE);
        return factory;
    }

}
