package test;

import javax.enterprise.inject.se.SeContainer;
import javax.enterprise.inject.se.SeContainerInitializer;
import javax.inject.Inject;

import org.junit.Test;

import app.speaker.alt.Speaker;
import app.speaker.annotation.English;
import app.speaker.annotation.Japanese;
import util.weld.WeldRunner;
import util.weld.WeldWrapper;

/*
 * WeldのAlternativesをテストするためのモジュール
 * Alternativeは同じinterfaceを実装した複数のクラスから特定のクラスに絞り込む場合に使用する.
 * 絞り込みの結果は複数のクラスでも問題ないが、Inject側では1クラスになるようにQualitierもしくはDefaultが必要になる.
 * 以下のクラスでは、Alternativesタグに、EnglishとJapaneseを指定あり、その中からAnnotationでクラスを特定する.
 * なお、Englishには@Defaultが指定してあるので、@Inject側にQualitierがない場合はEnglishが選択される.
 */
public class AltSpeakerTest implements WeldRunner {

	@Inject private Speaker speaker;
	@Inject @English  private Speaker english;
	@Inject private Speaker chainese;	// @Chaineseを指定するとWeldの初期化中にエラーになる
	@Inject @Japanese private Speaker japanese;

	//private AltSpeakerTest application;

	@Override
	public int start(String...args) {
		this.speaker.speak();
		return 0;
	}

	public void speak() {
		this.speaker.speak();
	}
	public void english() {
		this.chainese.speak();
	}
	public void chainese() {
		this.chainese.speak();
	}
	public void japanese() {
		this.japanese.speak();
	}

	/*
	@Before
	public void before() {
		// for weld-se 2.2.x
		//Weld weld = new Weld()
		//try (WeldContainer container = weld.initialize()instance()) {-
		
		// for weld-se 3.0.x
		try (SeContainer container = SeContainerInitializer.newInstance().initialize()) {
			application = container.select(AltSpeakerTest.class).get();
		}
	}
	*/

	@Test
	public void test1() {
		new WeldWrapper(AltSpeakerTest.class).weld(new String[0]);
		// Alternative + @Default により、英語が出力されればOK
	}
	@Test
	public void test2() {
		try (SeContainer container = SeContainerInitializer.newInstance().initialize()) {
			AltSpeakerTest application = container.select(AltSpeakerTest.class).get();
			application.speak();
		}
		// Alternative + @Default により英語が出力されればOK
	}
	@Test
	public void test3() {
		try (SeContainer container = SeContainerInitializer.newInstance().initialize()) {
			AltSpeakerTest application = container.select(AltSpeakerTest.class).get();
			application.english();
		}
		// Alternative + @English により英語が出力されればOK
	}
	@Test
	public void test4() {
		try (SeContainer container = SeContainerInitializer.newInstance().initialize()) {
			AltSpeakerTest application = container.select(AltSpeakerTest.class).get();
			application.japanese();
		}
		// Alternative + @Japanese により日本語が出力されればOK
	}
	@Test
	public void test5() {
		try (SeContainer container = SeContainerInitializer.newInstance().initialize()) {
			AltSpeakerTest application = container.select(AltSpeakerTest.class).get();
			application.chainese();
		}
		// Alternative + @Default により英語が出力されればOK
	}

}
