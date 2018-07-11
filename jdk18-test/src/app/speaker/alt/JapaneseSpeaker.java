package app.speaker.alt;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Alternative;

import app.speaker.annotation.Japanese;
import util.Print;

@Alternative
@Dependent
@Japanese
public class JapaneseSpeaker implements Speaker {

    @Override
    public void speak() {
        Print.println("(Alt) みなさん　こんにちは!!");
    }

}
