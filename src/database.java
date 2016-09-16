
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


public class database implements Runnable{
	private Connection conn = null;
	private Statement stmt = null;
	protected player player1;
	protected player player2;
	protected GUI_Board board;
	
	public database(player passedPlayer1, player passedPlayer2, GUI_Board passedBoard){
		//constructor
		player1= passedPlayer1;
		player2= passedPlayer2;
		board= passedBoard;
	}
	
	public synchronized void connectDatabase(){
		try {
			String url = "jdbc:mysql://localhost:3306/connectFour?user=dgonzalez33";
			String username = "dgonzalez33";
			String password = "password";
		 conn =
		 DriverManager.getConnection(url,username,password);
		 updateDatabase();
		 // Do something with the Connection
		} catch (SQLException ex) {
		 // handle any errors
			conn=null;
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
		finally{
			try {
				conn.close();
			} catch (SQLException e) {
				conn=null;
			}
		}
		
	}
	
	public void createDatabase(){
		try{
			stmt = conn.createStatement();
			String sql= "CREATE TABLE gameVars(Player int,Nam char(20), Token char(1), Turn char(5))";
			stmt.executeUpdate(sql);
			sql= "CREATE TABLE gameVars(Player int,Nam char(20), Token char(1), Turn char(5))";
			stmt.executeUpdate(sql);
			
		}
		catch(SQLException ex){
			 System.out.println("SQLException: " + ex.getMessage());
			 System.out.println("SQLState: " + ex.getSQLState());
			 System.out.println("VendorError: " + ex.getErrorCode());
		}
	}
	
	
	public void updateDatabase(){
		
		try {
		      stmt = conn.createStatement();
		      String sql = "INSERT INTO gameVars"
		      		+ " values ('"+player1.getName()+"','"+player1.getToken()+"','"
		      +player1.getTurn()+"','"+player2.getName()+"','"+player2.getToken()+"','"+player2.getTurn()+"')";
		      stmt.executeUpdate(sql);
		      for(int i=0;i<board.getGrid().length;i++){
		    	  for(int j=0;j<board.getGrid()[i].length;j++){
		    		  sql = "UPDATE game SET col"+(j+1)+"='"+board.getGrid()[i][j]+"' WHERE rowNum="+(i+1);
		    		  stmt.executeUpdate(sql);
		    	  }
		      }
		      
		     		
		    
		}
		catch (SQLException ex){
		 // handle any errors
		 System.out.println("SQLException: " + ex.getMessage());
		 System.out.println("SQLState: " + ex.getSQLState());
		 System.out.println("VendorError: " + ex.getErrorCode());
		}
		finally {
		 // close connections
		 
		 if (stmt != null) {
		 try {
		 stmt.close();
		 } catch (SQLException sqlEx) { } // ignore
		 stmt = null;
		 }
		}
	}

	@Override
	public void run() {
		Thread.yield();
		connectDatabase();
	}
	
}
