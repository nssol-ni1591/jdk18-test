package test;

import java.util.Random;
import java.util.stream.Stream;

import org.junit.Test;

import app.index.LineCounter;
import app.index.LineCounter2;
import app.index.LineCounter3;
import app.index.LineCounter4;
import util.Print;
import util.index.WithIndex;

public class LineNumberTest {

	private static final long SEED = 19610802;
	private static final String SEPARATOR = "-----";

	/*
	 * WithIndexを使用するパターン
	 * この場合は、indexを管理するためのWithIndexクラスと値を保持するためのValWithIndexクラスが必要になる
	 */
	@Test
	public void passgenTop20() {
		Print.println("[WithIndex]");
		WithIndex<String> w = new WithIndex<>();
		Random random = new Random(SEED);
		Stream.generate(() -> new String(random.ints('!', '~' + 1).limit(20).toArray(), 0, 20))
				.limit(10)
				.map(w.withIndex())
				.forEach(v -> Print.println(String.format("%s: %s", v.getIndex(), v.getVal())));
		/*
		 * Lambda内で使用する前提なのでValWithIndexクラスをimportする必要はないが
		 * Lamdba外で使用する場合はimportが必要になる
		 */
		Print.println(SEPARATOR);
	}

	/*
	 * LineCounterクラスは、コンストラクタで書式文字列（format）引き渡して、
	 * LineCounterクラスのapply()で引き渡された文字列とIndexを書式で編集するパターン
	 * ⇒書式パターンに合わせて編集することができるが、それほどの自由度はない
	 */
	@Test
	public void passgenTop21() {
		Print.println("[LineCounter]");
		LineCounter w = new LineCounter("%4d: ");
		Random random = new Random(SEED);
		Stream.generate(() -> new String(random.ints('!', '~' + 1).limit(20).toArray(), 0, 20))
				.limit(10)
				.map(w::apply)
				.forEach(Print::println);
		Print.println(SEPARATOR);
	}

	/*
	 * LineCounter2クラスは、このプロジェクト内に定義したKeyValueインターフェースを使用するパターン
	 */
	@Test
	public void passgenTop22() {
		Print.println("[LineCounter2]");
		LineCounter2<String> w = new LineCounter2<>("%s: %s");
		Random random = new Random(SEED);
		Stream.generate(() -> new String(random.ints('!', '~' + 1).limit(20).toArray(), 0, 20))
				.limit(10)
				.map(w::apply)
				.forEach(Print::println);
		Print.println(SEPARATOR);
	}

	/*
	 * LineCounter3クラスは、基本的にはLineCounter2と同じ
	 * KeyValueクラスの代わりにjdkに含まれるAbstractMap.SimpleImmutableEntry<K,V>を使用する
	 */
	@Test
	public void passgenTop23() {
		Print.println("[LineCounter3]");
		LineCounter3<String> w = new LineCounter3<>();
		Random random = new Random(SEED);
		Stream.generate(() -> new String(random.ints('!', '~' + 1).limit(20).toArray(), 0, 20))
				.limit(10)
				.map(w::apply)
				.forEach(Print::println);
		Print.println(SEPARATOR);
	}
	@Test
	public void passgenTop23a() {
		Print.println("[LineCounter3　書式指定あり]");
		LineCounter3<String> w = new LineCounter3<>("%4d: %s");
		Random random = new Random(SEED);
		Stream.generate(() -> new String(random.ints('!', '~' + 1).limit(20).toArray(), 0, 20))
				.limit(10)
				.map(w::apply)
				.forEach(Print::println);
		Print.println(SEPARATOR);
	}

	/*
	 * LineCounterクラスをjdkが提供するクラスで代用できないか　⇒今のところ不可
	 */
	@Test
	public void passgenTop24() {
		Print.println("[LineCounter4]");
		final LineCounter4 index = new LineCounter4(0);
		Random random = new Random(SEED);
		Stream.generate(() -> new String(random.ints('!', '~' + 1).limit(20).toArray(), 0, 20))
				.limit(10)
				.forEach(v -> Print.println(String.format("%4s: %s", index.next(), v)));
		Print.println(SEPARATOR);
	}

}
