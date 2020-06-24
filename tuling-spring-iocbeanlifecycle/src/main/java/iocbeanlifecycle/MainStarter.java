package iocbeanlifecycle;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MainStarter {
    public static void main(String[] args) {

        // ClassPathXmlApplicationContext的替代品，是用annotation注入bean的ApplicationContext
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MainConfig.class);

        Person person = (Person) context.getBean("person");

        System.out.println(person.getGender());

    }
}
