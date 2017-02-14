package app;

import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import util.AnnotationTest.MethodAnnotation;
import util.Intercepter;
import util.Print;

public class LambdaMain implements LambdaMainIF {

	static String msg = "2016/12/19 07:08:18  SOPE_DR_01-no_db.sh INFORMATION: ----- DR_NFSの同期処理 を開始します。 -----";

	public LambdaMain() {
	}

	/*
	 * 単に、配列に対して、ソートする実装方法
	 */
	public String[] p1_jdk17(String[] array) {
		Arrays.sort(array, new Comparator<String>() {
			@Override
			public int compare(String a, String b) {
				return a.compareToIgnoreCase(b);
			}
		});
		return array;
	}

	public String[] p1_jdk18(String[] array) {
		Arrays.sort(array, (a, b) -> a.compareToIgnoreCase(b));
		return array;
	}

	public String[] p1_jdk18_2(String[] array) {
		Arrays.sort(array, (a, b) -> {
			return a.compareToIgnoreCase(b);
		});
		return array;
	}

	public String[] p1_jdk18_3(String[] array) {
		Arrays.sort(array, (a, b) -> {
			System.out.println("a=" + a + ", b=" + b);
			return a.compareToIgnoreCase(b);
		});
		return array;
	}

	public String[] p1_jdk18_4(String[] array) {
		Arrays.sort(array, String::compareToIgnoreCase);
		return array;
	}

	/*
	 * 文字のソート以外にも簡単に変更できること
	 */
	public String[] p2_jdk18(String[] array) {
		// 文字数でソート
		Arrays.sort(array, (a, b) -> {
			return a.length() - b.length();
		});
		return array;
	}

	public String[] p2_jdk18_2(String[] array) {
		// バイト数でソート
		Arrays.sort(array, (a, b) -> {
			return a.getBytes().length - b.getBytes().length;
		});
		return array;
	}

	/*
	 * 以下はラムダ式というよりも、リフレクションと総称型をうまく使ってコードを短くできないか？
	 */
	public ArrayList<String> p3_jdk18(ArrayList<String> list) {
		System.out.println("ArrayList<String> p3_jdk18(ArrayList<String> list)");
		list.sort((a, b) -> {
			return a.compareToIgnoreCase(b);
		});
		return list;
	}

	/*
	 * リフレクションでは。引数がArrayListであっても、このメソッドを探し出すことができない
	 */
	public List<String> p3_jdk18(List<String> list) {
		System.out.println("List<String> p3_jdk18(List<String> list)");
		list.sort((a, b) -> {
			return a.length() - b.length();
		});
		return list;
	}

	// @MethodAnnotation
	public List<String> p4_jdk18(List<String> list) {
		list.sort((a, b) -> {
			return a.length() - b.length();
		});
		return list;
	}

	public static <T> T getProxyInstance(T instance) {
		Class<? extends Object> clazz = instance.getClass();
		// 対象クラスが実装するインターフェースのリスト
		Class[] classes = clazz.getInterfaces();
		Intercepter intercepter = new Intercepter(instance);
		T proxyInstance = (T) Proxy.newProxyInstance(clazz.getClassLoader(), classes, intercepter);
		return proxyInstance;
	}

	public static void main(String... args) {
		String[] array = msg.split(" +");
		LambdaMain m = new LambdaMain();

		Print.array(m, "p1_jdk17", array);
		Print.array(m, "p1_jdk18", array);
		// Print.printArray(m, "p1_jdk18_2", array);
		// Print.printArray(m, "p1_jdk18_3", array);
		Print.array(m, "p1_jdk18_4", array);
		Print.array(m, "p2_jdk18", array);

		/*
		 * 出力をかっこよく出力したい：：
		 * ラムダ式というより、StreamAPIになってしまった
		 */
		Stream.of(Print.print(m, "p2_jdk18_2", array)).forEach(System.out::println);
		System.out.println();

		// 上の実装でもいいけれど、可能ならばもっとスマートな実装はないものか？
		Stream.concat(Stream.of(Print.print(m, "p2_jdk18_2", array)), Stream.of("-")).forEach(System.out::println);

		Stream.concat(Arrays.stream(Print.print(m, "p2_jdk18_2", array)), Stream.of("--")).forEach(System.out::println);

		Stream<String> stream = Arrays.stream(Print.print(m, "p2_jdk18_2", array));
		Stream.concat(stream, Stream.of("---")).forEach(System.out::println);

		{
			final ArrayList<String> list = new ArrayList<>();
			Stream.of(array).forEach(s -> list.add(s));
			Print.list(m, "p3_jdk18", list);
		}
		{
			// これがいいね!!
			final List<String> list = Stream.of(array).collect(Collectors.toList());
			Print.list(m, "p3_jdk18", list);
		}
		{
			System.out.println(">>>> test");
			Stream.of(array)
					.sorted((a, b) -> a.compareToIgnoreCase(b))
					.forEach(System.out::println);
			System.out.println("<<<<");
		}
		{
			// asListで生成されるクラスは、[private static java.util.Arrays$ArrayList]なので、
			// 直接参照することができない
			final List<String> list = Arrays.asList(array);
			Print.list(m, "p3_jdk18", list);
		}

		// Interceptor
		{
			System.out.println(">>>> p3_jdk18 (Interceptor)");
			final List<String> list = Arrays.asList(array);

			LambdaMainIF targetClass = getProxyInstance(new LambdaMain());

			targetClass.p3_jdk18(list);
			System.out.println("<<<<");
		}
	}
}
