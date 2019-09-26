package app.interceptor;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

import app.interceptor.PrintCall.MethodAnnotation;
import util.Print;


public class Interceptor implements InvocationHandler {

	private Object target;

	public Interceptor(Object target) {
		this.target = target;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

		Print.println("called Intercepter.invoke");

		enter(method, args);

		// 実際のコードを呼び出し
		Object result = method.invoke(target, args);

		leave(method, result);

		// 実施した結果を返し、通常の実施を同じにする
		return result;
	}

	private void enter(Method method, Object[] args) {

		Print.println("start AOP: " + method.getName());

		Arrays.stream(method.getAnnotations()).forEach(Print::println);

		// MethodAnnotationアノテーションがついていないメソッドは、追加処理せずreturnする
		// ちなみに、Annotationは、実装クラスではなくinterfaceのメソッドに付加する => InterceptorはgetInterface()しか用意していない
		// "Stream" call chains should be simplified when possible 
		if (Arrays.stream(method.getAnnotations()).noneMatch(p -> p instanceof MethodAnnotation)) {
			return;
		}

		// 追加処理：
		// 引数の一覧を作成
		/*
		StringBuilder sb = new StringBuilder();
		if (args != null) {
			Arrays.stream(args).forEach(arg -> sb.append(arg.toString()).append(" "));
		}
		// メソッド名と引数を出力
		Print.println("追加処理: method:[" + method.getName() + "] param:[" + sb.toString() + "]");
		 */
		// 配列の要素一覧はListに変換すれば実現できる
		Print.println("呼出元 method:[" + method.getName() + "] param:" + Arrays.asList(args));
	}

	private void leave(Method method, Object result) {

		// AOPの処理が完了したことを出力。空行も出力。
		Print.println("end AOP: " + method.getName());

		// 結果がnullでなければ、結果を出力する
		if (result != null) {
			Print.println("result:" + result.toString());
		}
	}

	public static <T> T getProxyInstance(T instance) {
		Class<? extends Object> clazz = instance.getClass();
		// 対象クラスが実装するインターフェースのリスト
		@SuppressWarnings("rawtypes")
		Class[] classes = clazz.getInterfaces();
		Interceptor intercepter = new Interceptor(instance);
		@SuppressWarnings("unchecked")
		T proxyInstance = (T) Proxy.newProxyInstance(clazz.getClassLoader(), classes, intercepter);
		return proxyInstance;
	}

}
