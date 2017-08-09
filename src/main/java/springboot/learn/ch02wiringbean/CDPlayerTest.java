package springboot.learn.ch02wiringbean;

import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.SystemOutRule;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import springboot.learn.common.CompactDisc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by ztwang on 2017/7/14 0014.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = CDPlayerConfig.class)
public class CDPlayerTest {

    @Rule
    public final SystemOutRule sysOutRule = new SystemOutRule ().enableLog();

    @Autowired
    private CompactDisc compactDisc;

    @Autowired
    private MediaPlayer player;

    @Test
    public void cdShouldNotBeNull() {
        assertNotNull(compactDisc);
    }

    @Test
    public void play() {
        player.play();
        //windows 下换行为\r\n
        //记得用logback.xml关闭log，不然控制台输出的log信息会影响单元测试
        assertEquals("Playing Sgt. Pepper's Lonely " +
                "Hearts Club Band by The Beatles\r\n", sysOutRule.getLog());
    }
}
