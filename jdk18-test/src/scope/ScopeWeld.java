package scope;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

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
