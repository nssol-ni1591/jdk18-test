package test.stream;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collector.Characteristics;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;

//Collectors.groupingBy を使用するパターン
/*
 * 文字列のストリームをMapに変換したい
 * 例えば、CheckerXXのcall()で複数の属性でMap集合体を生成するのに使用したい
 */
public class CollectGroupingBy {

	private static final String MSG =
			"CRL checking started for certificate CN:Z06112 OU:nssol O:sdc L:CHUOU-KU ST:TOKYO C:JP issued by CN:SDC-VPN OU:SDC O:NS Solutions ST:Tokyo C:JP" +
					"Certificate realm restrictions successfully passed for /NSSDC-Auth1 with certificate CN:Z06112 OU:nssol O:sdc L:CHUOU-KU ST:TOKYO C:JP" +
					"Source IP realm restrictions successfully passed for /NSSDC-Auth1 with certificate CN:Z06112 OU:nssol O:sdc L:CHUOU-KU ST:TOKYO C:JP" +
					"The X.509 certificate for CN:Z06112 OU:nssol O:sdc L:CHUOU-KU ST:TOKYO C:JP issued by CN:SDC-VPN OU:SDC O:NS Solutions ST:Tokyo C:JP successfully passed CRL checking"
					;
	private static final String[] array = MSG.split(" +");

	@Test
	public void test1() {
		System.out.println("----文字列の1文字目をキーとしたMapを生成（inner Collectorクラス）----");
		Map<String, List<String>> map = Stream.of(array)
				.collect(Collectors.groupingBy(
						new Function<String, String>() {
							@Override
							public String apply(String t) {
								return t.substring(0, 1);
							}
						},
                        new Collector<String, List<String>, List<String>>() {

							@Override
							public Supplier<List<String>> supplier() {
								return new Supplier<List<String>>() {
									@Override
									public List<String> get() {
										return new ArrayList<>();
									}
								};
							}

							@Override
							public BiConsumer<List<String>, String> accumulator() {
								return new BiConsumer<List<String>, String>() {
									@Override
									public void accept(List<String> t, String u) {
										t.add(u);
									}
								};
							}

							@Override
							public BinaryOperator<List<String>> combiner() {
								return new BinaryOperator<List<String>>() {
									@Override
									public List<String> apply(List<String> t, List<String> u) {
										t.addAll(u);
										return t;
									}
									
								};
							}

							@Override
							public Function<List<String>, List<String>> finisher() {
								return new Function<List<String>, List<String>>() {
									@Override
									public List<String> apply(List<String> t) {
										return t;
									}
								};
							}

							@Override
							public Set<Characteristics> characteristics() {
								return new Set<Characteristics>() {

									@Override
									public int size() {
										return 0;
									}

									@Override
									public boolean isEmpty() {
										return false;
									}

									@Override
									public boolean contains(Object o) {
										return false;
									}

									@Override
									public Iterator<Characteristics> iterator() {
										return null;
									}

									@Override
									public Object[] toArray() {
										return null;
									}

									@Override
									public <T> T[] toArray(T[] a) {
										return null;
									}

									@Override
									public boolean add(Characteristics e) {
										return false;
									}

									@Override
									public boolean remove(Object o) {
										return false;
									}

									@Override
									public boolean containsAll(Collection<?> c) {
										return false;
									}

									@Override
									public boolean addAll(Collection<? extends Characteristics> c) {
										return false;
									}

									@Override
									public boolean retainAll(Collection<?> c) {
										return false;
									}

									@Override
									public boolean removeAll(Collection<?> c) {
										return false;
									}

									@Override
									public void clear() {
									}
									
								};
							}
							
						}
						));
		map.entrySet().forEach(System.out::println);
	}
	@Test
	public void test2() {
		System.out.println("----文字列の1文字目をキーとしたMapを生成（外だしCollectorクラス）----");
		Map<String, List<String>> map = Stream.of(array)
				.collect(Collectors.groupingBy(
						t -> t.substring(0, 1)
						, toList()
						));
		map.entrySet().forEach(System.out::println);
	}
	@Test
	public void test3() {
		System.out.println("----文字列の1文字目をキーとしたMapを生成（外だしCollectorクラス）----");
		Map<String, List<String>> map = Stream.of(array)
				.collect(Collectors.groupingBy(
						t -> t.substring(0, 1)
						, toList2()
						));
		map.entrySet().forEach(System.out::println);
	}
	@Test
	public void test4() {
		System.out.println("----文字列の1文字目をキーとしたMapを生成（Collector.ofの変数）----");
		// メソッド化しておけばメソッド参照で済む
		final Collector<String, ?, List<String>> toList = Collector.of(
				ArrayList<String>::new
				, (t, u) -> t.add(u)
				, (t, u) -> { t.addAll(u); return t; }
				, t -> t
				, Characteristics.IDENTITY_FINISH
				);

		Map<String, List<String>> map = Stream.of(array)
				.collect(Collectors.groupingBy(
						t -> t.substring(0, 1)
						, toList
						));
		map.entrySet().forEach(System.out::println);
	}
	@Test
	public void test5() {
		System.out.println("----文字列の1文字目をキーとしたMapを生成（Collectors.toList()）----");
		Map<String, List<String>> map = Stream.of(array)
				.collect(Collectors.groupingBy(
						t -> t.substring(0, 1)
						, Collectors.toList()
						));
		map.entrySet().forEach(System.out::println);
	}
	@Test
	public void test6() {
		System.out.println("----文字列の1文字目をキーとしたMapを生成（toList()ならば省略できる）");
		Map<String, List<String>> map = Stream.of(array)
				.collect(Collectors.groupingBy(
						t -> t.substring(0, 1)
						));
		map.entrySet().forEach(System.out::println);
	}
	@Test
	public void test7() {
		System.out.println("----文字列の1文字目をキー、2つ目のキーとして、文字数を設定したMapを生成するにはどうする？----");
		Map<String, Map<Integer, List<String>>> map = Stream.of(array)
				.collect(Collectors.groupingBy(
						t -> t.substring(0, 1)
						, Collectors.groupingBy(String::length)
						));
		map.entrySet().forEach(System.out::println);
	}
	@Test
	public void test8() {
		System.out.println("----文字列の1文字目をキー、2つ目のキーとして文字数、3つ目のキーとして最後の文字を設定したMapを生成するにはどうする？----");
		Map<String, Map<Integer, Map<String, List<String>>>> map = Stream.of(array)
				.collect(Collectors.groupingBy(
						t -> t.substring(0, 1)
						, Collectors.groupingBy(
								u -> u.length()
								, Collectors.groupingBy(
										t -> t.substring(t.length() - 1,
												t.length()))
								)
						));
		map.entrySet().forEach(System.out::println);
	}

	<T> Collector<T,?,List<T>> toList() {
		return new Collector<T,List<T>,List<T>>() {
			@Override
			public Supplier<List<T>> supplier() {
				return new Supplier<List<T>>() {
					@Override
					public List<T> get() {
						return new ArrayList<>();
					}
				};
			}

			@Override
			public BiConsumer<List<T>, T> accumulator() {
				return new BiConsumer<List<T>, T>() {
					@Override
					public void accept(List<T> t, T u) {
						t.add(u);
					}
				};
			}

			@Override
			public BinaryOperator<List<T>> combiner() {
				return new BinaryOperator<List<T>>() {
					@Override
					public List<T> apply(List<T> t, List<T> u) {
						t.addAll(u);
						return t;
					}
				};
			}

			@Override
			public Function<List<T>, List<T>> finisher() {
				return new Function<List<T>, List<T>>() {
					@Override
					public List<T> apply(List<T> t) {
						return t;
					}
				};
			}

			@Override
			public Set<Characteristics> characteristics() {
				return new HashSet<>();
			}
			
		};
	};

	<T> Collector<T,?,List<T>> toList2() {
		return new Collector<T,List<T>,List<T>>() {
			@Override
			public Supplier<List<T>> supplier() {
				//return () -> new ArrayList<>()
				return ArrayList::new;
			}

			@Override
			public BiConsumer<List<T>, T> accumulator() {
				return (t, u) -> t.add(u);
			}

			@Override
			public BinaryOperator<List<T>> combiner() {
				return (t, u) -> { t.addAll(u); return t; };
			}

			@Override
			public Function<List<T>, List<T>> finisher() {
				return t -> t;
			}

			@Override
			public Set<Characteristics> characteristics() {
				return new HashSet<>();
			}
			
		};
	};

}
