package app;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;
import java.io.PrintWriter;
import java.util.AbstractMap;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.ObjIntConsumer;
import java.util.stream.Stream;

import util.Print;
import util.ValWithIndex;

import java.util.function.Function;

public class StreamMain {

	static String msg = "2016/12/19 07:08:18  SOPE_DR_01-no_db.sh INFORMATION: ----- DR_NFSの同期処理 を開始します。 -----";

	public StreamMain() {
	}

	/*
	 * Streamを何から作り出す
	 */
	public void p1_jdk18() {
		// 基本系
		Stream.of(msg.split(" +"))
				.sorted((a, b) -> a.compareTo(b))
				.map(s -> String.format("\"%s\"", s))
				.forEach(System.out::println);
	}
	public void p1_jdk18_2() {
		// 配列から
		Arrays.stream(msg.split(" +"))
				.sorted((a, b) -> a.compareTo(b))
				.map(s -> String.format("\"%s\"", s))
				.forEach(System.out::println);
	}
	public void p1_jdk18_3() {
		// コレクションから
		List<String> list = Arrays.asList(msg.split(" +"));
		list.stream()
				.sorted((a, b) -> a.compareTo(b))
				.map(s -> String.format("\"%s\"", s))
				.forEach(System.out::println);
	}
	public void p1_jdk18_4() {
		// Readerから
		try (
				PipedReader ppr = new PipedReader();
				PipedWriter ppw = new PipedWriter(ppr);
				PrintWriter pw = new PrintWriter(ppw);
				BufferedReader br = new BufferedReader(ppr);
				)
		{
			Arrays.stream(msg.split(msg)).forEach(pw::println);
			pw.flush();
			pw.close();

			br.lines()
					.sorted((a, b) -> a.compareTo(b))
					.map(s -> String.format("\"%s\"", s))
					.forEach(System.out::println);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/*
	 * forEachメソッドの引数はConsumerインターフェースの実装クラス（のインスタンス）です。
	 * 上述のラムダ式は、匿名クラスをその場で定義してインスタンスを渡しているだけでしたので、自分で匿名クラスを記述しても問題ないわけです。
	 * したがって以下のような実装が可能です。
	 */
	public void p2_jdk18() {
		List<String> list = Arrays.asList(msg.split(" +"));
		list.stream()
				.sorted((a, b) -> a.compareTo(b))
				.map(s -> String.format("\"%s\"", s))
				.forEach(new Consumer<String>() {
					private int ix = 1;

					@Override
					public void accept(String s) {
						System.out.printf("%4s: %s\n", (ix++), s);
					}
				});
	}
	
	/*
	 * 一見すると、良さそうなのですが、Stream#parallelStreamを利用された場合の動作が全く保障できません。
	 */
	public void p3_jdk18() {
		List<String> list = Arrays.asList(msg.split(" +"));
		list.stream()
			.sorted((a, b) -> a.compareTo(b))
			.map(withIndex())
			.forEach(v -> System.out.printf("%4s: %s\n", v.getIndex(), v.getVal()));
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
	 */
	public void p3_jdk18_1() {
		List<String> list = Arrays.asList(msg.split(" +"));
		list.stream()
			.sorted((a, b) -> a.compareTo(b))
			.map(withIndex2())
			.forEach(v -> System.out.printf("%4s: %s\n", v.getKey(), v.getValue()));
	}
	public <T> Function<T, AbstractMap.SimpleImmutableEntry<Integer,T>> withIndex2() {
		return new Function<T, AbstractMap.SimpleImmutableEntry<Integer,T>>() {
			private int index;

			@Override
			public  AbstractMap.SimpleImmutableEntry<Integer,T> apply(T t) {
				return new  AbstractMap.SimpleImmutableEntry<Integer,T>(index++, t);
			}
		};
	}


	public <T> void eachWithIndex(Iterable<T> itr, ObjIntConsumer<T> action) {
		int index = 0;
		for (T t : itr) {
			action.accept(t, index++);
		}
	}

	public static void main(String... arvs) {
		StreamMain stream = new StreamMain();

		Print.print(stream, "p1_jdk18");
		Print.print(stream, "p1_jdk18_2");
		Print.print(stream, "p1_jdk18_3");
		Print.print(stream, "p1_jdk18_4");
		Print.print(stream, "p2_jdk18");
		Print.print(stream, "p3_jdk18");
		Print.print(stream, "p3_jdk18_1");

	}
}
