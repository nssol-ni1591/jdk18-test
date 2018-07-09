package util.logging;

import java.util.logging.Logger;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Default;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

@Dependent
public class LoggerProducer {

	@Default
	@Produces
	public Logger getLogger(InjectionPoint ip) {
		return Logger.getLogger(ip.getMember().getDeclaringClass().getName());
	}

	@StdOut
	@Produces
	public Logger getStdOutLogger(InjectionPoint ip) {
		return StdOutLogger.getLogger(ip.getMember().getDeclaringClass().getName());
	}

	@StdErr
	@Produces
	public Logger getStdErrLogger(InjectionPoint ip) {
		return StdErrLogger.getLogger(ip.getMember().getDeclaringClass().getName());
	}

}
