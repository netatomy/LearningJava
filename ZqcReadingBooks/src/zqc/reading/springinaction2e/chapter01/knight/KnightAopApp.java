package zqc.reading.springinaction2e.chapter01.knight;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class KnightAopApp {

    /**
     * @param args
     */
    public static void main(String[] args) {

        ApplicationContext ctx = new ClassPathXmlApplicationContext("knight_aop.xml");
        Knight knight = (Knight)ctx.getBean("knight");
        System.out.println(knight.embarkOnQuest());

    }

}
