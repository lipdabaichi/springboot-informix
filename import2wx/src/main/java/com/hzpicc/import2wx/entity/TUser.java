package com.hzpicc.import2wx.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

//t_user表的实体类
@Data
@Accessors(chain = true)
public class TUser {
    private String oto_binding_info_id;
    private String user_acnt_id;
    private String open_id;
    private String open_id_bak;
    private String identifytypecode;
    private String identifytype;
    private String identifynumber;
    private String obi_state;
    private Date bingding_time;
    private Date unbinding_time;
    private Date date_create;
    private Date date_modify;
    private Date date_delete;
    private String create_account_type;
    private String create_account_type_code;
    private String creator_account;
    private String creator_name;
    private String creator_group_acnt_type;
    private String creator_group_acnt_type_code;
    private String creator_group_account;
    private String creator_group_name;
    private String data_access_flag;
    private String uacnt_system;
    private String uacnt_system_code;
    private String user_name;
    private String ouc_code;
    private String mobile;
    private String obi_activity_type;
    private String obi_activity_type_code;


}
