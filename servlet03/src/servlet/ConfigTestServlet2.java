package servlet;

import jakarta.servlet.*;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * @Datetime: 2024/6/27下午1:28
 * @author: Camellia.xioahua
 */
public class ConfigTestServlet2 extends GenericServlet {

    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();
        //获取Servlet对象
        ServletConfig config = getServletConfig();
        //输出
        out.println("ServletConfig对象:"+config);
        //ServletConfig对象:org.apache.catalina.core.StandardWrapperFacade@21787084
    }
}
