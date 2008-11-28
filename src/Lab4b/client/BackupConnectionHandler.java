package Lab4b.client;

import java.net.*;
import java.io.*;
import server.words;

public class BackupConnectionHandler implements Runnable{

	Socket ConnectionToOneClient;

	DataInputStream in;		// this stream is to receive a String from client
	DataOutputStream out;	// this stream is to send to a String to client

	BackupWordsMonitor wordsMonitor;
	int round=0;

	public BackupConnectionHandler(Socket ConnectionToOneClient,BackupWordsMonitor wordsMonitor)
	throws Exception{

		this.ConnectionToOneClient=ConnectionToOneClient;
		// bound the streams to the connect to the client
		in = new DataInputStream( ConnectionToOneClient.getInputStream());
		out =new DataOutputStream( ConnectionToOneClient.getOutputStream());

		//keep track of list of words
		this.wordsMonitor=wordsMonitor;
	}

	public void run(){

		System.out.println("Waiting for round number...");		


		try{
			//get round from client
			round = in.readInt();
			System.out.println("Received Round Number: " + round);
		}catch(IOException io){
			io.printStackTrace(); 
			System.exit(1); 
		}

		try{
			//get next word for round and send it to the client
			String messageToClient = wordsMonitor.getNext(round);
			System.out.println("The next word sent to client: " +  messageToClient);
			
			out.writeUTF(messageToClient);
		}catch(IOException io2){
			io2.printStackTrace(); 
			System.exit(1); 
		}
	}


}

