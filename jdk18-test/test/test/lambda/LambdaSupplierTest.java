package test.lambda;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;
import java.io.PrintWriter;
import java.io.StringReader;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.junit.Test;

import util.Print;

/*
 * Streamを何から作り出す
 */
public class LambdaSupplierTest {

	private static final String MSG = "2016/12/19 ** 07:08:18  SOPE_DR_01-no_db.sh INFORMATION: ----- DR_NFSの同期処理 を開始します 。 -----";
	private static final String FORMAT = "\"%s\"";

	public LambdaSupplierTest() {
		// Do nothing
	}

	@Test
	public void p1Jdk18() {
		Print.println(">>>> p1Jdk18p4(): Stream.of（配列） から...");
		Stream.of(MSG.split(" +"))
				.sorted((a, b) -> a.compareTo(b))
				.map(s -> String.format(FORMAT, s))
				.forEach(Print::println);
	}
	@Test
	public void p1Jdk18p2() {
		Print.println(">>>> p1Jdk18p4(): Arrays.stream(配列) から...");
		Arrays.stream(MSG.split(" +"))
				.sorted((a, b) -> a.compareTo(b))
				.map(s -> String.format(FORMAT, s))
				.forEach(Print::println);
	}
	@Test
	public void p1Jdk18p3() {
		Print.println(">>>> p1Jdk18p4(): list.stream() から...");
		List<String> list = Arrays.asList(MSG.split(" +"));
		list.stream()
				.sorted((a, b) -> a.compareTo(b))
				.map(s -> String.format(FORMAT, s))
				.forEach(Print::println);
	}
	@Test
	public void p1Jdk18p4() {
		Print.println(">>>> p1Jdk18p4(): Pipedから...");
		try (PipedReader ppr = new PipedReader();
				PipedWriter ppw = new PipedWriter(ppr);
				) {
			try (BufferedReader br = new BufferedReader(ppr)) {
				//子スレッドからPipeにデータを書き込む
				new Thread(new Runnable() {
					@Override
					public void run() {
						try (PrintWriter pw = new PrintWriter(ppw)) {
							Arrays.stream(MSG.split(" +")).forEach(pw::println);
							pw.flush();
						}
						Print.println("run end.");
					}
				
				})
				.start();

				br.lines()
					.sorted((a, b) -> a.compareTo(b))
					.map(s -> String.format(FORMAT, s))
					.forEach(Print::println);
			}
		}
		catch (IOException e) {
			Print.stackTrace(e);
		}
	}
	@Test
	public void p1Jdk18p5() {
		Print.println(">>>> p1Jdk18p5(): Reader から...");
		StringReader sr = new StringReader(String.join(System.lineSeparator(), MSG.split(" +")));
		try (BufferedReader br = new BufferedReader(sr)) {
			br.lines()
				.sorted((a, b) -> a.compareTo(b))
				.map(s -> String.format(FORMAT, s))
				.forEach(Print::println);
		}
		catch (IOException e) {
			Print.stackTrace(e);
		}
	}
	@Test
	public void p1Jdk18p6() {
		Print.println(">>>> p1Jdk18p6(): Stream.builder()から...");
		Stream.Builder<String> sb = Stream.builder();
		// ストリームに入れるデータをループ等で全て取得する -> 大量データは不利
		Stream.of(MSG.split(" +")).forEach(sb::add);
		sb.build()
			.sorted((a, b) -> a.compareTo(b))
			.map(s -> String.format(FORMAT, s))
			.forEach(Print::println);
	}
	@Test
	public void p1Jdk18p7() {
		Print.println(">>>> p1Jdk18p7(): iterator から...");
		List<String> list = Arrays.asList(MSG.split(" +"));
		Iterable<String> it = () -> list.iterator();
		StreamSupport.stream(it.spliterator(), false)
			.sorted((a, b) -> a.compareTo(b))
			.map(s -> String.format(FORMAT, s))
			.forEach(Print::println);
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
