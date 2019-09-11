package com.hzpicc.import2wx.utils;

import java.util.Properties;
/**
 * @author gede
 * @version date：2019年5月26日 下午7:45:27
 * @description ：
 */
public class GlobalConstants {
    public static Properties interfaceUrlProperties;

    /**
     * @Description: TODO
     * @param @param key
     * @param @return
     */
    public static String getInterfaceUrl(String key) {
        return (String) interfaceUrlProperties.get(key);
    }
}
