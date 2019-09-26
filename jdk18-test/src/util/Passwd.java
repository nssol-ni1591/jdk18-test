package util;

import java.util.Optional;
import java.util.Random;
import java.util.stream.Stream;

public class Passwd {

	private static final long SEED = 19610802;

	private Passwd() {
		// Do nothing
	}

	/*
	 * patterns の正規化表現はAND条件 ... 当たり前だが
	 */
	// 事前に10000個のパスワードを組み立てて、その中から条件に合うパスワードを拾いだすパターン
	public static String passgen(int len, String... patterns) {
		Random random = new Random(SEED);
		Stream<String> stream =
			Stream.generate(() -> new String(random.ints('!', '~' + 1).limit(len).toArray(), 0, len))
				.limit(10000);
		// 適当かつ重要
		// つまり、10000個の文字列を作成しておいて、その中から条件に合う文字列を抜き出す

		// 条件にあう文字列が生成できた時点で終了させることができればよいのに

		// Stream に要求パターンで適合テストするフィルタを追加
		for (String p : patterns) {
			stream = stream.filter(s -> s.matches(p));
		}

		// 最初にすべてのテストをパスした文字列を返す
		Optional<String> pw = stream.findFirst();
		return pw.isPresent() ? pw.get() : null;
	}

	// もう少し効率を良くしたパターン（無条件に10000個の文字列を生成するのをやめる）
	public static String passgen2(int len, String... patterns) {
		Random random = new Random(SEED);
		Optional<String> pw =
			Stream.generate(() -> new String(random.ints('!', '~' + 1).limit(len).toArray(), 0, len))
				.filter(s -> {
					for (String p : patterns) {
						if (!s.matches(p))
							return false;
					}
					return true;
				})
				.findFirst();
		return pw.isPresent() ? pw.get() : null;
	}

	// パスワード要件にallMatch()を使用した場合
	public static String passgen3(int len, String... patterns) {
		Random random = new Random(SEED);
		Optional<String> pw =
			Stream.generate(() -> new String(random.ints('!', '~' + 1).limit(len).toArray(), 0, len))
				.filter(s -> Stream.of(patterns).allMatch(s::matches))
				.findFirst();
		return pw.isPresent() ? pw.get() : null;
	}

}
