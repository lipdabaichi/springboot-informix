package com.hzpicc.import2wx.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class ResponseToJson {
    private String errcode;
    private String errmsg;
    private List<TransferNode> result_list;

}
