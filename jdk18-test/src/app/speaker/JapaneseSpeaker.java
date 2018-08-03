package app.speaker;

import javax.enterprise.context.Dependent;

import app.speaker.annotation.Japanese;
import util.Print;

@Dependent
@Japanese
public class JapaneseSpeaker implements Speaker {

    @Override
    public void speak() {
        Print.println("みなさん　こんにちは!!");
    }

}
