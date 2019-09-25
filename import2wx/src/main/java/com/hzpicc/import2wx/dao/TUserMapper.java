package com.hzpicc.import2wx.dao;


import com.hzpicc.import2wx.entity.TUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Mapper
public interface TUserMapper {
    //查询t_user表中数据行数
    Integer queryCount();

    //查询前100条  可用数据
    List<TUser> select100();

    List<TUser> select100items4openId();

    void updateOpenId(String new_open_id, String open_id,String err_msg);

    void updateOpenId1( String open_id,String err_msg);

    void updateObiState(String open_id);


}

