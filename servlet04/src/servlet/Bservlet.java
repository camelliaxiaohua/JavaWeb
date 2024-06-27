package servlet;

import jakarta.servlet.*;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * @Datetime: 2024/6/27下午3:52
 * @author: Camellia.xioahua
 */
public class Bservlet extends GenericServlet {
    @Override
    public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {
        servletResponse.setContentType("text/html");
        PrintWriter out = servletResponse.getWriter();
        //获取ServletContext对象
        ServletContext application = this.getServletContext();
        out.println("ServletContext对象:"+application);
        //ServletContext对象:org.apache.catalina.core.ApplicationContextFacade@649c9d1d

        //取数据
        Object userObj = application.getAttribute("userObj");
        out.print("<br>"+userObj);
    }
}
