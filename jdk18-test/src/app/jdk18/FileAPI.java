package app.jdk18;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

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
			stream.forEach(System.out::println);
		}
	}

	public static void main(String... args) {
		Path path = Paths.get(".");

		System.out.println("path = " + path);
		System.out.println("absoulte path = " + path.toAbsolutePath());
		path.iterator().forEachRemaining(System.out::println);
		path.forEach(System.out::println);

		System.out.println("---- Files.list ----");
		FileAPI f = new FileAPI();
		try {
			f.print(path);
		}
		catch (IOException e) {
			e.printStackTrace();
		}

		Path readme = path.resolve("README.md");
		{
			System.out.println("---- dump (jdk1.6以前) ----");
			try (BufferedReader br = new BufferedReader(new FileReader(readme.toFile()))) {
				String s;
				while ((s = br.readLine()) != null) {
					System.out.println(s);
				}
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
		{
			System.out.println("---- dump (jdk1.7:Resource-try) ----");
			try (BufferedReader br = new BufferedReader(new FileReader(readme.toFile()))) {
				String s;
				while ((s = br.readLine()) != null) {
					System.out.println(s);
				}
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
		{
			// readAllLinesはファイルを全部読みこんでしまうので、サイズの大きなファイルには向かない
			System.out.println("---- dump (jdk1.8 その1) ----");
			try {
				Files.readAllLines(readme, StandardCharsets.UTF_8).forEach(System.out::println);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		{
			// ファイルサイズがわからないならば、こちら
			System.out.println("---- dump (jdk1.8 その2) ----");
			try (Stream<String> stream = Files.lines(readme, StandardCharsets.UTF_8)) {
				stream.forEach(System.out::println);
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}

		System.out.println("---- path api ----");
		{
			Path p = Paths.get("a", "b", "cee"); // line n1
			System.out.println(p);
			System.out.println(p.endsWith(Paths.get("b", "cee")));
			System.out.println(p.endsWith(Paths.get("ee")));
		}
	}

}
