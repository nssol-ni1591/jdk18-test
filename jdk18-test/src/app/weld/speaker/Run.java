package app.weld.speaker;

import javax.enterprise.inject.se.SeContainer;
import javax.enterprise.inject.se.SeContainerInitializer;
import javax.inject.Inject;

public class Run {

	@Inject private Speaker speaker;

	public void run() {
		this.speaker.speak();
	}

	public static void main(String[] args) {
		// for weld-se 2.2.x
		//Weld weld = new Weld()
		//try (WeldContainer container = weld.initialize()instance()) {-
		
		// for weld-se 3.0.x
		try (SeContainer container = SeContainerInitializer.newInstance().initialize()) {
			Run application = container.select(Run.class).get();
			application.run();
		}

	}

}
