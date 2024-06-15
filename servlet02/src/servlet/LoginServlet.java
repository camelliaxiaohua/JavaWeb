package servlet;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;

import java.io.IOException;

public class LoginServlet extends GenericServlet {
    @Override
    public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {
        System.out.println("正在处理用户登入请求，请稍后...");
        //在子类中使用config对象.
        ServletConfig config = this.getServletConfig();
        System.out.println("成功在service中获取config对象"+config);
    }

    /**
     * 有个问题：我们的业务逻辑可能会重新写init方法，会导致父类的init方法不执行，config为空。
     *
     */
    @Override
    public void init(){
        System.out.println("LoginServlet's init() method execute");
    }
}
