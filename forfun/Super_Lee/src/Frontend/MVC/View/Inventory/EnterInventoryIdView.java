package Frontend.MVC.View.Inventory;

import Frontend.MVC.Controller.Inventory.EnterInventoryIdController;
import Frontend.MVC.Model.FactoryModel;

import javax.swing.*;

public class EnterInventoryIdView extends JFrame{
    public JLabel EnterID;
    public JTextField InventoryID;
    public JButton OK;
    public JButton backButton;
    private EnterInventoryIdController controller;

    public EnterInventoryIdView(FactoryModel factoryModel, String worker) {
        controller = new EnterInventoryIdController(factoryModel, this, worker);

        EnterID = new JLabel();
        EnterID.setBounds(50,30,200,20);
        EnterID.setText("inventory ID:");

        InventoryID = new JTextField();
        InventoryID.setBounds(150,30,250,20);
        InventoryID.addActionListener(controller);

        OK = new JButton();
        OK.setBounds(215,400,70,20);
        OK.addActionListener(controller);
        OK.setText("OK");

        backButton = new JButton();
        backButton.setBounds(10,420,50,30);
        backButton.addActionListener(controller);
        backButton.setText("<<");

        this.setTitle("Inventory ID entering");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setSize(500, 500);
        this.setVisible(true);
        this.add(EnterID);
        this.add(InventoryID);
        this.add(OK);
        this.add(backButton);
    }
}
