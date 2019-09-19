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
import net.sf.json.JSONException;
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
    public String getProvinceToken(){
        String baseUrl = "http://35.1.36.100:8080/zjweixin/ZjpiccServForYR";
        String postBody = "{\n" +
                "    \"head\":{\n" +
                "        \"prono\":\"YunRuan201706\",\n" +
                "        \"seqno\":\"1234567890\",\n" +
                "        \"method\":\"getAccessToken\"\n" +
                "    },\n" +
                "    \"body\":{\n" +
                "\n" +
                "    }\n" +
                "}\n";



//
//        返回报文样式：
//        {
//            "retcode":"0",
//                "retmsg":"成功",
//                "seqno":"1234567890",
//                "accesstoken":"whZTdowCChxCZ7NXbsj2LRmtjw5bhm5FTnHlx-xZwup01M6jiTAAsJaI4wJFFuVSkHg4KoK_tolxC1F9atpKGlaaYXBfDBSZhNlMlnbGOoT5k8rj9RYh7Qn2LruU6X_TTVSfCCAJLI"
//        }

        return result;
    }
    /**
     * openid的转换
     * @param goalToken  目标公众号的token
     * @param fromAppid  原来公众号的appid
     * @param openIdList  原来公众号的openid List   一次最多100个
     */
    @Transactional
    public void transferAPI(String goalToken , String fromAppid , List<?> openIdList ) {
        String url = "http://api.weixin.qq.com/cgi-bin/changeopenid?access_token=" + goalToken;

        //1.发送post请求给腾讯API
        RequestToJson req = new RequestToJson();
        req.setFrom_appid(fromAppid);
        req.setOpenid_list(openIdList);
        JSONObject jsonData = JSONObject.fromObject(req);
        System.out.println("request_post的请求:"+jsonData);

        String result = HttpClientUtils.postJson(url, jsonData.toString());
        //以上是正确的
        System.out.println("腾讯API返回值:"+result);

        //2.把腾讯API获得到的值 String 转化为 Json 再转换成Object
        JSONObject apiJson = JSONObject.fromObject(result);
        //判断token失效,并处理
        JSONArray array ;
        try{
            array = JSONArray.fromObject(apiJson.getString("result_list"));
            //        System.out.println(array);
        }catch (JSONException e){
            System.out.println(apiJson.getString("errmsg"));
            System.out.println("目测是省公司token失效");
            return;
        }

        List<?> list = JSONArray.toList(array, new TransferNode(),new JsonConfig());
//        System.out.println("当前list::::"+list);
        Iterator it = list.iterator();
        while(it.hasNext()){
            TransferNode transferNode =(TransferNode)it.next();
            String new_openid = transferNode.getNew_openid();
            String ori_openid = transferNode.getOri_openid();
            String err_msg = transferNode.getErr_msg();
            System.out.println("当前行new_open_id是"+new_openid+">>>>>>>old_open_id是"+ori_openid);
            //3.写入数据库
            if (new_openid!= null) {
                tUserMapper.updateOpenId(new_openid,ori_openid,err_msg);   //暂时不启用
                System.out.println("此处是数据库修改new_open_id数据>>>>" + "new_openid:" + new_openid + ">>ori_openid<<<<" + ori_openid+">>>>err_msg"+err_msg);
            }else{
//                    tUserMapper.updateObiState(ori_openid);      后果很严重,慎重!!!!!!!!!!@@@@@@@@@@@@@@@@@@@@@@
                System.out.println("@@!!!!!!此处是更改标示位为N数据>>>>" + "new_openid:" + new_openid + ">>ori_openid<<<<" + ori_openid+">>>>err_msg"+err_msg);
            }
        }
        System.out.println("结束了");



    }


}