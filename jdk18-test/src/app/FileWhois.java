package app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class FileWhois {

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
	private void print(Path path) throws IOException {

		// URLを作成してGET通信を行う
//		URL url = new URL("http://whois.threet.co.jp/?key=124.35.2.180");
		URL url = new URL("http://whois.threet.co.jp/?key=202.214.0.0");
//		URL url = new URL("http://whois.threet.co.jp/?key=202.86.56.192");

		HttpURLConnection http = (HttpURLConnection)url.openConnection();
		http.setRequestMethod("GET");
		http.connect();

		// サーバーからのレスポンスを標準出力へ出す
		try (
				BufferedReader reader = new BufferedReader(new InputStreamReader(http.getInputStream()));
				) {
			/*
			stream.forEach(s -> {
				Stream.of(patterns).forEach(p -> p.group(s, 1));
			});
			*/
			String s;
			while((s = reader.readLine()) != null) {
//				Stream.of(patterns).forEach(p -> p.group(s, 1));
				for (PatternWrapper p : patterns) {
					p.group(s, 1);
				}
			}

		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
		Stream.of(patterns).forEach(p -> System.out.println(p.value()));
	}

	public static void main(String... args) {
		System.setProperty("proxySet", "true");
		System.setProperty("proxyHost", "proxy.ns-sol.co.jp");
		System.setProperty("proxyPort", "8000");
		
		Path path = Paths.get(args[0]);

//		System.out.println("path = " + path);
//		System.out.println("absoulte path = " + path.toAbsolutePath());
//		path.iterator().forEachRemaining(System.out::println);
//		path.forEach(System.out::println);

		System.out.println("---- " + args[0] + " ----");
		FileWhois f = new FileWhois();
		try {
			f.print(path);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private class PatternWrapper {

		private final Pattern p;
		private String value;
	
		private PatternWrapper(Pattern p) {
			this.p = p;
		}
		private PatternWrapper(String s) {
			this.p = Pattern.compile(s);
		}

		private String group(String s, int ix) {
			Matcher m = p.matcher(s);
			if (!m.matches()) {
				return null;
			}
//			m.reset();
//			if (!m.find(ix)) {
//				return null;
//			}
			value = m.group(ix);
			return value;
		}
		
		private String value() {
			return value;
		}
	}

}
