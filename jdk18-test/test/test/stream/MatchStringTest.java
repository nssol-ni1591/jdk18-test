package test.stream;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.stream.Stream;

import org.junit.Test;

import util.Print;

/**
 * 条件に合う文字列の有無を確認する際の処理速度の比較
 * 
 * @author NI1591
 *
 */
public class MatchStringTest {

	private static final String RESULT = " ms, result=";

//	private static final String COND = "153.156.73.80"
	private static final String COND = "08:32:52";

	private static final String FILENAME = "README.md";

	@Test
	public void test1() {
		boolean result = false;
		long time = System.currentTimeMillis();

		for (int ix = 0; ix < 100; ix++) {
			try (Stream<String> stream = Files.lines(Paths.get(FILENAME), StandardCharsets.UTF_8)) {
				Optional<String> rc = stream.sequential().filter(s -> s.contains(COND)).findFirst();
				result = rc.isPresent();
			} catch (IOException e) {
				Print.stackTrace(e);
			}
		}
		Print.println(
				"findFirst + serial   => elaps=" + (System.currentTimeMillis() - time) + RESULT + result);
	}
	@Test
	public void test2() {
		boolean result = false;
		long time = System.currentTimeMillis();
		for (int ix = 0; ix < 100; ix++) {
			try (Stream<String> stream = Files.lines(Paths.get(FILENAME), StandardCharsets.UTF_8)) {
				Optional<String> rc = stream.parallel().filter(s -> s.contains(COND)).findFirst();
				result = rc.isPresent();
			} catch (IOException e) {
				Print.stackTrace(e);
			}
		}
		Print.println(
				"findFirst + parallel => elaps=" + (System.currentTimeMillis() - time) + RESULT + result);
	}
	@Test
	public void test3() {
		boolean result = false;
		long time = System.currentTimeMillis();
		for (int ix = 0; ix < 100; ix++) {
			try (Stream<String> stream = Files.lines(Paths.get(FILENAME), StandardCharsets.UTF_8)) {
				Optional<String> rc = stream.parallel().filter(s -> s.contains(COND)).findAny();
				result = rc.isPresent();
			} catch (IOException e) {
				Print.stackTrace(e);
			}
		}
		Print.println(
				"findAny   + parallel => elaps=" + (System.currentTimeMillis() - time) + RESULT + result);
	}
	@Test
	public void test4() {
		boolean result = false;
		long time = System.currentTimeMillis();
		for (int ix = 0; ix < 100; ix++) {
			try (Stream<String> stream = Files.lines(Paths.get(FILENAME), StandardCharsets.UTF_8)) {
				boolean rc = stream.sequential().anyMatch(s -> s.contains(COND));
				result = rc;
			} catch (IOException e) {
				Print.stackTrace(e);
			}
		}
		Print.println(
				"anyMatch  + serial   => elaps=" + (System.currentTimeMillis() - time) + RESULT + result);
	}
	@Test
	public void test5() {
		boolean result = false;
		long time = System.currentTimeMillis();
		for (int ix = 0; ix < 100; ix++) {
			try (Stream<String> stream = Files.lines(Paths.get(FILENAME), StandardCharsets.UTF_8)) {
				boolean rc = stream.parallel().anyMatch(s -> s.contains(COND));
				result = rc;
			} catch (IOException e) {
				Print.stackTrace(e);
			}
		}
		Print.println(
				"anyMatch  + parallel => elaps=" + (System.currentTimeMillis() - time) + RESULT + result);
	}

}
