package scope;

import javax.enterprise.inject.se.SeContainer;
import javax.enterprise.inject.se.SeContainerInitializer;

import util.Print;

public class Run {

	public static void main(String...argv) {
		System.setProperty("java.util.logging.config.class", "util.log.LogConfig");
		System.setProperty("file.encoding", "UTF-8");

		int rc = 0;
		try (SeContainer container = SeContainerInitializer.newInstance().initialize()) {
			ScopeWeld app = container.select(ScopeWeld.class).get();
			app.start();

			ScopeWeld app2 = container.select(ScopeWeld.class).get();
			app2.start();
		}
		catch (Exception ex) {
			Print.stackTrace(ex);
			rc = 1;
		}
		System.exit(rc);
	}
}
