package Frontend.MVC.View.Supplier;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;

public class ContactsView extends JFrame {
	private JButton backButton=new JButton("Back");
	private JButton seeButton=new JButton("see");
	private JButton addButton=new JButton("Add");
	private JButton setButton=new JButton("Set");


	private JTextField contactIdField =new JTextField();
	private JLabel contactIdLabel = new JLabel("Contact id:");

	private final JLabel nameLabel = new JLabel("Name :");

	private JTextField nameField =new JTextField();
	private final JLabel phoneLabel = new JLabel("Phone :");

	private JTextField phoneField =new JTextField();

	private final JLabel mailLabel = new JLabel("Email:");
	private JTextField emailField =new JTextField();

	private JTextField supplierIdField =new JTextField();
	private JLabel supplierLabel = new JLabel("Supplier id:");

	private JPanel TablePanel;
	JTable contactsListTable;
	public ContactsView()
	{
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setSize(580,550);
		setTitle("Contacts of supplier");
		setLayout(null);
		this.setResizable(false);
		TablePanel= new JPanel();
		TablePanel.setLayout(new FlowLayout());
		TablePanel.add(new JScrollPane(contactsListTable), BorderLayout.CENTER); // Add table to TablePanel
		add(TablePanel);
		TablePanel.setBounds(0,80,545,370);
		JPanel topPanel = new JPanel();
		topPanel.setBounds(0,0,575,50);
		topPanel.setLayout(null);
		add(topPanel);
		topPanel.add(contactIdLabel).setBounds(5,0,70,30);
		topPanel.add(contactIdField).setBounds(80,0,70,30);
		topPanel.add(nameLabel).setBounds(160,0,70,30);
		topPanel.add(nameField).setBounds(220,0,70,30);
		topPanel.add(phoneLabel).setBounds(300,0,70,30);
		topPanel.add(phoneField).setBounds(350,0,80,30);
		topPanel.add(mailLabel).setBounds(440,0,60,30);
		topPanel.add(emailField).setBounds(480,0,80,30);

		JPanel beforeTopPanel = new JPanel();
		add(beforeTopPanel);
		beforeTopPanel.setBounds(0,50,575,30);
		beforeTopPanel.setLayout(null);
		beforeTopPanel.add(addButton).setBounds(200,0,70,30);
		beforeTopPanel.add(setButton).setBounds(275,0,70,30);




		JPanel otherPanel= new JPanel();
		add(otherPanel);
		otherPanel.setLayout(null);
		otherPanel.setBounds(0,450,500,40);
		otherPanel.add(backButton).setBounds(0,0,70,30);
		otherPanel.add(supplierLabel).setBounds(90,0,100,30);
		otherPanel.add(supplierIdField).setBounds(160,0,70,30);
		otherPanel.add(seeButton).setBounds(235,0,60,30);

	}

	public void addBackButtonListener(ActionListener listener) {
		backButton.addActionListener(listener);
	}
	public void addSeeButtonButtonListener(ActionListener listener) {
		seeButton.addActionListener(listener);
	}
	public void addAddButtonListener(ActionListener listener) {
		addButton.addActionListener(listener);
	}
	public void addSetButtonListener(ActionListener listener) {
		setButton.addActionListener(listener);
	}


	public String getSupplierId() {return  supplierIdField.getText();}
	public  void setContactsListTable(DefaultTableModel table) {
		if ( contactsListTable == null) {
			contactsListTable = new JTable();
			contactsListTable.setEnabled(false);
			contactsListTable.setAutoCreateRowSorter(true);
			TablePanel.add(new JScrollPane(contactsListTable), BorderLayout.CENTER);
			contactsListTable.setModel(table);
			revalidate();
		} else {
			contactsListTable.setModel(table); // Update the table model
		}
	}
	public String getContactID() {return  contactIdField.getText();}
	public String getContactName() {return  nameField.getText();}
	public String getContactPhone() {return  phoneField.getText();}
	public String getContactEmail() {return  emailField.getText();}







}
