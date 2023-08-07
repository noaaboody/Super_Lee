package Frontend.MVC.View.Inventory.ExistingInventoryActions.Item;

import Frontend.MVC.Controller.Inventory.ExistingInventoyActions.Item.ItemMenuController;
import Frontend.MVC.Model.FactoryModel;

import javax.swing.*;

public class ItemMenuView extends JFrame{
    public ItemMenuController controller;
    public JButton AddNewItemButton;
    public JButton EditItemButton;
    public JButton AddSaleItemButton;
    public JButton RemoveSaleItemButton;
    public JButton ReportDamagedItemButton;
    public JButton finalPrice;
    public JButton minAmountProducts;
    public JButton ShowItemButton;
    public JButton backButton;



    public ItemMenuView(FactoryModel factoryModel, int invID, String worker) {
        controller = new ItemMenuController(factoryModel,this, invID, worker);

        AddNewItemButton = new JButton();
        AddNewItemButton.setBounds(100,50,250,50);
        AddNewItemButton.addActionListener(controller);
        AddNewItemButton.setText("Add New Item");

        EditItemButton = new JButton();
        EditItemButton.setBounds(100,100,250,50);
        EditItemButton.addActionListener(controller);
        EditItemButton.setText("Edit Item");

        AddSaleItemButton = new JButton();
        AddSaleItemButton.setBounds(100,150,250,50);
        AddSaleItemButton.addActionListener(controller);
        AddSaleItemButton.setText("Add Sale for Item");

        RemoveSaleItemButton = new JButton();
        RemoveSaleItemButton.setBounds(100,200,250,50);
        RemoveSaleItemButton.addActionListener(controller);
        RemoveSaleItemButton.setText("Remove Sale for Item");

        ReportDamagedItemButton = new JButton();
        ReportDamagedItemButton.setBounds(100,250,250,50);
        ReportDamagedItemButton.addActionListener(controller);
        ReportDamagedItemButton.setText("Report About Damaged Item");

        finalPrice = new JButton();
        finalPrice.setBounds(100,300,250,50);
        finalPrice.addActionListener(controller);
        finalPrice.setText("Item's final Price");

        minAmountProducts = new JButton();
        minAmountProducts.setBounds(100,350,250,50);
        minAmountProducts.addActionListener(controller);
        minAmountProducts.setText("Items With Minimal Amount");

        ShowItemButton = new JButton();
        ShowItemButton.setBounds(100,400,250,50);
        ShowItemButton.addActionListener(controller);
        ShowItemButton.setText("Item's Details");

        backButton = new JButton();
        backButton.setBounds(10,420,50,30);
        backButton.addActionListener(controller);
        backButton.setText("<<");

        this.setTitle("Item Actions Window");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setSize(500, 500);
        this.setVisible(true);
        this.add(AddNewItemButton);
        this.add(EditItemButton);
        this.add(AddSaleItemButton);
        this.add(RemoveSaleItemButton);
        this.add(ReportDamagedItemButton);
        this.add(finalPrice);
        this.add(minAmountProducts);
        this.add(ShowItemButton);
        this.add(backButton);
    }

}
