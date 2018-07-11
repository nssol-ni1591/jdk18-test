package app.speaker.alt;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Alternative;

import app.speaker.annotation.Chainese;
import util.Print;

@Alternative
@Dependent
@Chainese
public class ChaineseSpeaker implements Speaker {

    @Override
    public void speak() {
        Print.println("(Alt) 你好!!");
    }

}
