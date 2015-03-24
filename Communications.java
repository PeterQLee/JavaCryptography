
public class Communications {
    //houses all the methods for createing a server, and sending messages to others
    private int communport;
    private int secretport;
    Cryptogravisor crypt;
    public Communications(int commport,int keyport, Cryptogravisor c) {
	//initializes variables, calls server methods
    }
    private void messageServer() {
	//initializes server on ports
	
    }
    private void keyServer() {
	//initializes keyserver on port
    }
    private void recieve(int port) {
	//recieves messages form port, is only called by the server method
    }
    public void send(String address, String message) {
	//send message to specified ip
    }
    public void sendKey(Object key) {
	//send encryption key via seperate port for cryptography purposes
    }
    public void kill() {
	//kill server
    }
}