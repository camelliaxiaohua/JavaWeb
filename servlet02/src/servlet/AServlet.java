package servlet;

import jakarta.servlet.*;

import java.io.IOException;

public class AServlet implements Servlet {

    //无参数构造方法
    public AServlet() {
        System.out.println("AServlet无参数构造方法开始执行了。");
    }

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        System.out.println("Aservlet's init method execute!");
    }

    @Override
    public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {
        System.out.println("Aservlet's service method execute");
    }

    @Override
    public void destroy() {
        System.out.println("Aservlet's destroy method execute");
    }

    @Override
    public String getServletInfo() {
        return "";
    }

    @Override
    public ServletConfig getServletConfig() {
        return null;
    }

}
