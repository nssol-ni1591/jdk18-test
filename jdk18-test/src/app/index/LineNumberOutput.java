package app.index;

import java.util.Random;
import java.util.stream.Stream;

import util.index.LineCounter;
import util.index.LineCounter2;
import util.index.LineCounter3;
import util.index.WithIndex;

public class LineNumberOutput {

	public static long SEED = 19610802;

	/*
	 * WithIndexを使用するパターン
	 * この場合は、indexを管理するためのWithIndexクラスと値を保持するためのValWithIndexクラスが必要になる
	 */
	public static void passgen_top20(int len) {
		WithIndex<String> w = new WithIndex<>();
		// Random random = new Random(System.currentTimeMillis())
		Random random = new Random(SEED);
		Stream.generate(() -> new String(random.ints('!', '~' + 1).limit(len).toArray(), 0, len))
				.limit(10)
				.map(w.withIndex())
				.forEach(v -> System.out.printf("%s: %s\n", v.getIndex(), v.getVal()));
		/*
		 * Lambda内で使用する前提なのでValWithIndexクラスをimportする必要はないが
		 * Lamdba外で使用する場合はimportが必要になる
		 */
	}

	/*
	 * LineCounterクラスは、コンストラクタで書式文字列（format）引き渡して、
	 * LineCounterクラスのapply()で引き渡された文字列とIndexを書式で編集するパターン
	 * ⇒書式パターンに合わせて編集することができるが、それほどの自由度はない
	 */
	public static void passgen_top21(int len) {
		LineCounter<String> w = new LineCounter<>("%4d: ");
		Random random = new Random(SEED);
		Stream.generate(() -> new String(random.ints('!', '~' + 1).limit(len).toArray(), 0, len))
				.limit(10)
				.map(w::apply)
				.forEach(System.out::println);
	}

	/*
	 * LineCounter2クラスは、このプロジェクト内に定義したKeyValueインターフェースを使用するパターン
	 */
	public static void passgen_top22(int len) {
		LineCounter2<String> w = new LineCounter2<>();
		Random random = new Random(SEED);
		Stream.generate(() -> new String(random.ints('!', '~' + 1).limit(len).toArray(), 0, len))
				.limit(10)
				.map(w::apply)
				.forEach(System.out::println);
	}

	/*
	 * LineCounter3クラスは、基本的にはLineCounter2と同じ
	 * KeyValueクラスの代わりにjdkに含まれるAbstractMap.SimpleImmutableEntry<K,V>を使用する
	 */
	public static void passgen_top23(int len) {
		LineCounter3<String> w = new LineCounter3<>();
		Random random = new Random(SEED);
		Stream.generate(() -> new String(random.ints('!', '~' + 1).limit(len).toArray(), 0, len))
				.limit(10)
				.map(w::apply)
				.forEach(System.out::println);
	}
	public static void passgen_top231(int len) {
		LineCounter3<String> w = new LineCounter3<>("%4d: %s");
		Random random = new Random(SEED);
		Stream.generate(() -> new String(random.ints('!', '~' + 1).limit(len).toArray(), 0, len))
				.limit(10)
				.map(w::apply)
				.forEach(System.out::println);
	}

	/*
	 * LineCounterクラスをjdkが提供するクラスで代用できないか　⇒今のところ不可
	 */
	/*
	public static void passgen_top24(int len) {
		Integer ix = new Integer(0);
		Random random = new Random(SEED);
		Stream.generate(() -> new String(random.ints('!', '~' + 1).limit(len).toArray(), 0, len))
				.limit(20)
				// .map(s -> w.apply(s))
				.map()
				.forEach(v -> System.out.printf("%4s: %s\n", v.getKey(), v.getValue()));
	}
	 */

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		System.out.println("[WithIndex]");
		passgen_top20(8);
		System.out.println("-----");

		System.out.println("[LineCounter]");
		passgen_top21(8);
		System.out.println("-----");

		System.out.println("[LineCounter2]");
		passgen_top22(8);
		System.out.println("-----");

		System.out.println("[LineCounter3]");
		passgen_top23(8);
		System.out.println("-----");

		System.out.println("[LineCounter3_1]");
		passgen_top231(8);
		System.out.println("-----");
	}
}
