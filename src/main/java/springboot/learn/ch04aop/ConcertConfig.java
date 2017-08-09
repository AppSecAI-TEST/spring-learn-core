package springboot.learn.ch04aop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import springboot.learn.common.CompactDisc;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;


/**
 * Created by ztwang on 2017/8/9 0009.
 */

@Configuration
@EnableAspectJAutoProxy
@ComponentScan
@PropertySource("classpath:/compact-disc.properties")
public class ConcertConfig {
    @Autowired
    private Environment env;

    @Bean
    public CompactDisc sgtPeppers() {
        BlankDisc cd = new BlankDisc();
        cd.setTitle(env.getProperty("cd.title"));
        cd.setAuthor(env.getProperty("cd.author"));
        String lyric = env.getProperty("cd.lyric");
        StringTokenizer tokenizer = new StringTokenizer(lyric,"\n");
        List<String> tracks = new ArrayList<>();
        while (tokenizer.hasMoreTokens()) {
            tracks.add(tokenizer.nextToken());
        }
        cd.setTracks(tracks);
        return cd;
    }
}
