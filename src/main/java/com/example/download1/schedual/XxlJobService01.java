package com.example.download1.schedual;

import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class XxlJobService01 {
    private Logger logger = LoggerFactory.getLogger(XxlJobService01.class);

    /**
     * 1、简单任务示例（Bean模式）
     */
    @XxlJob("MyDownLoadJobHandler")
    public boolean demoJobHandler() throws Exception {
        XxlJobHelper.log("XXL-JOB, Hello World.MyDownLoadJobHandler");

        logger.info("执行XXL-JOB-Bean任务,MyDownLoadJobHandler");
        for (int i = 0; i < 5; i++) {
            XxlJobHelper.log("beat at:" + i);
            TimeUnit.SECONDS.sleep(2);
        }
        // default success
        return XxlJobHelper.handleSuccess("XXL-JOB,MyDownLoadJobHandler");
    }

    public static void main(String[] args) {
        System.out.println("结束main");
    }
}
