1. NIO (not added up to now)
2. Netty
3. WebService

---

### WebService

1. Helloworld
    - 建工程添jar包
    - `HelloWorld`服务接口(`@WebService`注解)
    - `HelloWorldImpl`服务实现类
    - `MainServer`服务启动类(`JaxWsServerFactoryBean`)
    - 测试: 
        - Ecplise:直接测试
        - 建ClientTest调用对外暴露的服务: `JaxWsProxyFactoryBean`