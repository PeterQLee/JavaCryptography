import java.net.*;
import java.io.*;
public class Communications implements Runnable{
    //houses all the methods for createing a server, and sending messages to others
    private int communport;
    private int secretport;
    private Cryptogravisor crypt;

    private Thread mservthread;
    private Thread kservthread;
    //    private SocketServer msgServer;
    //private SocketServer keyServer;
    private boolean stopflag;
    public Communications(int commport,int keyport, Cryptogravisor c) {
	stopflag=false;
	//initializes variables, calls server methodse
	crypt=c;
	communport=commport;
	secretport=keyport;
	mservthread=new Thread(this);
	mservthread.start();
	kservthread=new Thread(this);
	kservthread.start();
	System.out.println("ayylmao");
	
    }
    private void messageServer() { //fix
	//THROW EXCEPTIONS
	//initializes server on ports
	System.out.println("messtart");
	try {

	   ServerSocket msgServer=new ServerSocket(communport);
	    
	    while (!stopflag) {
		
		Socket cn= msgServer.accept();
		//PrintWriter out = new PrintWriter(kkSocket.getOutputStream(), true);
		InputStream istream= cn.getInputStream();
		DataInputStream dat=new DataInputStream(istream);
		char bu;
		String message="";
		try { //keep going until EOF
		    while (true) {
			bu=dat.readChar();
			System.out.println(bu);
			message+=bu;
		    }
		}
		catch (EOFException e) {
		    System.out.println("end of file");//temp
		}
		//call method in crypt
		System.out.println("Fe"+message);
		crypt.handleMessage(message,cn.getInetAddress().toString());
		//close streams
		dat.close();istream.close();cn.close();
	    
	    	    
	    }
	    //cleanup
	    msgServer.close();
        }
	catch (Exception e) {
	    e.printStackTrace();
	}
	
	
	    
	    
    }
    
    private void keyServer() { 
	//initializes keyserver on port
	System.out.println("keyserver started");
	try {
	    ServerSocket keyServer=new ServerSocket(secretport);
	    
	    while (!stopflag) {
		Socket cn= keyServer.accept();
	    //PrintWriter out = new PrintWriter(kkSocket.getOutputStream(), true);
		InputStream istream= cn.getInputStream();
		DataInputStream dat=new DataInputStream(istream);
		byte buf[]=new byte[13]; //arb value, fix if not
		String message="";
		try { //keep going until EOF
		    dat.readFully(buf);
		}
		catch (EOFException e) {
		    System.out.println("end of file");//temp
		
		}
		//call method in crypt
		crypt.handleKey(buf,cn.getInetAddress().toString()); //safety??
		//close streams
		dat.close();istream.close();cn.close();
		
	    }
	    keyServer.close();
	}catch (Exception e) {e.printStackTrace();}
	
    }
  
    public void send(String address, String message) {
	//send message to specified ip
	try {
	    Socket s = new Socket(address,communport);
	    DataOutputStream out=new DataOutputStream(s.getOutputStream());
	    out.writeChars(message);
	    s.close();
	}
	catch(Exception e) {e.printStackTrace();}
    }
    public void sendKey(String address,byte[] key) {
	//send encryption key via seperate port for cryptography purposes
	try {

	    Socket s = new Socket(address,secretport);
	    DataOutputStream out=new DataOutputStream(s.getOutputStream());
	    out.write(key,0,key.length);
	    s.close();
	}
	catch(Exception e) {e.printStackTrace();}

    }
    public void kill() {
	//kill server		
	
	stopflag=true;
    }
    public void run() {
	//initalizes servers on threads
	if (Thread.currentThread()==mservthread) {//may not work
	    messageServer();
	}
	else if (Thread.currentThread()==kservthread) {
	    keyServer();
	}
    }
    //    public static void main(String args[] ){
	//new Communications(6000,6001,new Cryptogravisor());
    //    }
	
}