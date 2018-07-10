package app;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import util.Print;

/*
 * ラムダ式
 */
public class LambdaSort {

	static String msg = "2016/12/19 07:08:18  SOPE_DR_01-no_db.sh INFORMATION: ----- DR_NFSの同期処理 を開始します。 +++++";

	public String[] p1Jdk1x(String[] array) {
		return array;
	}
	public String[] p1Jdk1y(String[] array) {
		String[] b = new String[array.length];
		for (int ix = 0; ix < array.length; ix ++) {
			b[array.length - ix - 1] = array[ix];
		}
		return b;
	}
	/*
	 * 単に、配列に対して、ソートする実装方法
	 */
	public String[] p1Jdk17(String[] array) {
		// jdk1.7ベースなので敢えてinnerクラスを生成する
		Arrays.sort(array, new Comparator<String>() {
			@Override
			public int compare(String a, String b) {
				return a.compareToIgnoreCase(b);
			}
		});
		return array;
	}

	// 自然順序
	public String[] p1Jdk18(String[] array) {
		Arrays.sort(array, (a, b) -> a.compareToIgnoreCase(b));
		return array;
	}
	public String[] p1Jdk18p4(String[] array) {
		Arrays.sort(array, String::compareToIgnoreCase);
		return array;
	}
	public String[] p1Jdk18p5(String[] array) {
		Arrays.sort(array, Comparator.naturalOrder());
		return array;
	}
	public String[] p1Jdk18p6(String[] array) {
		Arrays.sort(array, Comparator.reverseOrder());
		return array;
	}

	// 並び逆順
	public String[] p1Jdk18p7(String[] array) {
		Arrays.sort(array, (a, b) -> -1);
		return array;
	}
	public String[] p1Jdk18p8(String[] array) {
		List<String> list = Arrays.asList(array);
		Collections.reverse(list);
		return list.toArray(new String[0]);
	}
	public String[] p1Jdk18p9(String[] array) {
		Arrays.sort(array, new Comparator<String>() {
			@Override
			public int compare(String o1, String o2) {
				return -1;
			}
		});
		return array;
	}

	/*
	 * 文字のソート以外にも簡単に変更できること
	 */
	public String[] p2Jdk18(String[] array) {
		// 文字数でソート
		Arrays.sort(array, (a, b) -> a.length() - b.length());
		return array;
	}

	public String[] p2Jdk18p2(String[] array) {
		// バイト数でソート
		Arrays.sort(array, (a, b) -> a.getBytes().length - b.getBytes().length);
		return array;
	}


	public static void main(String... args) {
		String[] array = msg.split(" +");
		LambdaSort m = new LambdaSort();

		Print.array(m, "p1Jdk1x", array);
		Print.array(m, "p1Jdk1y", array);

		Print.array(m, "p1Jdk17", array);
		Print.array(m, "p1Jdk18", array);
		// Print.printArray(m, "p1Jdk18p2", array)
		// Print.printArray(m, "p1Jdk18p3", array)
		Print.array(m, "p1Jdk18p4", array);
		Print.array(m, "p1Jdk18p5", array);
		Print.array(m, "p1Jdk18p6", array);
		Print.array(m, "p1Jdk18p7", array);
		Print.array(m, "p1Jdk18p8", array);
		Print.array(m, "p1Jdk18p9", array);

		Print.array(m, "p2Jdk18", array);



		Print.println();
		Print.println("----- Convert array to list -----");

		Print.println();
		Print.println(">>>> (1) forEach()で空のListに要素を積む");
		final ArrayList<String> list = new ArrayList<>();
		Stream.of(array).forEach(list::add);
		//Print.list(m, "p3Jdk18", list)
		Print.println(list);


		Print.println();
		Print.println(">>>> (2)　colloct()を使用してStreamをListに変換する (一般的な手法)");
		final List<String> list2 = Stream.of(array).collect(Collectors.toList());
		//Print.list(m, "p3Jdk18", list)
		Print.println(list2);

		Print.println();
		Print.println(">>>> (3) 配列をListに変換することは可能だが、"
				+ "asList()の返却値(Arrays$ArrayList)を引数として定義しているメソッド：p3_jdk18をリフレクションで探し出すことができない？");
		// asListで生成されるクラスは、[private static java.util.Arrays$ArrayList]なので、
		// 直接参照することができない
		final List<String> list3 = Arrays.asList(array);
		//Print.list(m, "p3Jdk18", list)
		Print.println(list3);

		Print.println();
		Print.println(">>>> (4) 配列をListに変換することは可能だが、asList()の返却値をArrayLlistに変換すればOK？");
		// asListで生成されるクラスは、[private static java.util.Arrays$ArrayList]なので、
		// 直接参照することができない
		final List<String> list4 = new ArrayList<>(Arrays.asList(array));
		//Print.list(m, "p3Jdk18", list)
		Print.println(list4);
	}

}
