package check;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

/*
 * コレクションや入力以外でストリームを生成させたい場合、無限ストリーム生成のStrem.{generate(..)、iterate(..)}を使用する
 * jdk8では、builder()を使用するか、無限ストリームをlimit()で停止できるが、予め要素数が変名している必要がある
 * jdk9では、takeWhile()/dreopWhile()を使用することで特定の条件で無限ストリームを停止させることができる
 */
public class TakeWhile {

	private String unit;

	public TakeWhile(String unit) {
		this.unit = unit;
	}

	public void jdk8() {
		Stream.Builder<String> builder = Stream.builder();
		Path d = Paths.get(unit).getParent();
		while (d != null) {
			builder.add(d.toString());
			d = d.getParent();
		}
		builder.build()
			.forEach(p -> System.out.format("[%s] %s\n", p, p.toString()));
	}
	public void jdk9() {
		Stream.iterate(Paths.get(unit).getParent(), d -> d.getParent())
			.takeWhile(d -> d != null)
//			.forEach(p -> System.out.format("[%s] %s\n", p, p.toString()));
			.forEach(System.out::println);
	}

	public static void main(String[] args) {
		String unit = "/a/b/c/x/y/x";
		TakeWhile t = new TakeWhile(unit);
		t.jdk8();
		t.jdk9();
		System.out.println("---");
	}
}