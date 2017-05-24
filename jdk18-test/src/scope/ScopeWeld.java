package scope;

import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

public class ScopeWeld {

	@Inject private Logger log;
	@Inject private ScopeSub sub1;
	@Inject private ScopeSub sub2;
	
	@PostConstruct
	public void init() {
		log.info("init this=" + this);
		log.info("\tsub1=" + sub1);
		log.info("\tsub2=" + sub2);
	}

	public void start() {
		log.info("start this=" + this);
		log.info("\tsub1=" + sub1);
		log.info("\tsub2=" + sub2);
	}

}
