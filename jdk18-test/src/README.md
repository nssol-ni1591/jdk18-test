[2017/10/20]
・app.TestExistsを追加
 => Stream<String> に特定の文字列を含むレコードの有無を確認するために、findFirst()とnayMatch()のどちらが早いかを確認した
 => 結果は、あまり変わらない。なので、よく使うfind系か、メソッドの目的に合っているmatch系か、好きな方を使えばよい
