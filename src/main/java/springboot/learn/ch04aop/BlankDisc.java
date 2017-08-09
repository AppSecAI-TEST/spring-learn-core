package springboot.learn.ch04aop;

import org.springframework.stereotype.Component;
import springboot.learn.common.CompactDisc;

import java.util.List;

/**
 * Created by ztwang on 2017/8/9 0009.
 */
public class BlankDisc implements CompactDisc{
    private String title;
    private String author;
    private List<String> tracks;

    public String getTitle() {
        return title;
    }

    @Override
    public void play() {
        System.out.println("Playing " + title + " by " + author);
    }

    @Override
    public void playTrack(int i) {
        if (tracks == null || tracks.isEmpty()) return;
        int len = tracks.size();
        if (i <= 0 || i > len) return;
        System.out.println(tracks.get(i - 1));
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public List<String> getTracks() {
        return tracks;
    }

    public void setTracks(List<String> tracks) {
        this.tracks = tracks;
    }

}
