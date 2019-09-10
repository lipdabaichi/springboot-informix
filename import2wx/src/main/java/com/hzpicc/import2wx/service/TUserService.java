package com.hzpicc.import2wx.service;

import com.hzpicc.import2wx.entity.TUser;
import org.springframework.stereotype.Service;

import java.util.List;

public interface TUserService {
    List<TUser> select100();

    Integer count();
}
