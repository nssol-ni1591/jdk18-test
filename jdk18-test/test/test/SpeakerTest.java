package test;

import javax.enterprise.inject.se.SeContainer;
import javax.enterprise.inject.se.SeContainerInitializer;
import javax.inject.Inject;

import org.junit.Test;

import app.speaker.Speaker;
import util.weld.WeldRunner;
import util.weld.WeldWrapper;

/*
 * WeldのAlternativesをテストするためのモジュール
 */
public class SpeakerTest implements WeldRunner {

	@Inject private Speaker speaker;

	@Override
	public int start(String...args) {
		this.speaker.speak();
		return 0;
	}

	public void run() {
		this.speaker.speak();
	}

	@Test
	public void test1() {
		// for weld-se 2.2.x
		//Weld weld = new Weld()
		//try (WeldContainer container = weld.initialize()instance()) {-
		
		// for weld-se 3.0.x
		try (SeContainer container = SeContainerInitializer.newInstance().initialize()) {
			SpeakerTest application = container.select(SpeakerTest.class).get();
			application.run();
		}
	}
	@Test
	public void test2() {
		new WeldWrapper(SpeakerTest.class).weld(new String[0]);
	}

}
