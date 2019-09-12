package com.hzpicc.import2wx.service;

import java.text.SimpleDateFormat;
import java.util.*;

import com.hzpicc.import2wx.dao.TUserMapper;
import com.hzpicc.import2wx.entity.RequestToJson;
import com.hzpicc.import2wx.entity.ResponseToJson;
import com.hzpicc.import2wx.entity.TUser;
import com.hzpicc.import2wx.entity.TransferNode;
import com.hzpicc.import2wx.utils.GlobalConstants;
import com.hzpicc.import2wx.utils.HttpClientUtils;
import com.hzpicc.import2wx.utils.HttpUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

import static com.hzpicc.import2wx.utils.HttpUtils.buildUrl;


@Service
public class WeChatTask {

    /**
     * 得到微信的token
     * @throws Exception
     */
    public void getToken_getTicket() throws Exception {
        Map<String, String> params = new HashMap<String, String>();
        params.put("grant_type", "client_credential");
        params.put("appid", GlobalConstants.getInterfaceUrl("appid"));
        params.put("secret", GlobalConstants.getInterfaceUrl("AppSecret"));
        //没加代理(会出错)
//        String jstoken = HttpUtils.sendGet(
//                GlobalConstants.getInterfaceUrl("tokenUrl"), params);
        //加了代理
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
     */
    @Autowired
    private TUserMapper tUserMapper;
    public  List<TUser> getOpenIdList(){
        List<TUser> tUsers = tUserMapper.select100items4openId();
        return tUsers;
    }
    /**
     * openid的转换
     * @param goalToken  目标公众号的token
     * @param fromAppid  原来公众号的appid
     * @param openIdList  原来公众号的openid List   一次最多100个
     */
    @Transactional
    public void transferAPI(String goalToken , String fromAppid , List<?> openIdList ) {
//        String url = "http://api.weixin.qq.com/cgi-bin/changeopenid?access_token=" + goalToken;
//
//        //1.发送post请求给腾讯API
//        RequestToJson req = new RequestToJson();
//        req.setFrom_appid(fromAppid);
//        req.setOpenid_list(openIdList);
//        JSONObject jsonData = JSONObject.fromObject(req);
//        System.out.println("request_post的请求:"+jsonData);
//
//        String result = HttpClientUtils.postJson(url, jsonData.toString());
        //以上是正确的

        String result = "{\n" +
                "    \"errcode\": 0,\n" +
                "    \"errmsg\": \"ok\",\n" +
                "    \"result_list\": [\n" +
                "        {\n" +
                "            \"ori_openid\": \"oEmYbwN-n24jxvk4Sox81qedINkQ\",\n" +
                "            \"new_openid\": \"o2FwqwI9xCsVadFah_HtpPfaR-X4\",\n" +
                "            \"err_msg\": \"ok\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"ori_openid\": \"oEmYbwN-n24jxvk4Sox81qedINk1\",\n" +
                "            \"new_openid\": \"o2FwqwI9xCsVadFah_HtpPfaR-X5\",\n" +
                "            \"err_msg\": \"ok\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"ori_openid\": \"oEmYbwH9uVd4RKJk7ZZg6SzL6tTo\",\n" +
                "            \"err_msg\": \"ori_openid error\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"ori_openid\": \"oEmYbwH9uVd4RKJk7ZZg6SzL6tT3\",\n" +
                "            \"err_msg\": \"ori_openid error\"\n" +
                "        }\n" +
                "    ]\n" +
                "}";
        System.out.println("腾讯API返回值:"+result);
        //2.把腾讯API获得到的值 String 转化为 Json 再转换成Object
        JSONObject apiJson = JSONObject.fromObject(result);
        JSONArray array = JSONArray.fromObject(apiJson.getString("result_list"));
        System.out.println(array);

        List<?> list = JSONArray.toList(array, new TransferNode(),new JsonConfig());
//        System.out.println("当前list::::"+list);


        Iterator it = list.iterator();
        while(it.hasNext()){
            TransferNode transferNode =(TransferNode)it.next();
            String new_openid = transferNode.getNew_openid().toString();
            String ori_openid = transferNode.getOri_openid().toString();
            if (new_openid!= null) {
//                    tUserMapper.updateOpenId(new_openid,ori_openid);   //暂时不启用
                System.out.println("此处是修改2数据>>>>" + "new_openid:" + new_openid + ">>ori_openid" + ori_openid);
            }else{
//                    tUserMapper.updateObiState(ori_openid);
                System.out.println("此处是更改标示位为N数据>>>>" + "new_openid:" + new_openid + ">>ori_openid" + ori_openid);
            }
        }
        System.out.println("结束了<<<<<<<<<<<<<<<<<<<<<<<<<<");
        //3.写入数据库

//        String oriOpenid = JSONObject.fromObject(result).getString("ori_openid");
//        String newOpenid = JSONObject.fromObject(result).getString("new_openid");
//        System.out.println(oriOpenid);
//        System.out.println(newOpenid);
    }


}