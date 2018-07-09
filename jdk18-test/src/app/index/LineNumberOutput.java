package app.index;

import java.util.Random;
import java.util.stream.Stream;

import util.Print;
import util.index.WithIndex;

public class LineNumberOutput {

	private static final long SEED = 19610802;
	private static final String SEPARATOR = "-----";

	/*
	 * WithIndexを使用するパターン
	 * この場合は、indexを管理するためのWithIndexクラスと値を保持するためのValWithIndexクラスが必要になる
	 */
	public static void passgenTop20(int len) {
		WithIndex<String> w = new WithIndex<>();
		Random random = new Random(SEED);
		Stream.generate(() -> new String(random.ints('!', '~' + 1).limit(len).toArray(), 0, len))
				.limit(10)
				.map(w.withIndex())
				.forEach(v -> Print.println(String.format("%s: %s", v.getIndex(), v.getVal())));
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
	public static void passgenTop21(int len) {
		LineCounter w = new LineCounter("%4d: ");
		Random random = new Random(SEED);
		Stream.generate(() -> new String(random.ints('!', '~' + 1).limit(len).toArray(), 0, len))
				.limit(10)
				.map(w::apply)
				.forEach(Print::println);
	}

	/*
	 * LineCounter2クラスは、このプロジェクト内に定義したKeyValueインターフェースを使用するパターン
	 */
	public static void passgenTop22(int len) {
		LineCounter2<String> w = new LineCounter2<>();
		Random random = new Random(SEED);
		Stream.generate(() -> new String(random.ints('!', '~' + 1).limit(len).toArray(), 0, len))
				.limit(10)
				.map(w::apply)
				.forEach(Print::println);
	}

	/*
	 * LineCounter3クラスは、基本的にはLineCounter2と同じ
	 * KeyValueクラスの代わりにjdkに含まれるAbstractMap.SimpleImmutableEntry<K,V>を使用する
	 */
	public static void passgenTop23(int len) {
		LineCounter3<String> w = new LineCounter3<>();
		Random random = new Random(SEED);
		Stream.generate(() -> new String(random.ints('!', '~' + 1).limit(len).toArray(), 0, len))
				.limit(10)
				.map(w::apply)
				.forEach(Print::println);
	}
	public static void passgenTop23a(int len) {
		LineCounter3<String> w = new LineCounter3<>("%4d: %s");
		Random random = new Random(SEED);
		Stream.generate(() -> new String(random.ints('!', '~' + 1).limit(len).toArray(), 0, len))
				.limit(10)
				.map(w::apply)
				.forEach(Print::println);
	}

	/*
	 * LineCounterクラスをjdkが提供するクラスで代用できないか　⇒今のところ不可
	 */
	public static void passgenTop24(int len) {
		final LineCounter4 index = new LineCounter4(0);
		Random random = new Random(SEED);
		Stream.generate(() -> new String(random.ints('!', '~' + 1).limit(len).toArray(), 0, len))
				.limit(10)
				.forEach(v -> Print.println(String.format("%4s: %s", index.next(), v)));
	}

	public static void main(String[] args) {

		Print.println("[WithIndex]");
		passgenTop20(8);
		Print.println(SEPARATOR);

		Print.println("[LineCounter]");
		passgenTop21(8);
		Print.println(SEPARATOR);

		Print.println("[LineCounter2]");
		passgenTop22(8);
		Print.println(SEPARATOR);

		Print.println("[LineCounter3]");
		passgenTop23(8);
		Print.println(SEPARATOR);

		Print.println("[LineCounter3　書式指定あり]");
		passgenTop23a(8);
		Print.println(SEPARATOR);

		Print.println("[LineCounter4]");
		passgenTop24(8);
		Print.println(SEPARATOR);
	}

}
