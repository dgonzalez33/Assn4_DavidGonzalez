import java.util.Random;


public class systemSupport {		//class used as support for the system.
	public systemSupport(){			//when a win, tie, turn happens is found here.
		
	}
	
	public boolean declareTie(int turns){
		if(turns>=56){
			return true;
		}
		return false;
	}
	public void declareTurn(player player1, player player2){
		Random ran = new Random();
		int rndm= ran.nextInt(2);
		if(rndm==0){
			player1.setTurn(true);

		}
		else{
			player2.setTurn(true);
		
		}
		
	}
	
	//returns true if there is a winner in the GUI_Board
	public boolean declareWinner(GUI_Board Board,String token){  //checks everything just incase its a sneaky win O O [] O
		for(int row=0;row<7;row++){
			for(int column=0;column<8;column++){
				if(column<=4){
					if(Board.getGridValue(row,column).equals(token) && Board.getGridValue(row,column+1).equals(token) &&
						Board.getGridValue(row,column+2).equals(token)&& Board.getGridValue(row,column+3).equals(token)){
						return true;
					}
				}
				
				if(row<=3){
					if(Board.getGridValue(row,column).equals(token) && Board.getGridValue(row+1,column).equals(token) &&
							Board.getGridValue(row+2,column).equals(token)&& Board.getGridValue(row+3,column).equals(token)){
						return true;
						}
				}
				if(row<=3 && column <=4){
					if(Board.getGridValue(row,column).equals(token) && Board.getGridValue(row+1,column+1).equals(token) &&
							Board.getGridValue(row+2,column+2).equals(token)&& Board.getGridValue(row+3,column+3).equals(token)){
						return true;
					}
				}
				if(row>=3 && column >=3){
					if(Board.getGridValue(row,column).equals(token) && Board.getGridValue(row-1,column-1).equals(token) &&
							Board.getGridValue(row-2,column-2).equals(token)&& Board.getGridValue(row-3,column-3).equals(token)){
						return true;
					}
				}
				if(row>=3 && column <=4){
					if(Board.getGridValue(row,column).equals(token) && Board.getGridValue(row-1,column+1).equals(token) &&
							Board.getGridValue(row-2,column+2).equals(token)&& Board.getGridValue(row-3,column+3).equals(token)){
						return true;
					}
				}
				if(row<=3 && column >=3){
					if(Board.getGridValue(row,column).equals(token) && Board.getGridValue(row+1,column-1).equals(token) &&
							Board.getGridValue(row+2,column-2).equals(token)&& Board.getGridValue(row+3,column-3).equals(token)){
						return true;
					}
				}
			}
		}
		return false;
			
	}
}
