package app;

import java.time.LocalDate;
import static java.time.temporal.TemporalAdjusters.*;

import java.time.DayOfWeek;

public class Calendar {

	public Calendar() { }
	public void print() {
		// 今月の第１週の日曜日を取得する。
		LocalDate start = LocalDate.now()
				.with(firstDayOfMonth())
				.with(previousOrSame(DayOfWeek.SUNDAY));

		/*
		 * generate()、iterate()、builder()は、データ列からではなくロジックで Stream を生成する。
		 * generate(), iterate()は無限の Stream の生成を前提としていて、
		 * 要素列生成側のロジックで終了させる手段がないので注意が必要だ。
		 * 無限の Stream を止めるには limit() や findFirst() などの短絡操作を使う。
		 */
		System.out.println("Sun Mon Tue　Wed Thu Fri Sat");
		// 日付を１日ずつ進めた Stream<LocalDate> を生成する
		java.util.stream.Stream.iterate(start, d -> d.plusDays(1L))
				.limit(7 * 6) // 6週間分の要素で打ち切る
				.map(d -> {
					// 日(day)の文字列にする
					String day;
					if (LocalDate.now().equals(d)) {
						day = String.format("*%2d ", d.getDayOfMonth());
					}
					else {
						day = String.format(" %2d ", d.getDayOfMonth());
					}
					switch (d.getDayOfWeek()) {
					case SATURDAY:
						return day += "\n"; // 土曜日なら改行
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
