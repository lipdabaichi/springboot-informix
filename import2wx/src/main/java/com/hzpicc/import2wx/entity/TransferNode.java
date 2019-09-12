package com.hzpicc.import2wx.entity;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class TransferNode {
    private String ori_openid;
    private String new_openid;
    private String err_msg;

    public String getOri_openid() {
        return ori_openid;
    }

    public void setOri_openid(String ori_openid) {
        this.ori_openid = ori_openid;
    }

    public String getNew_openid() {
        return new_openid;
    }

    public void setNew_openid(String new_openid) {
        this.new_openid = new_openid;
    }

    public String getErr_msg() {
        return err_msg;
    }

    public void setErr_msg(String err_msg) {
        this.err_msg = err_msg;
    }
}
