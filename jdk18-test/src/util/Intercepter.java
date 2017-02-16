package util;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;

import util.AnnotationTest.MethodAnnotation;

public class Intercepter implements InvocationHandler {

    private Object target;

    public Intercepter(Object target) {
        this.target = target;
    }

    @Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		// TODO Auto-generated method stub

    	System.out.println("called Intercepter.invoke");

    	enter(method, args);

    	// 実際のコードを呼び出し
		Object result = method.invoke(target, args);

		leave(method, result);

		// 実施した結果を返し、通常の実施を同じにする
		return result;
    }
    
    private void enter(Method method, Object[] args) {

		// 以下、MethodAnnotationアノテーションがついているメソッドは、処理を追加する
		System.out.println("AOP処理開始");

		// MethodAnnotationアノテーションがついていないメソッドは、追加処理せず、終了。
		if (!Arrays.stream(method.getAnnotations()).anyMatch(p -> p instanceof MethodAnnotation)) {
			//return method.invoke(target, args);
			return;
		}

		// 引数の一覧を作成
		StringBuilder sb = new StringBuilder();
		if (args != null) {
			Arrays.stream(args).forEach(arg -> sb.append(arg.toString()).append(" "));
		}
		// メソッド名と引数を出力
		System.out.println("呼び出しメソッド:" + method.getName() + " 引数:" + sb.toString());
		// 実際に実施し、結果を保存する
    }
    
	//	Object result = method.invoke(target, args);

    private void leave(Method method, Object result) {

		// AOPの処理が完了したことを出力。空行も出力。
		System.out.println("AOP処理完了");
		//System.out.println();

		// 結果がnullでなければ、結果を出力する
		if (result != null) {
			System.out.println("結果:" + result.toString());
		}
	}

}
