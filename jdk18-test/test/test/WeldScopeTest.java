package test;

import javax.enterprise.inject.se.SeContainer;
import javax.enterprise.inject.se.SeContainerInitializer;

import org.junit.Test;

import test.scope.ScopeWeld;
import util.Print;

/*
 * (1) @PostConstructの動作確認
 * (2) Weldで複数回呼び出しても、異なるインスタンスが引き渡されることの確認
 */
public class WeldScopeTest {

	@Test
	public void test1() {
		System.setProperty("java.util.logging.config.class", "util.logging.LogConfig");
		System.setProperty("file.encoding", "UTF-8");

		int rc = 0;
		try (SeContainer container = SeContainerInitializer.newInstance().initialize()) {
			ScopeWeld app1 = container.select(ScopeWeld.class).get();
			app1.start();

			ScopeWeld app2 = container.select(ScopeWeld.class).get();
			app2.start();
		}
		catch (Exception ex) {
			Print.stackTrace(ex);
			rc = 1;
		}
		System.exit(rc);
	}
/*
	public class ScopeWeld {

		@Inject private Logger log;
		@Inject private ScopeSub sub1;
		@Inject private ScopeSub sub2;
		
		@PostConstruct
		public void init() {
			log.log(Level.INFO, "init this={0}", this);
			log.log(Level.INFO, "\tsub1={0}", sub1);
			log.log(Level.INFO, "\tsub2={0}", sub2);
		}

		public void start() {
			log.log(Level.INFO, "start this={0}", this);
			log.log(Level.INFO, "\tsub1={0}", sub1);
			log.log(Level.INFO, "\tsub2={0}", sub2);
		}
	}

	public class ScopeSub {
		// Do not implements
	}
*/
}

