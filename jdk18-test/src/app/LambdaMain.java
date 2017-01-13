package app;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import util.Print;

public class LambdaMain {

	static String msg = "2016/12/19 07:08:18  SOPE_DR_01-no_db.sh INFORMATION: ----- DR_NFS�̓������� ���J�n���܂��B -----";

	public LambdaMain() { }
	
	/*
	 * �P�ɁA�z��ɑ΂��āA�\�[�g����������@
	 */
	public String[] p1_jdk17(String[] array) {
		Arrays.sort(array, new Comparator<String>() {
		    @Override
		    public int compare(String a, String b) {
		        return a.compareToIgnoreCase(b);
		    }
		});
		return array;
	}
	public String[] p1_jdk18(String[] array) {
		Arrays.sort(array, (a, b) -> a.compareToIgnoreCase(b));
		return array;
	}
	public String[] p1_jdk18_2(String[] array) {
		Arrays.sort(array, (a, b) -> { return a.compareToIgnoreCase(b); });
		return array;
	}
	public String[] p1_jdk18_3(String[] array) {
		Arrays.sort(array, (a, b) -> {
			System.out.println("a=" + a + ", b=" + b);
			return a.compareToIgnoreCase(b);
		});
		return array;
	}
	public String[] p1_jdk18_4(String[] array) {
		Arrays.sort(array, String::compareToIgnoreCase);
		return array;
	}

	/*
	 * �\�[�g�ȊO�ɂ��ȒP�ɕύX�ł��邱��
	 */
	public String[] p2_jdk18(String[] array) {
		Arrays.sort(array, (a, b) -> {
			return a.length() - b.length();
		});
		return array;
	}
	public String[] p2_jdk18_2(String[] array) {
		Arrays.sort(array, (a, b) -> {
			return a.getBytes().length - b.getBytes().length;
		});
		return array;
	}

	/*
	 * �ȉ��̓����_���Ƃ��������A���t���N�V�����Ƒ��̌^�����܂��g���ăR�[�h��Z���ł��Ȃ����H
	 */
	public ArrayList<String> p3_jdk18(ArrayList<String> list) {
		list.sort((a, b) -> {
			return a.compareToIgnoreCase(b);
		});
		return list;
	}
	/*
	 * ���t���N�V�����ł́B������ArrayList�ł����Ă��A���̃��\�b�h��T���o�����Ƃ��ł��Ȃ�
	 */
	public List<String> p3_jdk18(List<String> list) {
		list.sort((a, b) -> {
			return a.length() - b.length();
		});
		return list;
	}

	public static void main(String...args) {
		String[] array = msg.split(" +");
		LambdaMain m = new LambdaMain();

		Print.array(m, "p1_jdk17", array);
		Print.array(m, "p1_jdk18", array);
		//Print.printArray(m, "p1_jdk18_2", array);
		//Print.printArray(m, "p1_jdk18_3", array);
		Print.array(m, "p1_jdk18_4", array);
		Print.array(m, "p2_jdk18", array);

		/*
		 * �o�͂��������悭�o�͂������F�F
		 * �����_���Ƃ������AStreamAPI�ɂȂ��Ă��܂���
		 */
		Stream.of(Print.print(m, "p2_jdk18_2", array)).forEach(System.out::println);
		System.out.println();

		// ��̎����ł���������ǁA�\�Ȃ�΂����ƃX�}�[�g�Ȏ����͂Ȃ����̂��H
		Stream.concat(Stream.of(Print.print(m, "p2_jdk18_2", array)), Stream.of("-")).forEach(System.out::println);

		Stream.concat(Arrays.stream(Print.print(m, "p2_jdk18_2", array)), Stream.of("--")).forEach(System.out::println);
	
		Stream<String> stream = Arrays.stream(Print.print(m, "p2_jdk18_2", array));
		Stream.concat(stream, Stream.of("---")).forEach(System.out::println);

		{
			final ArrayList<String> list = new ArrayList<>();
			Stream.of(array).forEach(s -> list.add(s));
			Print.list(m, "p3_jdk18", list);
		}
		{
			// ���ꂪ������!!
			final List<String> list = Stream.of(array).collect(Collectors.toList());
			Print.list(m, "p3_jdk18", list);
		}
		{
			System.out.println(">>>> test");
			Stream.of(array)
				.sorted((a, b) -> a.compareToIgnoreCase(b))
				.forEach(System.out::println);
			System.out.println("<<<<");
		}
		{
			// asList�Ő��������N���X�́A[private static java.util.Arrays$ArrayList] �̂��߁A���ڎQ�Ƃ��邱�Ƃ��ł��Ȃ�
			final List<String> list = Arrays.asList(array);
			Print.list(m, "p3_jdk18", list);
		}
	}
}
