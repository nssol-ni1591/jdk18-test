package app;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.ObjIntConsumer;
import java.util.stream.Stream;

import util.Print;
import util.ValWithIndex;

import java.util.function.Function;

public class StreamMain {

	static String msg = "2016/12/19 07:08:18  SOPE_DR_01-no_db.sh INFORMATION: ----- DR_NFS�̓������� ���J�n���܂��B -----";

	public StreamMain() {
	}

	/*
	 * Stream����������o��
	 */
	public void p1_jdk18() {
		// ��{�n
		Stream.of(msg.split(" +"))
				.sorted((a, b) -> a.compareTo(b))
				.map(s -> String.format("\"%s\"", s))
				.forEach(System.out::println);
	}
	public void p1_jdk18_2() {
		// �z�񂩂�
		Arrays.stream(msg.split(" +"))
				.sorted((a, b) -> a.compareTo(b))
				.map(s -> String.format("\"%s\"", s))
				.forEach(System.out::println);
	}
	public void p1_jdk18_3() {
		// �R���N�V��������
		List<String> list = Arrays.asList(msg.split(" +"));
		list.stream()
				.sorted((a, b) -> a.compareTo(b))
				.map(s -> String.format("\"%s\"", s))
				.forEach(System.out::println);
	}
	public void p1_jdk18_4() {
		// Reader����
		try (
				PipedReader ppr = new PipedReader();
				PipedWriter ppw = new PipedWriter(ppr);
				PrintWriter pw = new PrintWriter(ppw);
				BufferedReader br = new BufferedReader(ppr);
				)
		{
			Arrays.stream(msg.split(msg)).forEach(pw::println);
			pw.flush();
			pw.close();

			br.lines()
					.sorted((a, b) -> a.compareTo(b))
					.map(s -> String.format("\"%s\"", s))
					.forEach(System.out::println);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/*
	 * forEach���\�b�h�̈�����Consumer�C���^�[�t�F�[�X�̎����N���X�i�̃C���X�^���X�j�ł��B
	 * ��q�̃����_���́A�����N���X�����̏�Œ�`���ăC���X�^���X��n���Ă��邾���ł����̂ŁA�����œ����N���X���L�q���Ă����Ȃ��킯�ł��B
	 * ���������Ĉȉ��̂悤�Ȏ������\�ł��B
	 */
	public void p2_jdk18() {
		List<String> list = Arrays.asList(msg.split(" +"));
		list.stream().sorted((a, b) -> a.compareTo(b)).map(s -> String.format("\"%s\"", s))
				.forEach(new Consumer<String>() {
					private int ix = 1;

					@Override
					public void accept(String s) {
						System.out.printf("%4s: %s\n", (ix++), s);
					}
				});
	}
	
	/*
	 * �ꌩ����ƁA�ǂ������Ȃ̂ł����AStream#parallelStream�𗘗p���ꂽ�ꍇ�̓��삪�S���ۏ�ł��܂���B
	 */
	public void p3_jdk18() {
		List<String> list = Arrays.asList(msg.split(" +"));
		list.stream()
			.sorted((a, b) -> a.compareTo(b))
			.map(withIndex())
			.forEach(v -> System.out.printf("%4s: %s\n", v.getIndex(), v.getVal()));
	}

	public <T> Function<T, ValWithIndex<T>> withIndex() {
		return new Function<T, ValWithIndex<T>>() {
			private int index;

			@Override
			public ValWithIndex<T> apply(T t) {
				return new ValWithIndex<>(t, index++);
			}
		};
	}

	public <T> void eachWithIndex(Iterable<T> itr, ObjIntConsumer<T> action) {
		int index = 0;
		for (T t : itr) {
			action.accept(t, index++);
		}
	}

	public static void main(String... arvs) {
		StreamMain stream = new StreamMain();

		Print.print(stream, "p1_jdk18");
		Print.print(stream, "p1_jdk18_2");
		Print.print(stream, "p1_jdk18_3");
		Print.print(stream, "p1_jdk18_4");
		Print.print(stream, "p2_jdk18");
		Print.print(stream, "p3_jdk18");

	}
}
