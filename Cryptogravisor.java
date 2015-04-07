import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.math.BigInteger;
import java.security.SecureRandom;
public class Cryptogravisor extends JFrame implements ActionListener{
    //the main class of the program
    //handles all the GUI, and calls methods in other classes
    private JPanel panelSouth, panelWest, panelEast, panelCenter, panelNorth;
    private JButton send, addContact, removeContact;//, decrypt;
    private JTextField input;
    private JTextArea convo;
    private JLabel labelmessage;
    private JList JLcontacts;
    private ArrayList<Encryption> encryptlist;
    private Contacts contacts;
    private Communications comm;
    private DefaultListModel tmplist;
    public Cryptogravisor() {
	//initialize lists

	encryptlist=new ArrayList<Encryption>();
	contacts=new Contacts();
	//initialize GUI and stuff
	comm=new Communications(6000,6001,this);
	setVisible(true);
	//initialize GUI
	panelSouth = new JPanel();
	panelEast = new JPanel();
	panelWest = new JPanel();
	panelCenter = new JPanel();
	panelNorth = new JPanel();


    	//create south panel
	panelSouth.setLayout(new FlowLayout());
	labelmessage = new JLabel("Enter a message:");
	panelSouth.add(labelmessage);
	input = new JTextField(20);
	panelSouth.add(input);
	send = new JButton("Send");
	panelSouth.add(send);
	send.addActionListener(this);
	//decrypt = new JButton ("Decrypt Message");
	//panelSouth.add(decrypt);
	
	//create center panel
	panelCenter.setLayout(new FlowLayout());
	convo = new JTextArea("New Convo\n", 30, 35);
	convo.setLineWrap(true);
	convo.setWrapStyleWord(true);
	panelCenter.add(convo);
	//create west panel
	panelWest.setLayout(new BorderLayout());
	addContact = new JButton ("Add Contact");
	panelWest.add(addContact, BorderLayout.NORTH);
	addContact.addActionListener(this);
	
	tmplist=new DefaultListModel();
	JLcontacts = new JList(tmplist);
	JLcontacts.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
	//contacts.addActionListener(this);
	panelWest.add(JLcontacts, BorderLayout.CENTER);
		
	removeContact = new JButton ("Remove Contact");
	removeContact.addActionListener(this);
	
	panelWest.add(removeContact, BorderLayout.SOUTH);

	add(panelSouth, BorderLayout.SOUTH);
	add(panelCenter, BorderLayout.CENTER);
	add(panelWest, BorderLayout.WEST);
	setTitle("Cryptography Messenger");
	setSize(655,500);
	setLocationRelativeTo(null);
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	setVisible(true);
	setResizable(false);
    }
    
    public void actionPerformed(ActionEvent e) {
	//responds to user input
	//delegates message sends, and user adds
	if(e.getSource()==send){
	   String newMessage = input.getText();
	   try {
	       sendMSG(contacts.getAddress(JLcontacts.getSelectedIndex()),newMessage);
	   }
	   catch (Exception ex) {
	       System.out.println("No key existing yet!");
	   }
	   
	   input.setText("");
       }
       
       else if(e.getSource()==addContact){
	   //prompts user to input a new name and IP
	   String name = JOptionPane.showInputDialog(null, "Enter a name:");
	   String IP = JOptionPane.showInputDialog(null, "Enter the IP:");
	   addToContacts(name,IP);
       }
       
       
       else if(e.getSource()==removeContact){
    	   int ind = JLcontacts.getSelectedIndex();
    	   try {
    	   tmplist.remove(ind);
    	   contacts.removeContact(ind);
    	   encryptlist.remove(ind);
    	   JLcontacts.remove(ind);
    	   } catch (Exception e1){
    		   e1.printStackTrace();
    	   }
       }

    }
    private void addToContacts(String n, String address) {
    	//checks if IP already in use
	
	if (!contacts.containsAddress(address)) {
	    byte[] bigint = Encryption.calcMod();
	    
	    // adds the user entry to the contacts class
	    contacts.addContact(n, address);
	    encryptlist.add(new Encryption(bigint));
	    // sends the key and mod to given address 
	    int ind=contacts.indexOfAddress(address);
	    try {
		System.out.println(bigint.length+" : "+encryptlist.get(ind).DiffieHellmanComputeKey().length);
		comm.sendKeyAndMod(address, encryptlist.get(ind).DiffieHellmanComputeKey(), bigint);
		tmplist.addElement(n+"@"+address); 
	    }
	    catch (Exception e) {
		System.out.println("ERROR");
		e.printStackTrace();
		//something went wrong connecting, remove user from list
		encryptlist.remove(ind);
		contacts.removeContact(ind);
	    }
	}
    }

    private void sendMSG(String address, String message) throws InvalidatedEncryptionException {
	//sends messaged based on the user's selected contact and their message in the textfield
	//encrypts for recipient, and sends to user with communications class
    	// sends the encrypted message to the address
    		// Added a tiny thing so it won't send blank messages
    if (message.length() != 0){
	convo.append("You:"+message+"\n");
		if (contacts.containsAddress(address)) {
		    String ret=encryptlist.get(contacts.indexOfAddress(address)).encryptText(message);
		    comm.send(address, ret);
		    convo.append("You:"+ret+"\n");
			}   
	    }
    }
    public void handleMessage(String message, String address) throws InvalidatedEncryptionException {
	
	if (!contacts.containsAddress(address)) {
	    convo.append("UNKNOWN COMMUNICATOR: "+message);
	}


	//called by server, and will be printed to GUI by this method
	// Creates the variables to find the correct encryption address
	else {
	    int index = contacts.indexOfAddress(address);
	    // When message is received it will be encrypted prints out encrypted message for testing purposes
	    
	// Decrypts the message and prints it
	// Assumes all messages that are received are encrypted
	    convo.append(contacts.getName(index)+" : "+message+"\n");
	    convo.append(contacts.getName(index)+" : "+encryptlist.get(index).decryptText(message)+"\n");
	}
    }
    public void handleKey(byte[] info, String address) {
	//handles encryption info passed for public key crypt
    	System.out.println("KEY INBOUND");
	if (contacts.containsAddress(address)) {
	    System.out.println("Key recieved");
	    int ind=contacts.indexOfAddress(address);
	    encryptlist.get(ind).calcKey(info);
	}
    }
	
    public void quit() {
	//ends program, sever connections
    }
    
    public void handleKeyAndMod(byte dat[], byte mod[], String address){
    	// Adds a new friend contact for the user to change the name of
	System.out.println("rec mod"+address);
    	contacts.addContact("New Friend", address);
    	encryptlist.add(new Encryption(mod));
    	// Calculates the key
	int ind=contacts.indexOfAddress(address);
    	encryptlist.get(ind).calcKey(dat);
	try {
	    comm.sendKey(address,encryptlist.get(ind).DiffieHellmanComputeKey());
	    tmplist.addElement("New Friend@"+address); 
	}
	catch (Exception e) {
	    System.out.println("ERROR");
	    e.printStackTrace();
	    //something went wrong connecting, remove user from list
	    encryptlist.remove(ind);
	    contacts.removeContact(ind);
	}
	
    }
    
    public static void main(String args[]) {
	new Cryptogravisor();
    }
	
}
							    
