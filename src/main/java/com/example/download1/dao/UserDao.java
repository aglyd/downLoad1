package com.example.download1.dao;

import com.example.download1.entity.User;
import com.sun.org.apache.bcel.internal.classfile.Code;
import org.apache.http.annotation.Contract;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserDao {

    public int insertUser(User user);


}
