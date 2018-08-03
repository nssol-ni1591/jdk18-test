package util.logging;

import java.util.logging.StreamHandler;

public class StdErrHandler extends StreamHandler {

	public StdErrHandler() {
		super();
		// 標準エラーへの出力設定
		setOutputStream(System.err);
		// 基本的なパラメータはクラス名をキーにしたpropertiesファイルから取得する
	}
}
