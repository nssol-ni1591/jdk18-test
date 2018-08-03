package util.logging;

import java.util.logging.Logger;

@StdOut
public class StdOutLogger extends Logger {

	protected StdOutLogger(String name, String resourceBundleName) {
		super(name, resourceBundleName);
	}

	public static Logger getLogger(String name) {
		// Loggerクラスのインスタンスを生成
		// 注意：複数のLoggerを使用する場合Nameは一意である必要がある
		Logger logger = Logger.getLogger("(StdOut)" + name);
		// ハンドラを追加
		logger.addHandler(new StdOutHandler());
		// formatter、levelはpropertiesファイルで指定する
		// ルートロガーのハンドラ（ConsoleHandler）へログメッセージを発行しない
		// =>意味ない。なぜならば、このloggerは一意なので、他のハンドラは存在しない
		logger.setUseParentHandlers(false);
		return logger;
	}

}
