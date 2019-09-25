package com.hzpicc.import2wx.controller;

import com.hzpicc.import2wx.entity.TUser;
import com.hzpicc.import2wx.service.TUserService;
import com.hzpicc.import2wx.service.WeChatTask;
import org.apache.commons.lang.StringUtils;
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
    @ResponseBody
    public String transfer(){
        List<TUser> openIdList = weChatTask.getOpenIdList();
        weChatTask.transferAPI(goalToken,Appid,openIdList);
        return "transfer ok";
    }

    @RequestMapping("reverse")
    @ResponseBody
    public String reverseToken(){
        goalToken = weChatTask.getProvinceToken();
        System.out.println("当前省公司token为" + goalToken);
        return "reverseToken ok";
    }

    //获得省公司的token  [注意2小时失效.]
    public static String goalToken = "25_NIEvy-d2P7rH3iDsiFYRgrpmgZNuKTob9gDLfr-rQx21SQUckTSAlXVchmgKOqdaTHFmyydIBg6OstO6FoZGthDhdZgd2DUHnXloP9uLvbejVkS-44lFG0CPwqil2adEmXBccGsTNQiS2AB-FUVeCHARGH";
    //原来的公众号的appid
    public static final String Appid = "wxba8bb5f408dc780d";

}
