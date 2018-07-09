package collect;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.SequenceInputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

import util.Print;

public class MapCollect {

	private static final String[] DATA1 = new String[] { "A", "B", "C", "D", "E" };
	private static final String[] DATA2 = new String[] { "X", "Y", "Z" };

	// test1系:DATA1を小文字に変換して、Listクラスに集約する

	// innerクラスで実装した場合
	public void test1() {
		Print.println("[MapCollect.test1]");

		final List<String> list = Arrays.stream(DATA1)
				// わざと innerクラスを使用
				.map(new Function<String, String>() {
					@Override
					public String apply(String s) {
						return s.toLowerCase();
					}
				})
				.collect(
						// わざと innerクラスを使用
						new Supplier<List<String>>() {
							@Override
							public List<String> get() {
								return new ArrayList<>();
							}
						}, new BiConsumer<List<String>, String>() {
							@Override
							public void accept(final List<String> t, final String s) {
								t.add(s);
							}
						}, new BiConsumer<List<String>, List<String>>() {
							@Override
							public void accept(final List<String> t, final List<String> u) {
								t.addAll(u);
							}
						});
		Print.println(list);
		// ⇒ [a, b, c, d, e]
	}
	// Streamを使用した場合
	public void test1a() {
		Print.println("[MapCollect.test1a]");

		final List<String> resultl1 = Arrays.stream(DATA1)
				// わざと SonarLintのエラーは無視すること
				.map(s -> s.toLowerCase())
				.collect(() -> new ArrayList<>(),
						(t, s) -> t.add(s),
						(t, u) -> t.addAll(u));

		Print.println(resultl1);
		// ⇒ [a, b, c, d, e]
	}
	// method参照を使用した場合
	public void test1b() {
		Print.println("[MapCollect.test1b]");

		final List<String> resultl1 = Arrays.stream(DATA1)
				.map(String::toLowerCase)
				.collect(ArrayList::new,
						(t, s) -> t.add(s),
						(t, u) -> t.addAll(u));

		Print.println(resultl1);
		// ⇒ [a, b, c, d, e]
	}

	// ストリームを結合する
	public void test3() {
		Print.println("[MapCollect.test3]");
		Stream<String> s1 = Arrays.stream(DATA1);
		Stream<String> s2 = Arrays.stream(DATA2);

		Stream.concat(s1, s2).forEach(Print::println);
	}
	// ファイルのデータを結合する
	public void test4() {
		Print.println("[MapCollect.test4]");
		String f1 = "./s1.txt";
		String f2 = "./s2.txt";

		Print.println("path=" + Paths.get(f1).toAbsolutePath().toString());

		try (Stream<String> s1 = Files.lines(Paths.get(f1));
				Stream<String> s2 = Files.lines(Paths.get(f2));
				) {
			Stream.concat(s1, s2).forEach(Print::println);
		}
		catch (IOException e) {
			Print.stackTrace(e);
		}
	}
	public void test4a() {
		Print.println("[MapCollect.test4a]");
		String f1 = "./s1.txt";
		String f2 = "./s2.txt";

		try (
				InputStream is1 = new FileInputStream(f1);
				InputStream is2 = new FileInputStream(f2);
				SequenceInputStream is = new SequenceInputStream(is1, is2);
				BufferedReader br = new BufferedReader(new InputStreamReader(is));
				) {
			br.lines().forEach(Print::println);
		}
		catch (IOException e) {
			Print.stackTrace(e);
		}
		
	}

	public static void main(String[] argv) {
		MapCollect t = new MapCollect();
		t.test1();
		t.test1a();
		t.test1b();

		t.test3();
		t.test4();
		t.test4a();
	}
}
