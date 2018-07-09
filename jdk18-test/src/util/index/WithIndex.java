package util.index;

import java.util.function.Function;

/*
 * オリジナルの行番号の付加クラス
 * 面倒なのは、値を保持するためのクラスが必要ということ⇒ValWithIndex<T>クラス
 */
public class WithIndex<T> {

	private int max = 0;
	
	public Function<T, ValWithIndex<T>> withIndex() {
		return new Function<T, ValWithIndex<T>>() {

			@Override
			public ValWithIndex<T> apply(T t) {
				return new ValWithIndex<>(t, nextIndex());
			}
		};
	}
	private synchronized int nextIndex() {
		return ++max;
	}
}
