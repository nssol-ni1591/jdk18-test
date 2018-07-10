package app.weld.speaker;

import javax.inject.Inject;

import test.speaker.Speaker;
import util.weld.WeldRunner;
import util.weld.WeldWrapper;

public class Run2 implements WeldRunner {

	@Inject private Speaker speaker;

	@Override
	public int start(String...args) {
		this.speaker.speak();
		return 0;
	}

	public static void main(String[] args) {
		int rc = new WeldWrapper(Run2.class).weld(args);
		System.exit(rc);
	}

}
