package servlet;

import jakarta.servlet.GenericServlet;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

/**
 * @Datetime: 2024/6/27下午2:57
 * @author: Camellia.xioahua
 */
public class ConfigTestServlet3 extends GenericServlet {

    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();
        out.println("ServletConfig对象："+this.getServletConfig());
        out.println("<br> ServletConfig对象名称："+this.getServletName());
        Enumeration<String> initParameterNames = this.getInitParameterNames();
        while (initParameterNames.hasMoreElements()){
            String initParameterName = initParameterNames.nextElement();
            String initParameter = this.getInitParameter(initParameterName);
            out.println("<br>"+initParameterName+" = "+initParameter);
        }
    }
}
