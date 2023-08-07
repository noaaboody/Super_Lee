package Frontend.MVC.View.Supplier;

import javax.swing.*;

public class ItemView extends JFrame{
	int frameWidth = 800;
	int frameHeight = 600;

	public ItemView() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(null);
		this.setSize(frameWidth,frameHeight);
		this.setResizable(false);
		this.setTitle("Supplier system main menu");

		this.setVisible(true);
	}
}
