---
title: GenericServlet
date: 2024-06-15 19:02:26
tags: 
categories: 
- Java Web
- Servlet



---


# Servlet：适配器思想

## 一、GenericServlet

### 1.1 为什么要使用适配器？

- 我们编写一个Servlet类直接实现Servlet接口有什么缺点？

    - 我们只需要service方法，其他方法大部分情况下是不需要使用的。代码很丑陋。

- 适配器设计模式Adapter

    - 手机直接插到220V的电压上，手机直接就报废了。怎么办？可以找一个充电器。这个充电器就是一个适配器。手机连接适配器。适配器连接220V的电压。这样问题就解决了。

- 编写一个GenericServlet类，这个类是一个抽象类，其中有一个抽象方法service。

    - GenericServlet实现Servlet接口。
    - GenericServlet是一个适配器。
    - 以后编写的所有Servlet类继承GenericServlet，重写service方法即可。

- 思考：GenericServlet类是否需要改造一下？怎么改造？更利于子类程序的编写？

    - 思考第一个问题：我提供了一个GenericServlet之后，init方法还会执行吗？

        - 还会执行。会执行GenericServlet类中的init方法。

    - 思考第二个问题：init方法是谁调用的？

        - Tomcat服务器调用的。

    - 思考第三个问题：init方法中的ServletConfig对象是谁创建的？是谁传过来的？

        - 都是Tomcat干的。
        - Tomcat服务器先创建了ServletConfig对象，然后调用init方法，将ServletConfig对象传给了init方法。

    - 思考一下Tomcat服务器伪代码：

        - ```java
      public class Tomcat {
          public static void main(String[] args){
              // .....
              // Tomcat服务器伪代码
              // 创建LoginServlet对象（通过反射机制，调用无参数构造方法来实例化LoginServlet对象）
              Class clazz = Class.forName("com.bjpowernode.javaweb.servlet.LoginServlet");
              Object obj = clazz.newInstance();
              
              // 向下转型
              Servlet servlet = (Servlet)obj;
              
              // 创建ServletConfig对象
              // Tomcat服务器负责将ServletConfig对象实例化出来。
              // 多态（Tomcat服务器完全实现了Servlet规范）
              ServletConfig servletConfig = new org.apache.catalina.core.StandardWrapperFacade();
              
              // 调用Servlet的init方法
              servlet.init(servletConfig);
              
              // 调用Servlet的service方法
              // ....
             
          } 
      }
      ```

### 1.2 适配器的实现



> 思想：直接写类实现Servlet接口会导致多个方法都要实现，代码很混乱。
>
> 但是在中间增加一个适配器（GenericServlet抽象类）让他实现Servlet接口，然后让我们的Servlet适配器。这样就可以按需实现方法。
>
> init方法重载以完成子类重写init方法以满足业务需求非常巧妙。

```java
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
```

```java
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

```
