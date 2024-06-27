---
title: Servlet：动态web应用请求/响应过程中角色与协议分析
date: 2024-06-12 19:57:20
tags:
categories:
- Java Web
- Servlet

---

!!! note 目录
<!-- toc -->



#Servlet：动态web应用请求/响应过程中角色与协议分析

## 一、动态web应用请求/响应过程中角色与协议分析

![](JavaWeb/assets/动态web应用请求响应过程中角色与协议分析.png)

### 1.1  有哪些角色（在整个BS结构的系统当中，有哪些角色参与）

- 浏览器软件的开发团队（谷歌浏览器、火狐浏览器、IE浏览器....）
- WEB Server的开发团队（Tomcat、Jetty、WebLogic、JBOSS、WebSphere....）
- DB Server的开发团队（Oracle、MySQL.....）
- webapp的开发团队（WEB应用是Java Web程序员开发的）

### 1.2 角色和角色之间需要遵守哪些规范，哪些协议

- webapp的开发团队和WEB Server的开发团队之间有一套规范: Java EE规范之一的Servlet规范。
    - Servlet规范的作用是什么？
        - **WEB Server和webapp解耦合。**
- Browser  和   Web Server之间有一套传输协议：HTTP协议（超文本传输协议）。
- webapp开发团队和DB Server的开发团队之间有一套规范：JDBC规范。

![](JavaWeb\assets\角色和角色之间需要遵守哪些规范.png)

### 1.3 Servlet规范是一个什么规范？

- 遵循Servlet规范的webapp，这个webapp就可以放在不同的WEB服务器中运行。（因为这个webapp是遵循Servlet规范的。）
- Servlet规范包括什么呢？
    - 规范了哪些接口
    - 规范了哪些类
    - 规范了一个web应用中应该有哪些配置文件
    - 规范了一个web应用中配置文件的名字
    - 规范了一个web应用中配置文件存放的路径
    - 规范了一个web应用中配置文件的内容
    - 规范了一个合法有效的web应用它的目录结构应该是怎样的。
    - .....