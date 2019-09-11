package com.hzpicc.import2wx.config.wechatInit;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
/**
 * @author gede
 * @version date：2019年5月26日 下午7:42:14
 * @description ：
 */
public class InterfaceUrlIntiServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    public void init(ServletConfig config) throws ServletException {
        InterfaceUrlInti.init();
    }
}