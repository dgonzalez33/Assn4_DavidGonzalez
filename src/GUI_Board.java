import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;



public class GUI_Board
{
	private int turn;//keep track on total turns on board, not who's turn it is
	private int[] column;//keeps track of columns filled, used so column is not overfilled
	static String[][] grid;//keeps track of board locations for checking wins and storing in database
	private JLabel Board;//The board used in connectFour class
	private Graphics2D graphics;//Used to draw on board
	private BufferedImage circle;//used for circle token
	private BufferedImage X;//used for X token
	private BufferedImage board_img;//used to make board
	
	public GUI_Board(){			//constructor initializes board by drawing grid and initializing attributes.
		setCircleImg("circle.png");//sets the file location for circle
		setXimg("x.png");			//sets file location for X
		turn=0;
		column=new int[8];
		grid=new String[7][8];
		for(int i=0;i<grid.length;i++){
			for(int j=0;j<grid[i].length;j++){
				grid[i][j]="";
			}
		}
		for(int i=0;i<column.length;i++){
			column[i]=490;
		}
		board_img =  new BufferedImage(580, 500,BufferedImage.TYPE_INT_ARGB);
	    graphics = board_img.createGraphics();
	    graphics.setBackground(Color.white);
	    graphics.fillRect(0, 0, 560, 490);
	    graphics.setColor(Color.black);
	    BasicStroke bs = new BasicStroke(1);
	  	graphics.setStroke(bs);
		for(int i=0;i<=560;i+=70)
		   graphics.drawLine(i, 0, i, 490);
		for(int i=0;i<=560;i+=70)
		   graphics.drawLine(0,i,560,i);
		 graphics.setColor(Color.red);
		Board = new JLabel(new ImageIcon(board_img));

	}
	public void setCircleImg(String file){//sets the circle token based on file in parameter
		try {
			circle=ImageIO.read(new File(file));
		} catch (IOException e) {
			System.out.println("Circle Image not found");
		}
	}
	public void setXimg(String file){//sets the x token based on file in parameter
		try {
			X=ImageIO.read(new File(file));
		}catch (IOException e) {
			System.out.println("X image not found");
		}
	}
	
	public JLabel getBoard(){
		return Board;
	}
	public int getTurn(){
		return turn;
	}
	public String getGridValue(int row, int column){
		return grid[row][column];
	}
	public String[][] getGrid(){
		return grid;
	}
   
	public void showWinner(player player1,player player2){
		JFrame winnerFrame= new JFrame();
		winnerFrame.setSize(700, 200);
		JLabel winnerText;
		
		if(player1.getWinner()){
			winnerText= new JLabel(player1.getName()+" is the winner!!!");
		}
		else{
			winnerText= new JLabel(player2.getName()+" is the winner!!!");
		}
		winnerText.setFont(new Font(winnerText.getName(),Font.PLAIN,60));
		winnerFrame.add(winnerText,BorderLayout.CENTER);
		winnerFrame.setResizable(false);
	    winnerFrame.setVisible(true);
	   
	}
	
	public void showTie(){
		JFrame winnerFrame= new JFrame();
		winnerFrame.setSize(700, 200);
		JLabel winnerText=new JLabel("The game is a tie!!!");
		winnerText.setFont(new Font(winnerText.getName(),Font.PLAIN,60));
		winnerFrame.add(winnerText,BorderLayout.CENTER);
		winnerFrame.setResizable(false);
	    winnerFrame.setVisible(true);
	}
   public boolean placeToken(int x,String token){ //returns true if token is placed, false if no token is placed.
	  
	   if(column[x]<=0)
		   return false;
	  
		drawToken(x*70,token);
		grid[column[x]/70][x]=token;
	   
   
	   turn++;
	   return true;

   }
   
   public void drawToken(int i,String token){
	   if(token.equals("X")){
		   graphics.drawImage(X,i+2,column[i/70]-68,null);
	   }
	   else{
		   graphics.drawImage(circle,i+2,column[i/70]-68,null);
		  
	   }
	   column[i/70]-=70;
	   Board.setIcon(new ImageIcon(board_img));
	 }

}