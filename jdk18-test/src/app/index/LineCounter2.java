package app.index;

import util.index.KeyValue;

/*
 * 汎用的なキーと値を保持するクラス：KeyValueの使用を前提に行番号を付加する
 * 
 */
public class LineCounter2<T> {

	private int max = 0;
	private String format = "%s: %s";

	public LineCounter2() {
	}
	public LineCounter2(String format) {
		this();
		this.format = format;
	}

	public KeyValue<Integer, T> apply(T arg0) {
		return new WithIndex<>(nextIndex(), arg0);
	}

	private synchronized int nextIndex() {
		return ++max;
	}
	
	public class WithIndex<U, V> implements KeyValue<U, V> {

		private final U key;
		private final V value;
		//private String format = "%s: %s"-

		public WithIndex(U key, V value) {
			this.key = key;
			this.value = value;
		}

		public U getKey() {
			return key;
		}
		public V getValue() {
			return value;
		}
		
		@Override
		public String toString() {
			return String.format(format, getKey(), getValue());
		}
	}
}
