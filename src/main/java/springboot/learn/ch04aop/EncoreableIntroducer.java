package springboot.learn.ch04aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.DeclareParents;
import org.springframework.stereotype.Component;

/**
 * Created by ztwang on 2017/8/9 0009.
 */
@Aspect
@Component
public class EncoreableIntroducer {

    @DeclareParents(value = "springboot.learn.ch04aop.Performance+",
            defaultImpl = DefaultEncoreable.class)
    public static Encoreable encorable;
}
