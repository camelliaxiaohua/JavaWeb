package servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * @Datetime: 2024/6/29下午2:48
 * @author: Camellia.xioahua
 */
public class BServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        //从request域中取出绑定数据。
        Object sysTime = request.getAttribute("sysTime");
        out.println("<h1>request域当中获取的系统时间= " + sysTime + "</h1>");
    }
}
