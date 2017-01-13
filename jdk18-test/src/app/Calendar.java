package app;

import java.time.LocalDate;
import static java.time.temporal.TemporalAdjusters.*;

import java.time.DayOfWeek;

public class Calendar {

	public Calendar() { }
	public void print() {
		// �����̑�P�T�̓��j�����擾����B
		LocalDate start = LocalDate.now()
				.with(firstDayOfMonth())
				.with(previousOrSame(DayOfWeek.SUNDAY));

		/*
		 * generate()�Aiterate()�Abuilder()�́A�f�[�^�񂩂�ł͂Ȃ����W�b�N�� Stream �𐶐�����B
		 * generate(), iterate()�͖����� Stream �̐�����O��Ƃ��Ă��āA
		 * �v�f�񐶐����̃��W�b�N�ŏI���������i���Ȃ��̂Œ��ӂ��K�v���B
		 * ������ Stream ���~�߂�ɂ� limit() �� findFirst() �Ȃǂ̒Z��������g���B
		 */
		System.out.println("Sun Mon Tue�@Wed Thu Fri Sat");
		// ���t���P�����i�߂� Stream<LocalDate> �𐶐�����
		java.util.stream.Stream.iterate(start, d -> d.plusDays(1L))
				.limit(7 * 6) // 6�T�ԕ��̗v�f�őł��؂�
				.map(d -> {
					// ��(day)�̕�����ɂ���
					String day;
					if (LocalDate.now().equals(d)) {
						day = String.format("*%2d ", d.getDayOfMonth());
					}
					else {
						day = String.format(" %2d ", d.getDayOfMonth());
					}
					switch (d.getDayOfWeek()) {
					case SATURDAY:
						return day += "\n"; // �y�j���Ȃ���s
					default:
						return day;
					}
					/*
				}).forEach(s -> {
					System.out.print(s);
				});
				*/
				})
				.forEach(System.out::print);
}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Calendar().print();
	}

}
