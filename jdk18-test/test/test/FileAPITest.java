package test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.junit.Test;

import util.Print;

/*
 * 新しい java.nio パッケージの確認
 */
public class FileAPITest {

	private Path path = Paths.get(".");
	private Path readme = path.resolve("README.md");

	/*
	 * 安全な再throw：
	 * メソッド内で発生する例外をExceptionでcatchして再throwしているが、throws句はIOExceptionのままでよい
	 */
	private void print(Path path) throws IOException {
		try (Stream<Path> stream = Files.list(path)) {
			stream.forEach(Print::println);
		}
	}

	@Test
	public void test1() {
		Print.println("---- ファイルへのパス ----");
		Print.println("path = " + path);
		Print.println("absoulte path = " + path.toAbsolutePath());
		path.iterator().forEachRemaining(Print::println);
		path.forEach(Print::println);
	}
	@Test
	public void test2() {
		Print.println("---- dump (jdk1.6以前) ----");
		BufferedReader br1 = null;
		try {
			br1 = new BufferedReader(new FileReader(readme.toFile()));
			String s;
			while ((s = br1.readLine()) != null) {
				Print.println(s);
			}
		}
		catch (IOException e) {
			Print.stackTrace(e);
		}
		finally {
			try {
				if (br1 != null) {
					br1.close();
				}
			}
			catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}
	@Test
	public void test3() {
		Print.println("---- dump (jdk1.7:try-with-resources) ----");
		try (BufferedReader br = new BufferedReader(new FileReader(readme.toFile()))) {
			String s;
			while ((s = br.readLine()) != null) {
				Print.println(s);
			}
		}
		catch (IOException e) {
			Print.stackTrace(e);
		}
	}
	@Test
	public void test4() {
		// readAllLinesはファイルを全部読みこんでしまうので、サイズの大きなファイルには向かない
		Print.println("---- dump (jdk1.8 その1) ----");
		try {
			Files.readAllLines(readme, StandardCharsets.UTF_8).forEach(Print::println);
		}
		catch (IOException e) {
			Print.stackTrace(e);
		}
	}
	@Test
	public void test5() {
		// ファイルサイズがわからないならば、こちら
		Print.println("---- dump (jdk1.8 その2) ----");
		try (Stream<String> stream = Files.lines(readme, StandardCharsets.UTF_8)) {
			stream.forEach(Print::println);
		}
		catch (IOException e) {
			Print.stackTrace(e);
		}
	}
	@Test
	public void test6() {
		Print.println("---- Files.list ----");
		FileAPITest f = new FileAPITest();
		try {
			f.print(path);
		}
		catch (IOException e) {
			Print.stackTrace(e);
		}
	}
	@Test
	public void test7() {
		Print.println("---- path api ----");
		Path p = Paths.get("a", "b", "cee"); // line n1
		Print.println(p);
		Print.println(p.endsWith(Paths.get("b", "cee")));
		Print.println(p.endsWith(Paths.get("ee")));
	}
/*
	public static void main(String... args) {
		FileAPITest f = new FileAPITest();
		f.test1();
		f.test2();
		f.test3();
		f.test4();
		f.test5();
		f.test6();
		f.test7();
	}
*/
}
