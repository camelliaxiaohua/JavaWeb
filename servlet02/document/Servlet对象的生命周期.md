---
title: Servlet对象的生命周期
date: 2024-06-15 19:01:19
tags: 
categories: 
- Java Web
- Servlet



---


# Servlet：生命周期

## 一、Servlet对象的生命周期

### 1.1 什么是Servlet对象生命周期？

- Servlet对象什么时候被创建？
- Servlet对象什么时候被销毁？
- Servlet对象创建了几个？
- **Servlet对象的生命周期表示：一个Servlet对象从出生到最终死亡的整个过程是怎样的？**

### 1.2 Servlet对象是由谁来维护的？

- Servlet对象的创建、方法调用和最终销毁，Java Web程序员是无权干预的。
- Servlet对象的生命周期由Tomcat服务器（WEB Server）全权负责。
- Tomcat服务器通常又称为：WEB容器。（这个叫法你要知道【WEB Container】）
- **WEB容器负责管理Servlet对象的生命周期。**

### 1.3 自己new的Servlet对象受WEB容器的管理吗？

- 自己new的Servlet对象是不受WEB容器管理的。

- WEB容器创建的Servlet对象，这些Servlet对象都会被放到一个集合当中（HashMap），只有放到这个HashMap集合中的Servlet才能够被WEB容器管理，自己new的Servlet对象不会被WEB容器管理。（自己new的Servlet对象不在容器当中）

- web容器底层应该有一个HashMap这样的集合，在这个集合当中存储了Servlet对象和请求路径之间的关系
![](../assets/web容器的map集合.png)
### 1.4 服务器在启动时Servlet对象有没有被创建出来（默认情况下）？

- 在Servlet中提供一个无参数的构造方法，启动服务器的时候测试构造方法是否执行。
    - 经过测试得出结论：默认情况下，服务器在启动的时候Servlet对象并不会被实例化。
- 这种设计是合理的。在用户发送请求之前，提前创建所有的Servlet对象会浪费内存资源。如果某些Servlet对象一直未被访问，它们将成为无用的对象，因此没有必要提前创建。

### 1.5 怎么让服务器启动的时候创建Servlet对象呢？

- 在servlet标签中添加<load-on-startup>子标签，在该子标签中填写整数，越小的整数优先级越高。

    ```xml
     <servlet>
            <servlet-name>Aservlet</servlet-name>
            <servlet-class>servlet.AServlet</servlet-class>
            <load-on-startup>1</load-on-startup>
        </servlet>
        <servlet-mapping>
            <servlet-name>Aservlet</servlet-name>
            <url-pattern>/camellia/Aservlet</url-pattern>
        </servlet-mapping>
  ```

### 1.6 Servlet对象生命周期

- 默认情况下服务器启动的时候AServlet对象并没有被实例化

- 用户发送第一次请求的时候，控制台输出了以下内容：

    ```markdown
    AServlet无参数构造方法执行了
    AServlet's init method execute!
    AServlet's service method execute!
    ```

- 根据以上输出内容得出结论：

    - <u>用户在发送第一次请求的时候Servlet对象被实例化（AServlet的构造方法被执行了。并且执行的是无参数构造方法。）</u>
    - AServlet对象被创建出来之后，Tomcat服务器马上调用了AServlet对象的init方法。（init方法在执行的时候，AServlet对象已经存在了。已经被创建出来了。）
    - 用户发送第一次请求的时候，init方法执行之后，Tomcat服务器马上调用AServlet对象的service方法。

    - 用户继续发送第二次请求，控制台输出了以下内容：

      ```
      AServlet's service method execute!
      ```

    - 根据以上输出结果得知，用户在发送第二次，或者第三次，或者第四次请求的时候，Servlet对象并没有新建，还是使用之前创建好的Servlet对象，直接调用该Servlet对象的service方法，这说明：

        1. 第一：<u>**Servlet对象是单例的**（**注意**：Servlet对象是单实例的，但Servlet类并不符合单例模式，因此我们称其为“假单例”。单实例的原因在于Servlet对象的创建由Tomcat控制，而不是由Java Web程序员控制。Tomcat只创建一个Servlet对象，因此是单实例，但属于假单例。真正的单例模式要求构造方法私有化。）</u>
        2. 第二：无参数构造方法、init方法只在第一次用户发送请求的时候执行。也就是说无参数构造方法只执行一次。init方法也只被Tomcat服务器调用一次。
        3. 第三：只要用户发送一次请求：service方法必然会被Tomcat服务器调用一次。发送100次请求，service方法会被调用100次。

    - 关闭服务器的时候，控制台输出了以下内容：

      ```
      AServlet's destroy method execute!
      ```

    - 通过以上输出内容，可以得出以下结论：

        - Servlet的destroy方法只被Tomcat服务器调用一次。
        - destroy方法是在什么时候被调用的？
            - 在服务器关闭的时候。
            - 因为服务器关闭的时候要销毁AServlet对象的内存。
            - 服务器在销毁AServlet对象内存之前，Tomcat服务器会自动调用AServlet对象的destroy方法。

    - destroy方法调用的时候，对象销毁了还是没有销毁呢？

        - destroy方法执行的时候AServlet对象还在，没有被销毁。destroy方法执行结束之后，AServlet对象的内存才会被Tomcat释放。

    - Servlet对象更像一个人的一生：

        - Servlet的无参数构造方法执行：标志着你出生了。
        - Servlet对象的init方法的执行：标志着你正在接受教育。
        - Servlet对象的service方法的执行：标志着你已经开始工作了，已经开始为人类提供服务了。
        - Servlet对象的destroy方法的执行：标志着临终。有什么遗言，抓紧的。要不然，来不及了。

    - 关于Servlet类中方法的调用次数？

        - 构造方法只执行一次。
        - init方法只执行一次。
        - service方法：用户发送一次请求则执行一次，发送N次请求则执行N次。
        - destroy方法只执行一次。

    - **<u>当我们Servlet类中编写一个有参数的构造方法，如果没有手动编写无参数构造方法会出现什么问题？</u>**

        - 报错了：500错误。
        - 注意：500是一个HTTP协议的错误状态码。
        - 500一般情况下是因为服务器端的Java程序出现了异常。（服务器端的错误都是500错误：服务器内部错误。）
        - 如果没有无参数的构造方法，会导致出现500错误，无法实例化Servlet对象。
        - 所以，一定要注意：在Servlet开发当中，不建议程序员来定义构造方法，因为定义不当，一不小心就会导致无法实例化Servlet对象。

    - 思考：Servlet的无参数构造方法是在对象第一次创建的时候执行，并且只执行一次。init方法也是在对象第一次创建的时候执行，并且只执行一次。那么这个无参数构造方法可以代替掉init方法吗？

        - 不能。
        - **Servlet规范中有要求，作为javaweb程序员，编写Servlet类的时候，不建议手动编写构造方法**，因为编写构造方法，很容易让无参数构造方法消失，这个操作可能会导致Servlet对象无法实例化。所以init方法是有存在的必要的。

    - init、service、destroy方法中使用最多的是哪个方法？

        - **使用最多就是service方法，service方法是一定要实现的，因为service方法是处理用户请求的核心方法。**
        - 什么时候使用init方法呢？
            - init方法很少用。
            - 通常在init方法当中做初始化操作，并且这个初始化操作只需要执行一次。例如：初始化数据库连接池，初始化线程池....
        - 什么时候使用destroy方法呢？
            - destroy方法也很少用。
            - 通常在destroy方法当中，进行资源的关闭。马上对象要被销毁了，还有什么没有关闭的，抓紧时间关闭资源。还有什么资源没保存的，抓紧时间保存一下。
