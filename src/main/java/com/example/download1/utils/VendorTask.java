package com.example.download1.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class VendorTask {

     private static final Logger logger = LoggerFactory.getLogger(VendorTask.class);

//    @Scheduled(fixedDelay = 5000)     //每延迟5秒执行
    public void doSomethingWithDelay() {
        System.out.println("I'm doing with delay now!");
    }

//    @Scheduled(fixedRate = 5000)
    public void doSomethingWithRate() {
        System.out.println("I'm doing with rate now!");
    }

//    @Scheduled(cron = "0/5 * * ? * *")        //服务器启动之后每过5s钟执行一次
    public void doSomethingWithCron() {
        System.out.println("I'm doing with cron now!");
    }

}
