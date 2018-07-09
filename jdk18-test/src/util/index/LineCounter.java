package util.index;

/*
 * コンストラクタで文字列に付加する書式:formatを指定しておき、
 * applyにより、Stringに文字列（想定は行番号）を付加する
 */
public class LineCounter<T> {

	private int max = 0;
	private String format;

	public LineCounter(String format) {
		this.format = format;
	}

	public String apply(String s) {
		return String.format(format + "%s", nextIndex(), s);
	}

	private synchronized int nextIndex() {
		return ++max;
	}
}
