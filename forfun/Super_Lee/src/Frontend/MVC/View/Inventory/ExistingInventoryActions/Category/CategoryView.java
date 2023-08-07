package Frontend.MVC.View.Inventory.ExistingInventoryActions.Category;

import Frontend.MVC.Controller.Inventory.ExistingInventoyActions.Category.CategoryController;
import Frontend.MVC.Model.FactoryModel;

import javax.swing.*;

public class CategoryView extends JFrame {
    private CategoryController controller;
    public JButton addSaleCategory;
    public JButton removeSaleCategory;
    public JButton addCategory;
    public JButton addSubCategory;
    public JButton addSubSubCategory;
    public JButton backButton;

    public CategoryView(FactoryModel factoryModel, int invID, String worker) {
        controller = new CategoryController(factoryModel,this, invID, worker);

        addSaleCategory = new JButton();
        addSaleCategory.setBounds(100,50,250,50);
        addSaleCategory.addActionListener(controller);
        addSaleCategory.setText("Add sale for category");

        removeSaleCategory = new JButton();
        removeSaleCategory.setBounds(100,110,250,50);
        removeSaleCategory.addActionListener(controller);
        removeSaleCategory.setText("Remove sale from category");

        addCategory = new JButton();
        addCategory.setBounds(100,170,250,50);
        addCategory.addActionListener(controller);
        addCategory.setText("Add category");

        addSubCategory = new JButton();
        addSubCategory.setBounds(100,230,250,50);
        addSubCategory.addActionListener(controller);
        addSubCategory.setText("Add sub category");

        addSubSubCategory = new JButton();
        addSubSubCategory.setBounds(100,290,250,50);
        addSubSubCategory.addActionListener(controller);
        addSubSubCategory.setText("Add sub sub category");

        backButton = new JButton();
        backButton.setBounds(10,420,50,30);
        backButton.addActionListener(controller);
        backButton.setText("<<");

        this.setTitle("Category actions");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setSize(500, 500);
        this.setVisible(true);
        this.add(addSaleCategory);
        this.add(removeSaleCategory);
        this.add(addCategory);
        this.add(addSubCategory);
        this.add(addSubSubCategory);
        this.add(backButton);
    }
}
