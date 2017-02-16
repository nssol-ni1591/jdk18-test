package util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public class Print {

	@SuppressWarnings("unchecked")
	static private <T> T cast(final Object o) {
		return (T) o;
	}

	/**
	 * メソッドの呼び出しに際して、メソッド名をprintしたいが、print文とメソッド呼び出しを2重に記述するのが面倒というだけのメソッド
	 * ⇒ならloggerメソッドでもいいじゃん？
	 * @param o リフレクション対象のインスタンス。想定は、呼び出し元のクラス
	 * @param f リフレクションするメソッド名
	 * @param arg メソッドを呼び出す際の引数
	 */
	static private <T> T wrapper(final Object o, final String f, final T arg) {
		System.out.println(">>>> Print#wrapper リフレクション: \"" + f + "\"");
		try {
			Method m;
			if (arg == null) {
				m = o.getClass().getMethod(f);
			}
			// 2017/02/14 引数がListの実装クラスかどうかを明示的に指定する
			else if (arg instanceof List) {
				m = o.getClass().getMethod(f, List.class);
			}
			else {
				m = o.getClass().getMethod(f, arg.getClass());
			}
			return cast(m.invoke(o, arg));

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
		System.out.println();
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
			System.out.println(s.toString());
		}
		System.out.println("<<<< Print#array end");
	}
	//static public <T extends List<?>> T list(final Object o, final String f, final T array) {
	static public <T extends List<? extends String>> T list(final Object o, final String f, final T array) {
		T rc = wrapper(o, f, array);
		if (rc != null) {
			rc.forEach(System.out::println);
		}
		System.out.println("<<<< Print#list end");
		return rc;
	}
}
