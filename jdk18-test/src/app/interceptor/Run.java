package app.interceptor;

import java.util.Arrays;
import java.util.List;

import util.Print;

@PrintCall
public class Run<T extends List<? extends String>> implements RunIF<T> {

	static String msg = "2016/12/19 07:08:18  SOPE_DR_01-no_db.sh INFORMATION: ----- DR_NFSの同期処理 を開始します。 +++++";

	/*
	 * 以下はラムダ式というよりも、リフレクションと総称型をうまく使ってコードを汎用化できないか？
	 */
	/*
	public ArrayList<? extends String> p3_jdk18(ArrayList<? extends String> list) {				-
		Print.println("ArrayList<? extends String> p3_jdk18(ArrayList<? extends String> list)");-
		list.sort((a, b) -> {																	-
			return a.compareToIgnoreCase(b);													-
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
	//@AnnotationTest.MethodAnnotation
	// ⇒Intercepterの呼び出しは、interfaceからproxy	クラスが生成されるのでclass側に設定されていても無意味
	@Override
	public T p3Jdk18(T list) {
		Print.println("call \"p3Jdk18(T list)\"");
		list.sort((a, b) -> a.length() - b.length());
		return list;
	}

	@Override
	public List<? extends String> p4Jdk18(List<? extends String> list) {
		Print.println("call \"p4Jdk18(List<? extends String> list)\"");
		list.sort((a, b) -> a.length() - b.length());
		return list;
	}


	public static void main(String[] args) {
		String[] array = msg.split(" +");
		final List<String> list = Arrays.asList(array);
		RunIF<List<String>> targetClass;

		Print.println();
		Print.println(">>>> p3Jdk18 (@AnnotationTest あり)");
		targetClass = Interceptor.getProxyInstance(new Run<List<String>>()); // この記述をなくしたい
		targetClass.p3Jdk18(list);
		Print.println("<<<< p3Jdk18 (Interceptor) end");

		Print.println();
		Print.println(">>>> p4Jdk18 (@AnnotationTest　なし)");
		targetClass = Interceptor.getProxyInstance(new Run<List<String>>()); // この記述をなくしたい
		targetClass.p4Jdk18(list);
		Print.println("<<<< p4Jdk18 (Interceptor) end");
	}

}
