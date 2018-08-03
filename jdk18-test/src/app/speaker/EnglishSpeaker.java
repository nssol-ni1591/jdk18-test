package app.speaker;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Default;

import app.speaker.annotation.English;
import util.Print;

@Dependent
@Default
@English
public class EnglishSpeaker implements Speaker {

    @Override
    public void speak() {
        Print.println("Hello World!!");
    }

}
