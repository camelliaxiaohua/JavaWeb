---
title: 在集成开发环境当中开发Servlet程序
date: 2024-06-15 15:20:14
tags: 
categories: 
- Java Web
- Servlet


---


# Servlet：在Idea开发环境当中开发Servlet程序

## 一、在集成开发环境当中开发Servlet程序



- 集成开发工具很多，其中目前使用比较多的是：

    - IntelliJ IDEA、Eclipse

### 1.1 使用IDEA集成开发工具开发Servlet

1. 第一步：New Project（先创建一个Empty Project【空工程】，然后在空工程下新建Module【模块】。（按习惯来））

2. 第二步：新建模块（File --> new --> Module...）
- 这里新建的是一个普通的JavaSE模块（先不要新建Java Enterprise模块）
- 这个Module自动会被放在New Project创建的空工程下面。

3. 第三步：让Module变成JavaEE的模块。（让Module变成webapp的模块。符合webapp规范。符合Servlet规范的Module）
- 在help中搜索：Add Framework Support...（添加框架支持）
- 在弹出的窗口中，选择Web Application（选择的是webapp的支持）
- 选择了这个webapp的支持之后，IDEA会自动给你生成一个符合Servlet规范的webpp目录结构。
- **重点，需要注意的：在IDEA工具中根据Web Application模板生成的目录中有一个web目录，这个目录就代表webapp的根**

4. 第四步（非必须）：根据Web Application生成的资源中有index.jsp文件，可以删除。
5. 第五步：编写Servlet（StudentServlet）
- `class StudentServlet implements Servlet`
- 将CATALINA_HOME/lib/servlet-api.jar和jsp-api.jar添加到classpath当中（这里的classpath说的是IDEA的classpath）
    - File --> Project Structrue --> Modules --> + 加号 --> Add JARS....
- 实现jakarta.servlet.Servlet接口中的5个方法。

6. 第六步：在Servlet当中的service方法中编写业务代码（我在这里连接数据库。）

7. 第七步：**在WEB-INF目录下新建了一个子目录：lib（这个目录名可不能随意，必须是全部小写的lib），并且将连接数据库的驱动jar包放到lib目录下。**
8. 第八步：在web.xml文件中完成StudentServlet类的注册。（请求路径和Servlet之间对应起来）

```xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns="http://xmlns.jcp.org/xml/ns/javaee"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee http://xmlns.jcp.org/xml/ns/javaee/web-app_4_0.xsd"
         version="4.0">
    
    <servlet>
        <servlet-name>student</servlet-name>
        <servlet-class>servlet.StudentServlet</servlet-class>
    </servlet>
    <servlet-mapping>
        <servlet-name>student</servlet-name>
        <url-pattern>/student</url-pattern>
    </servlet-mapping>
</web-app>
```
9. 第九步：给一个html页面，在HTML页面中编写一个超链接，用户点击这个超链接，发送请求，Tomcat执行后台的StudentServlet。

- student.html

- 这个文件不能放到WEB-INF目录里面，只能放到WEB-INF目录外面。

- student.html文件的内容

- ```html
    <!DOCTYPE html>
    <html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>student page</title>
    </head>
    <body>
        <!--这里的项目名是 /xmm ，无法动态获取，先写死-->
        <a href="/xmm/servlet/student">student list</a>
    </body>
    </html>
    ```



10. 第十步：让IDEA工具去关联Tomcat服务器。关联的过程当中将webapp部署到Tomcat服务器当中。

- IDEA工具右上角，绿色小锤子右边有一个：Add Configuration
- 左上角加号，点击Tomcat Server --> local
- 在弹出的界面中设置服务器Server的参数（基本上不用动）
- 在当前窗口中有一个Deployment（点击这个用来部署webapp），继续点击加号，部署即可。
- 修改 Application context为：/camellia

11. 第十一步：启动Tomcat服务器

- 在右上角有绿色的箭头，或者绿色的小虫子，点击这个绿色的小虫子，可以采用debug的模式启动Tomcat服务器。
- 我们开发中建议适用debug模式启动Tomcat

- 第十二步：打开浏览器，在浏览器地址栏上输入：http://localhost:8080/camellia/index.html