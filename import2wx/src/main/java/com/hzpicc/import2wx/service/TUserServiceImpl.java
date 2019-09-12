package com.hzpicc.import2wx.service;


import com.hzpicc.import2wx.dao.TUserMapper;

import com.hzpicc.import2wx.entity.TUser;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class TUserServiceImpl implements TUserService {

    @Autowired
    private TUserMapper tUserMapper;

    @Override
    public List<TUser> select100() {
        List<TUser> tUserList = tUserMapper.select100();

        return tUserList;
    }

    @Override
    public Integer count() {
        Integer num = tUserMapper.queryCount();
        return num;
    }

    @Override
    public List<TUser> select100items4openId() {
        List<TUser> tUserList = tUserMapper.select100items4openId();
        return tUserList;
    }
}
