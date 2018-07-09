package util.logging;

import java.util.logging.StreamHandler;

public class StdOutHandler extends StreamHandler {

	public StdOutHandler() {
		// 標準出力への出力設定
		setOutputStream(System.out);
		// 基本的なパラメータはクラス名をキーにしたpropertiesファイルから取得する
	}
}
