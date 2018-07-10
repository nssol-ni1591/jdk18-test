package test;

import org.junit.Test;

import util.Passwd;
import util.Print;

public class PasswdTest {

	private static final String MSG_LEN_EQ_20 = "パスワード要件： 20文字であること";
	private static final String MSG_ATTR_LITTLE = "パスワード要件： 英数字のみ、8文字";
	private static final String MSG_COMBINATION = "パスワード要件： 組み合わせ";

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

	/*
	 * Generate passgen()
	 */
	@Test
	public void test11() {
		// 20文字であること
		Print.println(MSG_LEN_EQ_20);
		Print.println(Passwd.passgen(20));
	}
	@Test
	public void test12() {
		// 英数字のみ、8文字
		Print.println(MSG_ATTR_LITTLE);
		Print.println(Passwd.passgen(8, PW_REQUIRE2));
		// 5cPvpSXd
	}
	@Test
	public void test13() {
		// 組み合わせ
		Print.println(MSG_COMBINATION);
		Print.println(Passwd.passgen(20, PW_REQUIRE1));
	}
	//Print.println();

		/*
		 * Generate passgen2()
		 */
	@Test
	public void test21() {
		// 20文字であること
		Print.println(MSG_LEN_EQ_20);
		Print.println(Passwd.passgen2(20));
	}
	@Test
	public void test22() {
		// 英数字のみ、8文字
		Print.println(MSG_ATTR_LITTLE);
		Print.println(Passwd.passgen2(8, PW_REQUIRE2));
		// 5cPvpSXd
	}
	@Test
	public void test23() {
		// 組み合わせ
		Print.println(MSG_COMBINATION);
		Print.println(Passwd.passgen2(20, PW_REQUIRE1));
	}
	//Print.println();

		/*
		 * Generate passgen3()
		 */
	@Test
	public void test31() {
		// 20文字であること
		Print.println(MSG_LEN_EQ_20);
		Print.println(Passwd.passgen3(20));
		Print.println(Passwd.passgen3(8));
		// CEIJC'Hvd{&C9@!,T][7
	}
	@Test
	public void test32() {
		// 英数字のみ、8文字
		Print.println(MSG_ATTR_LITTLE);
		Print.println(Passwd.passgen3(8, PW_REQUIRE2));
		// 5cPvpSXd
	}
	@Test
	public void test33() {
		// 組み合わせ
		Print.println(MSG_COMBINATION);
		Print.println(Passwd.passgen3(20, PW_REQUIRE1));
	}

	@Test
	public void test41() {
		// No.12 を有効にしてみる
		Print.println("パスワード要件： 英数字+(、8文字");
		Print.println(Passwd.passgen3(8, "[\\w\\(]+"));
		// 5cPvpSXd
	}

}
