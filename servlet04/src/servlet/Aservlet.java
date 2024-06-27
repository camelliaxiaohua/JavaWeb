package servlet;

import jakarta.servlet.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

/**
 * @Datetime: 2024/6/27下午3:47
 * @author: Camellia.xioahua
 */
public class Aservlet extends GenericServlet {
    @Override
    public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {
        servletResponse.setContentType("text/html");
        PrintWriter out = servletResponse.getWriter();
        //获取ServletContext对象
        ServletContext application = this.getServletContext();
        out.println("ServletContext对象:"+application);
        //ServletContext对象:org.apache.catalina.core.ApplicationContextFacade@649c9d1d

        // 获取上下文的初始化参数:getInitParameterNames()、getInitParameter(String xxx)。
        Enumeration<String> attributeNames = application.getInitParameterNames();
        while (attributeNames.hasMoreElements()) {
            String attributeName = attributeNames.nextElement();
            String attributeValue = application.getInitParameter(attributeName);
            out.println("<br>"+attributeName+":"+attributeValue);
        }

        //获取context path（动态的获取应用上下文的根）
        String contextPath = application.getContextPath();
        out.println("<br>contextPath:"+contextPath);

        //获取文件绝对路径
        //realPath:E:\Learn\JavaWeb\out\artifacts\servlet04_war_exploded\index.html
        String realPath = application.getRealPath("/index.html"); //index.html也可以，都表示从根路径开始。
        out.println("<br>realPath:"+realPath);
        //realPath:E:\Learn\JavaWeb\out\artifacts\servlet04_war_exploded\html\common.html
        realPath = application.getRealPath("/html/common.html");
        out.println("<br>realPath:"+realPath);

        //log
        //这个日志会自动记录到CATALINA_HOME/logs
        application.log("重剑无锋，大巧无工。");

        //向ServletContext应用域当中存数据
        User user = new User("XIAOHUA",23);
        application.setAttribute("userObj",user);
        //取数据,其它Servlet也可以取。
        Object userObj = application.getAttribute("userObj");
        out.print("<br>"+userObj);
        //删除
        application.removeAttribute("userObj");
    }
}
