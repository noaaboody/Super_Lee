package Frontend.MVC.View.Inventory;

import Frontend.MVC.Controller.Inventory.ExistingInventoryController;
import Frontend.MVC.Model.FactoryModel;

import javax.swing.*;
import java.awt.*;

public class ExistingInventoryView extends JFrame {
    private ExistingInventoryController controller;
    public JButton ItemActionsButton;
    public JButton CategoryActionsButton;
    public JButton ReportActionsButton;
    public JButton OrderActionsButton;
    public JButton backButton;

    public ExistingInventoryView(FactoryModel factoryModel, int invID, String worker) {
        controller = new ExistingInventoryController(new FactoryModel(), this, invID, worker);

        int frameWidth = 500;
        int frameHeight = 500;

        this.setTitle("Existing Inventory Actions Window");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setSize(frameWidth, frameHeight);
        this.setResizable(false);
        this.getContentPane().setBackground(new Color(138, 171, 215));
        this.setLocationRelativeTo(null);

        ItemActionsButton = new JButton("Item Actions");
        ItemActionsButton.setBounds((frameWidth - 250) / 2, 100, 250, 50);
        ItemActionsButton.addActionListener(controller);

        CategoryActionsButton = new JButton("Category Actions");
        CategoryActionsButton.setBounds((frameWidth - 250) / 2, 170, 250, 50);
        CategoryActionsButton.addActionListener(controller);

        ReportActionsButton = new JButton("Report Actions");
        ReportActionsButton.setBounds((frameWidth - 250) / 2, 240, 250, 50);
        ReportActionsButton.addActionListener(controller);

        OrderActionsButton = new JButton("Orders Actions");
        OrderActionsButton.setBounds((frameWidth - 250) / 2, 310, 250, 50);
        OrderActionsButton.addActionListener(controller);

        backButton = new JButton("<<");
        backButton.setBounds(10, 420, 50, 30);
        backButton.addActionListener(controller);

        this.add(ItemActionsButton);
        this.add(CategoryActionsButton);
        this.add(ReportActionsButton);
        this.add(OrderActionsButton);
        this.add(backButton);
        this.setVisible(true);
    }
}
