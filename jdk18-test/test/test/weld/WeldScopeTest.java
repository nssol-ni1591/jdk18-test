package test.weld;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.inject.se.SeContainer;
import javax.enterprise.inject.se.SeContainerInitializer;

import javax.inject.Inject;

import org.junit.Test;

import test.weld.scope.ScopeWeld;
import test.weld.scope.ScopeSub;

/*
 * (1) @PostConstruct、@PreDestroyの動作確認
 * (2) Weldで複数回呼び出しても、異なるインスタンスが引き渡されることの確認
 */
public class WeldScopeTest {

//	そもそも、このクラスはWeldを使用して生成していないので、@Injectは使えない
//	@Inject private Logger log;

	@Test
	public void test1() throws Exception {
		System.setProperty("java.util.logging.config.class", "util.logging.LogConfig");
		System.setProperty("file.encoding", "UTF-8");

		try (SeContainer container = SeContainerInitializer.newInstance().initialize()) {
			ScopeWeld app1 = container.select(ScopeWeld.class).get();
			app1.start();

			ScopeWeld app2 = container.select(ScopeWeld.class).get();
			app2.start();

			//　WeldでInnerクラスを取り出すことができない
			try {
				System.out.println("--:--:--.--- XXXX start app3");
///				log.log(Level.INFO, "--------------------------- start app3");
				ScopeInnerX app3 = container.select(WeldScopeTest.ScopeInnerX.class).get();
				System.out.println("start app3=[" + app3 + "]");
				app3.start();
			}
			catch (Throwable ex) {
				ex.printStackTrace();
			}
		}
		// @PreDestroyが呼び出されるのはこのタイミング
	}

	public class ScopeInnerX {

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

		@PreDestroy
		public void end() {
			log.log(Level.INFO, "end this={0}", this);
			log.log(Level.INFO, "\tsub1={0}", sub1);
			log.log(Level.INFO, "\tsub2={0}", sub2);
		}
	}
}

