package app.weld.speaker;

import javax.enterprise.inject.Alternative;

import util.Print;

@Alternative
public class EnglishSpeaker implements Speaker {

    @Override
    public void speak() {
        Print.println("Hello World!!");
    }

}
