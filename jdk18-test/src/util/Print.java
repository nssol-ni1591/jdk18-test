package util;

import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

public class Print {
	
	private static final PrintStream out = System.out;
	private static final PrintStream err = System.err;

	private Print() {
		// Do nothing
	}

	/**
	 * メソッドの呼び出しに際して、メソッド名をprintしたいが、print文とメソッド呼び出しを2重に記述するのが面倒というだけのメソッド
	 * ⇒ならloggerメソッドでもいいじゃん？
	 * @param o リフレクション対象のインスタンス。想定は、呼び出し元のクラス
	 * @param f リフレクションするメソッド名
	 * @param arg メソッドを呼び出す際の引数
	 */
	@SuppressWarnings("unchecked")
	private static <T> T wrapper(final Object o, final String f, final T arg) {
		out.println();
		out.println(">>>> Print#wrapper リフレクション: \"" + f + "\"");

		try {
			Method m;
			if (arg == null) {
				m = o.getClass().getMethod(f);
				return (T)m.invoke(o);
			}
			// 2017/02/14 引数がListの実装クラスかどうかを明示的に指定する
			else if (arg instanceof List) {
				m = o.getClass().getMethod(f, List.class);
			}
			else {
				m = o.getClass().getMethod(f, arg.getClass());
			}
			return (T)m.invoke(o, arg);
		}
		catch (NoSuchMethodException
				| SecurityException
				| IllegalAccessException
				| IllegalArgumentException
				| InvocationTargetException e) {
			stackTrace(e);
		}
		return null;
	}

	// PrintStreamのprintメソッド
	public static void println() {
		out.println();
	}
	public static void println(Object o) {
		if (o == null) {
			out.println(o);
		}
		else if (o.getClass().isArray()) {
			out.println(Arrays.asList((Object[])o));
		}
		else {
			out.println(o.toString());
		}
	}
	public static void print(Object o) {
		out.print(o.toString());
	}
	public static void stackTrace(Exception e) {
		e.printStackTrace();
	}
	public static void message(Exception e) {
		err.println(e.getMessage());
	}


	/*
	 * 以下の2つのメソッドがコンパイルエラーにならないのは少々不思議？
	 * => 返却値が異なる同一名のメソッド。staticならばOKなのか？
	 * 多重定義において、1つ目と2つ目のメソッドの返却値がことなる
	 */
	public static void print(final Object o, final String f) {
		wrapper(o, f, null);
		out.println();
	}
	/*
	 * argsにintが含まれる場合に変換ができない。呼出側でString.fomrat()を使用することで回避すること
	public static void prinf(String format, Object...args) {-
		out.printf(format, args);							-
	}														-
	*/

	/*
	 * クラスoのf（メソッド名）で指定されたメソッドをarrayを引数として実行して結果をPrintする
	 * なお、呼び出されるメソッドの返却値は配列であることを想定している
	 */
	public static void array(final Object o, final String f, final Object[] array) {
		Object[] b = array == null ? null : Arrays.copyOf(array, array.length);
		Object[] ret = wrapper(o, f, b);
		out.println(Arrays.asList(ret));
	}
	/*
	 * クラスoのf（メソッド名）で指定されたメソッドをarrayを引数として実行して結果をPrintする
	 * なお、呼び出されるメソッドの返却値はListであることを想定している
	 */
	public static <T> void list(final Object o, final String f, final T arg) {
		T list = wrapper(o, f, arg);
		if (list != null) {
			out.println(list);
		}
		out.println("<<<< Print#list end");
	}

}
