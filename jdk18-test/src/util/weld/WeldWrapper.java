package util.weld;

import java.lang.annotation.Annotation;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.inject.se.SeContainer;
import javax.enterprise.inject.se.SeContainerInitializer;
import javax.enterprise.util.AnnotationLiteral;


public class WeldWrapper {

	private static Logger log = Logger.getLogger(WeldWrapper.class.getName());

	private Class<? extends WeldRunner> cl;

	public WeldWrapper() { }
	public WeldWrapper(Class<? extends WeldRunner> cl) {
		this.cl = cl;
	}

	public int weld(String...argv) {
		return weld(null, argv);
	}
	public <E extends Annotation> int weld(AnnotationLiteral<E> anno, String...argv) {
		int rc = 0;

		// for weld-se 2.3.x
		//Weld weld = new Weld()
		//try(WeldContainer container = weld.initialize()) {-

		// for weld-se over 3.0.x
		try (SeContainer container = SeContainerInitializer.newInstance().initialize()) {
	        // start the container, retrieve a bean and do work with it
			WeldRunner application;
			if (anno == null) {
				// AnnotationがついていないCheckerクラスの生成
				application = container.select(cl).get();
			}
			else {
				// Annotation付きCheckerクラスの生成
				application = container.select(cl, anno).get();
			}
			if (application.check(argv) == 0) {
				rc = application.start(argv);
			}
	    }
		catch (Exception ex) {
			log.log(Level.SEVERE, "in weld", ex);
			rc = -1;
		}
		return rc;
	}

}
