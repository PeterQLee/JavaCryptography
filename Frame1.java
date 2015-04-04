import java.util.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.BorderLayout;
import java.awt.FlowLayout;

public class Frame1 extends JFrame implements ActionListener {	
	private JPanel panelSouth, panelWest, panelEast, panelCenter, panelNorth;
	private JButton send, addContact, removeContact, decrypt;
	private JTextField input;
   private JTextArea convo;
   private JLabel message;
   private JList contacts;
	ArrayList<String> contactList = new ArrayList<String>();
   
	public Frame1() {
		
		panelSouth = new JPanel();
		panelEast = new JPanel();
		panelWest = new JPanel();
		panelCenter = new JPanel();
		panelNorth = new JPanel();
      contactList.add("Peter");
      contactList.add("Reid");
      contactList.add("Jonny");
      contactList.add("Ryan");
      
		//create south panel
		panelSouth.setLayout(new FlowLayout());
		message = new JLabel("Enter a message:");
      panelSouth.add(message);
      input = new JTextField(20);
		panelSouth.add(input);
		send = new JButton("Send");
		panelSouth.add(send);
      send.addActionListener(this);
		decrypt = new JButton ("Decrypt Message");
		panelSouth.add(decrypt);
		
		//create center panel
		panelCenter.setLayout(new FlowLayout());
		JTextArea convo = new JTextArea("New Convo\n", 30, 35);
      convo.setLineWrap(true);
      convo.setWrapStyleWord(true);
		panelCenter.add(convo);
		
		//create west panel
		panelWest.setLayout(new BorderLayout());
		addContact = new JButton ("Add Contact");
		panelWest.add(addContact, BorderLayout.NORTH);
      addContact.addActionListener(this);
      
      contacts = new JList(contactList.toArray());
      //contacts.addActionListener(this);
      panelWest.add(contacts, BorderLayout.CENTER);
		
      removeContact = new JButton ("Remove Contact");
		panelWest.add(removeContact, BorderLayout.SOUTH);

		add(panelSouth, BorderLayout.SOUTH);
		add(panelCenter, BorderLayout.CENTER);
		add(panelWest, BorderLayout.WEST);
		setTitle("Messenger");
		setSize(655,500);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
      setResizable(false);
		
	}
   
   public void actionPerformed(ActionEvent e) {
      
      if(e.getSource()==send){
         String newMessage = input.getText();
         convo.append(newMessage);
         input.setText("");
	   }
      
      else if(e.getSource()==addContact){
         //prompts user to input a new name and IP
         String name = JOptionPane.showInputDialog(null, "Enter a name:");
         String IP = JOptionPane.showInputDialog(null, "Enter the IP:");
         contactList.add(name);
	   }
      
      else if(e.getSource()==decrypt){
      }
      
      else if(e.getSource()==removeContact){
      }
   }
	
	public static void main(String[] args) {
		Frame1 fr = new Frame1();
	}
}



