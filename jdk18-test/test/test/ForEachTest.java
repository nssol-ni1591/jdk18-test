package test;

import java.util.AbstractMap;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import util.Print;
import util.index.ValWithIndex;

import java.util.function.Function;

import org.junit.Test;

/*
 * 出力にIndexを付加する実装たち
 */
public class ForEachTest {

	private static String msg = "2016/12/19 07:08:18  SOPE_DR_01-no_db.sh INFORMATION: ----- DR_NFSの同期処理 を開始します。 -----";
	private static final String FORMAT = "%4s: %s";

	/*
	 * forEachメソッドの引数はConsumerインターフェースの実装クラス（のインスタンス）です。
	 * 上述のラムダ式は、匿名クラスをその場で定義してインスタンスを渡しているだけでしたので、自分で匿名クラスを記述しても問題ないわけです。
	 * したがって以下のような実装が可能です。
	 */
	@Test
	public void p2Jdk18() {
		Print.println("p2Jdk18:");
		List<String> list = Arrays.asList(msg.split(" +"));
		list.stream()
				.sorted((a, b) -> a.compareTo(b))
				.map(s -> String.format("\"%s\"", s))
				.forEach(new Consumer<String>() {
					private int ix = 1;

					@Override
					public void accept(String s) {
						Print.println(String.format(FORMAT, (ix++), s));
					}
				});
	}
	
	/*
	 * Indexを付加する
	 * ValWithIndexクラスが必要
	 */
	@Test
	public void p3Jdk18() {
		Print.println("p3Jdk18:");
		List<String> list = Arrays.asList(msg.split(" +"));
		list.stream()
			.sorted((a, b) -> a.compareTo(b))
			.map(withIndex())
			.forEach(v -> Print.println(String.format(FORMAT, v.getIndex(), v.getVal())));
	}

	/*
	 * 一見すると、良さそうなのですが、Stream#parallelStreamを利用された場合の動作が全く保障できません。
	 * 実行タイミングによっては、結果が異なる
	 */
	@Test
	public void p3Jdk18p1() {
		Print.println("p3Jdk18p1:");
		List<String> list = Arrays.asList(msg.split(" +"));
		list.stream()
			.parallel()
			.sorted((a, b) -> a.compareTo(b))
			.map(withIndex())
			.forEach(v -> Print.println(String.format(FORMAT, v.getIndex(), v.getVal())));
	}
	public <T> Function<T, ValWithIndex<T>> withIndex() {
		return new Function<T, ValWithIndex<T>>() {
			private int index;

			@Override
			public ValWithIndex<T> apply(T t) {
				return new ValWithIndex<>(t, index++);
			}
		};
	}

	/*
	 * 
	 */
	@Test
	public void p4Jdk18() {
		Print.println("p4Jdk18:");
		List<String> list = Arrays.asList(msg.split(" +"));
		list.stream()
			.sorted((a, b) -> a.compareTo(b))
			.map(withIndex2())
			.forEach(v -> Print.println(String.format(FORMAT, v.getKey(), v.getValue())));
	}
	public <T> Function<T, AbstractMap.SimpleImmutableEntry<Integer,T>> withIndex2() {
		return new Function<T, AbstractMap.SimpleImmutableEntry<Integer,T>>() {
			private int index;

			@Override
			public  AbstractMap.SimpleImmutableEntry<Integer,T> apply(T t) {
				return new  AbstractMap.SimpleImmutableEntry<>(index++, t);
			}
		};
	}

}
