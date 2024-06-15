package servlet;

import jakarta.servlet.*;

import java.io.IOException;

/**
 * 写一个标准通用的servlet。<br>
 * 以后所有的Servlet类都不要直接实现Servlet接口，继承GenericServlet即可<br>
 * GenericServlet就是一个适配器。<br>
 */
public abstract class GenericServlet implements Servlet {

    private ServletConfig config;

    /**
     * init方法中的servletConfig对象是TomCat创建好的。<br>
     * servletConfig对象属于局部变量，所以要改造为在service方法上使用。<br>
     * @param servletConfig
     * @throws ServletException
     */
    @Override
    final public void init(ServletConfig servletConfig) throws ServletException {
        this.config = servletConfig;
        this.init();
    }

    /**
     * 为防止子类重写init(ServletConfig servletConfig)方法，导致init(ServletConfig servletConfig)方法不执行，config为空，将其用final修饰。<br>
     * 但是，有时我们子类的有业务确实要重写init，此时我们可以写一个init的重载方法，在init(ServletConfig servletConfig)调用这个重载方法。<br>
     */
    public void init(){

    }

    @Override
    public ServletConfig getServletConfig() {
        return config;
    }

    /**
     * 抽象方法，这个方法最常用，所以要求子类必须实现service方法。
     * @param servletRequest
     * @param servletResponse
     * @throws ServletException
     * @throws IOException
     */
    @Override
    public abstract void service(ServletRequest servletRequest, ServletResponse servletResponse)
            throws ServletException, IOException;

    @Override
    public String getServletInfo() {
        return "";
    }

    @Override
    public void destroy() {

    }
}
