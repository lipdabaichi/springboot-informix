package com.hzpicc.import2wx;

import com.hzpicc.import2wx.config.wechatInit.InterfaceUrlInti;
import com.hzpicc.import2wx.dao.TUserMapper;
import com.hzpicc.import2wx.entity.RequestToJson;
import com.hzpicc.import2wx.entity.TUser;
import com.hzpicc.import2wx.utils.GlobalConstants;
import com.hzpicc.import2wx.utils.HttpClientUtils;
import lombok.Data;
import net.sf.json.JSONObject;
import org.jboss.logging.LogMessage;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.servlet.ServletException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.hzpicc.import2wx.utils.HttpUtils.buildUrl;

@RunWith(SpringRunner.class)
@SpringBootTest(classes =  Import2wxApplication.class)
public class Import2wxApplicationTests {
    String goalToken = "25_Ze4gt3d_oQk2EXuPj9K3UkGYum2tRifFAXwd3rCPCyZuXMku5MNurvsE1_RE4qdBEIrrBY8vlwlqIdPrfHb_G233juMEokwbgpkc-Qmfpy9DZXEYvLcltAI7-dXMKULoK2-652ymsHpASqFzTUTfCGACDC";

    @Autowired
    private TUserMapper tUserMapper;

    @Before
    public void init() throws ServletException {
        InterfaceUrlInti.init();
    }

    /**
     *
     * @throws Exception
     * @Test success
     */
	@Test
    public void getToken_getTicket() throws Exception {
        Map<String, String> params = new HashMap<String, String>();
        params.put("grant_type", "client_credential");
        params.put("appid", GlobalConstants.getInterfaceUrl("appid"));
        params.put("secret", GlobalConstants.getInterfaceUrl("AppSecret"));

//原版
//        String jstoken = HttpUtils.sendGet(
//                GlobalConstants.getInterfaceUrl("tokenUrl"), params);
        //以下三句配置了代理
        String url = buildUrl(GlobalConstants.getInterfaceUrl("tokenUrl"), params);
        System.out.println(url);
        String jstoken = HttpClientUtils.doGet(url);


//        System.out.println(">>>>>>>我来了马飞!!!!!!>>>>>>>>>>>>>>>>token:"+jstoken);
        String access_token = JSONObject.fromObject(jstoken).getString(
                "access_token"); // 获取到token并赋值保存
        GlobalConstants.interfaceUrlProperties.put("access_token", access_token);
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+"token为=============================="+access_token);
    }

    /**
     * 获得openid的list
     * @return
     * @Test success
     */
    @Test
    public void getOpenIdList(){
        List<TUser> tUsers = tUserMapper.select100items4openId();
        System.out.println(tUsers);
    }

    /**
     * openid的转换
     * @param goalToken  目标公众号的token
     * @param fromAppid  原来公众号的appid
     * @param openIdList  原来公众号的openid List   一次最多100个
     */

//    @Test
//    public void transferAPI(String goalToken , String fromAppid , List<String> openIdList ) {
//        String url = "http://api.weixin.qq.com/cgi-bin/changeopenid?access_token=" + goalToken;
//
////        HttpUtils.sendPost()
//        //发送post请求给腾讯API
//        RequestToJson req = new RequestToJson();
//        req.setFrom_appid(fromAppid);
//        req.setOldOpenIds(openIdList);
//        JSONObject jsonData = JSONObject.fromObject(req);
//        System.out.println(jsonData);
//
//
//        String result = HttpClientUtils.postJson(url, jsonData.toString());
//        System.out.println("迁移的结果:"+result);
//        //把腾讯API获得到的值 String 转化为 Json 再转换成Object
//
//        //写入数据库
//
//        String oriOpenid = JSONObject.fromObject(result).getString("ori_openid");
//        String newOpenid = JSONObject.fromObject(result).getString("new_openid");
//        System.out.println(oriOpenid);
//        System.out.println(newOpenid);
//    }

}
