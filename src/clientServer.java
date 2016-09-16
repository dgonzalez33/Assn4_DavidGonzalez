import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashSet;


public class clientServer extends Thread {
	Socket incoming;
	private static HashSet<PrintWriter> writers= new HashSet<PrintWriter>();
	public clientServer(Socket socket){
		incoming=socket;
	}
	public void run(){
		BufferedReader in=null;
		PrintWriter out=null;
		try{
		in= new BufferedReader(new InputStreamReader(incoming.getInputStream()));
		out = new PrintWriter(new OutputStreamWriter(incoming.getOutputStream()));
		//out.println("Connected!");
		writers.add(out);
		if(writers.size()==2){
			System.out.println("All players connected");
			for(PrintWriter writer: writers){
				writer.println("All players connected");
				writer.flush();
			}
		}
		while(true){
			String str = in.readLine();
			if(str==null){
				return;
			}
			for(PrintWriter writer: writers){
				System.out.println(str);
			if(str.startsWith("Name1:")){
				writer.println(str);
			}
			else if(str.startsWith("Name2:"))
				writer.println(str);
			else if(str.startsWith("Turn:"))
				writer.println(str);
			else if(str.startsWith("Move"))
				writer.println(str);
			else if(str.startsWith("Forfeit"))
				writer.println(str);
			else if(str.startsWith("Winner"))
				writer.println(str);
			else if(str.startsWith("Tie"))
				writer.println(str);
			
			else{
				out.flush();
				if(str.trim().equals("Quit"))
					break;
			}
			writer.flush();
			}
		}
		}catch(IOException e){
			System.out.println("Connection not good");
		}
		finally{
			writers.remove(out);
			try{
				incoming.close();
			}catch(IOException e){
				
			}
			
		}
	}
	
}
