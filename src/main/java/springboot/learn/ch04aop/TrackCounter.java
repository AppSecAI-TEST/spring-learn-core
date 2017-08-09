package springboot.learn.ch04aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by ztwang on 2017/8/9 0009.
 */
@Aspect
@Component
public class TrackCounter {
    private Map<Integer, Integer> trackCounters = new ConcurrentHashMap<>();

    @Pointcut("execution(* springboot.learn.ch04aop.BlankDisc.playTrack(int)) " +
            "&& args(trackNumber)")
    public void trackPlayed(int trackNumber){}

    @Before("trackPlayed(trackNumber)")
    public void countTrack(int trackNumber) {
        int curCounter = getPlayCount(trackNumber);
        trackCounters.put(trackNumber, curCounter + 1);
    }

    public int getPlayCount(int trackNumber) {
        return trackCounters.getOrDefault(trackNumber, 0);
    }
}
