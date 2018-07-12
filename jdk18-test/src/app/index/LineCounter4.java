package app.index;

/*
 * スレッドを使用しなければ正常に動作するパターン
 */
public class LineCounter4 {

	private int index;

	public LineCounter4(int index) {
		this.index = index;
	}

	public int next() {
		return ++index;
	}
}
