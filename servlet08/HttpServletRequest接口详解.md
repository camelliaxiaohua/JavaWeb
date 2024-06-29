## 17. HttpServletRequest接口详解

-----

- HttpServletRequest是一个接口，是Servlet规范中的一员。全限定名称：`jakarta.servlet.http.HttpServletRequest`

- HttpServletRequest接口的父接口：ServletRequest

```java
public interface HttpServletRequest extends ServletRequest {}
```



### 17.1 HttpServletRequest接口的实现类谁写的? HttpServletRequest对象是谁给创建的？
- 通过测试：`org.apache.catalina.connector.RequestFacade` 实现了 HttpServletRequest接口

```java
public class RequestFacade implements HttpServletRequest {}
```

- 测试结果说明：Tomcat服务器（WEB服务器、WEB容器）实现了HttpServletRequest接口，还是说明了Tomcat服务器实现了Servlet规范。而对于我们javaweb程序员来说，实际上不需要关心这个，我们只需要面向接口编程即可。我们关心的是HttpServletRequest接口中有哪些方法，这些方法可以完成什么功能！！！！



### 17.2 HttpServletRequest对象中都有什么信息？都包装了什么信息？
- HttpServletRequest对象是Tomcat服务器负责创建的。这个对象中封装了HTTP的请求协议。

- 实际上是用户发送请求的时候，遵循了HTTP协议，发送的是HTTP的请求协议，Tomcat服务器将HTTP协议中的信息以及数据全部解析出来，然后Tomcat服务器把这些信息封装到HttpServletRequest对象当中，传给了我们javaweb程序员。

- javaweb程序员面向HttpServletRequest接口编程，调用方法就可以获取到请求的信息了。



### 17.3 request和response对象的生命周期


#### 17.3.1 Request对象的生命周期
1. **创建**：
   当客户端（通常是浏览器）发送HTTP请求到服务器时，服务器会为每个请求创建一个新的`HttpServletRequest`对象。这个对象包含了请求的所有信息，例如请求参数、头信息、URI等。

2. **处理**：
   服务器将创建的`HttpServletRequest`对象传递给对应的Servlet的`service`方法，通常是`doGet`、`doPost`等方法。Servlet通过这个对象来获取请求信息和与客户端进行交互。

3. **销毁**：
   一旦Servlet完成了对请求的处理，服务器会销毁这个`HttpServletRequest`对象。对象的销毁意味着它不再有效，服务器会回收其占用的资源。


#### 17.3.2 Response对象的生命周期
1. **创建**：
   `HttpServletRequest`对象类似，当服务器接收到客户端的请求时，它也会创建一个新的`HttpServletResponse`对象。这个对象将用于生成和发送响应回客户端。

2. **处理**：
   服务器将创建的`HttpServletResponse`对象传递给对应的Servlet的`service`方法。Servlet通过这个对象来设置响应的状态码、头信息以及响应的内容。

3. **提交和销毁**：
   当Servlet完成对响应内容的生成，并且调用了`response`对象的`flushBuffer`方法或返回后，服务器会将响应数据发送给客户端。然后，服务器会销毁这个`HttpServletResponse`对象，并回收其占用的资源。

总结来说，<u>`request`和`response`对象的生命周期是非常短暂的，它们仅在一次请求-响应周期内有效。</u>从客户端发送请求到服务器生成并发送响应的整个过程中，这两个对象会被创建、使用并最终销毁。



### 17.4 HttpServletRequest接口中有哪些常用的方法？
1. 怎么获取前端浏览器用户提交的数据？

```java
Map<String,String[]> getParameterMap() 这个是获取Map
Enumeration<String> getParameterNames() 这个是获取Map集合中所有的key
String[] getParameterValues(String name) 根据key获取Map集合的value
* String getParameter(String name)  获取value这个一维数组当中的第一个元素。这个方法最常用。
// 以上的4个方法，和获取用户提交的数据有关系。     
```
>[!NOTE]
>1. String getParameter(String name)  获取value这个一维数组当中的第一个元素。这个方法最常用。
>2. <mark>**这四个方法适用于获取用户提交的数据，不要和对请求域的数据方法搞混。**</mark>

2. 方法测试

**Map<String,String[]> parameterMap = request.getParameterMap();**

```java
//1. 通过getParameterMap()获取参数Map集合
Map<String,String[]> parameterMap = request.getParameterMap();
//遍历Map集合（获取Map集合中所有的Key遍历）
Set<String> keys = parameterMap.keySet();
for (String key : keys) {
   //通过key获取value
   String[] values = parameterMap.get(key);
   out.println(key + ": " );
   for (String value : values) {
      out.println(value+" ");
   }
out.println("<br>");
}
```

**Enumeration<String> names = request.getParameterNames();
String[] values = request.getParameterValues("name");
String value = request.getParameter("name");**
```java
//2. 通过getParameterNames()获取Map集合所有key
Enumeration<String> keys1 = request.getParameterNames();
while (keys1.hasMoreElements()) {
   String key = keys1.nextElement();
   out.println(key + ": " );
   // 通过getParameterValues(key)获取所有values
   //request.getParameter("key")是获取数组的第一个元素，因为一般value只有一个元素。
   String[] values = request.getParameterValues(key);
   or (String value : values) {
      out.println(value+" ");
      }
   out.println("<br>");
}
```



### 17.5 前端表单提交的数据采用什么样的数据结构存储？
1. 前端提交的数据格式：username=abc&userpwd=111&aihao=s&aihao=d&aihao=tt

2. 采用Map集合来存储：
```xml
Map<String,String>
key存储String
value存储String
key         value
---------------------
username    abc
userpwd     111
aihao       s
aihao       d
aihao       tt
```
如果采用以上的数据结构存储会发现key重复的时候value覆盖。这样是不行的，因为map的key不能重复。但是将value存储改为String数组即可解决重复覆盖问题。
```xml
Map<String, String[]>
key存储String
value存储String[]
key				value
-------------------------------
username		{"abc"}
userpwd			{"111"}
aihao			{"s","d","tt"}
```
注意：前端表单提交数据的时候，假设提交了120这样的“数字”，其实是以字符串"120"的方式提交的，所以服务器端获取到的一定是一个字符串的"120"，而不是一个数字。（前端永远提交的是字符串，后端获取的也永远是字符串。）



### 17.6 “应用域”对象和“请求域”对象


#### 17.7.1 “应用域”对象是什么？
应用域对象通常指的是 `ServletContext` 对象。在 Java Web 开发中，它表示整个 web 应用的上下文，可以在应用的各个部分之间共享数据。因为它的生命周期与 web 应用一致，所以被称为“应用域”对象。

1. 什么情况下会考虑向ServletContext这个应用域当中绑定数据呢？
    - 第一：所有用户共享的数据。
    - 第二：这个共享的数据量很小。
    - 第三：这个共享的数据很少的修改操作。

   在以上三个条件都满足的情况下，使用这个应用域对象，可以大大提高我们程序执行效率。实际上向应用域当中绑定数据，就相当于把数据放到了缓存（Cache）当中，然后用户访问的时候直接从缓存中取，减少IO的操作，大大提升系统的性能，所以缓存技术是提高系统性能的重要手段。

3. **ServletContext当中有三个操作域的方法：**
```java
void setAttribute(String name, Object obj); // 向域当中绑定数据。
Object getAttribute(String name); // 从域当中根据name获取数据。
void removeAttribute(String name); // 将域当中绑定的数据移除
   
// 以上的操作类似于Map集合的操作。
Map<String, Object> map;
map.put("name", obj); // 向map集合中放key和value
Object obj = map.get("name"); // 通过map集合的key获取value
map.remove("name"); // 通过Map集合的key删除key和value这个键值对。
```

#### 17.7.2 “请求域”对象
“请求域”对象要比“应用域”对象范围小很多。生命周期短很多。请求域只在一次请求内有效。一个请求对象request对应一个请求域对象。一次请求结束之后，这个请求域就销毁了。

1. 请求域对象也有这三个方法：
```java
void setAttribute(String name, Object obj); // 向域当中绑定数据。
Object getAttribute(String name); // 从域当中根据name获取数据。
void removeAttribute(String name); // 将域当中绑定的数据移除
```
> [!NOTE]
> 1. 和应用域ServletContext使用方法相同，只是后者生命周期短，涉及请求转发。
> 2. 请注意区分这些操作方法与接收前端传来数据的四种常用方法。

2. 请求域和应用域的选用原则？
    - 尽量使用小的域对象，因为小的域对象占用的资源较少。

3. 转发（一次请求）
```java
// 第一步：获取请求转发器对象
RequestDispatcher dispatcher = request.getRequestDispatcher("/b");
// 第二步：调用转发器的forward方法完成跳转/转发
dispatcher.forward(request,response);
// 第一步和第二步代码可以联合在一起。
request.getRequestDispatcher("/b").forward(request,response);
```

4. **两个Servlet怎么共享数据？**
    - 将数据放到ServletContext应用域当中，当然是可以的，但是应用域范围太大，占用资源太多。不建议使用。
    - 可以将数据放到request域当中，<u>然后AServlet转发到BServlet，保证AServlet和BServlet在同一次请求当中</u>，这样就可以做到两个Servlet，或者多个Servlet共享同一份数据。

5. 转发的下一个资源必须是一个Servlet吗？
    - 不一定，只要是Tomcat服务器当中的合法资源，都是可以转发的。例如：html....
    - 注意：转发的时候，路径的写法要注意，转发的路径以“/”开始，不加项目名。

6. <mark>**关于request对象中两个非常容易混淆的方法：**</mark>
```java
// uri?username=zhangsan&userpwd=123&sex=1
String username = request.getParameter("username");

// 之前一定是执行过：request.setAttribute("name", new Object())
Object obj = request.getAttribute("name");
    
// 以上两个方法的区别是什么？
    // 第一个方法：获取的是用户在浏览器上提交的数据。
    // 第二个方法：获取的是请求域当中绑定的数据。
```
>[!NOTE]
>getParameter是针对获取前端发送的数据，setAttribute是针对请求域存储的数据。



### 17.8 HttpServletRequest接口的其他常用方法
1. 获取客户端的IP地址
```java
// 获取客户端的IP地址
String remoteAddr = request.getRemoteAddr();
```

2. 设置请求体的字符集。
    - **POST请求处理**：<u>**当客户端通过POST方法发送请求时**，请求的数据通常包含在请求体中</u>。如果请求体中包含中文或其他非ASCII字符，并且服务器未设置正确的字符集，就可能导致乱码问题。通过设置请求体的字符集，比如使用`request.setCharacterEncoding("UTF-8")`来告诉服务器如何解析请求体的数据，可以有效避免乱码问题。
    - **GET请求乱码问题**：与POST不同，<u>GET请求的参数是通过URL传递的</u>，通常以查询字符串的形式出现在URL中。如果URL中包含中文或其他非ASCII字符，并且服务器未正确解析这些字符，就可能导致乱码问题。<u>设置请求体字符集的方法并不能解决GET请求中的乱码问题，因为这些字符已经包含在URL中，而非请求体中。</u>


- **处理post请求乱码问题**
   ```java
   // 设置请求体的字符集。（显然这个方法是处理POST请求的乱码问题。这种方式并不能解决get请求的乱码问题。）
   // Tomcat10之后，request请求体当中的字符集默认就是UTF-8，不需要设置字符集，不会出现乱码问题。
   // Tomcat9前（包括9在内），如果前端请求体提交的是中文，后端获取之后出现乱码。
    request.setCharacterEncoding("UTF-8");
   ```

- **处理get请求乱码问题**
   ```java
   // get请求发送的时候，数据是在请求行上提交的，不是在请求体当中提交的。
   // 方案：修改CATALINA_HOME/conf/server.xml配置文件
   <Connector URIEncoding="UTF-8" />
   // 注意：从Tomcat8之后，URIEncoding的默认值就是UTF-8，所以GET请求也没有乱码问题了。
   ```
- **处理响应乱码问题**
```java
// 在Tomcat9之前（包括9），响应中文也是有乱码的，怎么解决这个响应的乱码？
response.setContentType("text/html;charset=UTF-8");
// 在Tomcat10之后，包括10在内，响应中文的时候就不在出现乱码问题了。以上代码就不需要设置UTF-8了。
```

3. 获取应用的根路径
```java
// 获取应用的根路径(动态的获取)
String contextPath = request.getContextPath();
```

4. 获取请求方式
```java
//获取请求方式
String method = request.getMethod();
```

5. 获取请求的URI
```java
// 获取请求的URI
String uri = request.getRequestURI();  // /aaa/testRequest
```

6. 获取servlet path
```java
// 获取servlet path
String servletPath = request.getServletPath(); //   /testRequest
```
