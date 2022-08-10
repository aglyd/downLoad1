package com.example.download1.dao;

import com.example.download1.entity.User;
import com.example.download1.entity.UserMoney;
import com.example.download1.utils.PropertiesUtil;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@SpringBootTest
class UserDaoTest {

    private static final Logger logger = LoggerFactory.getLogger(UserDaoTest.class);

    @Autowired
    public UserDao userDao;
    @Autowired
    public UserMoneyDao userMoneyDao;
    @Value("${user1.name1}")
    private String username;
    @Value("${spring.datasource.url}")
    private String jdbcurl;

    @Test
    void insertUser() {
        logger.info("开始测试新增用户信息");
        User user = new User("小新");
        int uid = userDao.insertUser(user);
        logger.info("新增用户uid：{},user:{}",uid,user);
        final int i = userMoneyDao.insertUserMoney(new UserMoney(user.getId(), new BigDecimal("10000001.213")));
        UserMoney userMoney = new UserMoney();
        userMoney.setUid(user.getId());
        final List<UserMoney> userMonies = userMoneyDao.selectUserMoneyList(userMoney);
        logger.info("测试结束，新增用户成功:{}，用户信息：{}",i,userMonies);
    }

    @Test
    void getName(){

        Map property = PropertiesUtil.read("application-Druid");
        String envName = property.get("envName").toString();
        String jdbcurl = property.get("jdbc.url").toString();
        logger.info("username:{},envName:{},jdbc_url:{}",username, envName,jdbcurl);
    }
}