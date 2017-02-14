package util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public class Print {

	/**
	 * メソッドの呼び出しに際して、メソッド名をprintしたいが、print文とメソッド呼び出しを2重に記述するのが面倒というだけのメソッド
	 * ⇒ならloggerメソッドでもいいじゃん？
	 * @param o リフレクション対象のインスタンス。想定は、呼び出し元のクラス
	 * @param f リフレクションするメソッド名
	 * @param arg メソッドを呼び出す際の引数
	 */
	static private <T> T wrapper(final Object o, final String f, final T arg) {
		System.err.println(">>>> Print#wrapper リフレクション: \"" + f + "\"");
		try {
			Method m;
			if (arg == null) {
				m = o.getClass().getMethod(f, new Class[0]);
				return (T) m.invoke(o, null);
			}
			// 2017/02/14 引数がListの実装クラスかどうかを明示的に指定する
			else if (arg instanceof List) {
				m = o.getClass().getMethod(f, List.class);
				return (T) m.invoke(o, arg);
			}
			else {
				m = o.getClass().getMethod(f, arg.getClass());
				return (T) m.invoke(o, arg);
			}
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/*
	 * 以下の2つのメソッドがコンパイルエラーにならないのは少々不思議？
	 * 多重定義において、1つ目と2つ目のメソッドの返却値がことなる
	 */
	static public void print(final Object o, final String f) {
		wrapper(o, f, null);
		System.err.println();
	}
	static public <T> T print(final Object o, final String f, final T arg) {
		T rc = wrapper(o, f, arg);
		return rc;
	}

	/*
	 * 配列をprintする
	 */
	static public void array(final Object o, final String f, final Object[] array) {
		//Object ret = print(o, f, array);
		//String[] array2 = (String[])ret;

		Object[] ret = wrapper(o, f, array);
		for (Object s : ret) {
			System.err.println(s.toString());
		}
		System.err.println("<<<< Print#array end");
	}
	//static public <T extends List<?>> T list(final Object o, final String f, final T array) {
	static public <T extends List<? extends String>> T list(final Object o, final String f, final T array) {
		T rc = wrapper(o, f, array);
		if (rc != null) {
			rc.forEach(System.err::println);
		}
		System.err.println("<<<< Print#list end");
		return rc;
	}
}
