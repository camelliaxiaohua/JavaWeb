## 14. 关于一个web站点的欢迎页面

### 14.1 什么是一个web站点的欢迎页面？

1. 对于一个webapp来说，我们是可以设置它的欢迎页面的。

2. 设置了欢迎页面之后，当你访问这个webapp的时候，或者访问这个web站点的时候，没有指定任何“资源路径”，这个时候会默认访问你的欢迎页面。

3. 我们一般的访问方式是：
    - http://localhost:8080/servlet06/login.html 这种方式是指定了要访问的就是login.html资源。

4. 如果我们访问的方式是：
    - http://localhost:8080/servlet06 如果我们访问的就是这个站点，没有指定具体的资源路径。它默认会访问谁呢？
    - 默认会访问你设置的欢迎页面。

### 14.2 怎么设置欢迎页面呢？

1. 第一步：我在IDEA工具的web目录下新建了一个文件login.html

2. 第二步：在web.xml文件中进行了以下的配置

   ```html
   <welcome-file-list>
        <welcome-file>login.html</welcome-file>
   </welcome-file-list>
   ```

    - 注意：设置欢迎页面的时候，这个路径不需要以“/”开始。并且这个路径默认是从webapp的根下开始查找。

3. 第三步：启动服务器，浏览器地址栏输入地址
    - http://localhost:8080/servlet07

#### 14.2.1 如何将webapp目录下的html设置为欢迎页？

- 在webapp根下新建html

- 在html下新建page目录

- 在page目录下新建page.html页面

- 在web.xml文件中应该这样配置

    - ```html
    <welcome-file-list>
        <welcome-file>html/page/page.html</welcome-file>
    </welcome-file-list>
    ```

      > [!NOTE]
      >
      > 注意：路径不需要以“/”开始，并且路径默认从webapp的根下开始找。

#### 14.2.2 **一个webapp是可以设置多个欢迎页面的**

```xml
<welcome-file-list>
   <welcome-file>html/page/page.html</welcome-file>
   <welcome-file>login.html</welcome-file>
</welcome-file-list>
```

> 注意 ：越靠上的优先级越高。找不到的继续向下找。

1. 当我的文件名设置为index.html的时候，不需要在web.xml文件中进行配置欢迎页面。这是为什么？

- 因为Tomcat服务器已经提前配置好了。

2. 实际上配置欢迎页面有两个地方可以配置：

    - 一个是在webapp内部的web.xml文件中。（在这个地方配置的属于局部配置）

    - 一个是在CATALINA_HOME/conf/web.xml文件中进行配置。（在这个地方配置的属于全局配置）

       ```xml
        <welcome-file-list>
            <welcome-file>index.html</welcome-file>
            <welcome-file>index.htm</welcome-file>
            <welcome-file>index.jsp</welcome-file>
        </welcome-file-list>
       ```

        - Tomcat服务器的全局欢迎页面是：index.html index.htm index.jsp。如果你一个web站点没有设置局部的欢迎页面，Tomcat服务器就会以index.html index.htm index.jsp作为一个web站点的欢迎页面。

      > 注意原则：局部优先原则。（就近原则）

#### 14.2.3 欢迎页可以是一个Servlet吗？

- 当然可以。欢迎页就是一个资源，既然是一个资源，那么可以是静态资源，也可以是动态资源。静态资源如：index.html、welcome.html等；动态资源如：Servlet类。


1. 第一步：写一个Servlet

   ```java
      public class WelcomeServlet extends HttpServlet {
          @Override
          protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
              resp.setContentType("text/html");
              PrintWriter out = resp.getWriter();
              out.println("<h1>Welcome to Servlet</h1>");
          }
      }
   ```



2. 第二步：在web.xml文件中配置servlet

   ```xml
      
          <servlet>
              <servlet-name>welcomeServlet</servlet-name>
              <servlet-class>servlet.WelcomeServlet</servlet-class>
          </servlet>
          <servlet-mapping>
              <servlet-name>welcomeServlet</servlet-name>
              <url-pattern>/welcome</url-pattern>
          </servlet-mapping>
      
   ```

3. 第三步：在web.xml文件中配置欢迎页

   ```xml
         <!--    注意：欢迎页面路径不要以/开始-->
          <welcome-file-list>
              <welcome-file>welcome</welcome-file>
              <welcome-file>html/page/page.html</welcome-file>
          </welcome-file-list>
      
   ```