package Frontend.MVC.View.Inventory;

import Frontend.MVC.Controller.Inventory.ExistingInventoryController;
import Frontend.MVC.Model.FactoryModel;

import javax.swing.*;

public class ExistingInventoryView extends JFrame {
    private ExistingInventoryController controller;
    public JButton ItemActionsButton;
    public JButton CategoryActionsButton;
    public JButton ReportActionsButton;
    public JButton OrderActionsButton;
    public JButton backButton;

    public ExistingInventoryView(FactoryModel factoryModel, int invID, String worker) {
        controller = new ExistingInventoryController(new FactoryModel(), this, invID, worker);

        ItemActionsButton = new JButton();
        ItemActionsButton.setBounds(100,50,250,50);
        ItemActionsButton.addActionListener(controller);
        ItemActionsButton.setText("Item Actions");

        CategoryActionsButton = new JButton();
        CategoryActionsButton.setBounds(100,110,250,50);
        CategoryActionsButton.addActionListener(controller);
        CategoryActionsButton.setText("Category Actions");

        ReportActionsButton = new JButton();
        ReportActionsButton.setBounds(100,170,250,50);
        ReportActionsButton.addActionListener(controller);
        ReportActionsButton.setText("Report Actions");

        OrderActionsButton = new JButton();
        OrderActionsButton.setBounds(100,230,250,50);
        OrderActionsButton.addActionListener(controller);
        OrderActionsButton.setText("Orders Actions");

        backButton = new JButton();
        backButton.setBounds(10,420,50,30);
        backButton.addActionListener(controller);
        backButton.setText("<<");

        this.setTitle("Existing Inventory Actions Window");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setSize(500, 500);
        this.setVisible(true);
        this.add(ItemActionsButton);
        this.add(CategoryActionsButton);
        this.add(ReportActionsButton);
        this.add(OrderActionsButton);
        this.add(backButton);
    }
}
