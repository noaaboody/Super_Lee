package Frontend.MVC.View.Inventory;

import Frontend.MVC.Controller.Inventory.EnterInventoryIdController;
import Frontend.MVC.Model.FactoryModel;

import javax.swing.*;
import java.awt.*;

public class EnterInventoryIdView extends JFrame{
    public JLabel EnterID;
    public JTextField InventoryID;
    public JButton OK;
    public JButton backButton;
    private EnterInventoryIdController controller;

    public EnterInventoryIdView(FactoryModel factoryModel, String worker) {
        controller = new EnterInventoryIdController(factoryModel, this, worker);

        int frameWidth = 500;
        int frameHeight = 500;

        EnterID = new JLabel();
        EnterID.setBounds((frameWidth - 400)/2, 100, 200, 20);
        EnterID.setText("inventory ID:");
        EnterID.setFont(new Font("Segoe UI Black", Font.PLAIN, 14));
        EnterID.setForeground(new Color(20, 40, 120));

        InventoryID = new JTextField();
        InventoryID.setBounds((frameWidth - 250)/2, 150, 250, 20);
        InventoryID.addActionListener(controller);

        OK = new JButton("OK");
        OK.setBounds((frameWidth - 70)/2, 300, 70, 20);
        OK.addActionListener(controller);

        backButton = new JButton("<<");
        backButton.setBounds(10, 420, 50, 30);
        backButton.addActionListener(controller);

        this.setTitle("Inventory ID entering");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setSize(frameWidth, frameHeight);
        this.setResizable(false);
        this.getContentPane().setBackground(new Color(138, 171, 215));
        this.setLocationRelativeTo(null);

        this.add(EnterID);
        this.add(InventoryID);
        this.add(OK);
        this.add(backButton);
        this.setVisible(true);
    }
}
