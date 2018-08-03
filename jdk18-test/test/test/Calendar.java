package test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;

import util.Print;
import util.weld.WeldRunner;
import util.weld.WeldWrapper;

import static java.time.temporal.TemporalAdjusters.*;

import java.time.DayOfWeek;

/*
 * Stream.iterate と LocalDate の使い方
 */
public class Calendar implements WeldRunner {

	public Calendar() {
		// Do nothing
	}

	@Override
	public int start(String... args) {
		print();
		return 0;
	}

	// 月替わりは改行し前日分の空白を出力する
	// ヘッダの曜日を取得する
	private String header(LocalDate d) {
		StringBuilder sb = new StringBuilder();
		sb.append(System.lineSeparator());
		sb.append(d.format(DateTimeFormatter.ofPattern("'['M yyyy']'")));
		sb.append(System.lineSeparator());
		sb.append(Stream.iterate(DayOfWeek.SUNDAY, d1 -> d1.plus(1L))
				.limit(7) // 1週間分の要素で打ち切る
				.map(d1 -> d1.getDisplayName(TextStyle.SHORT_STANDALONE, Locale.US))
				.collect(Collectors.joining(" ")));
		sb.append(System.lineSeparator());
		int week = d.getDayOfWeek().getValue();
		if (week < 7) {
			// 日曜日は除外
			sb.append(Stream.generate(() -> "    ").limit(week).collect(Collectors.joining("")));
		}
		return sb.toString();
	}

	//1年分のカレンダを出力する
	@Test
	public void print() {
		LocalDate start = LocalDate.now()
				.with(firstDayOfYear())	// 今年の最初の日
				//.with(firstDayOfMonth())	// 今月の最初の日
				//.with(previousOrSame(DayOfWeek.SUNDAY))
				;	// その週の最初の日曜日

		/*
		 * generate()、iterate()、builder()は、データ列からではなくロジックで Stream を生成する。
		 * generate(), iterate()は無限の Stream の生成を前提としていて、
		 * 要素列生成側のロジックで終了させる手段がないので注意が必要だ。
		 * 無限の Stream を止めるには limit() や findFirst() などの短絡操作を使う。
		 */
		// 日付を１日ずつ進めた Stream<LocalDate> を生成する
		Stream.iterate(start, d -> d.plusDays(1L))
				//.limit(7 * 6L)	// 6週間分の要素で打ち切る
				//.limit(7 * 36L)	// 36週間分の要素で打ち切る
				//.limit(LocalDate.now().with(lastDayOfYear()).getDayOfYear()) // 大みそかまでの日数で打ち切る
				.limit(LocalDate.now().with(lastDayOfYear()).getDayOfYear() - start.getDayOfYear() + 1) // 大みそかまでの日数で打ち切る
				.map(d -> {
					StringBuilder day = new StringBuilder();

					// 月替わりはヘッダを出力する
					if (d.getDayOfMonth() == 1) {
						day.append(header(d));
					}

					// 日(day)の文字列にする
					day.append(String.format("%s%2d ", LocalDate.now().equals(d) ? "*" : " ", d.getDayOfMonth()));

					if (d.getDayOfWeek() == DayOfWeek.SATURDAY	// 土曜日なら改行
							|| d.getDayOfMonth() == d.with(lastDayOfMonth()).getDayOfMonth()	// 月末なら改行
							) {
						day.append(System.lineSeparator()); // 土曜日なら改行
					}
					return day.toString();
				})
				.forEach(Print::print);
		Print.println();
	}

	//今月から年末までのカレンダを出力する
	@Test
	public void print2() {
		final LocalDate start = LocalDate.now()
				//.with(firstDayOfYear())	// 今年の最初の日
				.with(firstDayOfMonth())	// 今月の最初の日
				//.with(previousOrSame(DayOfWeek.SUNDAY))
				;	// その週の最初の日曜日

		/*
		 * generate()、iterate()、builder()は、データ列からではなくロジックで Stream を生成する。
		 * generate(), iterate()は無限の Stream の生成を前提としていて、
		 * 要素列生成側のロジックで終了させる手段がないので注意が必要だ。
		 * 無限の Stream を止めるには limit() や findFirst() などの短絡操作を使う。
		 */
		// 日付を１日ずつ進めた Stream<LocalDate> を生成する
		Stream.iterate(start, d -> d.plusDays(1L))
				//.limit(7 * 6L)	// 6週間分の要素で打ち切る
				//.limit(7 * 36L)	// 36週間分の要素で打ち切る
				.limit(LocalDate.now().with(lastDayOfYear()).getDayOfYear() - start.getDayOfYear() + 1) // 大みそかまでの日数で打ち切る
				//.limit(LocalDate.now().with(lastDayOfYear()).getDayOfYear()) // 大みそかまでの日数で打ち切る
				.flatMap(d -> {
					List<String> list = new ArrayList<>();

					// 月替わりはヘッダを出力する
					if (d.getDayOfMonth() == 1) {
						list.add(header(d));
					}

					// 日(day)の文字列にする
					list.add(String.format("%s%2d ", LocalDate.now().equals(d) ? "*" : " ", d.getDayOfMonth()));

					if (d.getDayOfWeek() == DayOfWeek.SATURDAY	// 土曜日なら改行
							|| d.getDayOfMonth() == d.with(lastDayOfMonth()).getDayOfMonth()	// 月末なら改行
							) {
						list.add(System.lineSeparator());
					}
					return list.stream();
				})
				.forEach(Print::print);
	}

	public static void main(String[] args) {
		new WeldWrapper(Calendar.class).weld(args);
	}

}
