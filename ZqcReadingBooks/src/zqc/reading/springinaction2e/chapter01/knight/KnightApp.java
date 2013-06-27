package zqc.reading.springinaction2e.chapter01.knight;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.FileSystemResource;

public class KnightApp {

    /**
     * @param args
     */
    public static void main(String[] args) {

        // Spring 2.x usage, hello.xml must be in the work directory of the eclipse...
        // BeanFactory factory = new XmlBeanFactory(new
        // FileSystemResource("hello.xml"));
        //
        // KnightOfTheRoundTable knight = (KnightOfTheRoundTable)
        // factory.getBean("knight");
        // System.out.println(knight.embarkOnQuest());

        // Spring 3.x usage, hello.xml must be in the src directory of the eclipse
        ApplicationContext ctx = new ClassPathXmlApplicationContext("hello.xml");
        Knight knight = (Knight)ctx.getBean("knight");
        System.out.println(knight.embarkOnQuest());
    }

}
