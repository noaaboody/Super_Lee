package Frontend.MVC.View.Supplier;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class SupItemView extends JFrame {
	int frameWidth = 800;
	int frameHeight = 600;

	private JButton addItemButton;
	private JButton setItemButton;
	private JButton getDetailsButton;
	private JButton backButton;

	JTextField idTextField ;
	JTextField nameTextField ;
	JTextField manufacturerTextField ;

	public SupItemView() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(null);
		this.setSize(frameWidth, frameHeight);
		this.setResizable(false);
		this.setTitle("Supplier item menu");
		this.getContentPane().setBackground(new Color(138, 171, 215));
		this.setLocationRelativeTo(null);

		JLabel headline  = new JLabel("General item menu");
		headline.setHorizontalAlignment(JLabel.CENTER);
		headline.setVerticalAlignment(JLabel.TOP);
		headline.setBounds((frameWidth - 600)/2, 100 , 600, 50);
		headline.setFont(new Font("Segoe UI Black",Font.PLAIN,20));
		headline.setForeground(new Color(20,40,120));
		this.add(headline);

		JPanel textPanel = new JPanel();
		textPanel.setBackground(new Color(138, 171, 215));
		textPanel.setLayout(new GridLayout(2, 3, 10, 5));
		textPanel.setBounds(200, 200, 400, 65);

		JLabel idLabel = new JLabel("ID:");
		idLabel.setHorizontalAlignment(SwingConstants.CENTER);
		idLabel.setVerticalAlignment(SwingConstants.BOTTOM);
		textPanel.add(idLabel);

		JLabel nameLabel = new JLabel("Name:");
		nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		nameLabel.setVerticalAlignment(SwingConstants.BOTTOM);
		textPanel.add(nameLabel);

		JLabel manufacturerLabel = new JLabel("Manufacturer:");
		manufacturerLabel.setHorizontalAlignment(SwingConstants.CENTER);
		manufacturerLabel.setVerticalAlignment(SwingConstants.BOTTOM);
		textPanel.add(manufacturerLabel);

		this.idTextField = new JTextField();
		textPanel.add(idTextField);

		this.nameTextField = new JTextField();
		textPanel.add(nameTextField);

		this.manufacturerTextField = new JTextField();
		textPanel.add(manufacturerTextField);

		JPanel buttonPanel = new JPanel();
		buttonPanel.setBackground(new Color(138, 171, 215));
		buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
		buttonPanel.setBounds(0, 450, frameWidth, 100);

		this.backButton = new JButton("Back");
		buttonPanel.add(backButton);

		this.addItemButton = new JButton("Add Item");
		buttonPanel.add(addItemButton);

		this.setItemButton = new JButton("Set Item");
		buttonPanel.add(setItemButton);

		this.getDetailsButton = new JButton("Get Details");
		buttonPanel.add(getDetailsButton);


		this.getContentPane().add(textPanel);
		this.getContentPane().add(buttonPanel);


		this.setVisible(true);
	}
	public void addAddItemBListener(ActionListener a){
		addItemButton.addActionListener(a);
	}
	public void addSetItemBListener(ActionListener a){
		setItemButton.addActionListener(a);
	}
	public void addGetItemBListener(ActionListener a){
		getDetailsButton.addActionListener(a);
	}
	public void addBackBListener(ActionListener a){
		backButton.addActionListener(a);
	}

	public void setTextFields(int id, String name, String manufacturer){
		idTextField.setText(""+id);
		nameTextField.setText(name);
		manufacturerTextField.setText(manufacturer);
	}

	public String getIdText(){
		return idTextField.getText();
	}
	public String getNameText(){
		return nameTextField.getText();
	}

	public String getManufacturerText(){
		return manufacturerTextField.getText();
	}
}



