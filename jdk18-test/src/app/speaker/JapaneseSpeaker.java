package app.speaker;

import javax.enterprise.inject.Alternative;
import javax.enterprise.inject.Default;

import util.Print;

@Default	//⇒Alternayiveとの組み合わせでは意味を持たない
@Alternative
public class JapaneseSpeaker implements Speaker {

    @Override
    @Default
    public void speak() {
        Print.println("みなさん　こんにちは!!");
    }

}
