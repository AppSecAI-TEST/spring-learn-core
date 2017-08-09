package springboot.learn.ch04aop;

import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

/**
 * Created by ztwang on 2017/8/9 0009.
 */
@Aspect
@Component
public class Audience {

    @Pointcut("execution(* springboot.learn.ch04aop.Performance.perform(..))")
    public void performance(){}

    @Before("performance()")
    public void silenceCellPhones() {
        System.out.println("Silencing Cell Phones");
    }

    @Before("performance()")
    public void takeSeats() {
        System.out.println("Taking Seats");
    }

    @AfterReturning("performance()")
    public void applause() {
        System.out.println("CLAP CLAP CLAP!!!");
    }

    @AfterThrowing("performance()")
    public void demandRefund() {
        System.out.println("Demanding A Refund");
    }
}
