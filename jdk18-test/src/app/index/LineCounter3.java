package app.index;

import java.util.AbstractMap;

/*
 * LineCounter2の汎用インターフェース（Keyvalue）に対して、
 * JDKで用意されているKeyValueクラス（Pairクラスというらしい）を使用したパターン。
 * これなら、わざわざKeyValueっクラスを用意することはない。
 * @see: http://qiita.com/y_q1m/items/8c26a773ae67bfe2a107
 */
public class LineCounter3<T> {

	private int max = 0;
	private String format = null;

	public LineCounter3() {
	}
	public LineCounter3(String format) {
		this();
		this.format = format;
	}

	public WithIndex<Integer, T> apply(T arg0) {
		return new WithIndex<>(nextIndex(), arg0);
	}

	private synchronized int nextIndex() {
		return ++max;
	}
	
	public class WithIndex<K, V> extends AbstractMap.SimpleImmutableEntry<K,V> {

		private static final long serialVersionUID = 1L;

		public WithIndex(K key, V value) {
			super(key, value);
		}
		
		@Override
		public String toString() {
			if (format != null) {
				return String.format(format, getKey(), getValue());
			}
			return super.toString();
		}
	}
}
