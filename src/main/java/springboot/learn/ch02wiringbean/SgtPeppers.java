package springboot.learn.ch02wiringbean;

import org.springframework.stereotype.Component;
import springboot.learn.common.CompactDisc;

/**
 * Created by ztwang on 2017/7/14 0014.
 */
@Component
public class SgtPeppers implements CompactDisc {
    private static final String title = "Sgt. Pepper's Lonely Hearts Club Band";
    private static final String artist = "The Beatles";
    @Override
    public void play() {
        System.out.println("Playing " + title + " by " + artist);
    }

    @Override
    public void playTrack(int i) {

    }
}
