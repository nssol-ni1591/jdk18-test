package scope;

import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;

public class Run {

	public static void main(String...argv) {
		System.setProperty("java.util.logging.config.class", "util.log.LogConfig");
		System.setProperty("file.encoding", "UTF-8");

		int rc = 0;
		Weld weld = new Weld();
		try (WeldContainer container = weld.initialize()) {
			ScopeWeld app = container.instance().select(ScopeWeld.class).get();
			app.start();

			ScopeWeld app2 = container.instance().select(ScopeWeld.class).get();
			app2.start();
		}
		catch (Exception ex) {
			ex.printStackTrace(System.err);
			rc = 1;
		}
		System.exit(rc);
	}
}
