package com.example.download1.service.impl;

import com.example.download1.dao.UserDao;
import com.example.download1.dao.UserMoneyDao;
import com.example.download1.entity.User;
import com.example.download1.entity.UserMoney;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

@Service
public class UserServiceImpl {
    private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    UserDao userDao;
    @Autowired
    UserMoneyDao userMoneyDao;

    public void insertUser(){
        logger.info("开始测试新增用户信息");
        User user = new User("小新");
        int uid = userDao.insertUser(user);
        logger.info("新增用户uid：{},user:{}",uid,user);
        final int j = userMoneyDao.insertUserMoney(new UserMoney(user.getId(), new BigDecimal("10000001.213")));
        UserMoney userMoney = new UserMoney();
        userMoney.setUid(user.getId());
        final List<UserMoney> userMonies = userMoneyDao.selectUserMoneyList(userMoney);
        logger.info("测试结束，新增用户成功:{}，用户信息：{}",j,userMonies);
    }


}
