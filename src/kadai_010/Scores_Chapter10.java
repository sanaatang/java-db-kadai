package kadai_010;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Scores_Chapter10 {

	public static void main(String[] args) {
		// TODO 自動生成されたメソッド・スタブ
		
		Connection con = null;
		Statement statement = null;
		Scanner scanner = null;
		Statement orderStatement = null;
		ResultSet resultSet = null;
		
		try {
			
			//データベースに接続
			con = DriverManager.getConnection(
					"jdbc:mysql://localhost/challenge_java",
					"root",
					"0824Dance"
			);
			
			System.out.println("データベース接続成功:" + con);
			
			
			
			//データ追加のSQLクエリを準備
			statement = con.createStatement();
			String sql = "UPDATE scores SET score_math = 95, score_english = 80 WHERE id = 5;";
			
			System.out.println("レコード更新を実行します");
			
			//SQLクエリを実行
			int rowCnt = statement.executeUpdate(sql);
			System.out.println( rowCnt + "件のレコードが更新されました");
			
			
			System.out.println("数学・英語の点数が高い順に並べ替えました");
			//データ並び替えのSQLクエリを準備
			orderStatement = con.createStatement();
			String orderSql = "SELECT * FROM scores ORDER BY score_math DESC, score_english DESC;";
			
			resultSet = orderStatement.executeQuery(orderSql);
			
			//SQLクエリの実行結果を抽出
			while(resultSet.next()) {
				int id = resultSet.getInt("id");
				String name = resultSet.getString("name");
				int scoreMath = resultSet.getInt("score_math");
				int scoreEnglish = resultSet.getInt("score_english");
				
				System.out.println(resultSet.getRow() + "件目：生徒ID=" + id + "／氏名=" + name + "／数学=" + scoreMath + "／英語=" + scoreEnglish);
		}
		
		
		} catch(SQLException e) {
			System.out.println("エラーが発生しました: " + e.getMessage());
		} finally {
			//オブジェクトの解放
				if( resultSet != null) {
					try { resultSet.close(); } catch(SQLException ignore) {}
				}
				if( scanner != null ) {
					scanner.close();
				}
				if( statement != null ) {
					try { statement.close(); } catch(SQLException ignore) {}
				}
				if( orderStatement != null ) {
					try { orderStatement.close(); } catch(SQLException ignore) {}
				}
				if( con != null ) {
					try { con.close(); } catch(SQLException ignore) {}
				}
			
		}

	}

}
