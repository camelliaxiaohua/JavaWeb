package servlet;

import jakarta.servlet.GenericServlet;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * @Datetime: 2024/6/28下午1:52
 * @author: Camellia.xioahua
 */
public class GetServlet extends GenericServlet {
    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        // 响应一些内容到浏览器上。
        res.setContentType("text/html;charset=utf-8");
        PrintWriter out = res.getWriter();
        out.println("<!doctype html>");
        out.println("<html>");
        out.println("    <head>");
        out.println("        <title>from get servlet</title>");
        out.println("    </head>");
        out.println("    <body>");
        out.println("        <h1>from post servlet</h1>");
        out.println("    </body>");
        out.println("</html>");
    }
}
