package zqc.reading.springinaction2e.chapter01.hello;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.FileSystemResource;

public class HelloApp {

    /**
     * @param args
     */
    public static void main(String[] args) {

        // spring 2.x usage
        // BeanFactory factory = new XmlBeanFactory(new
        // FileSystemResource("hello.xml"));
        // GreetingService service = (GreetingService) factory.getBean("greetingService");
        // service.sayGreeting();

        // spring 3.x usage
        ApplicationContext ctx = new ClassPathXmlApplicationContext("hello.xml");
        GreetingService service = (GreetingService) ctx.getBean("greetingService");
        service.sayGreeting();
    }
}
