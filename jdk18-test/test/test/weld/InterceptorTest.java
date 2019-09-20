package test.weld;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import app.interceptor.Interceptor;
import app.interceptor.PrintCall;
import util.Print;

public class InterceptorTest {

	static String msg = "2016/12/19 07:08:18  SOPE_DR_01-no_db.sh INFORMATION: ----- DR_NFSの同期処理 を開始します。 +++++";

	/*
	 * 以下はラムダ式というよりも、リフレクションと総称型をうまく使ってコードを汎用化できないか？
	 */
	/*
	app.jdk18.LambdaSort:
	public ArrayList<? extends String> p3_jdk18(ArrayList<? extends String> list) {				-
		Print.println("ArrayList<? extends String> p3_jdk18(ArrayList<? extends String> list)");-
		list.sort((a, b) -> {																	-
			return a.length() - b.length();													-
		});																						-
		return list;																			-
	}																							-
	*/

	/*
	 * 引数をArrayListからListのジェネリック化により汎用性を高めた（つもり）
	 * リフレクション経由でアクセスする場合、引数がListの実装クラス（ArrayList）であっても、このメソッドを探し出すことができない
	 * 2017/02/14時点では、Interceptorロジックしかcallされない
	 * リフレクション側で引数がListクラスであることを明示的に定義する　@See util.Print#wrapper
	 */
	@Test
	public void test1() {
		String[] array = msg.split(" +");
		final List<String> list = Arrays.asList(array);

		Print.println();
		Print.println(">>>> test1:　Annotation が interface にあり => 追加処理：メソッド名と引数が出力されているはず");
		RunIF1<List<String>> targetClass = Interceptor.getProxyInstance(new Test1<List<String>>()); // この記述をなくしたい
		targetClass.p4Jdk18(list);
		Print.println("<<<< test1 (Interceptor) end");
	}
	@Test
	public void test2() {
		String[] array = msg.split(" +");
		final List<String> list = Arrays.asList(array);

		Print.println();
		Print.println(">>>> test2:　Annotation が interfaceにも実装クラスにもない => 追加処理が出力されない");
		RunIF2<List<String>> targetClass = Interceptor.getProxyInstance(new Test2<List<String>>()); // この記述をなくしたい
		targetClass.p4Jdk18(list);
		Print.println("<<<< test2 (Interceptor) end");
	}
	@Test
	public void test3() {
		String[] array = msg.split(" +");
		final List<String> list = Arrays.asList(array);

		Print.println();
		Print.println(">>>> test3:　Annotation が 実装クラスにあり => 追加処理が出力されない （InterceptorクラスはInterfaceを取得しているので当然）");
		RunIF2<List<String>> targetClass = Interceptor.getProxyInstance(new Test3<List<String>>()); // この記述をなくしたい
		targetClass.p4Jdk18(list);
		Print.println("<<<< test3 (Interceptor) end");
	}
	@Test
	public void test4() {
		String[] array = msg.split(" +");
		final List<String> list = Arrays.asList(array);

		Print.println();
		Print.println(">>>> test4:　Annotation が 実装クラスにあり => AOP処理自体が行われない （Interfaceを使用していないので当然）");
		Test3<List<String>> targetClass = Interceptor.getProxyInstance(new Test3<List<String>>()); // この記述をなくしたい
		targetClass.p4Jdk18(list);
		Print.println("<<<< test3 (Interceptor) end");
	}
	@Test(expected = ClassCastException.class)
	public void test9() {
		String[] array = msg.split(" +");
		final List<String> list = Arrays.asList(array);
		Test2<List<String>> targetClass;

		Print.println();
		Print.println(">>>> test9: InterceptorのproxyクラスはInterfaceで受けないと例外が発生する");
		targetClass = Interceptor.getProxyInstance(new Test2<List<String>>()); // この記述をなくしたい
		targetClass.p4Jdk18(list);
		Print.println("<<<< test9 (Interceptor) end");
	}

	public interface RunIF1<T extends List<? extends String>> {
		@PrintCall.MethodAnnotation
		List<? extends String> p4Jdk18(List<? extends String> list);
	}
	public interface RunIF2<T extends List<? extends String>> {
		List<? extends String> p4Jdk18(List<? extends String> list);
	}

	public class Test1<T extends List<? extends String>> implements RunIF1<T> {

		@Override
		public List<? extends String> p4Jdk18(List<? extends String> list) {
//			Print.println("Test1: call \"p4Jdk18(List<? extends String> list)\"");
			list.sort((a, b) -> a.length() - b.length());
			return list;
		}
	}
	public class Test2<T extends List<? extends String>> implements RunIF2<T> {

		@Override
		public List<? extends String> p4Jdk18(List<? extends String> list) {
//			Print.println("Test2: call \"p4Jdk18(List<? extends String> list)\"");
			list.sort((a, b) -> a.length() - b.length());
			return list;
		}
	}
	public class Test3<T extends List<? extends String>> implements RunIF2<T> {

		@Override
		@PrintCall.MethodAnnotation
		public List<? extends String> p4Jdk18(List<? extends String> list) {
//			Print.println("TEST3: call \"p4Jdk18(List<? extends String> list)\"");
			list.sort((a, b) -> a.length() - b.length());
			return list;
		}
	}

}
