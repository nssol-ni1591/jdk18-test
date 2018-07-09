package app.strategy;

import java.util.function.Function;
import java.util.function.Predicate;

import util.Print;

public class Run {

	private static final String PATTERN_LITTLE = "[a-z]+";
	private static final String PATTERN_NUMBER = "\\d+";

	public void test1() {
		Print.println("<<<< ストラテジ・パターン (jdk17)");
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
	public void test2() {
		Print.println("<<<< ストラテジ・パターン (jdk18)");
		// jdk1.8
		Validator v3 = new Validator((String s) -> s.matches(PATTERN_NUMBER));
		Print.println(v3.validate("aaaa"));

		Validator v4 = new Validator((String s) -> s.matches(PATTERN_LITTLE));
		Print.println(v4.validate("bbbb"));
	}
	public void test3() {
		Print.println("<<<< ストラテジ・パターン (直接ストラテジのインターフェースを使用することもできるはず)");
		// jdk1.8
		ValidationStrategy v3 = (String s) -> s.matches(PATTERN_NUMBER);
		Print.println(v3.execute("aaaa"));

		ValidationStrategy v4 = (String s) -> s.matches(PATTERN_LITTLE);
		Print.println(v4.execute("bbbb"));
	}
	public void test4() {
		Print.println("<<<< ストラテジ・パターン (java.util.functionを使う)");
		// jdk1.8
		Predicate<String> p3 = s -> s.matches(PATTERN_NUMBER);
		Print.println(p3.test("aaaa"));

		Predicate<String> p4 = s -> s.matches(PATTERN_LITTLE);
		Print.println(p4.test("bbbb"));
	}
	public void test5() {
		Print.println("<<<< ストラテジ・パターン 2()");
		// jdk1.8
		Function<String, Boolean> f3 = s -> s.matches(PATTERN_NUMBER);
		Print.println(f3.apply("aaaa"));

		Function<String, Integer> f4 = String::length;
		Print.println(f4.apply("aaaa"));
	}

	public static void main(String[] args) {

		// Design pattern
		// (1) ストラテジ・パターン
		Run run = new Run();

		run.test1();
		run.test2();
		run.test3();
		run.test4();
		run.test5();
	}

}
