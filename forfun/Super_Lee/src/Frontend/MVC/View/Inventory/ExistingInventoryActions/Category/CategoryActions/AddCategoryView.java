package Frontend.MVC.View.Inventory.ExistingInventoryActions.Category.CategoryActions;

import Frontend.MVC.Controller.Inventory.ExistingInventoyActions.Category.CategoryActions.AddCategoryController;
import Frontend.MVC.Model.FactoryModel;
import Frontend.MVC.View.Inventory.PopUpView;

import javax.swing.*;

public class AddCategoryView extends JFrame {
    public JLabel categoryNameLabel;
    public JTextField categoryNameField;
    public JButton addButton;
    private AddCategoryController controller;
    public JButton backButton;

    public AddCategoryView(FactoryModel factoryModel, int invID, String worker){
        controller = new AddCategoryController(factoryModel,this, invID, worker);

        categoryNameLabel = new JLabel();
        categoryNameLabel.setBounds(50,30,200,20);
        categoryNameLabel.setText("Category name:");

        categoryNameField = new JTextField();
        categoryNameField.setBounds(150,30,250,20);
        categoryNameField.addActionListener(controller);

        addButton = new JButton();
        addButton.setBounds(215,400,70,20);
        addButton.addActionListener(controller);
        addButton.setText("Add");

        backButton = new JButton();
        backButton.setBounds(10,420,50,30);
        backButton.addActionListener(controller);
        backButton.setText("<<");

        this.setTitle("Add category");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setSize(500, 500);
        this.setVisible(true);
        this.add(categoryNameLabel);
        this.add(categoryNameField);
        this.add(addButton);
        this.add(backButton);
    }

    public void finish(String output, String worker){
        this.dispose();
        new PopUpView(output, worker);
    }
}
