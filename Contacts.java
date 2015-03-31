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
       int i=0,j=0;
       while(i<name.size()||j<address.size()){
          if(i<name.size()){
             contacts.add(name.get(i));
             i++;
          }
          if(i<address.size()){
             contacts.add(address.get(j));
             j++;        
          }
       }
       return contacts;
    }
    public int indexOfName(String n){
       //return index of a given name
       return name.indexOf(n);    
    }
    public int indexOfAddress(String a){
       //return index of a given name
       return address.indexOf(a);   
    }    
}