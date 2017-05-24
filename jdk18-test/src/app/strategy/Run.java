package app.strategy;

import java.util.function.Function;
import java.util.function.Predicate;

public class Run {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		// Design pattern
		// (1) ストラテジ・パターン
		{
			System.out.println("<<<< ストラテジ・パターン (jdk17)");
			// jdk1.7
			ValidationStrategy v3 = new ValidationStrategy() {
				public boolean execute(String s) {
					return s.matches("\\d+");
				}
			};
			System.out.println(v3.execute("aaaa"));

			ValidationStrategy v4 = new ValidationStrategy() {
				public boolean execute(String s) {
					return s.matches("[a-z]+");
				}
			};
			System.out.println(v4.execute("bbbb"));
		}
		{
			System.out.println("<<<< ストラテジ・パターン (jdk18)");
			// jdk1.8
			Validator v3 = new Validator((String s) -> s.matches("\\d+"));
			System.out.println(v3.validate("aaaa"));

			Validator v4 = new Validator((String s) -> s.matches("[a-z]+"));
			System.out.println(v4.validate("bbbb"));
		}
		{
			System.out.println("<<<< ストラテジ・パターン (直接ストラテジのインターフェースを使用することもできるはず)");
			// jdk1.8
			ValidationStrategy v3 = (String s) -> s.matches("\\d+");
			System.out.println(v3.execute("aaaa"));

			ValidationStrategy v4 = (String s) -> s.matches("[a-z]+");
			System.out.println(v4.execute("bbbb"));
		}
		{
			System.out.println("<<<< ストラテジ・パターン (java.util.functionを使う)");
			// jdk1.8
			Predicate<String> p3 = s -> s.matches("\\d+");
			System.out.println(p3.test("aaaa"));

			Predicate<String> p4 = s -> s.matches("[a-z]+");
			System.out.println(p4.test("bbbb"));
		}
		{
			System.out.println("<<<< ストラテジ・パターン 2()");
			// jdk1.8
			Function<String, Boolean> f3 = s -> s.matches("\\d+");
			System.out.println(f3.apply("aaaa"));

			Function<String, Integer> f4 = s -> s.length();
			System.out.println(f4.apply("aaaa"));
		}
	}

}
