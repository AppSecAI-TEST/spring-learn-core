package springboot.learn.ch04aop;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import springboot.learn.common.CompactDisc;

import static org.junit.Assert.assertEquals;

/**
 * Created by ztwang on 2017/8/9 0009.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ConcertConfig.class)
public class ConcertTest {

    @Autowired
    @Qualifier("great")
    private Performance great;

    @Autowired
    @Qualifier("bad")
    private Performance bad;

    @Autowired
    private CompactDisc cd;

    @Autowired
    private TrackCounter counter;

    @Test(expected = RuntimeException.class)
    public void testConcertAspect() {
        great.perform();
        bad.perform();
    }

    @Test
    public void testTrackCounter() {
        cd.playTrack(1);
        cd.playTrack(2);
        cd.playTrack(3);
        cd.playTrack(3);
        cd.playTrack(3);
        cd.playTrack(3);
        cd.playTrack(7);
        cd.playTrack(7);

        assertEquals(1, counter.getPlayCount(1));
        assertEquals(1, counter.getPlayCount(2));
        assertEquals(4, counter.getPlayCount(3));
        assertEquals(0, counter.getPlayCount(4));
        assertEquals(0, counter.getPlayCount(5));
        assertEquals(0, counter.getPlayCount(6));
        assertEquals(2, counter.getPlayCount(7));
    }

    @Test
    public void testIntroduce() {
        ((Encoreable) great).performEncore();
    }

}
