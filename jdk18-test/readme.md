# jdk1.8 の新機能を確認したい

- ※説明不測で何を行いたいのかわからない
- ※パッケージ app と util の使い分け、test との関連もわからない部分がある

## ラムダ式、Functionインターフェース

### Package: app.index.LineCounter*
##### テストモジュール： test.LineNumberTest
##### 概要
Stream処理において、対象に特定のデータを付加するような仕組みの検討

サンプルとして、データとして行番号を付加するLineCounter{234}*

### Package: app.interceptor
##### テストモジュール： test.weld.InterceptorTest
##### 概要
まず、インターセプタの使い方の確認

次に、@PrintCall.MethodAnnotationが付加されていることで、enter()メソッドの追加処理が行われることを確認する

※追加処理：呼出元のメソッド名と引数を出力する

※Interceptor.getInterface()で対象を取り出しているので、InterfaceにAnnotationが必要

### weld-seを使うことで問題が解決した??


### Package: app.speaker
##### テストモジュール： test.weld.speaker.*
##### 概要
WeldのAlternativesにより、Annotationもしくはbeans.xmlにより、@Inject先を切り替えられることを確認する

### Package: app.validator
##### テストモジュール： 
##### 概要

### Package: util.passwd
##### テストモジュール： test.PasswdTest
##### 概要
特定の条件に従った文字列（パスワード）を生成するためのロジック

### Package: util.Print
##### テストモジュール： test.PrintTest
##### 概要
リフレクションを使用して、メソッドの実行と結果を出力する

ならびに、System.out.*のメソッドとの混在が汚いので、よく使うメソッドを再定義してある

### Package: util.elaps
##### テストモジュール： 
##### 概要

### Package: util.index
##### テストモジュール： 
##### 概要

### Package: util.logging
##### テストモジュール：test.weld.WeldLogTest

##### 概要
アノテーションでログの出力先を切り替えたい
- @StdOut
- @StdErr

### Package: util.weld
##### テストモジュール： 
##### 概要


### テストモジュール単体

##### テストモジュール： test.stream.*
Streamとlambda式

##### CollectGroupingBy
##### CollectToMap
##### ForEachTest
##### MatchStringTest
条件に合う文字列の有無を確認する際の処理速度の比較

##### StreamTest
以下の2つの処理をjdk7以前とjdk8以降の実装イメージの変遷
- 大小文字変換
- ファイル結合

##### テストモジュール： test.weld.WeldScopeTest、test.weld.scope.*
##### 概要
@PostConstruct、@PreDestroyの動作確認

### 履歴
#####[2017/10/20]
・app.TestExistsを追加
- => Stream<String> に特定の文字列を含むレコードの有無を確認するためにfindFirst()とnayMatch()のどちらが早いかを確認した
- => 結果は、あまり変わらない。なので、よく使うfind系か、メソッドの目的に合っているmatch系か、好きな方を使えばよい
