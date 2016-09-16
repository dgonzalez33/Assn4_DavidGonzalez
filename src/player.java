import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class player{	//class for the player
	private String name;
	private Boolean turn;
	private String token;
	private Boolean winner;
	private String ip;
	Socket socket;
    BufferedReader input;
    PrintWriter output;
    
	public player(String tok){		
		name="";
		turn=false;
		token=tok;
		winner=false;
		ip=("");
	}

	
	public String getName(){		//just setters and getters.
		return name;
	}
	public void setName(String newName){
		name=newName;
	}
	public void setToken(String newToken){
		token=newToken;
	}
	public String getToken(){
		return token;
	}
	public void setTurn(Boolean newTurn){
		turn=newTurn;
	}
	public boolean getTurn(){
		return turn;
	}
	public void setWinner(Boolean newWinner){
		winner=newWinner;
	}
	public boolean getWinner(){
		return winner;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String _ip) {
		ip = _ip;
	}
}
