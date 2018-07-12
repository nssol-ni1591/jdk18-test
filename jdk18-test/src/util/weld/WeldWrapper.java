package util.weld;

import java.lang.annotation.Annotation;

import javax.enterprise.inject.se.SeContainer;
import javax.enterprise.inject.se.SeContainerInitializer;
import javax.enterprise.util.AnnotationLiteral;


public class WeldWrapper {

	private Class<? extends WeldRunner> cl;

	public WeldWrapper(Class<? extends WeldRunner> cl) {
		this.cl = cl;
	}

	public int weld(String...args) {
		return weld(null, args);
	}
	public <E extends Annotation> int weld(AnnotationLiteral<E> anno, String...args) {
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
			rc = application.check(args);
			if (rc == 0) {
				rc = application.start(args);
			}
	    }
		return rc;
	}

}
