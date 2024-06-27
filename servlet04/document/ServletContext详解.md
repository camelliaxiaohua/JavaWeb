# ServletContext

### 1. ServletContext 是什么？

- ServletContext 是一个接口，是 Servlet 规范的一部分。

### 2. ServletContext 是由谁实现的？

- Tomcat 服务器（WEB 服务器）实现了 ServletContext 接口。

  ```java
  public class org.apache.catalina.core.ApplicationContextFacade implements ServletContext {}
  ```

### 3. ServletContext 对象是由谁创建的？在什么时候创建的？

- ServletContext 对象在 WEB 服务器启动时创建。
- ServletContext 对象是由 WEB 服务器创建的。
- 每个 Web 应用程序只有一个 ServletContext 对象。
- ServletContext 对象在服务器关闭时销毁。

### 4. 如何理解 ServletContext？

- **Context 的含义：**
  - Servlet 对象的环境对象（Servlet 的上下文对象）。

- **ServletContext 对象对应于整个 web.xml 文件。**
- 想象一个教室有 50 名学生（每名学生代表一个 Servlet），都在同一个教室里。这个教室就相当于 ServletContext 对象。
- 存放在 ServletContext 中的数据是所有 Servlet 共享的。
  - 例如，教室里的空调是所有学生共享的，语文老师也是所有学生共享的。
- Tomcat 是一个容器，可以容纳多个 Web 应用程序，每个 Web 应用程序对应一个 ServletContext 对象。

### 5. ServletContext 接口中的常用方法

#### 1. `getInitParameter(String name)` 和 `getInitParameterNames()`

- `public String getInitParameter(String name);`

  - 通过初始化参数的 name 获取其 value。

- `public Enumeration<String> getInitParameterNames();`

  - 获取所有初始化参数的 name。

   ```xml
   <!--以下是ServletContext对象的方法，获取到是什么信息呢？是以下的配置信息-->
   <context-param>
       <param-name>pageSize</param-name>
       <param-value>10</param-value>
   </context-param>
   <context-param>
       <param-name>startIndex</param-name>
       <param-value>0</param-value>
   </context-param>
   <!--注意：以上的配置信息属于应用级的配置信息，一般一个项目中共享的配置信息会放到以上的标签当中。-->
   <!--如果你的配置信息只是想给某一个servlet作为参考，那么你配置到servlet标签当中即可，使用ServletConfig对象来获取。-->
   ```

- **示例代码：**

  ```java
  // 导包省略
  
  public class AServlet extends GenericServlet {
      @Override
      public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {
          servletResponse.setContentType("text/html");
          PrintWriter out = servletResponse.getWriter();
          // 获取ServletContext对象
          ServletContext application = this.getServletContext();
          out.println("ServletContext对象：" + application);
  
          // 获取上下文的初始化参数
          Enumeration<String> attributeNames = application.getInitParameterNames();
          while (attributeNames.hasMoreElements()) {
              String attributeName = attributeNames.nextElement();
              String attributeValue = application.getInitParameter(attributeName);
              out.println("<br>" + attributeName + ":" + attributeValue);
          }
      }
  }
  ```

#### 2. `public String getContextPath();`

用于获取当前 Web 应用程序的上下文路径（Context Path）。上下文路径是指 Web 应用程序在 Web 服务器中的根路径，它是相对于服务器的根路径的部分。

```java
ServletContext application = this.getServletContext();
//获取context path（动态的获取应用上下文的根）
String contextPath = application.getContextPath();
out.println("<br>contextPath:"+contextPath);
```

- 输出：contextPath:/context

![图片描述](../../assets/上下文.png)


#### 4. `public String getRealPath(String path)`

用于获取指定路径在文件系统中的真实路径（Real Path）。在 Web 应用程序中，通常会使用相对路径来引用资源，例如配置文件、上传文件等。这些相对路径是相对于 Web 应用程序的根目录来确定的。

#### 5. 日志记录相关方法

`public void log(String message)` 和 `public void log(String message, Throwable t)` 是 `ServletContext` 接口提供的两个方法，用于记录日志信息。

1. **`public void log(String message)`**：
  - 这个方法用于记录简单的日志消息，通常用于记录一般性的信息或者调试信息。
  - 参数 `message` 是要记录的日志消息内容。

2. **`public void log(String message, Throwable t)`**：
  - 这个方法用于记录带有异常信息的日志消息。
  - 参数 `message` 是要记录的日志消息内容。
  - 参数 `t` 是要记录的异常对象，它包含了发生的异常信息。

```java
// 通过ServletContext对象也是可以记录日志的
public void log(String message);
public void log(String message, Throwable t);
// 这些日志信息记录到哪里了？
// C:\Users\24211\AppData\Local\JetBrains\IntelliJIdea2024.1\tomcat

```

- Tomcat服务器的logs目录下都有哪些日志文件？

  1. catalina.2021-11-05.log 服务器端的java程序运行的控制台信息。

  2. local host.2021-11-05.log ServletContext对象的log方法记录的日志信息存储到这个文件中。

  3. localhost_access_log.2021-11-05.txt 访问日志

#### 6. ServletContext 对象及应用域相关操作

1. ServletContext对象还有另一个名字：应用域（后面还有其他域，例如：请求域、会话域）
  - 如果所有的用户共享一份数据，并且这个数据很少的被修改，并且这个数据量很少，可以将这些数据放到ServletContext这个应用域中

2. 为什么是所有用户共享的数据？

  - ServletContext 是所有用户共享数据的容器，因为它在整个应用程序的生命周期内只有一个实例，并且可以存储全局的配置和资源。

3. 为什么数据量要小？

  -  因为数据量比较大的话，太占用堆内存，并且这个对象的生命周期比较长，服务器关闭的时候，这个对象才会被销毁。大数据量会影响服务器的性能，占用内存较小的数据量可以考虑放进去。

4. 怎么向ServletContext应用域中存数据

  - ```java
     javapublic void setAttribute(String name, Object value);
     ```

5. 怎么从ServletContext应用域中取数据

  - ```java
     public Object getAttribute(String name);
     ```

6. 怎么删除ServletContext应用域中的数据

  - ```java
     public void removeAttribute(String name); 
     ```

### 6. 关于 Servlet 类的继承结构：

以后编写 Servlet 类的时候，实际上是不会去直接继承 GenericServlet 类的，因为我们是基于 HTTP 协议的 B/S 结构系统。在 Servlet 规范中，提供了一个专门为 HTTP 协议准备的 Servlet 类叫做 HttpServlet。我们编写的 Servlet 类应当继承 HttpServlet 类，因为它更便捷地处理 HTTP 协议。

```mark
jakarta.servlet.Servlet（接口）【爷爷】
jakarta.servlet.GenericServlet implements Servlet（抽象类）【儿子】
jakarta.servlet.http.HttpServlet extends GenericServlet（抽象类）【孙子】

我们以后编写的 Servlet 要继承 HttpServlet 类。

```
