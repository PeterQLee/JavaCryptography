
public class Cipher {

    public static String cipherText(String text, String passw) {
	//XOR encrypts 
	String ret;
	int paslen=passw.length();
	for (int i=0;i<text.length();i++) {
	    ret+=text.charAt(i)^passw.charAt(i%paslen);
	}
	return ret;
    }
}