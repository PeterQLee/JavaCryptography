import java.math.BigInteger;
import java.security.SecureRandom;
class InvalidatedEncryptionException extends Exception {
    //called if encryption is called without a key being computed
    public InvalidatedEncryptionException (String message) {
	super(message);
    }
}

public class Encryption {
    //houses all the methods for encrypting messages
    private BigInteger base;
    private BigInteger mod;
    private BigInteger secret;
    private BigInteger key;
    
    

    public Encryption(byte[] mod) {
	//initiates values
	SecureRandom r=new SecureRandom();
	secret=new BigInteger(127,10,r);
	this.mod=new BigInteger(mod);
	this.base=BigInteger.valueOf(7);
	key=BigInteger.valueOf(-1);

    }
    /* public static int getBase() {
	//generates from a list of primes
	int primes[]={2,3,5,7,11,13};
	int sel=(int)(Math.random()*6);
	return primes[sel];
	}*/
    public static byte[] ensureByteSize(byte [] n) {
	//ensures proper 32 bit bytes before returning
	if (n.length==32)return n;
	else {
	    byte g[]=new byte[32];
	    int i;
	    for (i=32-1;i>32-n.length;i--) {
		g[i]=n[i];
	    }
	    for (;i>0;i--) {
		g[i]=0;
	    }
	    return g;
	}
    }
    public static byte[] calcMod() {
	//generate a random modulus
	SecureRandom r=new SecureRandom();
	BigInteger tmp=new BigInteger(255,10,r);
	return  ensureByteSize(tmp.toByteArray());
    }
    public static String cipher(String text,String cipher) {
	//XOR encrypts                                                          
        String ret="";
        int paslen=cipher.length();
        for (int i=0;i<text.length();i++) {
            ret+=text.charAt(i)^cipher.charAt(i%paslen);
        }
        return ret;
    }
    public void changeBase(int b) {
	base=BigInteger.valueOf(b);
    }
    public byte[] DiffieHellmanComputeKey() { //will need bigInteger
	//algorithm taken from http://en.wikipedia.org/wiki/Diffie%E2%80%93Hellman_key_exchange

	//UNTESTD!
	return ensureByteSize(base.modPow(secret,mod).toByteArray());
	//return base^secret %mod
    }
    public void calcKey(byte[] v) {//UNTESTED
	
	//computes key given input
	BigInteger tmp=new BigInteger(v);
	key=tmp.modPow(secret,mod);
	System.out.println("SCRET KEY IS"+key);
    }
    public String encryptText(String input) throws InvalidatedEncryptionException{
	if (key.equals(-1)) {
	    
	    throw new InvalidatedEncryptionException("No calculated key");
	}
	char t[]=input.toCharArray();
	byte btearray[]=key.toByteArray();
	//for each letter, bitwise for each 8bit portion of the key
	for (int i=0;i<t.length-1;i++) {
	    //for each 8 bits
	    //Xor with each 8 bits of key
	    for (int j=0;j<btearray.length;j++) {

		t[i]=(char)((byte)t[i]^btearray[j]);
		//will stay under 0xff
	    }
	    //adds each result to next, just to scramble it some more
	    t[i+1]=(char)((byte)t[i]+(byte)(t[i+1])&0xff);

	    //&0xff is to prevent overflow
	}
	for (int j=0;j<btearray.length;j++) {

	    t[t.length-1]=(char)((byte)t[t.length-1]^btearray[j]);
	}
	return new String(t);
	
    }
    
    public String decryptText(String input) throws InvalidatedEncryptionException{
	//basically, does opposite of encryption algorithm
	if (key.equals(-1)) {
	    throw new InvalidatedEncryptionException("No calculated key");

        }
        char t[]=input.toCharArray();
        byte btearray[]=key.toByteArray();
	String ret="";
	char let=t[0];
	for (int j=0;j<btearray.length;j++) {
	    //order for bitwise shouldn't matter
	    let=(char)((byte)let^btearray[j]);
	}
	ret+=let;
	for (int i=1;i<t.length;i++) {
	    let=t[i];
	    for (int j=0;j<btearray.length;j++) {
	    	let=(char)((byte)let^btearray[j]);	
	    }
	    let=(char)((byte)(let-t[i-1])&0xff); //ensures proper sign
	    ret+=let;
	}
	return ret;
    }
		
    /*    public static void main(String args[]) throws Exception{   
	Encryption enc=new Encryption(5,BigInteger.valueOf(7));
	enc.key=BigInteger.valueOf(23);
	String m="I Am a cow";
	String r=enc.encryptText(m);
	System.out.println(r);
	System.out.println(enc.decryptText(r));
	}*/
	
	
    
    
}