package app.interceptor;

import java.util.Arrays;
import java.util.List;

@PrintCall
public class Run<T extends List<? extends String>> implements RunIF<T> {

	static String msg = "2016/12/19 07:08:18  SOPE_DR_01-no_db.sh INFORMATION: ----- DR_NFSの同期処理 を開始します。 +++++";

	/*
	 * 以下はラムダ式というよりも、リフレクションと総称型をうまく使ってコードを汎用化できないか？
	 */
	/*
	public ArrayList<? extends String> p3_jdk18(ArrayList<? extends String> list) {
		System.out.println("ArrayList<? extends String> p3_jdk18(ArrayList<? extends String> list)");
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
	 * リフレクション側で引数がListクラスであることを明示的に定義する　@See util.Print#wrapper
	 */
	//@AnnotationTest.MethodAnnotation
	// ⇒Intercepterの呼び出しは、interfaceからproxy	クラスが生成されるのでclass側に設定されていても無意味
	@Override
	public T p3_jdk18(T list) {
		System.out.println("call \"p3_jdk18(T list)\"");
		list.sort((a, b) -> {
			return a.length() - b.length();
		});
		return list;
	}

	@Override
	public List<? extends String> p4_jdk18(List<? extends String> list) {
		System.out.println("call \"p4_jdk18(List<? extends String> list)\"");
		list.sort((a, b) -> {
			return a.length() - b.length();
		});
		return list;
	}


	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String[] array = msg.split(" +");
		final List<String> list = Arrays.asList(array);

		{
			System.out.println("--------");
			System.out.println(">>>> p3_jdk18 (@AnnotationTest あり)");

			RunIF<List<String>> targetClass = Interceptor.getProxyInstance(new Run<List<String>>()); // この記述をなくしたい
			targetClass.p3_jdk18(list);
			System.out.println("<<<< p3_jdk18 (Interceptor) end");
		}
		{
			System.out.println("--------");
			System.out.println(">>>> p4_jdk18 (@AnnotationTest　なし)");

			RunIF<List<String>> targetClass = Interceptor.getProxyInstance(new Run<List<String>>()); // この記述をなくしたい
			targetClass.p4_jdk18(list);
			System.out.println("<<<< p4_jdk18 (Interceptor) end");
		}
	}

}
