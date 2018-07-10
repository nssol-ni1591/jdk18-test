package util.logging;

import java.io.IOException;
import java.util.logging.LogManager;

import util.Print;

public class LogConfig {

	public LogConfig() {
		init();
	}

	public void setup() {
		// Do nothing
	}

	public static void init() {
		try {
			LogManager.getLogManager().readConfiguration(LogConfig.class.getResourceAsStream("/META-INF/logging.properties"));
			// このクラスと同じパッケージでは無い場合は /myapp/logging.properties など絶対パス指定
		}
		catch (IOException e) {
			Print.stackTrace(e);
		}
	}
}
