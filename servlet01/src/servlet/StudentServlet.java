package servlet;

import jakarta.servlet.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.Locale;
import java.util.ResourceBundle;

public class StudentServlet implements Servlet {
    @Override
    public void init(ServletConfig servletConfig) throws ServletException {

    }

    @Override
    public ServletConfig getServletConfig() {
        return null;
    }

    @Override
    public void service(ServletRequest request, ServletResponse response) throws ServletException, IOException {
        //设置响应的类型
        response.setContentType("text/html");
        PrintWriter writer = response.getWriter();
        // 连接数据库
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        //绑定资源
        ResourceBundle resource = ResourceBundle.getBundle("jabc_zh_CN", Locale.CHINA);
        String driver = resource.getString("driver");
        String url = resource.getString("url");
        String name = resource.getString("username");
        String password = resource.getString("password");
        try {
            //1.注册数据库驱动
            Class.forName(driver);
            //2.连接数据库
            con = DriverManager.getConnection(url,name,password);
            //3.获取数据库操作对象
            String sql = "select * from t_student";
            ps = con.prepareStatement(sql);
            //执行SQL
            rs = ps.executeQuery();
            //处理查询结果集
            while (rs.next()){
                String name1 = rs.getString("name");
                String birth = rs.getString("birth");
                writer.print(name1+"\t"+birth+"<br>");
            }

        } catch (ClassNotFoundException e) {
           e.printStackTrace();
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            //6、释放资源
            if (rs != null) try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            if (ps != null) try {
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            if (con != null) try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String getServletInfo() {
        return "";
    }

    @Override
    public void destroy() {

    }
}
