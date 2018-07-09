package util.weld;

import java.lang.annotation.Annotation;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.inject.se.SeContainer;
import javax.enterprise.inject.se.SeContainerInitializer;
import javax.enterprise.util.AnnotationLiteral;

public class WeldWrapper<T extends WeldRunner> {

	private static Logger log = Logger.getLogger(WeldWrapper.class.getName());

	private Class<T> cl;

	public WeldWrapper() { }
	public WeldWrapper(Class<T> cl) {
		this.cl = cl;
	}

	public int weld(String...argv) {
		return weld(null, argv);
	}
	public <E extends Annotation> int weld(AnnotationLiteral<E> anno, String...argv) {
		int rc = 0;

		try(SeContainer container = SeContainerInitializer.newInstance().initialize()) {
	        // start the container, retrieve a bean and do work with it
			T application;
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
