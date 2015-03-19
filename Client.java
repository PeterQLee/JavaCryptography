import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class Client extends JFrame{
	
	private JTextField userText;
	private JTextArea chatWindow;
	private ObjectOutputStream output;
	private ObjectInputStream input;
	private Socket connection;
	private String msg = "";
	private String serverIP;
	
	public Client(String host){
		super("Client");
		serverIP = host;
		userText = new JTextField();
		userText.setEditable(false);
		userText.addActionListener(
			new ActionListener(){
				public void actionPerformed(ActionEvent event){
					sendMessage(event.getActionCommand());
					userText.setText("");
				}
			}
		);
		add(userText, BorderLayout.NORTH);
		chatWindow = new JTextArea();
		add(new JScrollPane(chatWindow), BorderLayout.CENTER);
		setSize(300, 150);
		setVisible(true);
 	}
	
	public void runClient(){
		try{
			connectToServer();
			setupStreams();
			chatting();
		}catch(EOFException eofException){
			showMessage("\n Client terminated the connection");
		}catch(IOException ioException){
			ioException.printStackTrace();
		}finally{
			closeAll();
		}
	}
	
	private void connectToServer() throws IOException{
		showMessage("Attempting connection... \n");
		connection = new Socket(InetAddress.getByName(serverIP), 6789);
		showMessage(" Connected to: " + connection.getInetAddress().getHostName());
	}
	
	private void setupStreams() throws IOException{
		output = new ObjectOutputStream(connection.getOutputStream());
		output.flush();
		input = new ObjectInputStream(connection.getInputStream());
		showMessage("\n Streams now setup! \n");
	}
	
	private void chatting() throws IOException{
		ableToType(true);
		do{
			try{
				msg = (String) input.readObject();
				showMessage("\n" + msg);
			}catch(ClassNotFoundException clasNotFoundException){
				showMessage("\n Illegal message type!");
			}
		}while(!msg.equals("SERVER - END"));
	}
	
	private void closeAll(){
		showMessage("\n Closing connections...");
		ableToType(false);
		try{
			output.close();
			input.close();
			connection.close();
		}catch(IOException ioException){
			ioException.printStackTrace();
		}
	}
	
	private void sendMessage(String msg){
		try{
			output.writeObject("CLIENT - " + msg);
			output.flush();
			showMessage("\nCLIENT - " + msg);
		}catch(IOException ioException){
			chatWindow.append("\n an error occured while sending the message.");
		}
	}
	
	private void showMessage(final String m){
		SwingUtilities.invokeLater(
			new Runnable(){
				public void run(){
					chatWindow.append(m);
				}
			}
		);
	}
	
	private void ableToType(final boolean check){
		SwingUtilities.invokeLater(
			new Runnable(){
				public void run(){
					userText.setEditable(check);
				}
			}
		);
	}
	
}
