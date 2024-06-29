package servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.Map;
import java.util.Set;

/**
 * @Datetime: 2024/6/29上午9:17
 * @author: Camellia.xioahua
 */
public class RequestTestServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();
        //获取客户端的IP地址
        String remoteAddr = req.getRemoteAddr();
        out.println(remoteAddr);
        System.out.println(remoteAddr);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse respone) throws ServletException, IOException {
        respone.setContentType("text/html");
        PrintWriter out = respone.getWriter();
        //面向接口编程:HttpServletRequest接口
        //获取前端提交数据
        //1. 通过getParameterMap()获取参数Map集合
        Map<String,String[]> parameterMap = request.getParameterMap();
        //遍历Map集合（获取Map集合中所有的Key遍历）
        Set<String> keys = parameterMap.keySet();
        for (String key : keys) {
            //通过key获取value
            String[] values = parameterMap.get(key);
            out.println(key + ": " );
            for (String value : values) {
                out.println(value+" ");
            }
            out.println("<br>");
        }

        //2. 通过getParameterNames()获取Map集合所有key
        Enumeration<String> keys1 = request.getParameterNames();
        while (keys1.hasMoreElements()) {
            String key = keys1.nextElement();
            out.println(key + ": " );
            // 通过getParameterValues(key)获取所有values
            //request.getParameter("key")是获取数组的第一个元素，因为一般只有一个元素。
            String[] values = request.getParameterValues(key);
            for (String value : values) {
                out.println(value+" ");
            }
            out.println("<br>");
        }

    }
}
