package util.weld;

public interface WeldRunner {

	int start(String[] argv);

	/*
	 * 実行前の環境や引数のチェック
	 * @return 0 正常
	 * @return 0以外 異常
	 */
	default int check(String...argv) {
		return 0;
	}

}
