package weld.speaker;

import javax.inject.Inject;

import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;

public class Run {

	public static void main(String[] args) {
		Weld weld = new Weld();
		/*
		WeldContainer container = weld.initialize();
		MyApplication application = container.instance().select(MyApplication.class).get();
		application.run();
		weld.shutdown();
		*/
		try (WeldContainer container = weld.initialize()) {
			Run application = container.instance().select(Run.class).get();
			application.run();
		}
	}

	@Inject
	private Speaker speaker;

	public void run() {
		this.speaker.speak();
	}
}
