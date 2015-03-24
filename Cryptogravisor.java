public class Cryptogravisor extends JFrame {
    //the main class of the program
    //handles all the GUI, and calls methods in other classes
    
    private ArrayList<Encryption> encryptlist;
    private Cryptogravisor() {
	//initialize GUI and stuff
    }
    
    private void actionPerformed() {
	//responds to user input
	//delegates message sends, and user adds

    }
    private void addToContacts(String name, String address) {
	//adds the user entry to the contacts class
    }
    private void updateContactList() {
	//updates GUI, to the current contents of Contacts class
    }
    private void sendMSG() {
	//sends messaged based on the user's selected contact and their message in the textfield
    }
    public void handleMessage(String message, String address) {
	
	//called by server, and will be printed to GUI by this method
    }
    public void handleKey(Object info, String address) {
	//handles encryption info passed for public key crypt
    }
	
    public void quit() {
	//ends program, sever connections
    }
    public static void main(String args[]) {
	new Cryptogravisor();

    }
	
}
							    