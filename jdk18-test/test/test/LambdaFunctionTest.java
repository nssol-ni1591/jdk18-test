package test;

import java.util.function.Function;
import java.util.function.Predicate;

import org.junit.Test;

import app.validator.ValidationStrategy;
import app.validator.Validator;
import util.Print;

public class LambdaFunctionTest {

	private static final String PATTERN_LITTLE = "[a-z]+";
	private static final String PATTERN_NUMBER = "\\d+";

	@Test
	public void test1() {
		Print.println("<<<< パターン (jdk17ベース実装)");
		// jdk1.7
		ValidationStrategy v3 = new ValidationStrategy() {
			public boolean execute(String s) {
				return s.matches(PATTERN_NUMBER);
			}
		};
		Print.println(v3.execute("aaaa"));

		ValidationStrategy v4 = new ValidationStrategy() {
			public boolean execute(String s) {
				return s.matches(PATTERN_LITTLE);
			}
		};
		Print.println(v4.execute("bbbb"));
	}
	@Test
	public void test2() {
		Print.println("<<<< パターン (jdk18)");
		// jdk1.8
		Validator v3 = new Validator((String s) -> s.matches(PATTERN_NUMBER));
		Print.println(v3.validate("aaaa"));

		Validator v4 = new Validator((String s) -> s.matches(PATTERN_LITTLE));
		Print.println(v4.validate("bbbb"));
	}
	@Test
	public void test3() {
		Print.println("<<<< パターン (直接ストラテジのインターフェースを使用することもできるはず)");
		// jdk1.8
		ValidationStrategy v3 = (String s) -> s.matches(PATTERN_NUMBER);
		Print.println(v3.execute("aaaa"));

		ValidationStrategy v4 = (String s) -> s.matches(PATTERN_LITTLE);
		Print.println(v4.execute("bbbb"));
	}
	@Test
	public void test4() {
		Print.println("<<<< パターン (java.util.functionを使う)");
		// jdk1.8
		Predicate<String> p3 = s -> s.matches(PATTERN_NUMBER);
		Print.println(p3.test("aaaa"));

		Predicate<String> p4 = s -> s.matches(PATTERN_LITTLE);
		Print.println(p4.test("bbbb"));
	}
	@Test
	public void test5() {
		Print.println("<<<< パターン (java標準Function)");
		// jdk1.8
		Function<String, Boolean> f3 = s -> s.matches(PATTERN_NUMBER);
		Print.println(f3.apply("aaaa"));

		Function<String, Boolean> f4 = s -> s.matches(PATTERN_LITTLE);
		Print.println(f4.apply("aaaa"));
	}

	public static void main(String[] args) {
		// Design pattern
		LambdaFunctionTest run = new LambdaFunctionTest();
		run.test1();
		run.test2();
		run.test3();
		run.test4();
		run.test5();
	}

}
