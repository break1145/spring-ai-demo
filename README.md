# Getting Started

### Reference Documentation

For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/3.4.4/maven-plugin)
* [Create an OCI image](https://docs.spring.io/spring-boot/3.4.4/maven-plugin/build-image.html)

### Maven Parent overrides

Due to Maven's design, elements are inherited from the parent POM to the project POM.
While most of the inheritance is fine, it also inherits unwanted elements like `<license>` and `<developers>` from the
parent.
To prevent this, the project POM contains empty overrides for these elements.
If you manually switch to a different parent and actually want the inheritance, you need to remove those overrides.




### demo
1. 实现了基本的sse流推送。在[application.yaml](src%2Fmain%2Fresources%2Fapplication.yaml) 替换api-key，本地启动后调用 POST http://localhost:8080/chat/stream
```http request
Headers:
Content-Type: application/json
Accept: text/event-stream
Body:
   {
   "prompt": "评价一下丁真"
   }
```