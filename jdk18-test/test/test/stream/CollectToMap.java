package test.stream;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;

/*
 * 文字列のストリームをMapに変換したい
 * 例えば、CheckerXXのcall()で複数の属性でMap集合体を生成するのに使用したい
 */
public class CollectToMap {

	private static final String MSG =
			"CRL checking started for certificate CN:Z06112 OU:nssol O:sdc L:CHUOU-KU ST:TOKYO C:JP issued by CN:SDC-VPN OU:SDC O:NS Solutions ST:Tokyo C:JP" +
					"Certificate realm restrictions successfully passed for /NSSDC-Auth1 with certificate CN:Z06112 OU:nssol O:sdc L:CHUOU-KU ST:TOKYO C:JP" +
					"Source IP realm restrictions successfully passed for /NSSDC-Auth1 with certificate CN:Z06112 OU:nssol O:sdc L:CHUOU-KU ST:TOKYO C:JP" +
					"The X.509 certificate for CN:Z06112 OU:nssol O:sdc L:CHUOU-KU ST:TOKYO C:JP issued by CN:SDC-VPN OU:SDC O:NS Solutions ST:Tokyo C:JP successfully passed CRL checking"
					;
	private static final String[] array = MSG.split(" +");

	// メソッド化しておけばメソッド参照で済む
	private BinaryOperator<List<String>> mergeList =
			(left, right) -> Stream.of(left, right).flatMap(List::stream).collect(Collectors.toList());

	@Test
	public void test1() {
		System.out.println("----文字列の1文字目をキーとしたMapを生成----");
		Map<String, List<String>> map = Stream.of(array)
				.collect(Collectors.toMap(
						new Function<String, String>() {
							@Override
							public String apply(String t) {
								return t.substring(0, 1);
							}
						}
						, new Function<String, List<String>>() {
							@Override
							public List<String> apply(String t) {
								List<String> list = new ArrayList<>();
								list.add(t);
								return list;
							}
						}
						, new BinaryOperator<List<String>>() {
							@Override
							public List<String> apply(List<String> t, List<String> u) {
								t.addAll(u);
								return t;
							}
						}))
				;
		map.entrySet()
			.forEach(s -> {
				System.out.println(s);
			});
	}
	@Test
	public void test2() {
		System.out.println("----文字列の1文字目をキーとしたMapを生成----");
		Map<String, List<String>> map = Stream.of(array)
				.collect(Collectors.toMap(
						t -> t.substring(0, 1)
						, t -> {
							List<String> list = new ArrayList<>();
							list.add(t);
							return list;
						}
						, (t, u) -> {
							t.addAll(u);
							return t;
						}))
				;
		map.entrySet().forEach(System.out::println);
	}
	@Test
	public void test3() {
		System.out.println("----文字列の1文字目をキーとしたMapを生成----");
		Map<String, List<String>> map = Stream.of(array)
				.collect(Collectors.toMap(
						t -> t.substring(0, 1)
						, t -> new ArrayList<>(Collections.singletonList(t))
						, (t, u) -> Stream.concat(t.stream(), u.stream()).collect(Collectors.toList())
						));
		map.entrySet().forEach(System.out::println);
	}
	@Test
	public void test4() {
		System.out.println("----文字列の1文字目をキー、2つ目のキーとして、文字数を設定したMapを生成するにはどうする？----");
		Map<String, Map<Integer, List<String>>> map = Stream.of(array)
				.collect(Collectors.toMap(
						t -> t.substring(0, 1)
						, t -> {
							/*
							Map<Integer, List<String>> map31s = new HashMap<>();
							map31s.put(Integer.valueOf(t.length()), new ArrayList<>(Collections.singletonList(t)));
							return map31s;
							*/
							return new HashMap<>(
									Collections.singletonMap(
											Integer.valueOf(t.length())
											, new ArrayList<>(Collections.singletonList(t))
											)
									);
						}
						, (t, u) -> {
							// t, u: Map<Integer, List<String>>
							/*
							u.keySet().stream()
								.forEach(k -> {
									List<String> uval = u.get(k);
									List<String> tval = t.get(k);
									if (tval == null) {
										t.put(k, uval);
									}
									else {
										tval.addAll(uval);
									}
								});
						    return t;
						    */
							/*
							u.keySet().stream()
								.forEach(key -> {
									t.merge(key, u.get(key), (oldVal, newVal) -> {
										// oldVal: tに存在するキーに対する値
										// newVal: u.get(key)と同じ
										oldVal.addAll(newVal);
										return oldVal;
									});
								});
							 */
							u.forEach((ukey, uval) -> 
								t.merge(ukey, uval, (oldVal, newVal) -> {
									// oldVal: tに存在するキーに対する値
									// newVal: u.get(key)と同じ
									return Stream.concat(oldVal.stream(), newVal.stream())
											.collect(Collectors.toList());
								})
							);
							return t;
							/* 以下のマージでは存在するキーに対して更新となってしまう
							final Map<Integer, List<String>> map31t = new HashMap<>();
							t.forEach(map31t::put);
							u.forEach(map31t::put);
							return map31t;
							*/
							//u.forEach(t::put);
							//return t;
						}
						));
		map.entrySet().forEach(System.out::println);
	}
	@Test
	public void test5() {
		System.out.println("----文字列の1文字目をキー、2つ目のキーとして、文字数を設定したMapを生成するにはどうする？----");
		Map<String, Map<Integer, List<String>>> map = Stream.of(array)
				.collect(Collectors.toMap(
						t -> t.substring(0, 1)
						, t -> new HashMap<>(
								Collections.singletonMap(
										Integer.valueOf(t.length())
										, new ArrayList<>(Collections.singletonList(t))
										)
								)
						, (t, u) -> {
							u.forEach((ukey, uval) -> 
								t.merge(ukey, uval, mergeList));	// メソッド参照
							return t;
						}
						));
		map.entrySet().forEach(System.out::println);
	}
	/*
	@Test
	public void test6() {
		System.out.println("----文字列の1文字目をキー、2つ目のキーとして文字数、3つめのキーとして要素数を設定したMapを生成するにはどうする？----");
		System.out.println("---- =>要素数は無理。キーがわからない----");
		Map<String, Map<Integer, Map<Integer, List<String>>>> map = Stream.of(array)
				.collect(Collectors.toMap(
						t -> t.substring(0, 1)
						, t -> {
							return new HashMap<String, Map<Integer, Map<Integer, List<String>>>> (
									Collections.singletonMap(
											Integer.valueOf(t.length())
											, new HashMap<Integer, List<String>>(Collections.singletonMap(
													1 //Integer.valueOf(1)
													, new ArrayList<String>(Collections.singletonList(t))))
											)
									);
						}
						, (t, u) -> {
							u.forEach((ukey, uval) -> 
								t.merge(ukey, uval, (oldVal, newVal) -> {
									// oldVal: tに存在するキーに対する値
									// newVal: u.get(key)と同じ
									// Map<Integer, List<String>>
									if (oldVal == null) {
										return newVal;
									}
									else {
										// keyは要素数 => oldListのキー値がわからない取得できない!!
										//int key = newVal.keySet().iterator().next();
										//List<String> oldList = oldVal.get(key);
										List<String> newList = newVal.get(1);

										//oldList.addAll(newList);
										oldVal.put(newList.size(), newList);
										//oldVal.remove(key);
									}
									return oldVal;
								})
							);
							return t;
						}
						));
		map.entrySet().forEach(System.out::println);
	}
	*/
}
