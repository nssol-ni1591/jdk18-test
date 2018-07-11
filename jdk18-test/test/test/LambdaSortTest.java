package test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;

import util.Print;

/*
 * ラムダ式
 */
public class LambdaSortTest {

	private static final String MSG = "2016/12/19 07:08:18  SOPE_DR_01-no_db.sh INFORMATION: ** ----- DR_NFSの同期処理 を開始します 。 +++++";
	private final String[] array = MSG.split(" +");
	private final List<String> list = Arrays.asList(array);

	@Test
	public void p1Jdk1x() {
		Print.println("(1) 配列データの単純出力");
		Print.println(array);
	}
	@Test
	public void p1Jdk1y() {
		Print.println("(2) 配列データの逆転");
		String[] b = new String[array.length];
		for (int ix = 0; ix < array.length; ix ++) {
			b[array.length - ix - 1] = array[ix];
		}
		Print.println(b);
	}

	/*
	 * 単に、配列に対して、ソートする実装方法
	 */
	@Test
	public void p1Jdk17() {
		// jdk1.7ベースなので敢えてinnerクラスを生成する
		Print.println("(1) ソート：jdk1.7ベースなので敢えてinnerクラスを生成する");
		Arrays.sort(array, new Comparator<String>() {
			@Override
			public int compare(String a, String b) {
				return a.compareToIgnoreCase(b);
			}
		});
		Print.println(array);
	}
	@Test
	public void p1Jdk18() {
		Print.println("(2) ソート： lambda式");
		Arrays.sort(array, (a, b) -> a.compareToIgnoreCase(b));
		Print.println(array);
	}
	@Test
	public void p1Jdk18p4() {
		Print.println("(3) ソート： method参照");
		Arrays.sort(array, String::compareToIgnoreCase);
		Print.println(array);
	}
	@Test
	public void p1Jdk18p5() {
		Print.println("(4) ソート： 組込関数Comparator.naturalOrder()");
		Arrays.sort(array, Comparator.naturalOrder());
		Print.println(array);
	}

	// 並び逆順
	@Test
	public void p1Jdk18p7() {
		Print.println("(1) 逆ソート： lambda式");
		Arrays.sort(array, (a, b) -> -1);
		Print.println(array);
	}
	@Test
	public void p1Jdk18p8() {
		Print.println("(2) 逆ソート： Collections.reverse(list)");
		List<String> list = Arrays.asList(array);
		Collections.reverse(list);
		Print.println(list.toArray(new String[0]));
	}
	@Test
	public void p1Jdk18p9() {
		Print.println("(3) 逆ソート： innerクラス");
		Arrays.sort(array, new Comparator<String>() {
			@Override
			public int compare(String o1, String o2) {
				return -1;
			}
		});
		Print.println(array);
	}
	@Test
	public void p1Jdk18p6() {
		Print.println("(4) 逆ソート： 組込関数Comparator.reverseOrder()");
		Arrays.sort(array, Comparator.reverseOrder());
		Print.println(array);
	}

	/*
	 * 文字のソート以外にも簡単に変更できること
	 */
	@Test
	public void p2Jdk18() {
		Print.println("(1) レコードの文字数でソート");
		// 文字数でソート
		Arrays.sort(array, (a, b) -> a.length() - b.length());
		Print.println(array);
	}
	@Test
	public void p2Jdk18p2() {
		Print.println("(2) レコードのバイト数でソート");
		// バイト数でソート
		Arrays.sort(array, (a, b) -> a.getBytes().length - b.getBytes().length);
		Print.println(array);
	}

	/*
	 * ストリームをソート
	 */
	@Test
	public void p3Jdk18() {
		Print.println("(1) 配列をstream変換してソート");
		Print.println(Stream.of(array).sorted().toArray(s -> new String[s]));
	}
	@Test
	public void p3Jdk18p2() {
		Print.println("(2) 配列をstream変換してソート(constractor参照)");
		Print.println(Stream.of(array).sorted().toArray(String[]::new));
	}
	@Test
	public void p3Jdk18p1() {
		Print.println("(3) リストをstream変換してソート");
		Print.println(list.stream().sorted().collect(Collectors.toList()));
	}

	/*
	 * 配列からリストへ変換
	 */
	@Test
	public void p4Jdk18p1() {
		Print.println("(1) forEach()で空のListに要素を積む");
		final List<String> list2 = new ArrayList<>();
		Arrays.asList(array).forEach(list2::add);
		Print.println(list2);
	}
	@Test
	public void p4Jdk18p2() {
		Print.println("(2)　colloct(Collectors.toList())を使用してStreamをListに変換する (一般的な手法)");
		Print.println(Arrays.asList(array).stream().collect(Collectors.toList()));
	}
	@Test
	public void p4Jdk18p3() {
		Print.println("(3) 配列をListに変換することは可能だが、"
				+ "asList()の返却値(Arrays$ArrayList)を引数として定義しているメソッド：p3_jdk18をリフレクションで探し出すことができない？");
		// asListで生成されるクラスは、[private static java.util.Arrays$ArrayList]なので、
		// 直接参照することができない
		Stream.of(array).collect(Collectors.toList());
		Print.println(list);
	}
	@Test
	public void p4Jdk18p4() {
		Print.println("(4) 配列をListに変換することは可能だが、asList()の返却値をArrayLlistに変換すればOK？");
		// asListで生成されるクラスは、[private static java.util.Arrays$ArrayList]なので、
		// 直接参照することができない
		Print.println(Arrays.asList(array));
	}
	

	public static void main(String... args) {
		LambdaSortTest m = new LambdaSortTest();
	
		Print.println();
		Print.println("----- Print array -----");
		m.p1Jdk1x();
		m.p1Jdk1y();

		Print.println();
		Print.println("----- Sort array -----");
		//ソート
		m.p1Jdk17();
		m.p1Jdk18();
		m.p1Jdk18p4();
		m.p1Jdk18p5();
		//逆ソート
		m.p1Jdk18p7();
		m.p1Jdk18p8();
		m.p1Jdk18p9();
		m.p1Jdk18p6();

		Print.println();
		Print.println("----- Sort array (record length) -----");
		m.p2Jdk18();
		m.p2Jdk18p2();

		Print.println();
		Print.println("----- Sort stream -----");
		m.p3Jdk18();
		m.p3Jdk18p2();
		m.p3Jdk18p1();

		Print.println();
		Print.println("----- Convert array to list -----");
		m.p4Jdk18p1();
		m.p4Jdk18p2();
		m.p4Jdk18p3();
		m.p4Jdk18p4();
	}

}
