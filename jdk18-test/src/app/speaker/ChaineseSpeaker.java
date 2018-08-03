package app.speaker;

import javax.enterprise.context.Dependent;

import app.speaker.annotation.Chainese;
import util.Print;

@Dependent
@Chainese
public class ChaineseSpeaker implements Speaker {

    @Override
    public void speak() {
        Print.println("你好!!");
    }

}
