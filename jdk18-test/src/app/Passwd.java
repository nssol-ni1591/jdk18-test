package app;

import java.util.Optional;
import java.util.Random;
import java.util.stream.Stream;

public class Passwd {

	public static long SEED = 19610802;


	/*
	 * patterns の正規化表現はAND条件 ... 当たり前だが
	 */
	public static Optional<String> passgen(int len, String... patterns) {
		// Random random = new Random(System.currentTimeMillis());
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
		return stream.findFirst();
	}

	public static Optional<String> passgen2(int len, String... patterns) {
		// Random random = new Random(System.currentTimeMillis());
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
		return pw;
	}

	public static Optional<String> passgen3(int len, String... patterns) {
		// Random random = new Random(System.currentTimeMillis());
		Random random = new Random(SEED);
		Optional<String> pw = Stream.generate(() -> new String(random.ints('!', '~' + 1)
				.limit(len)
				.toArray(), 0, len))
				.filter(s -> Stream.of(patterns)
						.allMatch(s::matches))
				.findFirst();
		return pw;
	}

	public static void main(String[] args) {

		/*
		 * Generate passgen()
		 */
		// 20文字であること
		System.out.println(passgen(20).get());
		// System.out.println(passgen(8).get());
		// CEIJC'Hvd{&C9@!,T][7

		// 英数字のみ、8文字
		System.out.println(passgen(8, "\\w+", "[^_]+").get());
		// 5cPvpSXd

		// 組み合わせ
		System.out.println(passgen(8, // 8文字
				"[\\w!?#$%&~^:;|=+*/-]+", // 使用可能な文字
				".*[A-Z].*", // 英大文字必須
				".*[a-z].*", // 英子文字必須
				".*[0-9].*", // 数字必須
				".*[!?#$%&~^:;|=+*/-].*", // 記号必須
				"(?!.*(.).*\\1).+" // 重複排除
		).get());
		// /|DsG9^!

		/*
		 * Generate passgen2()
		 */
		// 20文字であること
		System.out.println(passgen2(20).get());
		// CEIJC'Hvd{&C9@!,T][7

		// 英数字のみ、8文字
		System.out.println(passgen2(8, "\\w+", "[^_]+").get());
		// 5cPvpSXd

		// 組み合わせ
		System.out.println(passgen2(8, // 8文字
				"[\\w!?#$%&~^:;|=+*/-]+", // 使用可能な文字
				".*[A-Z].*", // 英大文字必須
				".*[a-z].*", // 英子文字必須
				".*[0-9].*", // 数字必須
				".*[!?#$%&~^:;|=+*/-].*", // 記号必須
				"(?!.*(.).*\\1).+" // 重複排除
		).get());
		// /|DsG9^!

		/*
		 * Generate passgen3()
		 */
		// 20文字であること
		System.out.println(passgen3(20).get());
		System.out.println(passgen3(8).get());
		// CEIJC'Hvd{&C9@!,T][7

		// 英数字のみ、8文字
		System.out.println(passgen3(8, "\\w+", "[^_]+").get());
		// 5cPvpSXd

		// 組み合わせ
		System.out.println(passgen3(8, // 8文字
				"[\\w!?#$%&~^:;|=+*/-]+", // 使用可能な文字
				".*[A-Z].*", // 英大文字必須
				".*[a-z].*", // 英子文字必須
				".*[0-9].*", // 数字必須
				".*[!?#$%&~^:;|=+*/-].*", // 記号必須
				"(?!.*(.).*\\1).+" // 重複排除
		).get());
		// /|DsG9^!

		// No.12 を有効にしてみる
		System.out.println(passgen3(8, "[\\w\\(]+").get());
		// 5cPvpSXd
	}

}
