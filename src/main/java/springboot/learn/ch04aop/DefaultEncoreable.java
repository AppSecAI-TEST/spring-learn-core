package springboot.learn.ch04aop;

/**
 * Created by ztwang on 2017/8/9 0009.
 */
public class DefaultEncoreable implements Encoreable{
    @Override
    public void performEncore() {
        System.out.println("Encoreable Performance");
    }
}
