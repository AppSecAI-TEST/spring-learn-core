package springboot.learn.ch04aop;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * Created by ztwang on 2017/8/9 0009.
 */
@Component
@Qualifier("great")
public class GreatChorus implements Performance {
    @Override
    public void perform() {
        System.out.println("Great Performance");
    }
}
