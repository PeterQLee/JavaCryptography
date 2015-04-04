import javax.swing.*;
import java.util.ArrayList;
import java.math.BigInteger;
import java.security.SecureRandom;
public class Cryptogravisor extends JFrame {
    //the main class of the program
    //handles all the GUI, and calls methods in other classes
    
    private ArrayList<Encryption> encryptlist;
    private Contacts contacts;
    private Communications comm;
    public Cryptogravisor() {
	super ("ayylmao");
	encryptlist=new ArrayList<Encryption>();
	contacts=new Contacts();
	//initialize GUI and stuff
	comm=new Communications(6000,6001,this);
	setVisible(true);
    }
    
    public void actionPerformed() {
	//responds to user input
	//delegates message sends, and user adds

    }
    private void addToContacts(String name, String address) {
    	// Creates the big integer
    	BigInteger bigint = Encryption.calcMod();
    	// adds the user entry to the contacts class
    	contacts.addContact(name, address);
    	encryptlist.add(new Encryption(bigint));
    	// sends the key and mod to given address 
    	comm.sendKeyAndMod(address, encryptlist.get(contacts.indexOfAddress(address)).DiffieHellmanComputeKey(), bigint);
    }
    private void updateContactList() {
	//updates GUI, to the current contents of Contacts class
    }
    private void sendMSG(String address, String message) throws InvalidatedEncryptionException {
	//sends messaged based on the user's selected contact and their message in the textfield
	//encrypts for recipient, and sends to user with communications class
    	// sends the encrypted message to the address
	    comm.send(address, encryptlist.get(encryptlist.indexOf(address)).encryptText(message));
    }
    public void handleMessage(String message, String address) throws InvalidatedEncryptionException {
	//called by server, and will be printed to GUI by this method
	    	// Creates the variables to find the correct encryption address
	    	int index = contacts.indexOfAddress(address);
	    	// When message is received it will be encrypted prints out encrypted message for testing purposes
	    	System.out.print(message);
	    	// Decrypts the message and prints it
	    	// Assumes all messages that are received are encrypted
	    	System.out.print(encryptlist.get(index).decryptText(message));
    }
    public void handleKey(byte[] info, String address) {
	//handles encryption info passed for public key crypt
    	
    	addToContacts("New Friend", address);
    }
	
    public void quit() {
	//ends program, sever connections
    }
    
    public void handleKeyAndMod(byte dat[], byte mod[], String address){
    	// Adds a new friend contact for the user to change the name of
    	addToContacts("New Friend", address);
    	encryptlist.add(new Encryption(mod));
    	// Calculates the key
    	encryptlist.get(contacts.indexOfAddress(address)).calcKey(dat);
    	comm.sendKey(address,encryptlist.get(contacts.indexOfAddress(address)).DiffieHellmanComputeKey());
    }
    
    public static void main(String args[]) {
	new Cryptogravisor();
    }
	
}
							    
