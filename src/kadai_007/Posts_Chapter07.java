package kadai_007;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;


public class Posts_Chapter07 {

	public static void main(String[] args) {
		// TODO 自動生成されたメソッド・スタブ
		
        Connection con = null;
        PreparedStatement statement = null;
        PreparedStatement selectStatement = null;
        PreparedStatement deleteStatement = null;
        
        try {
            // データベースに接続
            con = DriverManager.getConnection(
                "jdbc:mysql://localhost/challenge_java",
                "root",
                "0824Dance"
            );

            System.out.println("データベース接続成功:" + con);
            
            // **既存のデータを削除**
            String sqlDelete = "DELETE FROM posts";
            deleteStatement = con.prepareStatement(sqlDelete);
            deleteStatement.executeUpdate();
            System.out.println("既存のデータを削除しました");
            
            //二次元配列
            Object[][] postData = {
            		{1003, "2023-02-08", "昨日の夜は徹夜でした・・", 13},
            		{1002, "2023-02-08", "お疲れ様です！", 12},
            		{1003, "2023-02-09", "今日も頑張ります！", 18},
            		{1001, "2023-02-09", "無理は禁物ですよ！", 17},
                    {1002, "2023-02-10", "明日から連休ですね！", 20}            		            		
            };
            
            //SQLクエリを準備
            String sql = "INSERT INTO posts (user_id, posted_at, post_content, likes) VALUES (?,?,?,?)";
            statement = con.prepareStatement(sql);		
            
            
            int rowCnt = 0;
            
            System.out.println("レコード追加を実行します");
            for( int i = 0; i < postData.length; i++ ) {
            	statement.setInt(1,(int) postData[i][0]);
            	statement.setString(2, (String) postData[i][1]);
            	statement.setString(3, (String) postData[i][2]);
            	statement.setInt(4,(int)  postData[i][3]);
                       
            //SQLクエリを実行
            rowCnt += statement.executeUpdate();
            
            }
            
            System.out.println( rowCnt + "件のレコードが追加されました");
            
            //user_idが1002のデータを検索するSQL
            String sqlSelect = "SELECT posted_at, post_content, likes FROM posts WHERE user_id = ?";
            selectStatement = con.prepareStatement(sqlSelect);
            selectStatement.setInt(1,1002);
            
            ResultSet result = selectStatement.executeQuery();
            
            System.out.println("ユーザーIDが1002のレコードを検索しました");
            
            //SQLクエリ実行結果の抽出
            int count = 1;
            while(result.next()) {
            	Date postedAt = result.getDate("posted_at");
            	String content = result.getString("post_content");
            	int likes = result.getInt("likes");
            	
            	System.out.println(count + "件目：投稿日時=" + postedAt + "／投稿内容" + content + "／いいね数" + likes);
            	count++;
            }
            
        } catch(SQLException e) {
        	System.out.println("エラー発生:" + e.getMessage());
        } finally {
        	//オブジェクトの解放
        if( statement != null ) {
        	try { statement.close(); } catch(SQLException ignore) {}
        }
        if( selectStatement !=null ) {
        	try { selectStatement.close(); } catch(SQLException ignore) {}
        }
        if( con != null ) {
        	try { con.close(); } catch(SQLException ignore) {}
        }
      }
   }
}
