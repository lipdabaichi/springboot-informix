package com.hzpicc.import2wx;

import com.hzpicc.import2wx.config.wechatInit.InterfaceUrlInti;
import com.hzpicc.import2wx.utils.GlobalConstants;
import com.hzpicc.import2wx.utils.HttpClientUtils;
import com.hzpicc.import2wx.utils.HttpUtils;
import com.hzpicc.import2wx.utils.WeChatTask;
import net.sf.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.hzpicc.import2wx.utils.HttpUtils.buildUrl;

@RunWith(SpringRunner.class)
@SpringBootTest(classes =  Import2wxApplication.class)
public class Import2wxApplicationTests {
    @Before
    public void init() throws ServletException {
        InterfaceUrlInti.init();
    }

	@Test
    public void getToken_getTicket() throws Exception {
        Map<String, String> params = new HashMap<String, String>();
        params.put("grant_type", "client_credential");
        params.put("appid", GlobalConstants.getInterfaceUrl("appid"));
        params.put("secret", GlobalConstants.getInterfaceUrl("AppSecret"));

//原版
//        String jstoken = HttpUtils.sendGet(
//                GlobalConstants.getInterfaceUrl("tokenUrl"), params);
        String url = buildUrl(GlobalConstants.getInterfaceUrl("tokenUrl"), params);
        System.out.println(url);
        String jstoken = HttpClientUtils.doGet(url);


//        System.out.println(">>>>>>>我来了马飞!!!!!!>>>>>>>>>>>>>>>>token:"+jstoken);
        String access_token = JSONObject.fromObject(jstoken).getString(
                "access_token"); // 获取到token并赋值保存
        GlobalConstants.interfaceUrlProperties.put("access_token", access_token);
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+"token为=============================="+access_token);
    }


}
