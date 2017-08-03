# bean装配
> bean 有三种装配方式，最常用的是自动装配(包括javaConfig装配，xml装配)

## 自动装配
`@Component`可以写上自定义的Bean ID`@Component("bean_id")` 也可用采用java DI规范用`@Name("bean_id")`代替

`@ComponentScan`默认将配置类所在的包当成扫描的base-package，可以手动设置`@ComponentScan("base_pachage")`或`@ComponentScan(basePackages={"base_package1", "base_package2"})`(这样做类型不安全not type-safe)或
`@ComponentScan(basePackageClasses={Class1.class, Class2.class})`(将Class1和Class2所在的包作为base-package， 一般可以创建一个空接口在为class1,class2，防止想把包含业务逻辑的class删除带来的麻烦)

`@Autowired` 刚刚的`@ComponentScan`只是负责自动将class声明为bean，但当需要在某个组件内依赖其他组件时，就需要spring自动在上下文中找到合适的bean，然后完成自动装配(autowiring)，`@Autowired`可以在构造器，属性域，任何普通方法上`@Autowired public void doSomthing(OneComponent arg)`，有且只有一个bean符合则装配成功，找不到报错(`@Autowired(required=false)` 可以在找不到的时候不装配)，多于一个报错，`@Autowired`是spring特有注解，java DI标准是`@Inject`

```java
public interface CompactDisc {
    void play();
}

public interface MediaPlayer {
    void play();
}

@Component
public class SgtPeppers implements CompactDisc {
    private static final String title = "Sgt. Pepper's Lonely Hearts Club Band";
    private static final String artist = "The Beatles";
    @Override
    public void play() {
        System.out.println("Playing " + title + " by " + artist);
    }
}

@Component
public class CDPlayer implements MediaPlayer {
    private CompactDisc cd;

    @Autowired
    public CDPlayer(CompactDisc cd) {
        this.cd = cd;
    }

    @Override
    public void play() {
        cd.play();
    }
}

/*
 * 在配置类中开启自动装配(默认是关闭的), 
 * 没设定base-package情况下，自动扫描和CDPlayerConfig处于同一包下的class
 * xml里是<context:component-scan base-package="xxx">
 */
@Configuration
@ComponentScan
public class CDPlayerConfig {
}



```