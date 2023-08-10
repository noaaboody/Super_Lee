package Frontend.MVC.View.Inventory;

import Frontend.MVC.Controller.Inventory.AddInventoryController;
import Frontend.MVC.Model.FactoryModel;

import javax.swing.*;
import java.awt.*;

public class AddInventoryView extends JFrame {
    public JLabel addressLabel;
    public JTextField addressField;
    public JButton addButton;
    private AddInventoryController controller;
    public JButton backButton;

    public AddInventoryView(FactoryModel factoryModel, String worker){
        controller = new AddInventoryController(factoryModel, this, worker);

        int frameWidth = 800;
        int frameHeight = 600;

        addressLabel = new JLabel();
        addressLabel.setBounds((frameWidth - 400)/2, 100, 200, 20);
        addressLabel.setText("address:");
        addressLabel.setFont(new Font("Segoe UI Black", Font.PLAIN, 14));
        addressLabel.setForeground(new Color(20, 40, 120));

        addressField = new JTextField();
        addressField.setBounds((frameWidth - 250)/2, 150, 250, 20);
        addressField.addActionListener(controller);

        addButton = new JButton("OK");
        addButton.setBounds((frameWidth - 70)/2, 300, 70, 20);
        addButton.addActionListener(controller);

        backButton = new JButton("<<");
        backButton.setBounds(10, 420, 50, 30);
        backButton.addActionListener(controller);

        this.setTitle("Add new Inventory");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setSize(frameWidth, frameHeight);
        this.setResizable(false);
        this.getContentPane().setBackground(new Color(138, 171, 215));
        this.setLocationRelativeTo(null);

        this.add(addressLabel);
        this.add(addressField);
        this.add(addButton);
        this.add(backButton);
        this.setVisible(true);
    }

    public void finish(String output, String worker){
        this.dispose();
        new PopUpView(output, worker);
    }
}
