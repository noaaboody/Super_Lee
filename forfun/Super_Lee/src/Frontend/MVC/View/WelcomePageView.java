package Frontend.MVC.View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class WelcomePageView extends JFrame {

	JButton supplierB;
	JButton inventoryB ;

	public WelcomePageView (){

		int frameWidth = 800;
		int frameHeight = 600;

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(null);
		this.setSize(frameWidth,frameHeight);
		this.setResizable(false);
		this.setTitle("Super Lee");
		this.getContentPane().setBackground(new Color(138, 171, 215));
		this.setLocationRelativeTo(null);

		JLabel title = new JLabel("Welcome to Super Lee System");
		title.setHorizontalAlignment(JLabel.CENTER);
		title.setVerticalAlignment(JLabel.TOP);
		title.setBounds((frameWidth - 600)/2, 100 , 600, 50);
		title.setFont(new Font("Segoe UI Black",Font.PLAIN,20));
		title.setForeground(new Color(20,40,120));

		inventoryB = new JButton("Inventory");
		supplierB = new JButton("Suppliers");


		JPanel panel = new JPanel();
		panel.setBounds(100, 300, 600, 100);
		panel.setLayout(new GridLayout(1, 2, 25, 20));
		panel.setBackground(new Color(138, 171, 215));
		panel.add(inventoryB);
		panel.add(supplierB);


		this.add(title);
		this.add(panel);
		this.setVisible(true);
	}

	public void addInventoryBListener(ActionListener a){
		inventoryB.addActionListener(a);
	}

	public void addSupplierBListener(ActionListener a){
		supplierB.addActionListener(a);
	}
}
