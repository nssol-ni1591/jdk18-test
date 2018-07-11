package test;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import org.junit.Test;

import util.Print;

/*
 * Streamを何から作り出す
 */
public class LambdaSupplierTest {

	private static final String MSG = "2016/12/19 07:08:18  SOPE_DR_01-no_db.sh INFORMATION: ----- DR_NFSの同期処理 を開始します。 -----";
	private static final String FORMAT = "\"%s\"";

	public LambdaSupplierTest() {
		// Do nothing
	}

	@Test
	public void p1Jdk18() {
		// 基本系
		Stream.of(MSG.split(" +"))
				.sorted((a, b) -> a.compareTo(b))
				.map(s -> String.format(FORMAT, s))
				.forEach(Print::println);
	}
	@Test
	public void p1Jdk18p2() {
		// 配列から
		Arrays.stream(MSG.split(" +"))
				.sorted((a, b) -> a.compareTo(b))
				.map(s -> String.format(FORMAT, s))
				.forEach(Print::println);
	}
	@Test
	public void p1Jdk18p3() {
		// コレクションから
		List<String> list = Arrays.asList(MSG.split(" +"));
		list.stream()
				.sorted((a, b) -> a.compareTo(b))
				.map(s -> String.format(FORMAT, s))
				.forEach(Print::println);
	}

	public void p1Jdk18p4() {
		// Readerから
		try (
				PipedReader ppr = new PipedReader();
				PipedWriter ppw = new PipedWriter(ppr);
				PrintWriter pw = new PrintWriter(ppw);
				BufferedReader br = new BufferedReader(ppr);
				)
		{
			//Pipeを使っていない => 当然、処理途中でストールする
			//データ量が小さすぎる
			Arrays.stream(MSG.split(" +")).forEach(pw::println);
			pw.flush();

			br.lines()
					.sorted((a, b) -> a.compareTo(b))
					.map(s -> String.format(FORMAT, s))
					.forEach(Print::println);
		}
		catch (IOException e) {
			Print.stackTrace(e);
		}
	}
/*
	public static void main(String... arvs) {
		LambdaSupplierTest stream = new LambdaSupplierTest();

		Print.print(stream, "p1Jdk18");
		Print.print(stream, "p1Jdk18p2");
		Print.print(stream, "p1Jdk18p3");
		Print.print(stream, "p1Jdk18p4");
	}
*/
}
