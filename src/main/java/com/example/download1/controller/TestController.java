package com.example.download1.controller;

import com.example.download1.utils.KafkaUtils;
import net.minidev.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/v1")
public class TestController {
//    @Autowired
    private KafkaUtils kafkaUtils;

    private static Logger log = LoggerFactory.getLogger(TestController.class);

    /**
     * 测试推送kafka消息
     * @return 结果
     */
    @GetMapping("/kafkaTest")
    public JSONObject kafkaTest() {
        Map map = new HashMap();
        map.put("title","lyptest");
        map.put("url","null");
        map.put("time","2022-09-23");
        map.put("source_url","https://www.baidu.com");
        map.put("content","这是一条测试数据");
        map.put("info","这是一条测试数据");
        map.put("create_time","1993915081000");
        map.put("task_id","54700bf6-b5ab-48ec-81a0-3e10136275fa");
        map.put("spider_id","5efc28d07d955672d3e5a565");
        map.put("project_id","5eec32da7d955672d3c59a67");
        String s = JSONObject.toJSONString(map);
        System.out.println("json:"+s);
//        kafkaUtils.kafkaSendMsg("bxyq", s);
        log.info("发送kafka队列成功！");
        return null;
    }

}
