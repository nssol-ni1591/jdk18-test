package test.weld.speaker;

import javax.enterprise.inject.se.SeContainer;
import javax.enterprise.inject.se.SeContainerInitializer;
import javax.inject.Inject;

import org.junit.Test;

import app.speaker.Speaker;
import app.speaker.annotation.Chainese;
import app.speaker.annotation.English;
import app.speaker.annotation.Japanese;
import util.weld.WeldRunner;
import util.weld.WeldWrapper;

/*
 * WeldのAlternativesをテストするためのモジュール
 */
public class SpeakerTest implements WeldRunner {

	@Inject private Speaker speaker;
	@Inject @English  private Speaker english;
	@Inject @Chainese private Speaker chainese;
	@Inject @Japanese private Speaker japanese;

	//private SpeakerTest application;

	@Override
	public int start(String...args) {
		this.speaker.speak();
		return 0;
	}

	public void speak() {
		this.speaker.speak();
	}
	public void english() {
		this.speaker.speak();
	}
	public void chainese() {
		this.chainese.speak();
	}
	public void japanese() {
		this.japanese.speak();
	}
/*
	public void before() {
		// for weld-se 2.2.x
		//Weld weld = new Weld()
		//try (WeldContainer container = weld.initialize()instance()) {-
		
		// for weld-se 3.0.x
		try (SeContainer container = SeContainerInitializer.newInstance().initialize()) {
			application = container.select(SpeakerTest.class).get();
		}
	}
*/
	@Test
	public void test1() {
		new WeldWrapper(SpeakerTest.class).weld(new String[0]);
		// @Default により英語が出力されればOK
	}
	@Test
	public void test2() {
		try (SeContainer container = SeContainerInitializer.newInstance().initialize()) {
			SpeakerTest application = container.select(SpeakerTest.class).get();
			application.speak();
		}
		// @Default により英語が出力されればOK
	}
	@Test
	public void test3() {
		try (SeContainer container = SeContainerInitializer.newInstance().initialize()) {
			SpeakerTest application = container.select(SpeakerTest.class).get();
			application.english();
		}
		// @English により英語が出力されればOK
	}
	@Test
	public void test4() {
		try (SeContainer container = SeContainerInitializer.newInstance().initialize()) {
			SpeakerTest application = container.select(SpeakerTest.class).get();
			application.japanese();
		}
		// @Japanese により日本語で出力されればOK
	}
	@Test
	public void test5() {
		try (SeContainer container = SeContainerInitializer.newInstance().initialize()) {
			SpeakerTest application = container.select(SpeakerTest.class).get();
			application.chainese();
		}
		// @Chainese により中国で出力されればOK
	}

}
