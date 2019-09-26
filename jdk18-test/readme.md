# jdk1.8 の新機能を確認したい

- 説明不測で何を行いたいのかわからない
- パッケージ app と util の使い分け、test との関連もわからない部分がある

## テストモジュール： test.Calendar
### 概要
	Streamのiterate()とlimit()を使用したサンプル。

## テストモジュール： test.FileAPITest
### 概要
	java.nioを使用したサンプル。

## テストモジュール： test.LineNumerTest
### テスト対象: app.index.LineCounter*
### 概要
	Stream処理において、対象に特定のデータを付加するような仕組みのサンプル。
	文字列データに行番号を付加するクラス。
	- LineCounter: スレッドを使用しなければ正常に動作するパターン（String.format()使用）
	- LineCounter2: スレッドでも正常に動作する+行Noを保持するためのクラスを使う
	- LineCounter3: スレッドでも正常に動作する+AbstractMap.SimpleImmutableEntry<K,V>を使う
	- LineCounter4: スレッドを使用しなければ正常に動作するパターン（基本）

## テストモジュール： test.MapComputeIfTest
### 概要
	Mapのconpute()を使用したサンプル。Mapに対するキーの有無により、追加、置換、削除を行う。

## テストモジュール： test.PasswdTest
### テスト対象: util.Passwd
### 概要
	特定の条件に従った文字列（パスワード）を生成するためのクラス（util.Passwd）のテスト。

## テストモジュール： test.PrintTest
### テスト対象: util.Print
### 概要
	リフレクションを使用して、メソッドの実行と結果を出力する。
	ならびに、System.out.*のメソッドとの混在が汚いので、よく使うメソッドを再定義してある。
	ちなみに、util.Printは、テスト実行時のログ出力を容易にするためのクラス。特に新機能とは関係ない。

## テストモジュール： test.WhoisTest
### 概要
	指定されたWhoisサーバからデータを取得し出力するだけ。特に新機能とは関係ない。

## テストモジュール： test.lamdba.LambdaFunctionTest
### テスト対象: app.validator
### 概要
	Function機能の確認としてストラテジパターンを使用する。ただ、言ってしまえば、DIで問題は解決できる。
	- test1: jdk1.7以前（ストラテジパターンの実装にInnerクラスを使用する）
	- test2: ストラテジパターンにクラスを使用して、ラムダ式を使用。しかし、test3の方が容易で、Funtionにつながる。
	- test3: ストラテジパターンをInterfaceで実装。
	- test4: Predicate<T>を使うパターン
	- test5: Function<T>を使うパターン

## テストモジュール： test.lamdba.LambdaSortTest
### 概要
	コレクション（配列など）のいろいろなソート方式の実装。

## テストモジュール： test.lamdba.LambdaSupplierTest
### 概要
	いろいろなstreamの生成方法の確認。

## テストモジュール： test.stream. CollectGroupingBy
## テストモジュール： test.stream. CollectToMap
### 概要
	以下の2つの処理をjdk7以前とjdk8以降の実装イメージの変遷
	- 大小文字変換
	- ファイル結合

## テストモジュール： test.stream. ForEachTest
## テストモジュール： test.stream. MatchStringTest
### テスト対象:
### 概要
	条件に合う文字列の有無を確認する際の処理速度の比較

## テストモジュール： test.stream.StreamTest
### テスト対象
### 概要
	- test1系: DATA1を小文字に変換して、Listクラスに集約する
	- test3系: ストリームを結合する
	- test4系: ファイルのデータを結合する

## テストモジュール： test.weld.InterceptorTest
### テスト対象: app.interceptor
### 概要
	まず、インターセプタの使い方の確認。
	次に、@PrintCall.MethodAnnotationが付加されていることで、enter()メソッドの追加処理が行われることを確認する。
	- 補足
		- 追加処理：呼出元のメソッド名と引数を出力する
		- Interceptor.getInterface()で対象を取り出しているので、InterfaceにAnnotationが必要
		- weld-seを使うことで問題が解決した??

## テストモジュール： test.weld.WeldLogTest
### テスト対象: util.logging
### 概要
	アノテーションでログの出力先を切り替えたい
	- @StdOut
	- @StdErr

## テストモジュール： test.weld.WeldScopeTest、test.weld.scope.*
### 概要
	@PostConstruct、@PreDestroyの動作確認

## テストモジュール： test.weld.speaker.SpeakerTest, AltSpeakerTest
### テスト対象: app.speaker
### 概要
	WeldのAlternatives機能（Annotationもしくはbeans.xmlにより）を使用して、@Inject先を切り替えられることを確認する


## 履歴
#####[2017/10/20]
・app.TestExistsを追加
- => Stream<String> に特定の文字列を含むレコードの有無を確認するためにfindFirst()とnayMatch()のどちらが早いかを確認した
- => 結果は、あまり変わらない。なので、よく使うfind系か、メソッドの目的に合っているmatch系か、好きな方を使えばよい
