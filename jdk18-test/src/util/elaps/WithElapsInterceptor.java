package util.elaps;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.Priority;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

@Interceptor	// インターセプターの宣言
@Dependent
@WithElaps		// バインド用アノテーション
@Priority(Interceptor.Priority.APPLICATION) // 優先度
public class WithElapsInterceptor {

	@Inject private Logger log;

	/**
	 * インターセプターのメソッド
	 * 
	 * @param ic 実行コンテキスト - 本来実行される処理。
	 * @return 本来実行される処理の戻り値
	 * @throws Exception 何らかの例外
	 */
	@AroundInvoke
	public Object invoke(InvocationContext ic) throws Exception {
		// ターゲットは、CDIのクライアントプロキシなので、スーパークラスを取得。
		String classAndMethod = ic.getTarget().getClass().getSuperclass().getName() + "#" + ic.getMethod().getName();

		Object rc = null;
		long time = System.currentTimeMillis();
		try {
			// メソッドの実行
			rc = ic.proceed();
			return rc;
		}
		finally {
			time = System.currentTimeMillis() - time;
			// メソッド終了に経過時間のログを出力
			log.log(Level.INFO, "{0} elaps={1} ms", new Object[] { classAndMethod, time });
		}
	}

}
