package servlet;

import jakarta.servlet.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

/**
 * ServletConfig
 *  1.ServletConfig是什么？
 *    - jakarta.servlet.ServletConfig
 * @Datetime: 2024/6/27上午11:02
 * @author: Camellia.xioahua
 */
public class ConfigTestServlet extends GenericServlet {
    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();
        // 获取ServletConfig对象
        ServletConfig config = this.getServletConfig();
        // 输出该对象
        out.println("ServletConfig对象是："+config);
        //ServletConfig对象是：org.apache.catalina.core.StandardWrapperFacade@3cdfe47

        //获取<servlet-name></servlet-name>
        String servletName = config.getServletName();
        out.println("<br>"+servletName);
        // 通过ServletConfig对象的两个方法，可以获取到web.xml文件中的初始化参数配置信息。
        // java.util.Enumeration<java.lang.String>	getInitParameterNames() 获取所有的初始化参数的name
        // java.lang.String	getInitParameter(java.lang.String name) 通过初始化参数的name获取value
        Enumeration<String> initParameterNames = config.getInitParameterNames();
        //遍历集合
        while (initParameterNames.hasMoreElements()){
            String initParameterName = initParameterNames.nextElement();
            String initParameter = config.getInitParameter(initParameterName);
            out.println("<br>"+initParameterName+" = "+initParameter);
        }
        //通过ServletConfig对象获取ServletContext对象。
        ServletContext application = config.getServletContext();
        out.println("<br>"+application);


        //GenericServlet源码，我们不必获取ServletConfig对象，因为GenericServlet有提供。
        Enumeration<String> initParameterNames1 = this.getInitParameterNames();
        while (initParameterNames1.hasMoreElements()){
            String initParameterName1 = initParameterNames1.nextElement();
            String initParameter1 = config.getInitParameter(initParameterName1);
            out.println("<br>"+initParameterName1+" = "+initParameter1);
        }
        ServletContext application1 = this.getServletContext();
        out.println("<br>"+application1);
    }
}
