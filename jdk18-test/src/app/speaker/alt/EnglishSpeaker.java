package app.speaker.alt;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Alternative;
import javax.enterprise.inject.Default;

import app.speaker.annotation.English;
import util.Print;

@Alternative
@Dependent
@English
@Default
public class EnglishSpeaker implements Speaker {

    @Override
    public void speak() {
        Print.println("(Alt) Hello World!!");
    }

}
