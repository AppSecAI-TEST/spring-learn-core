package springboot.learn.ch02wiringbean;

/**
 * Created by ztwang on 2017/7/14 0014.
 */
public abstract class AbstractCrawl {
    protected String name;

    public AbstractCrawl(String name) {
        this.name = name;
    }
    protected void log() {
        System.out.println(name + " invoke run()");
        run();
    }
    abstract void run();
}



