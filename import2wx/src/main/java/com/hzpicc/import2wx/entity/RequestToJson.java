package com.hzpicc.import2wx.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;
@Data
@Accessors(chain = true)
public class RequestToJson {
    private String from_appid;
    private List<?> openid_list;
}

