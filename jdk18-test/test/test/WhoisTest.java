package test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import org.junit.BeforeClass;
import org.junit.Test;

import util.Print;

public class WhoisTest {

	private final PatternWrapper[] patterns = {
			new PatternWrapper("% Information related to '(.*)'"),
			new PatternWrapper("inetnum: +(\\d+\\.\\d+\\.\\d+\\.\\d+) .*"),
			new PatternWrapper("descr: +([\\S ]+)"),
			new PatternWrapper("country: +(\\w+)"),

			new PatternWrapper("a. \\[Network Number\\] +(\\d+\\.\\d+\\.\\d+\\.\\d+/\\d+)"),
			new PatternWrapper("g. \\[Organization\\] +([\\S ]+)"),
			new PatternWrapper("\\[ (\\w+) database provides .*"),
	};

	/*
	 * 安全な再throw：
	 * メソッド内で発生する例外をExceptionでcatchして再throwしているが、throws句はIOExceptionのままでよい
	 */
	private void print(URL url) throws IOException {

		// URLを作成してGET通信を行う
		HttpURLConnection http = (HttpURLConnection)url.openConnection();
		http.setRequestMethod("GET");
		http.connect();

		// サーバーからのレスポンスを標準出力へ出す
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(http.getInputStream()))) {
			String s;
			while((s = reader.readLine()) != null) {
				for (PatternWrapper p : patterns) {
					String val = p.group(s, 1);
					if (val != null) {
						Print.println("val: " + val);
					}
				}
			}

		}
	}
	private void print2(URL url) throws IOException {

		// URLを作成してGET通信を行う
		HttpURLConnection http = (HttpURLConnection)url.openConnection();
		http.setRequestMethod("GET");
		http.connect();

		// サーバーからのレスポンスを標準出力へ出す
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(http.getInputStream()))) {
			reader.lines().map(s -> {
				Optional<String> rc = Stream.of(patterns)
					.map(p -> p.group(s, 1))
					.filter(Objects::nonNull)
					.findFirst();
				return rc.isPresent() ? rc.get() : null;
			})
			.filter(Objects::nonNull)
			.forEach(Print::println);
		}
	}

	@BeforeClass
	public static void beforeClass() {
		System.setProperty("proxySet", "true");
		System.setProperty("proxyHost", "proxy.ns-sol.co.jp");
		System.setProperty("proxyPort", "8000");
	}

	@Test
	public void test1() throws IOException {
		Print.println("[test1]");
		URL url = new URL("http://whois.threet.co.jp/?key=124.35.2.180");
		new WhoisTest().print(url);
	}
	@Test
	public void test2() throws IOException {
		Print.println("[test2]");
		URL url = new URL("http://whois.threet.co.jp/?key=202.214.0.0");
		new WhoisTest().print(url);
	}
	@Test
	public void test2a() throws IOException {
		Print.println("[test2a]");
		URL url = new URL("http://whois.threet.co.jp/?key=202.214.0.0");
		new WhoisTest().print2(url);
	}
	@Test
	public void test3() throws IOException {
		Print.println("[test3]");
		URL url = new URL("http://whois.threet.co.jp/?key=202.86.56.192");
		new WhoisTest().print(url);
	}

	private class PatternWrapper {

		private final Pattern p;

		private PatternWrapper(String s) {
			this.p = Pattern.compile(s);
		}

		private String group(String s, int ix) {
			Matcher m = p.matcher(s);
			return m.matches() ? m.group(ix) : null;
		}
	}

}
