package app;

import java.util.Optional;
import java.util.Random;
import java.util.stream.Stream;

import util.Print;

public class Passwd {

	private static final long SEED = 19610802;
	private static final String[] PW_REQUIRE1 = {
			"[\\w!?#$%&~^:;|=+*/-]+", // 使用可能な文字
			".*[A-Z].*", // 英大文字必須
			".*[a-z].*", // 英子文字必須
			".*[0-9].*", // 数字必須
			".*[!?#$%&~^:;|=+*/-].*", // 記号必須
			"(?!.*(.).*\\1).+" // 重複排除
	};
	private static final String[] PW_REQUIRE2 = {
			"\\w+",		// 使用可能な文字
			"[^_]+"
	};

	private static final String MSG_LEN_EQ_20 = "パスワード要件： 20文字であること";
	private static final String MSG_ATTR_LITTLE = "パスワード要件： 英数字のみ、8文字";
	private static final String MSG_COMBINATION = "パスワード要件： 組み合わせ";

	/*
	 * patterns の正規化表現はAND条件 ... 当たり前だが
	 */
	// 事前に10000個のパスワードを組み立てて、その中から条件に合うパスワードを拾いだすパターン
	public static String passgen(int len, String... patterns) {
		Random random = new Random(SEED);
		Stream<String> stream = Stream.generate(() -> new String(random.ints('!', '~' + 1)
				.limit(len)
				.toArray(), 0, len))
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

	// もう少し効率をよくしたパターン
	public static String passgen2(int len, String... patterns) {
		Random random = new Random(SEED);
		Optional<String> pw = Stream.generate(() -> new String(random.ints('!', '~' + 1)
				.limit(len)
				.toArray(), 0, len))
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
		Optional<String> pw = Stream.generate(() -> new String(random.ints('!', '~' + 1)
				.limit(len)
				.toArray(), 0, len))
				.filter(s -> Stream.of(patterns)
						.allMatch(s::matches))
				.findFirst();
		return pw.isPresent() ? pw.get() : null;
	}

	public static void main(String[] args) {

		/*
		 * Generate passgen()
		 */
		// 20文字であること
		Print.println(MSG_LEN_EQ_20);
		Print.println(passgen(20));

		// 英数字のみ、8文字
		Print.println(MSG_ATTR_LITTLE);
		Print.println(passgen(8, PW_REQUIRE2));
		// 5cPvpSXd

		// 組み合わせ
		Print.println(MSG_COMBINATION);
		Print.println(passgen(20, PW_REQUIRE1));

		Print.println();

		/*
		 * Generate passgen2()
		 */
		// 20文字であること
		Print.println(MSG_LEN_EQ_20);
		Print.println(passgen2(20));

		// 英数字のみ、8文字
		Print.println(MSG_ATTR_LITTLE);
		Print.println(passgen2(8, PW_REQUIRE2));
		// 5cPvpSXd

		// 組み合わせ
		Print.println(MSG_COMBINATION);
		Print.println(passgen2(20, PW_REQUIRE1));

		Print.println();

		/*
		 * Generate passgen3()
		 */
		// 20文字であること
		Print.println(MSG_LEN_EQ_20);
		Print.println(passgen3(20));
		Print.println(passgen3(8));
		// CEIJC'Hvd{&C9@!,T][7

		// 英数字のみ、8文字
		Print.println(MSG_ATTR_LITTLE);
		Print.println(passgen3(8, PW_REQUIRE2));
		// 5cPvpSXd

		// 組み合わせ
		Print.println(MSG_COMBINATION);
		Print.println(passgen3(20, PW_REQUIRE1));

		// No.12 を有効にしてみる
		Print.println("パスワード要件： 英数字+(、8文字");
		Print.println(passgen3(8, "[\\w\\(]+"));
		// 5cPvpSXd
	}

}
