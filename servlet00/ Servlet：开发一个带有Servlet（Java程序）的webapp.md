---
title: 开发一个带有Servlet（Java程序）的webapp
date: 2024-06-15 09:27:40
tags: 
categories: 
- Java Web
- Servlet

---


# Servlet：开发一个带有Servlet（Java程序）的webapp

## * 一、开发一个带有Servlet（Java程序）的webapp（重点）

### 1.1 开发步骤

1. 第一步：在webapps目录下新建一个目录，起名crm（这个crm就是webapp的名字）。

   > **<u>注意：crm就是这个webapp的根</u>**

2. 第二步：在webapp的根下新建一个目录：<u>**WEB-INF**</u>

   > [!CAUTION]
   >
   > **注意：这个目录的名字是Servlet规范中规定的，必须全部大写，必须一模一样。**

3. 第三步：在WEB-INF目录下新建一个目录：<u>**classes**</u>
   > [!CAUTION]
   > 注意：这个目录的名字必须是全部小写的classes。这也是Servlet规范中规定的。另外这个目录下一定存放的是Java程序编译之后的class文件（这里存放的是字节码文件）。

4. 第四步：在WEB-INF目录下新建一个目录：lib

   > 注意：这个目录不是必须的。但如果一个Web应用需要第三方的JAR包，那么这个JAR包必须放到lib目录中。这个目录的名称不能随意更改，必须全部小写为lib。例如，Java语言连接数据库时需要数据库驱动的JAR包，那么这个JAR包必须放到lib目录中。这是Servlet规范中规定的。

5. 第五步：在WEB-INF目录下新建一个文件：<u>**web.xml**</u>
   > [!CAUTION]
   >**注意：这个文件是必须的，文件名必须为web.xml，并且必须放在指定位置。这个配置文件描述了请求路径和Servlet类之间的映射关系。**

    - 这个文件最好从其他的Web应用程序中拷贝，不建议手写，没有必要。

    - ```xml
      <?xml version="1.0" encoding="UTF-8"?>
      
      <web-app xmlns="https://jakarta.ee/xml/ns/jakartaee"
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xsi:schemaLocation="https://jakarta.ee/xml/ns/jakartaee
                            https://jakarta.ee/xml/ns/jakartaee/web-app_5_0.xsd"
        version="5.0"
        metadata-complete="true">
      
      
      </web-app>
      
      ```

6. 第六步：编写一个Java程序，这个Java程序必须实现Servlet接口，不能随意开发。

    - Servlet接口不在JDK当中，Servlet接口是Java EE规范的一部分。（因为Servlet属于Java EE，是另一套类库，不属于Java SE。）
    - Servlet接口（Servlet.class文件）由Oracle提供。（最初是由Sun公司提供的。）
    - Tomcat服务器实现了Servlet规范，因此需要使用Servlet接口。Tomcat服务器的CATALINA_HOME\lib目录下有一个servlet-api.jar文件，解压这个servlet-api.jar后，你会看到里面有一个Servlet.class文件。
    - **重点：** 从JakartaEE 9开始，Servlet接口的全名变为：jakarta.servlet.Servlet。
   > <u>注意：编写这个Java程序时，Java源代码的位置无所谓，你可以将其放在任何地方，只需将编译后的class文件放到classes目录下即可。</u>

7.  第七步：编译我们编写的HelloServlet

    - 重点：你怎么能让你的HelloServlet编译通过呢？配置环境变量CLASSPATH

      `CLASSPATH=.;C:\dev\apache-tomcat-10.0.12\lib\servlet-api.jar`

    - 思考问题：以上配置的CLASSPATH和Tomcat服务器运行有没有关系？

        - 没有任何关系，以上配置这个环境变量只是为了让你的HelloServlet能够正常编译生成class文件。

8. 第八步：将以上编译之后的HelloServlet.class文件拷贝到WEB-INF\classes目录下。

9. <u>**第九步：在web.xml文件中编写配置信息，让“请求路径”和“Servlet类名”关联在一起。**</u>

    - 这一步用专业术语描述：在web.xml文件中注册Servlet类。

    - ```xml
      
      <?xml version="1.0" encoding="UTF-8"?>
      
      <web-app xmlns="https://jakarta.ee/xml/ns/jakartaee"
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xsi:schemaLocation="https://jakarta.ee/xml/ns/jakartaee
                            https://jakarta.ee/xml/ns/jakartaee/web-app_5_0.xsd"
        version="5.0"
        metadata-complete="true">
      
      	<!-- Servlet描述信息 -->
      	<!-- 每个Servlet对应一个Servlet-Mapping -->
      	<servlet>
      		<servlet-name>camellia</servlet-name>
      		<!-- 这个位置必须是带有包名的全限定类名 -->
      		<servlet-class>com.camellia.servlet.HelloServlet</servlet-class>
      	</servlet>
      
      	<!-- Servlet映射信息 -->
      	<servlet-mapping>
      		<!-- 这个名称可以随意指定，但必须与上面的servlet-name一致 -->
      		<servlet-name>camellia</servlet-name>
      		<!-- 这里需要一个路径 -->
      		<!-- 唯一的要求是路径必须以 / 开始 -->
      		<!-- 当前路径可以随意指定 -->
      		<url-pattern>/camellia</url-pattern>
      	</servlet-mapping>
      </web-app>
      ```


- 第十步：启动Tomcat服务器

- 第十一步：打开浏览器，在浏览器地址栏上输入一个url，这个URL必须是：

    - http://127.0.0.1:8080/crm/camellia
      > [!CAUTION]
      > 注意：浏览器上的请求路径不能随便写，这个请求路径必须和web.xml文件中的url-pattern一致。
      > 浏览器上的请求路径和web.xml文件中的url-pattern的唯一区别就是：浏览器上的请求路径带项目名：/crm

### 1.2 细节部分

- 浏览器上编写的路径太复杂，可以使用超链接。（**<u>非常重要：html页面只能放到WEB-INF目录外面。</u>**）

- 以后不需要我们编写main方法了。tomcat服务器负责调用main方法，Tomcat服务器启动的时候执行的就是main方法。我们javaweb程序员只需要编写Servlet接口的实现类，然后将其注册到web.xml文件中，即可。

- 总结一下：一个合法的webapp目录结构应该是怎样的？

    ```ASN.1
    webapproot
         |------WEB-INF
         		  |------classes(存放字节码)
         		  |------lib(第三方jar包)
         		  |------web.xml(注册Servlet)
         |------html
         |------css
         |------javascript
         |------image
         ....
    ```

- 浏览器发送请求，到最终服务器调用Servlet中的方法的过程。（以下这个过程描述的很粗糙。）

    1. 用户输入URL，或者直接点击超链接：http://127.0.0.1:8080/crm/camellia
    2. 然后Tomcat服务器接收到请求，截取路径：/crm/camellia
    3. Tomcat服务器找到crm项目
    4. Tomcat服务器在web.xml文件中查找/camellia 对应的Servlet是：com.bjpowernode.servlet.HelloServlet
    5. Tomcat服务器通过反射机制，创建com.camellia.servlet.HelloServlet的对象。
    6. Tomcat服务器调用com.camellia.servlet.HelloServlet对象的service方法。

### 1.3 关于JavaEE的版本

- JavaEE目前最高版本是 JavaEE8
- JavaEE被Oracle捐献了，Oracle将JavaEE规范捐献给Apache了。
- Apache把JavaEE换名了，以后不叫JavaEE了，以后叫做 jakarta EE。
- 以后没有JavaEE了。以后都叫做Jakarta EE。
- JavaEE8版本升级之后的"JavaEE 9"，不再是"JavaEE9"这个名字了，叫做JakartaEE9
- JavaEE8的时候对应的Servlet类名是：javax.servlet.Servlet
- JakartaEE9的时候对应的Servlet类名是：jakarta.servlet.Servlet （包名都换了）
- 如果你之前的项目还是在使用javax.servlet.Servlet，那么你的项目无法直接部署到Tomcat10+版本上。你只能部署到Tomcat9-版本上。在Tomcat9以及Tomcat9之前的版本中还是能够识别javax.servlet这个包。



### 1.4 解决Tomcat服务器在DOS命令窗口中的乱码问题（控制台乱码）

将CATALINA_HOME/conf/logging.properties文件中的内容修改如下：

java.util.logging.ConsoleHandler.encoding = GBK

### 1.5 向浏览器响应一段HTML代码

```java
public void service(ServletRequest request, ServletResponse response){
    response.setContentType("text/html");
    PrintWriter out = response.getWriter();
    out.print("<h1>hello servlet!</h1>");
}
```

###  1.6 在Servlet中连接数据库，怎么做？

- Servlet是Java程序，所以在Servlet中完全可以编写JDBC代码连接数据库。
- 在一个webapp中去连接数据库，需要将驱动jar包放到WEB-INF/lib目录下。（com.mysql.cj.jdbc.Driver 这个类就在驱动jar包当中。）