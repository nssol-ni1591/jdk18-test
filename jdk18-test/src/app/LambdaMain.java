package app;

import java.lang.reflect.Proxy;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import util.Intercepter;
import util.Print;
import util.ValidationStrategy;
import util.Validator;

public class LambdaMain<T extends List<? extends String>> implements LambdaMainIF<T> {

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
			System.err.println("a=" + a + ", b=" + b);
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
	 * 以下はラムダ式というよりも、リフレクションと総称型をうまく使ってコードを汎用化できないか？
	 */
	/*
	public ArrayList<? extends String> p3_jdk18(ArrayList<? extends String> list) {
		System.err.println("ArrayList<? extends String> p3_jdk18(ArrayList<? extends String> list)");
		list.sort((a, b) -> {
			return a.compareToIgnoreCase(b);
		});
		return list;
	}
	*/
	/*
	 * 引数をArrayListからListのジェネリック化により汎用性を高めた（つもり）
	 * リフレクション経由でアクセスする場合、引数がListの実装クラス（ArrayList）であっても、このメソッドを探し出すことができない
	 * 2017/02/14時点では、Interceptorロジックしかcallされない
	 * リフレクション側で引数がListクラスであることを明示的に定義する
	 */
	//public <U extends List<? extends String>> p3_jdk18(<U extends List<? extends String>> list) {
	public T p3_jdk18(T list) {
		System.err.println("<T extends List<? extends String>> p3_jdk18(<T extends List<? extends String>> list)");
		list.sort((a, b) -> {
			return a.length() - b.length();
		});
		return list;
	}

	
	public List<? extends String> p4_jdk18(List<? extends String> list) {
		list.sort((a, b) -> {
			return a.length() - b.length();
		});
		return list;
	}

	public static <T> T getProxyInstance(T instance) {
		Class<? extends Object> clazz = instance.getClass();
		// 対象クラスが実装するインターフェースのリスト
		@SuppressWarnings("rawtypes")
		Class[] classes = clazz.getInterfaces();
		Intercepter intercepter = new Intercepter(instance);
		@SuppressWarnings("unchecked")
		T proxyInstance = (T) Proxy.newProxyInstance(clazz.getClassLoader(), classes, intercepter);
		return proxyInstance;
	}

	public static void main(String... args) {
		String[] array = msg.split(" +");
		LambdaMain<List<String>> m = new LambdaMain<List<String>>();

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
		Stream.of(Print.print(m, "p2_jdk18_2", array)).forEach(System.err::println);
		System.err.println();

		// 上の実装でもいいけれど、可能ならばもっとスマートな実装はないものか？
		Stream.concat(Stream.of(Print.print(m, "p2_jdk18_2", array)), Stream.of("-")).forEach(System.err::println);

		Stream.concat(Arrays.stream(Print.print(m, "p2_jdk18_2", array)), Stream.of("--")).forEach(System.err::println);

		Stream<String> stream = Arrays.stream(Print.print(m, "p2_jdk18_2", array));
		Stream.concat(stream, Stream.of("---")).forEach(System.err::println);

		
		System.err.println();
		System.err.println("----- Convert array to list -----");
		{
			System.err.println();
			System.err.println(">>>> (1) forEach()で空のListに要素を積む");

			final ArrayList<String> list = new ArrayList<>();
			Stream.of(array).forEach(s -> list.add(s));
			Print.list(m, "p3_jdk18", list);
		}
		{
			System.err.println();
			System.err.println(">>>> (2)　colloct()を使用してStreamをListに変換する (一般的な手法)");

			final List<String> list = Stream.of(array).collect(Collectors.toList());
			Print.list(m, "p3_jdk18", list);
		}

		{
			System.err.println();
			System.err.println(">>>> (3) 配列をListに変換することは可能だが、"
					+ "asList()の返却値(Arrays$ArrayList)を引数として定義しているメソッド：p3_jdk18をリフレクションで探し出すことができない？");
			// asListで生成されるクラスは、[private static java.util.Arrays$ArrayList]なので、
			// 直接参照することができない
			final List<String> list = Arrays.asList(array);
			Print.list(m, "p3_jdk18", list);
		}
		{
			System.err.println();
			System.err.println(">>>> (4) 配列をListに変換することは可能だが、asList()の返却値をArrayLlistに変換すればOK？");
			// asListで生成されるクラスは、[private static java.util.Arrays$ArrayList]なので、
			// 直接参照することができない
			final List<String> list = new ArrayList<String>(Arrays.asList(array));
			Print.list(m, "p3_jdk18", list);
		}

		// Interceptor
		{
			System.err.println();
			System.err.println(">>>> p3_jdk18 (Interceptor)");
			final List<String> list = Arrays.asList(array);

			LambdaMainIF<List<String>> targetClass = getProxyInstance(new LambdaMain<List<String>>());	//この記述をなくしたい

			targetClass.p3_jdk18(list);
			System.err.println("<<<< p3_jdk18 (Interceptor) end");
		}

		System.err.println("--------");
		// Design pattern
		// (1) ストラテジ・パターン
		{
			System.err.println("<<<< ストラテジ・パターン (jdk18)");
			// jdk1.8
			Validator v3 = new Validator((String s) -> s.matches("\\d+"));
			System.err.println(v3.validate("aaaa"));

			Validator v4 = new Validator((String s) -> s.matches("[a-z]+"));
			System.err.println(v4.validate("bbbb"));
		}
		{
			System.err.println("<<<< ストラテジ・パターン (直接ストラテジのインターフェースを使用することもできるはず)");
			// jdk1.8
			ValidationStrategy v3 = (String s) -> s.matches("\\d+");
			System.err.println(v3.execute("aaaa"));

			ValidationStrategy v4 = (String s) -> s.matches("[a-z]+");
			System.err.println(v4.execute("bbbb"));
		}
		{
			System.err.println("<<<< ストラテジ・パターン (java.util.functionを使う)");
			// jdk1.8
			Predicate<String> p3 = s -> s.matches("\\d+");
			System.err.println(p3.test("aaaa"));

			Predicate<String> p4 = s -> s.matches("[a-z]+");
			System.err.println(p4.test("bbbb"));
		}
		{
			System.err.println("<<<< ストラテジ・パターン 2()");
			// jdk1.8
			Function<String, Boolean> f3 = s -> s.matches("\\d+");
			System.err.println(f3.apply("aaaa"));

			Function<String, Integer> f4 = s -> s.length();
			System.err.println(f4.apply("aaaa"));
		}
		
		System.err.println("--------");
		{
			Path p = Paths.get("a", "b", "cee"); // line n1
			System.out.println(p);
			System.out.println(p.endsWith(Paths.get("b", "cee")));
			System.out.println(p.endsWith(Paths.get("ee")));
		}
	}
}
