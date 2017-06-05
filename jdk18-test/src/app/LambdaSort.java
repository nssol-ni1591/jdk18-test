package app;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import util.Print;

public class LambdaSort<T extends List<? extends String>> {

	static String msg = "2016/12/19 07:08:18  SOPE_DR_01-no_db.sh INFORMATION: ----- DR_NFSの同期処理 を開始します。 +++++";

	public String[] p1_jdk1x(String[] array) {
		return array;
	}
	public String[] p1_jdk1y(String[] array) {
		String[] b = new String[array.length];
		for (int ix = 0; ix < array.length; ix ++) {
			b[array.length - ix - 1] = array[ix];
		}
		return b;
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

	// 自然順序
	public String[] p1_jdk18(String[] array) {
		Arrays.sort(array, (a, b) -> a.compareToIgnoreCase(b));
		return array;
	}
	public String[] p1_jdk18_4(String[] array) {
		Arrays.sort(array, String::compareToIgnoreCase);
		return array;
	}
	public String[] p1_jdk18_5(String[] array) {
		Arrays.sort(array, Comparator.naturalOrder());
		return array;
	}
	public String[] p1_jdk18_6(String[] array) {
		Arrays.sort(array, Comparator.reverseOrder());
		return array;
	}

	// 並び逆順
	public String[] p1_jdk18_7(String[] array) {
		Arrays.sort(array, (a, b) -> { return -1; });
		return array;
	}
	public String[] p1_jdk18_8(String[] array) {
		List<String> list = Arrays.asList(array);
		Collections.reverse(list);
		return (String[])list.toArray();
	}
	public String[] p1_jdk18_9(String[] array) {
		Arrays.sort(array, new Comparator<String>() {
			public int compare(String o1, String o2) {
				return -1;
			}
		});
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


	public static void main(String... args) {
		String[] array = msg.split(" +");
		LambdaSort<List<String>> m = new LambdaSort<List<String>>();

		Print.array(m, "p1_jdk1x", array);
		Print.array(m, "p1_jdk1y", array);

		Print.array(m, "p1_jdk17", array);
		Print.array(m, "p1_jdk18", array);
		// Print.printArray(m, "p1_jdk18_2", array);
		// Print.printArray(m, "p1_jdk18_3", array);
		Print.array(m, "p1_jdk18_4", array);
		Print.array(m, "p1_jdk18_5", array);
		Print.array(m, "p1_jdk18_6", array);
		Print.array(m, "p1_jdk18_7", array);
		Print.array(m, "p1_jdk18_8", array);
		Print.array(m, "p1_jdk18_9", array);

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

		
		System.out.println();
		System.out.println("----- Convert array to list -----");
		{
			System.out.println();
			System.out.println(">>>> (1) forEach()で空のListに要素を積む");

			final ArrayList<String> list = new ArrayList<>();
			Stream.of(array).forEach(s -> list.add(s));
			Print.list(m, "p3_jdk18", list);
		}
		{
			System.out.println();
			System.out.println(">>>> (2)　colloct()を使用してStreamをListに変換する (一般的な手法)");

			final List<String> list = Stream.of(array).collect(Collectors.toList());
			Print.list(m, "p3_jdk18", list);
		}

		{
			System.out.println();
			System.out.println(">>>> (3) 配列をListに変換することは可能だが、"
					+ "asList()の返却値(Arrays$ArrayList)を引数として定義しているメソッド：p3_jdk18をリフレクションで探し出すことができない？");
			// asListで生成されるクラスは、[private static java.util.Arrays$ArrayList]なので、
			// 直接参照することができない
			final List<String> list = Arrays.asList(array);
			Print.list(m, "p3_jdk18", list);
		}
		{
			System.out.println();
			System.out.println(">>>> (4) 配列をListに変換することは可能だが、asList()の返却値をArrayLlistに変換すればOK？");
			// asListで生成されるクラスは、[private static java.util.Arrays$ArrayList]なので、
			// 直接参照することができない
			final List<String> list = new ArrayList<String>(Arrays.asList(array));
			Print.list(m, "p3_jdk18", list);
		}
	}

}
