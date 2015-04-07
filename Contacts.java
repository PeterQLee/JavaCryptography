import java.util.ArrayList;
public class Contacts {

    //housescontacts for the GUI, makes it simpler for user

    private ArrayList<String> address;
    private ArrayList<String> name;

    public Contacts() {
	address=new ArrayList<String>();
	name=new ArrayList<String>();
    }
    public void addContact(String name, String address) {
	   //adds contacts to arraylist
      this.address.add(address);
      this.name.add(name);  
    }
    public void removeContact(int ind) {
	   //removes contact from indexes
      address.remove(ind);
      name.remove(ind); 
    }
    public ArrayList<String> getContacts() {
   	 //returns arraylist of contacts
   	 //in order of name, then address
   	 //e.g. [Bob,192.168.2.1,Charley,127.0.0.1,Cameron,192.222.3.1..etc]
       ArrayList<String> contacts=new ArrayList<String>();
       int i=0;
       for(;i<name.size();i++){
          
             contacts.add(name.get(i));
          
             contacts.add(address.get(i));
          
         
       }
       return contacts;
    }
    public String getAddress(int ind) {
	if (ind>=0 && ind<address.size()) {
	    return address.get(ind);
	}
	return null;
    }
    public String getName(int ind) {
	if (ind>=0 && ind<name.size()) {
	    return  name.get(ind);
	}
	return null;
    }
    public int indexOfName(String n){
       //return index of a given name
       return name.indexOf(n);    
    }
    public int indexOfAddress(String a){
       //return index of a given address
       return address.indexOf(a);   
    }
    public boolean containsAddress(String address){
    	// If it contains a given address will return true;
    	if (this.address.size() != 0){
	    	for (int i = 0; i < this.address.size(); i++){
	    		if (this.address.get(i).equals(address)){
	    			return true;
	    		} else {
	    			return false;
	    		}
	    	}
	}
    		return false;
    	
    	// will never reach this simply adding it to stop error
		
    }
}
