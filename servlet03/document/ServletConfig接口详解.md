# ServletConfig

## 1. ServletConfig 是什么？

- **包名**：`jakarta.servlet.ServletConfig`
- **角色**：Servlet 规范的一部分。
- **类型**：一个接口。
- **注释**：`jakarta.servlet.Servlet` 也是一个接口。

## 2. 谁实现了这个接口？

- **实现者**：由 Web 服务器实现。
- **示例**：Tomcat 通过 `org.apache.catalina.core.StandardWrapperFacade implements ServletConfig`。
- **考虑**：不同的 Web 服务器（例如 Jetty）可能有不同的类和包名，但它们都实现了 `ServletConfig`。

## 3. 与 Servlet 的关系

- **关联**：每个 `Servlet` 对象都有一个对应的 `ServletConfig` 对象。
- **一对一**：一个 `Servlet` 对应一个 `ServletConfig`，100 个 `Servlet` 对象会有 100 个 `ServletConfig` 对象。

## 4. ServletConfig 对象的创建

- **创建者**：由 Web 服务器（如 Tomcat）创建。
- **时间点**：在创建 `Servlet` 对象的同时创建 `ServletConfig` 对象。

## 5. ServletConfig 的用途

- **含义**：`Config` 是 Configuration（配置）的缩写。
- **角色**：作为 `Servlet` 对象的配置信息对象（`.xml`）。

## 6. ServletConfig 中包含的信息

包装了`web.xml` 文件中的 `<servlet>` 标签。
- **示例**：
  ```xml
  <servlet>
        <servlet-name>configServlet</servlet-name>
        <servlet-class>servlet.ConfigTestServlet</servlet-class>
    </servlet>
  ```
- **封装**：Web 服务器（如 Tomcat）解析 `web.xml` 文件，并将 `<servlet>` 标签中的配置信息封装到 `ServletConfig` 对象中。


## 7. ServletConfig 接口中的方法

| 修饰符和类型                               | 方法                                      | 描述                                                                                         |
|--------------------------------------------|-------------------------------------------|----------------------------------------------------------------------------------------------|
| `java.lang.String`                         | `getInitParameter(java.lang.String name)` | 返回包含命名初始化参数的值的 `String`，如果参数不存在则返回 `null`。                          |
| `java.util.Enumeration<java.lang.String>`  | `getInitParameterNames()`                 | 返回包含 servlet 初始化参数名称的 `Enumeration` 对象，如果 servlet 没有初始化参数则返回空 `Enumeration`。 |
| `ServletContext`                           | `getServletContext()`                     | 返回 `ServletContext`，引用当前正在执行的 servlet 上下文。                                    |
| `java.lang.String`                         | `getServletName()`                        | 返回此 servlet 实例的名称。                                                                  |

### 7.1 配置Servlet对象的初始化信息

> 在`<servlet></servlet>`可以配置Servlet对象的初始化信息

- **示例**：
  ```xml
   <servlet>
        <servlet-name>configServlet</servlet-name>
        <servlet-class>servlet.ConfigTestServlet</servlet-class>
        <!--这里是可以配置一个Servlet对象的初始化信息的。-->
        <init-param>
            <param-name>driver</param-name>
            <param-value>com.mysql.cj.jdbc.Driver</param-value>
        </init-param>
        <init-param>
            <param-name>url</param-name>
            <param-value>jdbc:mysql://localhost:3306/bjpowernode</param-value>
        </init-param>
        <init-param>
            <param-name>user</param-name>
            <param-value>root</param-value>
        </init-param>
        <init-param>
            <param-name>password</param-name>
            <param-value>root1234</param-value>
        </init-param>
   <!--以上<servlet></servlet>标签中的<init-param></init-param>是初始化参数。-->
    </servlet>
  ```
- 这些初始化参数信息会自动被 Web 服务器封装到 `ServletConfig` 对象中。


### 7.2 ServletConfig中的`getInitParameterNames()`和`getInitParameter()`方法

- `getServletName()` 返回当前 Servlet 实例的名称。
- `getInitParameterNames()` 方法返回一个 `Enumeration<String>` 对象，该对象包含当前 Servlet 的所有初始化参数的名称。如果 Servlet 没有初始化参数，则返回一个空的 `Enumeration`。这个方法通常用于遍历所有初始化参数名称，以便进一步处理它们的值。
- `getInitParameter(String name)` 方法返回指定名称的初始化参数的值。如果参数不存在，则返回 `null`。这个方法通常用于获取特定初始化参数的值。
- `getServletContext()`返回当前 Servlet 实例正在执行的 ServletContext 对象的引用。ServletContext 提供了与 Web 应用程序环境交互的方法，如获取资源、设置和获取属性等。

- **示例代码:**
```java
package servlet;

import jakarta.servlet.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

public class ConfigTestServlet extends GenericServlet {
    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();
        // 获取ServletConfig对象
        ServletConfig config = this.getServletConfig();
        // 输出该对象
        out.println("ServletConfig对象是："+config);
        //ServletConfig对象是：org.apache.catalina.core.StandardWrapperFacade@3cdfe47

        //获取<servlet-name></servlet-name>
        String servletName = config.getServletName();
        out.println("<br>"+servletName);

        // 通过ServletConfig对象的两个方法，可以获取到web.xml文件中的初始化参数配置信息。
        Enumeration<String> initParameterNames = config.getInitParameterNames();
        //遍历集合
        while (initParameterNames.hasMoreElements()){
            String initParameterName = initParameterNames.nextElement();
            String initParameter = config.getInitParameter(initParameterName);
            out.println("<br>"+initParameterName+" = "+initParameter);
        }
      //通过ServletConfig对象获取ServletContext对象。
      ServletContext application = config.getServletContext();
      out.println("<br>"+application);
    }
}

```

#### 7.3 注意事项

>[!NOTE]
> 分析GenericServlet源码可以知道我们根本不必获取ServletConfig对象，因为GenericServlet有提供。

```java
    //在init被赋值
    private transient ServletConfig config;
    //返回指定名称的初始化参数的值。
    @Override
    public String getInitParameter(String name) {
        return getServletConfig().getInitParameter(name);
    }
    //获取Servlet 的所有初始化参数的名称。
    @Override
    public Enumeration<String> getInitParameterNames() {
        return getServletConfig().getInitParameterNames();
    }
    //获取ServletConfig对象
    @Override
    public ServletConfig getServletConfig() {
        return config;
    }
    //返回当前 Servlet 实例的名称。
    @Override
    public String getServletName() {
        return config.getServletName();
    }
    //返回当前 Servlet 实例正在执行的 ServletContext 对象的引用
    @Override
    public ServletContext getServletContext() {
      return getServletConfig().getServletContext();
    }
```

- **7.2示例代码改写**：
```java
package servlet;

import jakarta.servlet.GenericServlet;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

public class ConfigTestServlet3 extends GenericServlet {

    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();
        out.println("ServletConfig对象："+this.getServletConfig());
        out.println("<br> ServletConfig对象名称："+this.getServletName());
        Enumeration<String> initParameterNames = this.getInitParameterNames();
        while (initParameterNames.hasMoreElements()){
            String initParameterName = initParameterNames.nextElement();
            String initParameter = this.getInitParameter(initParameterName);
            out.println("<br>"+initParameterName+" = "+initParameter);
        }
      ServletContext application1 = this.getServletContext();
      out.println("<br>"+application1);
    }
}
```