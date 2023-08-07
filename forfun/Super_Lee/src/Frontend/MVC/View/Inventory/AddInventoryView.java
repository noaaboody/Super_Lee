package Frontend.MVC.View.Inventory;

import Frontend.MVC.Controller.Inventory.AddInventoryController;
import Frontend.MVC.Model.FactoryModel;

import javax.swing.*;

public class AddInventoryView extends JFrame {
    public JLabel addressLabel;
    public JTextField addressField;
    public JButton addButton;
    private AddInventoryController controller;
    public JButton backButton;

    public AddInventoryView(FactoryModel factoryModel, String worker){
        controller = new AddInventoryController(factoryModel, this, worker);

        addressLabel = new JLabel();
        addressLabel.setBounds(50,30,200,20);
        addressLabel.setText("address:");

        addressField = new JTextField();
        addressField.setBounds(150,30,250,20);
        addressField.addActionListener(controller);

        addButton = new JButton();
        addButton.setBounds(215,400,70,20);
        addButton.addActionListener(controller);
        addButton.setText("OK");

        backButton = new JButton();
        backButton.setBounds(10,420,50,30);
        backButton.addActionListener(controller);
        backButton.setText("<<");

        this.setTitle("Add new Inventory");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setSize(500, 500);
        this.setVisible(true);
        this.add(addressLabel);
        this.add(addressField);
        this.add(addButton);
        this.add(backButton);
    }

    public void finish(String output, String worker){
        this.dispose();
        new PopUpView(output, worker);
    }
}
