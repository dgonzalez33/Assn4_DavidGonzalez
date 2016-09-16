import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
 

public class C4_client extends Thread{
	   private int PORT;
	   private String IP;
	    private Socket socket;
	    private BufferedReader in;
	    private PrintWriter out;
	    private connectFour board;
	public C4_client(int PORT, String IP,connectFour c4){
		this.PORT=PORT;
		this.IP=IP;
		board=c4;
	}
	public void send(String protocol){
			System.out.println("Sending: "+protocol);
			out.println(protocol);
			out.flush();
			
		
	}
	public String protocolHandler(String protocol){
		if(protocol.startsWith("Name")){
			return protocol.substring(6);
		}
		if(protocol.startsWith("Move")){
			return protocol.substring(5);
		}
		if(protocol.startsWith("Turn")){
			return protocol.substring(5);
		}
		return protocol;
			
		
	}
	public void finalSend(){
		out.println("BYE");
		out.flush();
	}
	public String receive(){
		//while(true){
			String str="";
			try {
				str = in.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return str;
		
	}
	public void connect(){
		try{
			socket = new Socket(IP,PORT);
			in= new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out= new PrintWriter(new OutputStreamWriter(socket.getOutputStream()),true);
		
		}catch(Exception e){
			System.out.println("Could not connect");
		}
	}
	
	public void run(){
		while(true){
			try{
				String move = null;
				move=in.readLine();
				if(!(move==null) && move.startsWith("Move:")){
					move=protocolHandler(move);
					board.drawToken(Integer.parseInt(move));

				}
				if(!(move==null) && move.startsWith("Forfeit")){
					board.winner();
					break;
				}
				if(!(move==null) && (move.startsWith("Winner") || move.startsWith("Tie"))){
					break;
				}
			}
			catch(IOException e){
				break;
			}
			
		}
	}
	
}
