package iocbeanlifecycle;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/*
没有这个类，就只是main主启动类，Person这个实体类和AOP功能的processor类(注:processor类也是component,要注解)
但是并没有创建bean加入到spring容器中;  注解配置进入IOC容器: @Configuration + @Bean

如果没有本类，就: Exception in thread "main" ....factory.NoSuchBeanDefinitionException: No bean named 'person' available
*/
@Configuration // @Configuration ~ beans.xml
@ComponentScan(basePackages = {"iocbeanlifecycle"})
public class MainConfig {

    @Bean(initMethod = "initPerson")
    public Person person() {
        Person person = new Person();
        person.setGender("M");
        person.setName("lei");
        return person;
    }
}
