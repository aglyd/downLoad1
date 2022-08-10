package com.example.download1.entity;

import java.math.BigDecimal;
import java.util.Date;


public class UserMoney {

    private static final long serialVersionUID = 1L;

    private Integer uid;

    private BigDecimal money;

    private Date insertTime;

    private Date updateTime;

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public Date getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(Date insertTime) {
        this.insertTime = insertTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public UserMoney(Integer uid, BigDecimal money) {
        this.uid = uid;
        this.money = money;
    }

    public UserMoney() {
    }

    @Override
    public String toString() {
        return "UserMoney{" +
                "uid=" + uid +
                ", money=" + money +
                ", insertTime=" + insertTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
