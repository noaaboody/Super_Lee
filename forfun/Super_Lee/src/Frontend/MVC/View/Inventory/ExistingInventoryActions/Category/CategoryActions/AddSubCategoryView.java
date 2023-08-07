package Frontend.MVC.View.Inventory.ExistingInventoryActions.Category.CategoryActions;

import Frontend.MVC.Controller.Inventory.ExistingInventoyActions.Category.CategoryActions.AddSubCategoryController;
import Frontend.MVC.Model.FactoryModel;
import Frontend.MVC.View.Inventory.PopUpView;

import javax.swing.*;

public class AddSubCategoryView extends JFrame {
    public JLabel categoryNameLabel;
    public JLabel subCategoryNameLabel;
    public JTextField categoryNameField;
    public JTextField subCategoryNameField;
    public JButton addButton;
    private AddSubCategoryController controller;
    public JButton backButton;

    public AddSubCategoryView(FactoryModel factoryModel, int invID, String worker){
        controller = new AddSubCategoryController(factoryModel,this, invID, worker);

        categoryNameLabel = new JLabel();
        categoryNameLabel.setBounds(50,30,200,20);
        categoryNameLabel.setText("Category name:");

        categoryNameField = new JTextField();
        categoryNameField.setBounds(200,30,200,20);
        categoryNameField.addActionListener(controller);

        subCategoryNameLabel = new JLabel();
        subCategoryNameLabel.setBounds(50,60,200,20);
        subCategoryNameLabel.setText("Sub category name:");

        subCategoryNameField = new JTextField();
        subCategoryNameField.setBounds(200,60,200,20);
        subCategoryNameField.addActionListener(controller);

        addButton = new JButton();
        addButton.setBounds(215,400,70,20);
        addButton.addActionListener(controller);
        addButton.setText("Add");

        backButton = new JButton();
        backButton.setBounds(10,420,50,30);
        backButton.addActionListener(controller);
        backButton.setText("<<");

        this.setTitle("Add sub category");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setSize(500, 500);
        this.setVisible(true);
        this.add(categoryNameLabel);
        this.add(categoryNameField);
        this.add(subCategoryNameLabel);
        this.add(subCategoryNameField);
        this.add(addButton);
        this.add(backButton);
    }

    public void finish(String output, String worker){
        this.dispose();
        new PopUpView(output, worker);
    }
}