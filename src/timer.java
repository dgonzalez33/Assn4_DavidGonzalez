

import javax.swing.JLabel;


public class timer implements Runnable {
private JLabel Time;
private int minute;
private int second;
private Thread data;
boolean gameFinished;
protected player player1;
protected player player2;
protected GUI_Board board;
	public timer(player passedPlayer1, player passedPlayer2, GUI_Board passedBoard){
		player1= passedPlayer1;
		player2= passedPlayer2;
		board= passedBoard;
		gameFinished=false;
		Time= new JLabel(minute+":0"+second);
		
	}
	public void setGameFinished(boolean isFinished){
		gameFinished=isFinished;
	}
	
	public JLabel getTimer(){
		return Time;
	}
	
	public void updateGame(){
		second= (second+1)%60;
		if(second==0){
			minute++;
		}
		if(second>=10)
			Time.setText(minute+":"+second);
		else
			Time.setText(minute+":0"+second);
		}
	
	@Override
	public void run() {
		while(!gameFinished){
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		updateGame();
		if(second%10==0){
			data= new Thread(new database(player1,player2,board));
			data.start();
		}
		}
		
	}
}
