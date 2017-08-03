package springboot.learn.ch02wiringbean;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.SystemOutRule;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by ztwang on 2017/7/14 0014.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = CDPlayerConfig.class)
public class CrawlTest {

    @Rule
    public SystemOutRule systemOutRule = new SystemOutRule().enableLog();

    @Autowired
    private AbstractCrawl crawl1;

    @Autowired
    private AbstractCrawl crawl2;

    @Test
    public void testCrawl() {
        Assert.assertNotNull(crawl1);
        crawl1.log();
        Assert.assertEquals("Crawl1 invoke run()\r\n" +
                            "Crawl1 run\r\n", systemOutRule.getLog());

        // 单例模式，装配的crawl是同一个
        Assert.assertSame(crawl1, crawl2);
    }
}
