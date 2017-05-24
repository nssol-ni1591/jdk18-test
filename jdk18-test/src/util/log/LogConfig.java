package util.log;

import java.io.IOException;
import java.util.logging.LogManager;

public class LogConfig {

	public LogConfig() {
		try {
			LogManager.getLogManager().readConfiguration(getClass().getResourceAsStream("/logging.properties"));
			// このクラスと同じパッケージでは無い場合は /myapp/logging.properties など絶対パス指定
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
