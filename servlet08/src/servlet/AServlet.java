package servlet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

/**
 * @Datetime: 2024/6/29下午2:48
 * @author: Camellia.xioahua
 */
public class AServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        //向request域绑定当前时间
        Date nowTime = new Date();
        //向request域中绑定数据
        request.setAttribute("sysTime", nowTime);
        //从request域中取出绑定数据。
        Object sysTime = request.getAttribute("sysTime");
        out.println("<h1>request域当中获取的系统时间= " + sysTime + "</h1>");

        /**
         * 转发一次请求
         * 不能自己new BServlet()，因为自己创建的不受tomcat管理。
         */
        //1. 获取请求转发器，把下一个资源的路径告诉给tomcat
        RequestDispatcher dispatcher = request.getRequestDispatcher("/b");
        //2.调用请求转发器RequestDispatcher的forward方法进行转发。
        dispatcher.forward(request, response);
        // 转发到一个html也可以，只要是web容器中的合法资源即可。
        request.getRequestDispatcher("/html/page.html").forward(request,response);
    }


}
