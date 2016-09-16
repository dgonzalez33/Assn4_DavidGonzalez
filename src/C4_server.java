import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;



public class C4_server extends Thread{
	private PrintWriter output;
	private BufferedReader input;
	private ServerSocket server;
	private Socket connection;
	private String[] data;
	public C4_server() {
		data= new String[4];//index=0 host name, index=1 connector name, index=2 turn, index=3 move

	}

	
	public void run(){
		ServerSocket socket=null;
		try{
			socket = new ServerSocket(9900);
			System.out.println("Server is running");
			while(true){
				new clientServer(socket.accept()).start();
			}
	}
		catch(IOException e){
				System.out.println("Server already up...");
				System.out.println("Remember to close...");
				System.out.println("Stupid");
		}
		finally{
			try {
				socket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	
	}
	
}
