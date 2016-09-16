
public class vicotor {
	package hw4;

	import java.io.BufferedReader;
	import java.io.IOException;
	import java.io.InputStreamReader;
	import java.io.PrintWriter;
	import java.io.StringReader;
	import java.net.Socket;
	import java.util.LinkedList;
	import java.util.List;
	import java.util.Scanner;




	public class GameServerHandler {
	//Socket to connect to the server
	Socket chatSocket;
	PrintWriter serverOut;
	BufferedReader serverIn;
	//checks if its connected to the server
	private boolean connected;
	//the client this handler works for
	GameServerListener client;
	//creates a handler with a client for server  hostname:port
	public GameServerHandler(GameServerListener client, String hostName, int port){
	this.client = client;
	try{
	chatSocket = new Socket(hostName, port);
	serverOut = new PrintWriter(chatSocket.getOutputStream());
	serverIn = new BufferedReader(new InputStreamReader(chatSocket.getInputStream()));
	connected = true;
	}catch(Exception e){
	client.badConnectionMsg();
	}
	new Thread(()->{
	try{
	String line;
	line = serverIn.readLine();
	while(line !=null){
	getMessage(line);
	line = serverIn.readLine();
	}
	}catch (Exception e){
	tryDisconnect();
	}
	}).start();
	}
	//gets and parses message from the server
	private void getMessage(String line){
	String msg = null;
	List<String> args = new LinkedList<>();
	StringReader in = new StringReader(line);
	Scanner sc = new Scanner(in);
	sc.useDelimiter(":");
	msg = sc.next().trim();
	sc.useDelimiter("[:,]");
	while(sc.hasNext()){
	args.add(sc.next().trim());
	}
	sc.close();
	System.out.print("Received message: "+ msg+ "with arguments: ");
	for(String s: args)
	System.out.print(s + " ");
	System.out.println();
	//call methods on listener based off the message
	if(msg == null){
	client.invalidMessageReceived();
	return;
	}
	try{
	if(msg.equals("ack_user")){
	client.acknowledgeUserMsg(Boolean.parseBoolean(args.get(0)), (args.size()>1)? args.get(1) : null);
	}
	if(msg.equals("size")){
	client.sizeMsg(Integer.parseInt(args.get(0)));
	}
	if(msg.equals("users")){
	List <Boolean> userAvailable = new LinkedList<>();
	List <String> users = new LinkedList<>();
	System.out.print("users: ");
	for(String user: args){
	if(user.charAt(0)=='+')
	userAvailable.add(new Boolean(true));
	else
	userAvailable.add(new Boolean(false));
	users.add(user.substring(1));
	System.out.print(user.substring(1) + " ");
	}
	System.out.println();
	client.updateUsers(users, userAvailable);
	}
	if (msg.equals("user_play")){
	client.userPlayRequestMsg(args.get(0));
	return;
	}
	if(msg.equals("ack_user_play")){
	client.acknowledgeUserPlayMsg(Boolean.parseBoolean(args.get(0)), (args.size() >1)? args.get(1) : null);
	return;
	}
	if(msg.equals("turn")){
	client.turnMsg(Boolean.parseBoolean(args.get(0)));
	return;
	}
	if(msg.equals("ack_move")){
	client.acknowledgeMoveMsg(Integer.parseInt(args.get(0)), Integer.parseInt(args.get(1)), Boolean.parseBoolean(args.get(2)));
	return;
	}
	if(msg.equals("move")){
	client.moveMsg(Integer.parseInt(args.get(0)), Integer.parseInt(args.get(1)), Boolean.parseBoolean(args.get(2)));
	return;
	}
	if(msg.equals("ack_gg")){
	client.acknowledgeGGMsg();
	return;
	}
	if(msg.equals("gg")){
	client.ggMSG();
	return;
	}
	if(msg.equals("bye")){
	client.byeMsg();
	return;
	}
	if(msg.equals("disconnected")){
	client.disconnectMsg();
	return;
	}
	}catch(IndexOutOfBoundsException e){
	System.err.println("Message has wrong number of arguments");
	}
	client.invalidMessageReceived();
	return;
	}
	//try to disconnect from the server
	public void tryDisconnect(){
	if(connected){
	try{
	serverOut.close();
	serverIn.close();
	chatSocket.close();
	connected = false;
	client.disconnectMsg();
	}catch(IOException e){
	client.badDisconnectMsg();
	}
	}
	}

	//checks if its connected to the server
	public boolean isConnected(){
	return connected;
	}
	//Sends the user message
	public void sendNameMsg(String name){
	serverOut.println("user:"+ name);
	serverOut.flush();
	}
	//makes move message
	public void makeMove(int x, int y){
	serverOut.println("move:" + x +"," + y);
	serverOut.flush();
	}
	//forfeit the game
	public void sendGGMsg(){
	serverOut.println("gg:");
	serverOut.flush();
	}
	//request to play with an opponent
	public void sendUserPlayMsg(String opponent){
	serverOut.println("user_play:" + opponent);
	serverOut.flush();
	}
	//Send user response
	public void sendUserPlayResponseMsg(boolean b){
	serverOut.println("ack_user_play:" + b);
	serverOut.flush();
	}
	}
}
