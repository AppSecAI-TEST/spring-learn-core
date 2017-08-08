package springboot.learn.ch03advancebean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

/**
 * Created by ztwang on 2017/8/8 0008.
 */
@Configuration
@PropertySource(value = {"classpath:/ch03app.properties"})
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
