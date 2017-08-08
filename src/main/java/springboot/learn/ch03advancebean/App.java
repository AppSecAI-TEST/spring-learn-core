package springboot.learn.ch03advancebean;

import org.springframework.stereotype.Component;

/**
 * Created by ztwang on 2017/8/8 0008.
 */
public class App {
    private String title;
    private String author;

    public App(String title, String author) {
        this.title = title;
        this.author = author;
    }

    @Override
    public String toString() {
        return "App{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                '}';
    }
}
