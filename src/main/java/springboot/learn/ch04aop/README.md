# AOP概念
## 作用
将应用中的横向关注点与业务逻辑进行解耦
## 术语
通知(advice): 定义切面何时使用与要完成的工作
- Before, After, After-returning, After-throwing, Around
连接点(join point): 能够应用通知的所有点
切点(ponitcut): 通知被应用到的具体的点(在哪些连接点)
织入(weaving): 将切面应用到目标对象并创建代理对象
- 编译期: 切面在目标编译期被织入 需要特殊编译器 如AspectJ的编译器
- 类加载期: 在目标类被加载期织入 需要特殊类加载器 如AspectJ 5的类加载器
- 运行期: 运行阶段为目标对象动态创建代理对象 如Spring AOP

# 标准Spring AOP
代理对象包裹目标对象，拦截调用，先执行切面逻辑然后将调用发送给真正的bean

## 一般方式
1. 创建目标对象接口
```java
public interface Performance {
    void perform();
}
```

2. 创建目标对象类
```java
@Component
@Qualifier("great")
public class GreatChorus implements Performance {
    @Override
    public void perform() {
        System.out.println("Great Performance");
    }
}
```
```java
@Component
@Qualifier("bad")
public class BadChorus implements Performance{
    @Override
    public void perform() {
        System.out.println("Bad Performance");
        throw new RuntimeException("Bad Performance");
    }
}
```

3. 创建切面POJO
```java
@Aspect
@Component  //切面也是POJO，也需要被spring创建
public class Audience {

    @Pointcut("execution(public * springboot.learn.ch04aop.Performance.perform(..))")
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
```

4. java config
```java
@Configuration
@EnableAspectJAutoProxy //对目标对象自动创建代理对象
@ComponentScan  //自动创建bean
public class ConcertConfig {
}
```

5. 测试
```java
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ConcertConfig.class)
public class ConcertTest {

    @Autowired
    @Qualifier("great")
    private Performance great;

    @Autowired
    @Qualifier("bad")
    private Performance bad;

    @Test(expected = RuntimeException.class)
    public void testConcertAspect() {
        great.perform();
        bad.perform();
    }

}
/** output
Silencing Cell Phones
Taking Seats
Great Performance
CLAP CLAP CLAP!!!
Silencing Cell Phones
Taking Seats
Bad Performance
Demanding A Refund
*/
```
## 环绕通知
```java
@Around("somePointCut(..)")
public void someAdvice(ProceedingJoinPoint jp) {
    //more...
    jp.proceed(); //调用目标对象的方法
    /more...
}
```
## 处理通知中的参数
```java
@Aspect
@Component
public class TrackCounter {
    private Map<Integer, Integer> trackCounters = new ConcurrentHashMap<>();    //线程安全

    @Pointcut("execution(* springboot.learn.ch04aop.BlankDisc.playTrack(int)) " +
            "&& args(trackNumber)") //与切点方法参数中的名称相匹配
    public void trackPlayed(int trackNumber){}

    @Before("trackPlayed(i)") //与通知方法参数中的名称相匹配
    public void countTrack(int i) {
        int curCounter = getPlayCount(i);
        trackCounters.put(i, curCounter + 1);
    }

    public int getPlayCount(int trackNumber) {
        return trackCounters.getOrDefault(trackNumber, 0);
    }
}
```

## 通过aop引入新功能
> 代理对象将被通知的bean与引入的代理包含起来，现有方法的调用交给被通知的bean，被引入方法的调用交给引入的代理

```java
public interface Encoreable {
    void performEncore();
}
```

```java
public class DefaultEncoreable implements Encoreable{
    @Override
    public void performEncore() {
        System.out.println("Encoreable Performance");
    }
}
```

```java
@Aspect
@Component
public class EncoreableIntroducer {

    @DeclareParents(value = "springboot.learn.ch04aop.Performance+",
            defaultImpl = DefaultEncoreable.class)
    public static Encoreable encorable;
}
```

```java
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ConcertConfig.class)
public class ConcertTest {
    @Test
    public void testIntroduce() {
        ((Encoreable) great).performEncore();
    }
}
    
```