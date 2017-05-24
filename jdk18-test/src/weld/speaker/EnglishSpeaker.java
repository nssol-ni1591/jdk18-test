package weld.speaker;

import javax.enterprise.inject.Alternative;

@Alternative
public class EnglishSpeaker implements Speaker {

    @Override
    public void speak() {
        System.out.println("Hello World!!");
    }

}
