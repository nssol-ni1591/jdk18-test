package util.log;

import java.util.logging.Logger;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

@Dependent
public class LoggerProducer {

	@Produces
	@Dependent
	public Logger getLogger(InjectionPoint ip) {
//		return Logger.getLogger(ip.getMember().getDeclaringClass().getPackage().getName());
		return Logger.getLogger(ip.getMember().getDeclaringClass().getName());
	}

}
