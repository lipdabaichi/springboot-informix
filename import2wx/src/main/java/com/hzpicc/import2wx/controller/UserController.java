package com.hzpicc.import2wx.controller;

import com.hzpicc.import2wx.entity.TUser;
import com.hzpicc.import2wx.service.TUserService;
import com.hzpicc.import2wx.service.WeChatTask;
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


    @Autowired
    private WeChatTask weChatTask;
    @RequestMapping("transfer")
    public void transfer(){
        List<TUser> openIdList = weChatTask.getOpenIdList();
        weChatTask.transferAPI(goalToken,Appid,openIdList);
    }

    //获得省公司的token  [注意2小时失效.]
    public static String goalToken = "25_Xu5Qft30gPmaMY9-SvBXhPJvuecwD3FvaEPRaLMLqKR96B98BPnmgPXDbO0bJDnH4I8p0N9MABruHcgo9-x58Jg1mgAB80pHO2S7MO1QpwDv37a7KDrYeRFB9aoh1UvSzATbG0dhU2f2iefKXVMjCEASQH";
    //原来的公众号的appid
    public static final String Appid = "wxba8bb5f408dc780d";

}
