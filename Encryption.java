
public class Encryption {
    //houses all the methods for encrypting messages
    public static String cipher(text,cipher) {
	//XOR encrypts                                                          
        String ret;
        int paslen=passw.length();
        for (int i=0;i<text.length();i++) {
            ret+=text.charAt(i)^passw.charAt(i%paslen);
        }
        return ret;
    }
    
}