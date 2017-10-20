package app;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * 条件に合う文字列の有無を確認する際の処理速度の比較
 * 
 * @author NI1591
 *
 */
public class TestExists {

//	private static final String COND = "153.156.73.80";
	private static final String COND = "08:32:52";

	public static void main(String[] argv) {

		boolean result = false;

		long time = System.currentTimeMillis();
		for (int ix = 0; ix < 100; ix++) {
			try (Stream<String> stream = Files.lines(Paths.get(argv[0]), StandardCharsets.UTF_8)) {
				Optional<String> rc = stream.sequential().filter(s -> s.contains(COND)).findFirst();
				result = rc.isPresent();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
//		System.out.println(
//				"findFirst + serial   => elaps=" + (System.currentTimeMillis() - time) + " ms, result=" + result);

		time = System.currentTimeMillis();
		for (int ix = 0; ix < 100; ix++) {
			try (Stream<String> stream = Files.lines(Paths.get(argv[0]), StandardCharsets.UTF_8)) {
				Optional<String> rc = stream.sequential().filter(s -> s.contains(COND)).findFirst();
				result = rc.isPresent();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println(
				"findFirst + serial   => elaps=" + (System.currentTimeMillis() - time) + " ms, result=" + result);

		time = System.currentTimeMillis();
		for (int ix = 0; ix < 100; ix++) {
			try (Stream<String> stream = Files.lines(Paths.get(argv[0]), StandardCharsets.UTF_8)) {
				Optional<String> rc = stream.parallel().filter(s -> s.contains(COND)).findFirst();
				result = rc.isPresent();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println(
				"findFirst + parallel => elaps=" + (System.currentTimeMillis() - time) + " ms, result=" + result);

		time = System.currentTimeMillis();
		for (int ix = 0; ix < 100; ix++) {
			try (Stream<String> stream = Files.lines(Paths.get(argv[0]), StandardCharsets.UTF_8)) {
				Optional<String> rc = stream.parallel().filter(s -> s.contains(COND)).findAny();
				result = rc.isPresent();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println(
				"findAny   + parallel => elaps=" + (System.currentTimeMillis() - time) + " ms, result=" + result);

		time = System.currentTimeMillis();
		for (int ix = 0; ix < 100; ix++) {
			try (Stream<String> stream = Files.lines(Paths.get(argv[0]), StandardCharsets.UTF_8)) {
				boolean rc = stream.sequential().anyMatch(s -> s.contains(COND));
				result = rc;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println(
				"anyMatch  + serial   => elaps=" + (System.currentTimeMillis() - time) + " ms, result=" + result);

		time = System.currentTimeMillis();
		for (int ix = 0; ix < 100; ix++) {
			try (Stream<String> stream = Files.lines(Paths.get(argv[0]), StandardCharsets.UTF_8)) {
				boolean rc = stream.parallel().anyMatch(s -> s.contains(COND));
				result = rc;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println(
				"anyMatch  + parallel => elaps=" + (System.currentTimeMillis() - time) + " ms, result=" + result);

	}
}
