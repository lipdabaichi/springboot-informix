package com.hzpicc.import2wx;

import com.hzpicc.import2wx.config.wechatInit.InterfaceUrlInti;
import com.hzpicc.import2wx.dao.TUserMapper;
import com.hzpicc.import2wx.entity.RequestToJson;
import com.hzpicc.import2wx.entity.TUser;
import com.hzpicc.import2wx.utils.GlobalConstants;
import com.hzpicc.import2wx.utils.HttpClientUtils;
import com.hzpicc.import2wx.utils.HttpUtils;
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

    @Autowired
    private TUserMapper tUserMapper;

    @Before
    public void init() throws ServletException {
        InterfaceUrlInti.init();
    }

    /**
     * 获取当前市公司的token
     * @throws Exception
     * @Test success
     */
	@Test
    public void getToken_getTicket() throws Exception {
        Map<String, String> params = new HashMap<String, String>();
        params.put("grant_type", "client_credential");
        params.put("appid", GlobalConstants.getInterfaceUrl("appid"));
        params.put("secret", GlobalConstants.getInterfaceUrl("AppSecret"));

        //配置代理
        String url = buildUrl(GlobalConstants.getInterfaceUrl("tokenUrl"), params);
        System.out.println(url);
        String jstoken = HttpClientUtils.doGet(url);

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
     * 通过get请求访问4400次该网页
     */
    @Test
    public void run4400() {
        String baseUrl = "http://localhost:8080/user/transfer";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Boolean result = false;
        int count = 0;
        Map<String, String> s = new HashMap<>();
        while (!result) {
            try {
                Thread.sleep(2 * 1000); //设置暂停的时间 5 秒
                count++;
                System.out.println(sdf.format(new Date()) + "--循环执行第" + count + "次");
//                String s = HttpClientUtils.doGet(baseUrl);    //不能用.主要因为是设置了代理
                String s2 = HttpUtils.sendGet(baseUrl, s);
                System.out.println(s2);
                if (count == 3163) {
                    result = true;
                    break;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
