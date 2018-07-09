package collect;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.stream.Stream;

import com.google.common.base.Function;
import com.google.common.base.Supplier;

public class MapCollect {

	public void test1() {

		Stream<String> streamf = Arrays.stream(new String[] { "A", "B", "C", "D", "E" });

		Function<String, String> f1 = new Function<String, String>() {
			@Override
			public String apply(String s) {
				return s.toLowerCase();
			}
		};
		Function<String, String> f2 = String::toLowerCase;

		System.out.println("1:" + f1.apply("ABC"));
		System.out.println("2:" + f1.apply("XYZ"));
		
		
		//final Stream<String> resultf = streamf.map(f2);	// Errorになる。なぜ？

		/*
		final List<String> resultf = streamf
				//.map(String::toLowerCase)
				.map(new Function<String, String>() {
					@Override
					public String apply(final String s) {
						return s.toLowerCase();
					}
				})
				.collect(
						new Supplier<List<String>>() {
							@Override
							public List<String> get() {
								return new ArrayList<String>();
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
						})
					;
		System.out.println(resultf);
		// ⇒ [a, b, c, d, e]
	*/

	}
	public void test2() {
		Stream<String> s1 = Arrays.stream(new String[] { "A", "B", "C", "D", "E" });
		Stream<String> s2 = Arrays.stream(new String[] { "X", "Y", "Z" });

		final List<String> resultl1 = s1
				.map(s -> s.toLowerCase())
				.collect(() -> new ArrayList<String>(),
						(t, s) -> t.add(s),
						(t, u) -> t.addAll(u));

		System.out.println(resultl1);
		// ⇒ [a, b, c, d, e]
	}

	public void test3() {
		Stream<String> s1 = Arrays.stream(new String[] { "A", "B", "C", "D", "E" });
		Stream<String> s2 = Arrays.stream(new String[] { "X", "Y", "Z" });

		Stream.of(s1, s2);
		Stream.concat(s1, s2)
			.forEach(System.out::println);

	}
	public void test4() {
		String f1 = "/s1.txt";
		String f2 = "/s2.txt";
		
		
		InputStream is = getClass().getResourceAsStream(f1);


		Stream<String> s1 = Arrays.stream(new String[] { "A", "B", "C", "D", "E" });
		Stream<String> s2 = Arrays.stream(new String[] { "X", "Y", "Z" });

		Stream.of(f1, f2);
		Stream.concat(s1, s2)
			.forEach(System.out::println);

	}
	public static void main(String[] argv) {
		MapCollect t = new MapCollect();
		t.test1();
		t.test2();
		t.test3();
		t.test4();
	}
}
