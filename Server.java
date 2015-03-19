import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Server extends JFrame{
	
	private JTextField userText;
	private JTextArea chatWindow;
	private ObjectOutputStream output;
	private ObjectInputStream input;
	private ServerSocket server;
	private Socket connection;
	
	public Server(){
		super("My Instant Messanger");
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
		add(new JScrollPane(chatWindow));
		setSize(300, 150);
		setVisible(true);
	}
	
	public void runServer(){
		try{
			server = new ServerSocket(6789, 100);
			while(true){
				try{
					waitForConnection();
					setupStreams();
					chatting();
				}catch(EOFException eofException){
					showMessage("\n Server ended the connection!");
				}finally{
					closeAll();
				}
			}
		}catch(IOException ioException){
			ioException.printStackTrace();
		}
	}
	
	private void waitForConnection() throws IOException{
		showMessage(" Waiting for someone to connect...\n");
		connection = server.accept();
		showMessage(" Now connected to " + connection.getInetAddress().getHostName());
	}
	
	private void setupStreams() throws IOException{
		output = new ObjectOutputStream(connection.getOutputStream());
		output.flush();
		input = new ObjectInputStream(connection.getInputStream());
		showMessage("\n Streams are now setup! \n");
	}
	
	private void chatting() throws IOException{
		String msg = " You are now connected! ";
		sendMessage(msg);
		ableToType(true);
		do{
			try{
				msg = (String) input.readObject();
				showMessage("\n" + msg);
			}catch(ClassNotFoundException classNotFoundException){
				showMessage("\n User tried to send an illegal message! ");
			}
		}while(!msg.equals("CLIENT - END"));
	}
	
	private void closeAll(){
		showMessage("\n Closing connections... \n");
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
			output.writeObject("SERVER - " + msg);
			output.flush();
			showMessage("\nSERVER - " + msg);
		}catch(IOException ioException){
			chatWindow.append("\n ***ERROR: CANNOT SEND MESSAGE!***");
		}
	}
	
	private void showMessage(final String text){
		SwingUtilities.invokeLater(
			new Runnable(){
				public void run(){
					chatWindow.append(text);
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
