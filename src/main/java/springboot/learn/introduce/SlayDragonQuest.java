package springboot.learn.introduce;

import java.io.PrintStream;

/**
 * Created by ztwang on 2017/7/13 0013.
 */
public class SlayDragonQuest implements Quest{
    private PrintStream printStream;

    public SlayDragonQuest(PrintStream printStream) {
        this.printStream = printStream;
    }

    @Override
    public void embark() {
        printStream.println("Embarking on quest to slay the dragon !");
    }
}
