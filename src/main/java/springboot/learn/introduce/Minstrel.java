package springboot.learn.introduce;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

import java.io.PrintStream;

/**
 * Created by ztwang on 2017/7/13 0013.
 */
@Aspect
public class Minstrel {
    private PrintStream printStream;

    public Minstrel(PrintStream printStream) {
        this.printStream = printStream;
    }

    @Pointcut("execution(* *.embarkOnQuest())")
    public void embark(){}

    @Before("embark()")
    public void singBeforeQuest() {
        printStream.println("Fa la la, the knight is so brave!");
    }

    @After("embark()")
    public void singAfterQuest() {
        printStream.println("Tee hee hee, the brave knight " +
                            "did embark on a quest!");
    }
}
