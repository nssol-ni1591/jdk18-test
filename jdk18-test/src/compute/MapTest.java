package compute;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class MapTest {

	/*
	 * compute(K key, BiFunction<? super K,? super V,? extends V> remappingFunction)
	 * 
	 * 第一引数で渡した key でマップを検索し、そのキーと取得した値を第二引数で指定した remappingFunction に渡す。
	 * remappingFunction が null 以外の値を返した場合は、マップの中身をその値で置き換える。
	 * remappingFunction が null を返した場合、そのキーのエントリは削除される。
	 */
	public void test01() {
		System.out.println("[test01]");

		Map<String, String> map = new HashMap<>();
        map.put("hoge", "HOGE");
        map.put("fuga", "FUGA");
        map.put("piyo", "PIYO");

        System.out.println("before : " + map);
        map.compute("hoge", (key, old) -> "<" + old + ">");	// null以外 -> 置換
        map.compute("fuga", (key, old) -> null);			// null -> 削除
        map.compute("fizz", (key, old) -> "FIZZ");			// キーがない -> 追加
        System.out.println("after : " + map);
	}
	/*
	 * computeIfAbsent(K key, Function<? super K,? extends V> mappingFunction)
	 * 
	 * key で指定したキーでマップを検索した結果が null の場合、第二引数で指定した mappingFunction が実行される。
	 * mappingFunction が null 以外の値を返した場合、そのときのキーと値がマップに追加される。
	 * =>　つまり、キーが存在しない場合は、Functionの返却を値としたキーの追加を行う
	 * デフォルトの実装は同期されないので、マルチスレッド使うときは注意。
	 * ConcurrentHashMap の computeIfAbsent() は同期するようにデフォルトメソッドをオーバーライドしており、そのことが API ドキュメントに明記されている。
	 */
	public void test02() {
		System.out.println("[test02]");

		Map<String, String> map = new TreeMap<>();
        map.put("hoge", "HOGE");
        map.put("fuga", "FUGA");
        map.put("piyo", "PIYO");

        System.out.println("before : " + map);
        map.computeIfAbsent("hoge", (key) -> null /*ここは実行されない*/);	// キーが存在 -> nullなので実行されない
        map.computeIfAbsent("fuga", (key) -> "FUGA");					// キーが存在 -> null以外でも値を更新しない
        map.computeIfAbsent("fuga", (key) -> map.put("piyo", "<FUGA>"));// キーが存在 -> null以外でも式を実行しない
        map.computeIfAbsent("fizz", (key) -> "FIZZ");					// キーが存在しない -> fixx:FIZZが登録
        map.computeIfAbsent("nana", (key) -> null);						// キーが存在しない -> 何も変化しない
        System.out.println("after : " + map);
	}
	/*
	 * computeIfPresent(K key, BiFunction<? super K,? super V,? extends V> remappingFunction)
	 * 
	 * key で指定した値でマップを検索して、 null 以外の値を返した場合は remappingFunction を実行する。
	 * remappingFunction が null 以外の値を返した場合は、マップの中身をその値で置き換える。
	 * remappingFunction が null を返した場合、そのキーのエントリは削除される。
	 */
	public void test03() {
		System.out.println("[test03]");
		
        Map<String, String> map = new HashMap<>();
        map.put("hoge", "HOGE");
        map.put("fuga", "FUGA");
        map.put("piyo", "PIYO");

        System.out.println("before : " + map);
        map.computeIfPresent("hoge", (key, old) -> "<" + old + ">");
        map.computeIfPresent("fuga", (key, old) -> null);
        map.computeIfPresent("fizz", (key, old) -> "FIZZ" /*ここは実行されない*/);
        System.out.println("after : " + map);
	}
	
	public void test04_1(Map<String, Map<String, Map<String, Map<String, String>>>> map1, String m) {
		Map<String, Map<String, Map<String, String>>> map2 = map1.computeIfAbsent(m, key -> new TreeMap<>());
		Map<String, Map<String, String>> map3 = map2.computeIfAbsent(m, key -> new TreeMap<>());
		Map<String, String> map4 = map3.computeIfAbsent(m, key -> new TreeMap<>());
		map4.put(m, m);
	}
	public void test04() {
		System.out.println("[test04]");

        Map<String, Map<String, Map<String, Map<String, String>>>> map1 = new TreeMap<>();

        System.out.println("before : " + map1);
        test04_1(map1, "abc1");
        test04_1(map1, "abc2");
        test04_1(map1, "abc3");
        System.out.println("after : " + map1);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MapTest test = new MapTest();
		test.test01();
		test.test02();
		test.test03();
		test.test04();
	}

}
