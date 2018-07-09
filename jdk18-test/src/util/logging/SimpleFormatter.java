package util.logging;

import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public class SimpleFormatter extends Formatter {
	
	@Override
	public String format(LogRecord record) {
		return record.getLoggerName() + " - " + record.getMessage() + System.lineSeparator();
	}

}
