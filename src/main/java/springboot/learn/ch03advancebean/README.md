# 条件化的装配
1. `@Profile`
> 解决Bean跨越不同部署环境的尴尬

控制激活profile的是两个参数`spring.profiles.default`和`spring.profiles.active`后者覆盖前者

> 优秀的实践是在开发环境中设置`spring.profiles.default`在生产、测试环境中的环境变量、JVM系统属性设置`spring.profiles.active`

这两个参数可以设置在:
- application.properties里 `DispatcherServlet`的初始化参数
- Web应用的上下文参数
- 作为JNDI条目
- 环境变量(操作系统的)
- JVM系统变量(java -D\<name\>=\<value\> -jar xx.jar)
- 集成测试类上的@ActiveProfiles\
2. `@Conditional` 
> spring4 才有，比profile更加精确控制Bean的创建

传入一个实现了`Condition`接口的类，根据条件决定是否创建Bean

# 限定符注解
> 一个接口有多个实现的Bean，在创建Bean时没问题，但在装配(wiring)时会产生
歧义(`NoUniqueBeanDefinitionException`)

1. 使用`@Primary`设置首选装配的Bean
2. 使用`@Qualifier`
    - 直接在进行装配的方法，构造方法等地方使用(默认使用Bean的类名,首字母小写)
    - 在被装配的Bean和需要该Bean的地方上使用
3. 自定义`@Qualifier`
```java
@Target({ElementType.CONSTRUCTOR, ElementType.FIELD,
        ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Qualifier
public @interface Cold{}

@Target({ElementType.CONSTRUCTOR, ElementType.FIELD,
        ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Qualifier
public @interface Creamy{}

@Component
@Cold
@Creamy
public class Popsicle implements Desert{}

@Autowired
@Cold
@Creamy
public void setDesert{}
```

# Bean的作用域
> `@Scope`使用的时候可以是传入字符串，但更安全的方式是使用类常量

1. Singleton 单例 整个应用只创建一个实例，如果类是易变的那么使用这个模式不安全，因为实例被用过一次后，重用的时候已经改变了
2. Prototype 原型 每次注入获取时都会重新创建 `ConfigurableBaenFactory.SCOPE_PROTOTYPE`
3. Session 会话 web应用中，每个会话创建一个 如购物车 `WebApplciationContext.SCOPE_SESSION`
4. Request 请求 web应用中，每个请求创建一个
`WebApplciationContext.SCOPE_REQUEST`

3，4两点`@Scope`中需要设置代理`proxyMode=ScopedProxyMode.INTERFACES`，代理将真正的处理交给Session范围内的Bean

# 运行时值注入
## 从properties文件中获取
```java
@Configuration
// @PropertySource(value = {"classpath:/ch03app.properties"}, encoding='utf-8')
@PropertySource("classpath:/ch03app.properties")
public class AppConfig {
    @Autowired
    Environment env;

    @Bean
    public App getApp() {
        return new App(
                env.getProperty("app.title"),
                env.getProperty("app.author"));
    }
}

String getPropperty(String key)
String getPropperty(String key, String defaultValue)
T getProperty(String key, Class<T> type) //比如从文件中获得String但实际需要Integer就使用此来转换
T getProperty(String key, Class<T> type, T defaultValue)
```

# SpEL spring表达式
> 可以用在装配bean上也可以用在使用Thymeleaf模板作为视图的web应用上用以引用模型数据 样式"#{...}"

1. 字面量 `#{1.2} #{false} #{'hello'} #{9.87E4}`
2. 引用Bean属性
    - `#{sgtPress.artist}` 
    - `#{sgtPress.selectorArtist()?.toUpperCase()} //?.防止null`
3. 引用静态成员与方法 `T(java.lang.Math).PI`
4. 同java的算术运算符(`^`表示幂) 逻辑运算符(and or not |)
5. 同java三目运算符 和检查null场景的三目运算(`#{dict.title ?: 'Hahah'} //条件为null则返回默认结果 Hahah`)
6. 正则表达式 `#{admin.email matches 'xxx'}`
7. 计算集合
    - `#{songs[4].title}`
    - `#{'Test this SpEL'[4]}`
    - `#{songs.?[artist eq 'Aeromith']}` 对集合进行过滤 []里为条件
    - `#{songs.^[condition]}` 查找第一个符合condition的song
    - `#{songs.$[condition]}` 查找最后一个符合condition的song
    - `#{songs.![title]}` 将歌曲的title属性投身成为新的列表

