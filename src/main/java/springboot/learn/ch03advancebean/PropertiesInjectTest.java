package springboot.learn.ch03advancebean;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by ztwang on 2017/8/8 0008.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class PropertiesInjectTest {

    @Autowired
    private App app;

    @Test
    public void testAppContent() {
        System.out.println(app);
    }
}
