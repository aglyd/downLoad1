package com.example.download1.dao;

import com.example.download1.entity.UserMoney;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMoneyDao {

    public int insertUserMoney(UserMoney userMoney);

    public List<UserMoney> selectUserMoneyList(UserMoney userMoney);
}
