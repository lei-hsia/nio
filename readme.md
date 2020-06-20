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
        
    (onhold for now)
    
---

### luban: BIO->NIO

1. 因为`serverSocket`的`accept`和`read`两个方法都阻塞，所以如果要支持并发，
就需要多线程； i.e. 不支持并发; 如果支持并发，需要一个线程绑定那个程序，那么
只阻塞这一个线程，所以BIO不支持并发
2. 但是BIO: 巨大浪费: 多线程造成服务器资源浪费
3. NIO: 用单线程处理并发
4. 单线程BIO为什么不能处理多并发: 见 `BIOMysqlServer`