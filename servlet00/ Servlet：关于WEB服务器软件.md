---
title: Servlet：关于WEB服务器软件
date: 2024-06-12 15:00:54
tags:
categories:
- Java Web
- Servlet
- 
---



# Servlet：关于WEB服务器软件

## 一、关于WEB服务器软件

### 1.1 Web服务软件

- WEB服务器软件（这些软件都是提前开发好的。）
    - Tomcat（WEB服务器）
    - jetty（WEB服务器）
    - JBOSS（应用服务器）
    - WebLogic（应用服务器）
    - WebSphere（应用服务器）



- 应用服务器和WEB服务器的关系？
    - **应用服务器实现了JavaEE的所有规范。**(JavaEE有13个不同的规范。)
    - **WEB服务器只实现了JavaEE中的Servlet + JSP两个核心的规范。**
    - 通过这个讲解说明了：应用服务器是包含WEB服务器的。
    - 用过JBOSS服务器的同学应该很清楚，JBOSS中内嵌了一个Tomcat服务器。



### 1.2 TomCat配置

1. Tomcat下载
- apache官网地址：https://www.apache.org/
- tomcat官网地址：https://tomcat.apache.org
- tomcat开源免费的轻量级WEB服务器。
- tomcat还有另外一个名字：catalina（catalina是美国的一个岛屿，风景秀丽，据说作者是在这个风景秀丽的小岛上开发了一个轻量级的WEB服务器，体积小，运行速度快，因此tomcat又被称为catalina）
- tomcat的logo是一只公猫（寓意表示Tomcat服务器是轻巧的，小巧的，果然，体积小，运行速度快，只实现了Servlet+JSP规范）
- tomcat是java语言写的。
- tomcat服务器要想运行，必须先有jre（Java的运行时环境）



2. **Tomcat服务器要想运行，需要先有jre，所以要先安装JDK，配置java运行环境。**

- `JAVA_HOME=C:\Program Files\Java\jdk-17.0.1`
- `PATH=%JAVA_HOME%\bin`
- 启动Tomcat服务器只配置path对应的bin目录是不行的,还需要配置两个环境变量
    - `JAVA_HOME=JDK的根`
    - `CATALINA_HOME=Tomcat服务器的根`



3. **Tomcat服务器的安装：**

- 直接zip包解压即可。
- 启动Tomcat
    - bin目录下有一个文件：startup.bat,通过它可以启动Tomcat服务器。
        - xxx.bat文件是windows操作系统专用的，bat文件是批处理文件，这种文件中可以编写大量的windows的dos命令，然后执行bat文件就相当于批量的执行dos命令。
        - startup.sh，这个文件在windows当中无法执行，在Linux环境当中可以使用。在Linux环境下能够执行的是shell命令，大量的shell命令编写在shell文件当中，然后执行这个shell文件可以批量的执行shell命令。
        - tomcat服务器提供了bat和sh文件，说明了这个tomcat服务器的通用性。
        - 分析startup.bat文件得出，执行这个命令，实际上最后是执行catalina.bat文件。
        - catalina.bat文件中有这样一行配置：`MAINCLASS=org.apache.catalina.startup.Bootstrap `（这个类就是main方法所在的类。）
        - tomcat服务器就是Java语言写的，既然是java语言写的，那么启动Tomcat服务器就是执行main方法。
    - 打开dos命令窗口，在dos命令窗口中输入startup.bat来启动tomcat服务器。



4. 关于Tomcat服务器的目录
- bin ： 这个目录是Tomcat服务器的命令文件存放的目录，比如：启动Tomcat，关闭Tomcat等。
- conf： 这个目录是Tomcat服务器的配置文件存放目录。**（server.xml文件中可以配置端口号，默认Tomcat端口是8080）**
- lib ：这个目录是Tomcat服务器的核心程序目录，因为Tomcat服务器是Java语言编写的，这里的jar包里面都是class文件。
- logs: Tomcat服务器的日志目录，Tomcat服务器启动等信息都会在这个目录下生成日志文件。
- temp：Tomcat服务器的临时目录。存储临时文件。
- webapps：这个目录当中就是用来存放大量的webapp（web application：web应用）
- work：这个目录是用来存放JSP文件翻译之后的java文件以及编译之后的class文件。


5. 启动/关闭TomCat

    - 启动Tomcat： startup
    - 关闭Tomcat：stop （shutdown.bat文件重命名为stop.bat，为什么？原因是shutdown命令和windows中的关机命令冲突。所以修改一下。）
6. 测试Tomcat服务器
- 打开浏览器，在浏览器的地址栏上输入URL即可：
    - http://ip地址:端口号
    - 端口号：8080
    - 本机的IP地址是：127.0.0.1、或者是localhost。

>配置Tomcat服务器需要的环境变量。
>1. JAVA_HOME=JDK的根
>2. CATALINA_HOME=Tomcat服务器的根
>3. PATH=%JAVA_HOME%\bin;%CATALINA_HOME%\bin

### 1.3 实现一个最基本的web应用

> 这个web应用中没有java小程序

1. 第一步：找到CATALINA_HOME\webapps目录

- 因为所有的webapp要放到webapps目录下。（这是Tomcat服务器的要求。如果不放到这里，Tomcat服务器找不到你的应用。）
2. 第二步：在CATALINA_HOME\webapps目录下新建一个子目录，起名：oa

- 这个目录名oa就是你这个webapp的名字。
3. 第三步：在oa目录下新建资源文件，例如：index.html

- 编写index.html文件的内容。
4. 第四步：启动Tomcat服务器
5. 第五步：打开浏览器，在浏览器地址栏上输入这样的URL：

- http://127.0.0.1:8080/oa/index.html

- 思考一个问题：

    - 在浏览器上直接输入一个URL和超链接一样吗？既然是一样的，我们完全可以使用超链接。

      ```html
      <!--注意以下的路径，以/开始，带项目名，是一个绝对路径。不需要添加：http://127.0.0.1:8080-->
      <a href="/oa/login.html">user login2</a>
      
      <!--多个层级也没有关系，正常访问即可。-->
      <!--注意：我们目前前端上的路径都以“/”开始的，都是加项目名的。-->
      <a href="/oa/test/debug/d.html">d page</a>
      ```


- http://127.0.0.1:8080/oa/userList.html
    - 访问这个地址，可以展示一个用户列表页面。但是这个用户列表页面是写死在HTML文件当中的。这种资源我们称为**静态资源**。怎么能变成动态资源。显然需要连接数据库。
    - 连接数据库需要JDBC程序，也就是说需要编写Java程序连接数据库，数据库中有多少条记录，页面上就显示多少条记录，这种技术被称为**动态网页技术**。（动态网页技术并不是说页面中有flash动画。动态网页技术是说页面中的数据是动态的，根据数据库中数据的变化而变化。）
