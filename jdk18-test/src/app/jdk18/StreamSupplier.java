package app.jdk18;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import util.Print;

/*
 * Streamを何から作り出す
 */
public class StreamSupplier {

	static String msg = "2016/12/19 07:08:18  SOPE_DR_01-no_db.sh INFORMATION: ----- DR_NFSの同期処理 を開始します。 -----";

	public StreamSupplier() {
	}

	public void p1_jdk18() {
		// 基本系
		Stream.of(msg.split(" +"))
				.sorted((a, b) -> a.compareTo(b))
				.map(s -> String.format("\"%s\"", s))
				.forEach(System.out::println);
	}
	public void p1_jdk18_2() {
		// 配列から
		Arrays.stream(msg.split(" +"))
				.sorted((a, b) -> a.compareTo(b))
				.map(s -> String.format("\"%s\"", s))
				.forEach(System.out::println);
	}
	public void p1_jdk18_3() {
		// コレクションから
		List<String> list = Arrays.asList(msg.split(" +"));
		list.stream()
				.sorted((a, b) -> a.compareTo(b))
				.map(s -> String.format("\"%s\"", s))
				.forEach(System.out::println);
	}
	public void p1_jdk18_4() {
		// Readerから
		try (
				PipedReader ppr = new PipedReader();
				PipedWriter ppw = new PipedWriter(ppr);
				PrintWriter pw = new PrintWriter(ppw);
				BufferedReader br = new BufferedReader(ppr);
				)
		{
			Arrays.stream(msg.split(msg)).forEach(pw::println);
			pw.flush();
			pw.close();

			br.lines()
					.sorted((a, b) -> a.compareTo(b))
					.map(s -> String.format("\"%s\"", s))
					.forEach(System.out::println);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	public static void main(String... arvs) {
		StreamSupplier stream = new StreamSupplier();

		Print.print(stream, "p1_jdk18");
		Print.print(stream, "p1_jdk18_2");
		Print.print(stream, "p1_jdk18_3");
		Print.print(stream, "p1_jdk18_4");
	}

}
