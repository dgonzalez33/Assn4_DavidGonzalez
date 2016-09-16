import java.awt.Dimension;
import java.awt.event.*;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

import javax.swing.*;


@SuppressWarnings("serial")
public class connectFour extends JFrame implements MouseListener, ActionListener{
	private GUI_Board board;		//private fields, but does not have getters and setters since there is no class using 
	private player player1;			//connect four in this implementation.
	private player player2;
	private systemSupport support;
	private GUI_Interface inter;
	private timer timer;
	private Thread timerThread;
	private C4_client client;
	
	public connectFour(){			//Constructur is used to initialize everything.
			super("Connect Four"); 
			initBoard();
			addJuice();
			addAction();
		    pack();
		    setResizable(false);
		    setVisible(true);			
	}
	public void initBoard(){
        inter= new GUI_Interface();
        player1= new player("O");
        player2= new player("X");
        support= new systemSupport();
        board= new GUI_Board();
        timer= new timer(player1,player2,board);
        timerThread= new Thread(timer); 
	}
	public void addAction(){
		board.getBoard().addMouseListener(this);
		inter.getForfeit().addActionListener(this);
		inter.getRestart().addActionListener(this);
		inter.getQuit().addActionListener(this);
		inter.getHost().addActionListener(this);
		inter.getConnect().addActionListener(this);
	}
	public void addJuice(){//as in the stuff inside connect four
		 Box namePanel = Box.createHorizontalBox();
		JPanel VBox = new JPanel();
        JPanel IPpanel= new JPanel();
        JPanel connections=new JPanel();
        JPanel timerPanel = new JPanel();
        JPanel buttonPanel = new JPanel();
        JLabel player1Name= new JLabel("Player1 Name:  ");
        BoxLayout layout = new BoxLayout(VBox,BoxLayout.Y_AXIS);
        VBox.setLayout(layout);
        namePanel.add(player1Name);
        namePanel.add(inter.getPlayer1Name());
        namePanel.add(Box.createRigidArea(new Dimension(10,0)));
        namePanel.add(new JLabel("Player2 Name:  "));
        namePanel.add(inter.getPlayer2Name());
        VBox.add(namePanel);
        IPpanel.add(new JLabel("Player1 IP: "));
        IPpanel.add(inter.getPlayer1IP());
        IPpanel.add(Box.createRigidArea(new Dimension(10,0)));
        IPpanel.add(new JLabel("Player2 IP: "));
        IPpanel.add(inter.getPlayer2IP());
        VBox.add(IPpanel);
        connections.add(inter.getHost());
        connections.add(inter.getConnect());
        VBox.add(connections);
        timerPanel.add(timer.getTimer());
        VBox.add(timerPanel);
        VBox.add(inter.getTurn());
	    VBox.add(board.getBoard());
	    buttonPanel.add(inter.getForfeit());
        buttonPanel.add(inter.getRestart());
        buttonPanel.add(inter.getQuit());
        VBox.add(buttonPanel);
        add(VBox);
	}
	
	public static void main(String[] args) {	//runs connect four by creating an instance.
    	new connectFour();
	}
	public void startGame(){
		timerThread.start();
		inter.enableForfeit();
		inter.enableRestart();
		support.declareTurn(player1,player2);
        player1.setName(inter.getPlayer1Text());
        player2.setName(inter.getPlayer2Text());
        if(player1.getTurn()){
			inter.setTurnText(player1.getName(),player1.getToken());
			client.send("Turn:"+player1.getName());
		}
		else{
			inter.setTurnText(player2.getName(),player2.getToken());
			client.send("Turn:"+player2.getName());
		}
        inter.disablePlayer1Name();
       
	}
	public void connectStart(){
		timerThread.start();
		inter.enableForfeit();
		inter.enableRestart();
		player1.setName(inter.getPlayer1Text());
	    player2.setName(inter.getPlayer2Text());
	    if(player1.getTurn()){
			inter.setTurnText(player1.getName(),player1.getToken());
		}
		else{
			inter.setTurnText(player2.getName(),player2.getToken());
		}
	    inter.disablePlayer1Name();
	}
	
	public void actionPerformed(ActionEvent e) {	//what happens when a button is clicked
		
		if(e.getSource()==inter.getForfeit()){
	
		//	timer.setGameFinished(true);
		//	if(player1.getTurn()){
			//	player2.setWinner(true);
			//}
			//else{
				//player1.setWinner(true);
			//}
			client.send("Forfeit");
			//board.showWinner(player1, player2);	
			//inter.disableForfeit();
		}
		if(e.getSource()==inter.getRestart()){
			timer.setGameFinished(true);
			dispose();
			new connectFour();
		}
		if(e.getSource()==inter.getQuit()){
			timer.setGameFinished(true);
			dispose();
		}
		if(e.getSource()==inter.getHost()){
			C4_server server= new C4_server();
			server.start();
			client= new C4_client(9900,"localhost",this);
			client.connect();
			String response=null;
			while(response==null ||!response.equalsIgnoreCase("All players connected")){
				response=client.receive();
			}
			client.send("Name1:"+inter.getPlayer1Text());
			//String response="";
			while(response==null || !response.startsWith("Name2")){
				response=client.receive();
			}
			response=client.protocolHandler(response);
			inter.setPlayer2Text(response);
			startGame();
			client.start();
		}
		if(e.getSource()==inter.getConnect()){
			client= new C4_client(9900,"localhost",this);
			client.connect();
			client.send("Name2:"+inter.getPlayer1Text());
			String response="";
			while(response==null||!response.startsWith("Name1")){
				response=client.receive();
				System.out.println(response);
			}
			response=client.protocolHandler(response);
			inter.setPlayer2Text(response);
			while(response==null||!response.startsWith("Turn")){
				response=client.receive();
				System.out.println(response);
			}
			response=client.protocolHandler(response);
			if(response.equals(inter.getPlayer1Text())){
				player1.setTurn(true);
			}
			else{
				player2.setTurn(true);
			}
			connectStart();
			client.start();
		}
	}

@Override
public void mousePressed(MouseEvent e) {	//what happens when the board is clicked
	if(player1.getWinner()||player2.getWinner()||!(player1.getTurn()||player2.getTurn() ))
		return;
	//if(!board.placeToken(e.getX()/70,player1,player2))		//placeToken returns false so no token is placed and no action is done
		//return;
	if(player1.getTurn()){
		client.send("Move:"+e.getX()/70);
	
	}
		

}

public void drawToken(int x){
	//if(!board.placeToken(x,token))		//placeToken returns false so no token is placed and no action is done
		//return;
	if(player1.getTurn()){
		if(!board.placeToken(x,player1.getToken()))		//placeToken returns false so no token is placed and no action is done
			return;
		if(support.declareWinner(board, player1.getToken())){
			timer.setGameFinished(true);
			player1.setWinner(true);
			board.showWinner(player1, player2);
			inter.disableForfeit();
			client.send("Winner:"+player1.getName());
			return;
		}
		else if(support.declareTie(board.getTurn())){
			board.showTie();
			inter.disableForfeit();
			client.send("Tie");

			return;
		}
	}
if(player2.getTurn()){
	if(!board.placeToken(x,player2.getToken()))		//placeToken returns false so no token is placed and no action is done
		return;
	if(support.declareWinner(board, player2.getToken())){
		timer.setGameFinished(true);
		player2.setWinner(true);
		board.showWinner(player1,player2);
		inter.disableForfeit();
		client.send("Winner:"+player2.getName());
		return;
	}
	else if(support.declareTie(board.getTurn())){
		timer.setGameFinished(true);
		board.showTie();
		inter.disableForfeit();
		client.send("Tie");
		return;
	}
}

inter.switchTurns(player1, player2);
}
public void winner(){
	timer.setGameFinished(true);
	if(player1.getTurn()){
		player2.setWinner(true);
	}
	else{
		player1.setWinner(true);
	}
	board.showWinner(player1, player2);	
	inter.disableForfeit();
}


@Override	//rest are unused, not sure why it wont let me take them off.
public void mouseClicked(MouseEvent e) {
	// TODO Auto-generated method stub
	
}


@Override
public void mouseEntered(MouseEvent e) {
	// TODO Auto-generated method stub
	
}


@Override
public void mouseExited(MouseEvent e) {
	// TODO Auto-generated method stub
	
}


@Override
public void mouseReleased(MouseEvent e) {
	// TODO Auto-generated method stub
	
}


}