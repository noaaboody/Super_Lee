package Frontend.MVC.View.Inventory.ExistingInventoryActions.Category.CategoryActions;

import Frontend.MVC.Controller.Inventory.ExistingInventoyActions.Category.CategoryActions.RemoveSaleCategoryController;
import Frontend.MVC.Model.FactoryModel;
import Frontend.MVC.View.Inventory.PopUpView;

import javax.swing.*;

public class RemoveSaleCategoryView extends JFrame{
    public JLabel categoryNameLabel;
    public JLabel saleIdLabel;
    public JTextField categoryNameField;
    public JTextField saleIdField;
    public JButton removeButton;
    private RemoveSaleCategoryController controller;
    public JButton backButton;

    public RemoveSaleCategoryView(FactoryModel factoryModel, int invID, String worker){
        this.controller = new RemoveSaleCategoryController(factoryModel, this, invID, worker);

        categoryNameLabel = new JLabel();
        categoryNameLabel.setBounds(50,30,200,20);
        categoryNameLabel.setText("Category name:");

        categoryNameField = new JTextField();
        categoryNameField.setBounds(150,30,250,20);
        categoryNameField.addActionListener(controller);

        saleIdLabel = new JLabel();
        saleIdLabel.setBounds(50,60,200,20);
        saleIdLabel.setText("Sale id:");

        saleIdField = new JTextField();
        saleIdField.setBounds(150,60,250,20);
        saleIdField.addActionListener(controller);

        removeButton = new JButton();
        removeButton.setBounds(200,400,100,20);
        removeButton.addActionListener(controller);
        removeButton.setText("Remove");

        backButton = new JButton();
        backButton.setBounds(10,420,50,30);
        backButton.addActionListener(controller);
        backButton.setText("<<");

        this.setTitle("Remove sale from category");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setSize(500, 500);
        this.setVisible(true);
        this.add(categoryNameLabel);
        this.add(categoryNameField);
        this.add(saleIdLabel);
        this.add(saleIdField);
        this.add(removeButton);
        this.add(backButton);
    }

    public void finish(String output, String worker){
        this.dispose();
        new PopUpView(output, worker);
    }
}
