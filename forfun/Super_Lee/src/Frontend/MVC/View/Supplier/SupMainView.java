package Frontend.MVC.View.Supplier;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class SupMainView extends JFrame{

	JButton contactB;
	JButton itemB ;
	JButton supplierB ;
	JButton backB;
	public SupMainView (){

		int frameWidth = 800;
		int frameHeight = 600;

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(null);
		this.setSize(frameWidth,frameHeight);
		this.setResizable(false);
		this.setTitle("Supplier system main menu");
		this.getContentPane().setBackground(new Color(138, 171, 215));
		this.setLocationRelativeTo(null);

		JLabel title = new JLabel("Supplier system main menu");
		title.setHorizontalAlignment(JLabel.CENTER);
		title.setVerticalAlignment(JLabel.TOP);
		title.setBounds((frameWidth - 600)/2, 100 , 600, 50);
		title.setFont(new Font("Segoe UI Black",Font.PLAIN,20));
		title.setForeground(new Color(20,40,120));

		contactB = new JButton("Contact Button");// add/set contact info
		itemB = new JButton("Item Button");// add/set item to system/supplier
		supplierB = new JButton("Supplier Button");// add/set supplierAgreement, add/set condition
		backB = new JButton("Back");


		JPanel panel = new JPanel();
		panel.setBounds(100, 300, 600, 100);
		panel.setLayout(new GridLayout(2, 3, 20, 20));
		panel.setBackground(new Color(138, 171, 215));
		panel.add(contactB);
		panel.add(itemB);
		panel.add(supplierB);
		panel.add(backB);


		this.add(title);
		this.add(panel);
		this.setVisible(true);
	}

	public void addContactBListener(ActionListener a){
		contactB.addActionListener(a);
	}

	public void addItemBListener(ActionListener a){
		itemB.addActionListener(a);
	}

	public void addSupplierBListener(ActionListener a){
		supplierB.addActionListener(a);
	}
	public void addBackBListener(ActionListener a) {backB.addActionListener(a);}

	public void enableBack() {backB.setEnabled(false);}
}
