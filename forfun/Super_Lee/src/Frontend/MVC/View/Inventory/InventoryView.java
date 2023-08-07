package Frontend.MVC.View.Inventory;

import Frontend.MVC.Controller.Inventory.InventoryFrontController;
import Frontend.MVC.Model.FactoryModel;

import javax.swing.*;
import java.awt.*;

public class InventoryView extends JFrame {

    private InventoryFrontController controller;
    private FactoryModel factoryModel;
    public JButton backButton;
    public JButton AddNewInventory;
    public JButton ExistingInventory;

    public InventoryView(String worker) {
        factoryModel = new FactoryModel();
        controller = new InventoryFrontController(factoryModel, this, worker);

        int frameWidth = 800;
        int frameHeight = 600;

        this.setTitle("Inventory Actions Window");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setSize(frameWidth, frameHeight);
        this.setResizable(false);
        this.getContentPane().setBackground(new Color(138, 171, 215));
        this.setLocationRelativeTo(null);

        AddNewInventory = new JButton("Add new inventory");
        AddNewInventory.addActionListener(controller);

        ExistingInventory = new JButton("Actions on existing inventory");
        ExistingInventory.addActionListener(controller);

        JPanel panel = new JPanel();
        panel.setBounds((frameWidth - 600) / 2, 300, 600, 100);
        panel.setLayout(new GridLayout(1, 2, 25, 20));
        panel.setBackground(new Color(138, 171, 215));
        panel.add(AddNewInventory);
        panel.add(ExistingInventory);

        backButton = new JButton("<<");
        backButton.setBounds(10, 420, 50, 30);
        backButton.addActionListener(controller);

        this.add(panel);
        this.add(backButton);

        this.setVisible(true);
    }
}
