package test;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import util.Print;

/*
 * ラムダ式
 */
public class PrintTest {

	private static final String MSG = "2016/12/19 07:08:18  SOPE_DR_01-no_db.sh INFORMATION: ** ----- DR_NFSの同期処理 を開始します 。 +++++";

	public String[] array(String[] array) {
		Print.println("(1)  配列データの単純出力");
		return array;
	}
	public List<String> list(List<String> list) {
		Print.println("(2)  リストデータの単純出力");
		return list;
	}
	public String print() {
		Print.println("(3)  文字列出力");
		return MSG;
	}

	@Test
	public void test1() {
		PrintTest m = new PrintTest();
		final String[] array = MSG.split(" +");
		final List<String> list = Arrays.asList(array);

		Print.print(m, "print");

		// 配列の出力イメージはオブジェクトのアドレスになる
		Print.array(m, "array", array);
		System.out.println(array);

		// listの出力イメージは同じ
		Print.list(m, "list", list);
		System.out.println(list);
	}
	@Test
	public void test2() {
		Print.println();
		Print.println(null);
		Print.println("abc");
		Print.println(MSG.split(" +"));
		Print.print("xyz");
	}

	@Test
	public void test9() {
		Exception ex = new Exception("Exception massage");
		Print.stackTrace(ex);
		Print.message(ex);
	}

}
