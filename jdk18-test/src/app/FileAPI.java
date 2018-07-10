package app;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import util.Print;

/*
 * 新しい java.nio パッケージの確認
 */
public class FileAPI {

	/*
	 * 安全な再throw：
	 * メソッド内で発生する例外をExceptionでcatchして再throwしているが、throws句はIOExceptionのままでよい
	 */
	private void print(Path path) throws IOException {
		try (Stream<Path> stream = Files.list(path)) {
			stream.forEach(Print::println);
		}
	}

	public static void main(String... args) {
		Path path = Paths.get(".");

		Print.println("path = " + path);
		Print.println("absoulte path = " + path.toAbsolutePath());
		path.iterator().forEachRemaining(Print::println);
		path.forEach(Print::println);

		Print.println("---- Files.list ----");
		FileAPI f = new FileAPI();
		try {
			f.print(path);
		}
		catch (IOException e) {
			Print.stackTrace(e);
		}

		Path readme = path.resolve("README.md");

		Print.println("---- dump (jdk1.6以前) ----");
		try (BufferedReader br = new BufferedReader(new FileReader(readme.toFile()))) {
			String s;
			while ((s = br.readLine()) != null) {
				Print.println(s);
			}
		}
		catch (IOException e) {
			Print.stackTrace(e);
		}

		Print.println("---- dump (jdk1.7:Resource-try) ----");
		try (BufferedReader br = new BufferedReader(new FileReader(readme.toFile()))) {
			String s;
			while ((s = br.readLine()) != null) {
				Print.println(s);
			}
		}
		catch (IOException e) {
			Print.stackTrace(e);
		}

		// readAllLinesはファイルを全部読みこんでしまうので、サイズの大きなファイルには向かない
		Print.println("---- dump (jdk1.8 その1) ----");
		try {
			Files.readAllLines(readme, StandardCharsets.UTF_8).forEach(Print::println);
		}
		catch (IOException e) {
			Print.stackTrace(e);
		}

		// ファイルサイズがわからないならば、こちら
		Print.println("---- dump (jdk1.8 その2) ----");
		try (Stream<String> stream = Files.lines(readme, StandardCharsets.UTF_8)) {
			stream.forEach(Print::println);
		}
		catch (IOException e) {
			Print.stackTrace(e);
		}


		Print.println("---- path api ----");
		Path p = Paths.get("a", "b", "cee"); // line n1
		Print.println(p);
		Print.println(p.endsWith(Paths.get("b", "cee")));
		Print.println(p.endsWith(Paths.get("ee")));
	}

}
