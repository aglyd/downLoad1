package com.example.download1.schedual;

import com.example.download1.dao.UserDao;
import com.example.download1.dao.UserMoneyDao;
import com.example.download1.entity.User;
import com.example.download1.entity.UserMoney;
import com.example.download1.service.impl.UserServiceImpl;
import com.example.download1.utils.QiangtaiService;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
public class XxlJobService01 {
    private Logger logger = LoggerFactory.getLogger(XxlJobService01.class);

    @Autowired
    UserServiceImpl userService;
    /**
     * 1、简单任务示例（Bean模式）
     */
//    @XxlJob("MyDownLoadJobHandler")
    public boolean demoJobHandler() throws Exception {
        XxlJobHelper.log("XXL-JOB, Hello World.MyDownLoadJobHandler");

        logger.info("执行XXL-JOB-Bean任务,MyDownLoadJobHandler");
        userService.insertUser();
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
