
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;


public class GUI_Interface  {
	private JTextField player1Name;
	private JTextField player2Name;
	private JLabel player1IP;
	private JTextField player2IP;
	private JButton forfeit;
	private JButton restart;
	private JLabel turn;
	private JButton quit;
	private JButton host;
	private JButton connect;
	public GUI_Interface(){
		player1Name= new JTextField("player1");
		player2Name= new JTextField("player2");
		forfeit= new JButton("Forfeit");
		restart= new JButton("Restart");
		turn= new JLabel("Start Game");
		quit= new JButton("Quit");
		player1IP=new JLabel("162.193.153.1");
		player2IP=new JTextField(10);
		host=new JButton("Host");
		connect=new JButton("Connect");
		player2Name.setEnabled(false);
		forfeit.setEnabled(false);
		restart.setEnabled(false);
	}
	
	public void switchTurns(player player1, player player2){
		player1.setTurn(!player1.getTurn());
		player2.setTurn(!player2.getTurn());
		if(player1.getTurn())
			turn.setText(player1.getName()+"'s turn "+player1.getToken());
		else
			turn.setText(player2.getName()+"'s turn "+player2.getToken());
	}
	public JButton getHost(){
		return host;
	}
	public JButton getConnect(){
		return connect;
	}
	public String getPlayer1Text(){		//straight forward method names, mostly getters and setters.
		return player1Name.getText();
	}
	public String getPlayer2Text(){		//straight forward method names, mostly getters and setters.
		return player2Name.getText();
	}
	public JTextField getPlayer1Name(){
		return player1Name;
	}
	public JTextField getPlayer2Name(){
		return player2Name;
	}

	public void setPlayer2Text(String name){
		player2Name.setText(name);
	}
	public JLabel getTurn()
	{
		return turn;
	}
	public JButton getForfeit(){
		return forfeit;
	}
	public JButton getRestart(){
		return restart;
	}
	public JButton getQuit(){
		return quit;
	}
	public void setTurnText(String name, String token){
		turn.setText(name+"'s turn "+token);
	}
	public void disableForfeit(){
		forfeit.setEnabled(false);
	}
	public void disablePlayer1Name(){
		player1Name.setEnabled(false);
	}
	public void disablePlayer2Name(){
		player2Name.setEnabled(false);
	}
	
	public void disableRestart(){
		restart.setEnabled(false);
	}
	public void enableForfeit(){
		forfeit.setEnabled(true);
	}

	public void enableRestart(){
		restart.setEnabled(true);
	}

	public JLabel getPlayer1IP() {
		return player1IP;
	}

	public void setPlayer1IP(JLabel player1ip) {
		player1IP = player1ip;
	}

	public JTextField getPlayer2IP() {
		return player2IP;
	}

	public void setPlayer2IP(JTextField player2ip) {
		player2IP = player2ip;
	}

	
}

