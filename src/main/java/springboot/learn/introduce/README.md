# 使用注解方式进行Bean的DI与AOP介绍
1. `@Configuration` 注释一个类表明它的主要目的是作为bean定义的来源(相当于一个xml文件)，此外，`@Configuration`
注释的类允许inter-bean依赖关系在相同的类中，通过简单地调用其他`@Bean`方法被定义
2. `@Bean`注释是用来表示一个方法实例化(相当于xml文件中的\<bean/\>)，配置和初始化一个由Spring IoC容器管理的新的对象
3. `AnnotationConfigApplicationContext`是一个Spring应用上下文,从一个或多个基于Java的配置类中加载spring应用上下文
```java
@Configuration
public class AppConfig {

    @Bean
    public MyService myService() {
        return new MyServiceImpl();
    }

}

// 传入用@Component注解的bean
public static void main(String[] args) {
    ApplicationContext ctx = new AnnotationConfigApplicationContext(MyServiceImpl.class, Dependency1.class, Dependency2.class);
    MyService myService = ctx.getBean(MyService.class);
    myService.doStuff();
}

// 传入@Configuration注解的class
public static void main(String[] args) {
    ApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);
    MyService myService = ctx.getBean(MyService.class);
    myService.doStuff();
}

// 使用register
public static void main(String[] args) {
    AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
    ctx.register(AppConfig.class, OtherConfig.class);
    ctx.register(AdditionalConfig.class);
    ctx.refresh();
    MyService myService = ctx.getBean(MyService.class);
    myService.doStuff();
}
```
4. 使用`@EnableAspectJAutoProxy`开启aop，相当于`<aop:aspectj-autoproxy/>`，自动扫面配置下bean，如果该bean是`@Aspect`(被一个或多个切面通知),则将自动为一个bean生产一个代理，来拦截方法调用并保证通知按需执行。
```java
@Configuration
@EnableAspectJAutoProxy
public class AppConfig {
    //other beans

    //首先包含切面的bean也是一个普通的bean,也可以进行DI
    @Bean
    public NotVeryUsefulAspect notVeryUsefulAspect() {
        return new NotVeryUsefulAspect();
    }
}

@Aspect
public class NotVeryUsefulAspect {
    @Pointcut("execution(...)")
    public void pointcut1(){}

    @Before("pointcut1()")
    public void doBefore(){
        //do Something
    }
}
```

# 应用上下文
> 应用中各种bean生存的容器，上下文负责管理他们的整个生命周期
- `AnotationConfigApplicaionContext` 从一个或多个基于Java的配置类中加载spring应用上下文
- `AnnotationConfigWebApplicationContext` 从一个或多个基于Java的配置类中加载spring web应用上下文
- `ClassPathXmlApplicationContext` 从类路径下一个或多个xml文件中加载上下文
- `FileSystemXmlApplciationContext` 从文件系统下的一个或多个xml文件中加载上下文
- `XmlWebApplciationContect` 从Web应用下的一个或多个xml文件中加载上下文