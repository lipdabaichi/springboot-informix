package com.hzpicc.import2wx.controller;

import com.hzpicc.import2wx.entity.TUser;
import com.hzpicc.import2wx.service.TUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private TUserService tUserService;

    @RequestMapping("/select100")
    @ResponseBody
    public List<TUser>  select100(){
        List<TUser> tUsers = tUserService.select100();
        return tUsers;
    }
    @RequestMapping("/count")
    @ResponseBody
    public Integer  count(){
        Integer num = tUserService.count();
        return num;
    }


}
