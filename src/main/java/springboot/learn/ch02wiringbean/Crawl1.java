package springboot.learn.ch02wiringbean;

import org.springframework.stereotype.Component;

/**
 * Created by ztwang on 2017/7/14 0014.
 */
@Component
public class Crawl1 extends AbstractCrawl {
    private static String name = Crawl1.class.getSimpleName();
    public Crawl1() {
        super(name);
    }

    @Override
    void run() {
        System.out.println("Crawl1 run");
    }
}
