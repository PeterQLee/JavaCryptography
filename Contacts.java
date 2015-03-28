import java.util.ArrayList;
public class Contacts {
    //housescontacts for the GUI, makes it simpler for user

    private ArrayList<String> address;
    private ArrayList<String> name;

    public Contacts() {
	
    }
    public void addContact(String name, String address) {
	//adds contacts to arraylist
    }
    public void removeContact(int ind) {
	//removes contact from indexes
    }
    public ArrayList<String> getContacts() {
	//returns arraylist of contacts
	//in order of name, then address
	//e.g. [Bob,192.168.2.1,Charley,127.0.0.1,Cameron,192.222.3.1..etc]
	return null;
    }
}
