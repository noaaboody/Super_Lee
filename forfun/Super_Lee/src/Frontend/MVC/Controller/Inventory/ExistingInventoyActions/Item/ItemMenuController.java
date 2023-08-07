package Frontend.MVC.Controller.Inventory.ExistingInventoyActions.Item;

import Frontend.MVC.Model.FactoryModel;
import Frontend.MVC.View.Inventory.ExistingInventoryActions.Item.ItemActions.*;
import Frontend.MVC.View.Inventory.ExistingInventoryActions.Item.ItemMenuView;
import Frontend.MVC.View.Inventory.InventoryView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ItemMenuController implements ActionListener {
    private ItemMenuView view;
    private FactoryModel factoryModel;
    private int invID;
    private String worker;

    public ItemMenuController(FactoryModel factoryModel, ItemMenuView itemView, int invID, String worker) {
        this.worker = worker;
        this.factoryModel = factoryModel;
        this.invID = invID;
        this.view = itemView;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == view.AddNewItemButton) {
            view.dispose();
            new AddItemView(factoryModel, invID, worker);
        }
        if (e.getSource() == view.AddSaleItemButton) {
            view.dispose();
            new AddSaleItemView(factoryModel, invID, worker);
        }
        if (e.getSource() == view.ShowItemButton) {
            view.dispose();
            String itemIdInput = JOptionPane.showInputDialog(null, "Enter Item ID:", "Item ID", JOptionPane.QUESTION_MESSAGE);
            if (itemIdInput != null) {
                try {
                    int itemId = Integer.parseInt(itemIdInput);
                    String itemDetails = factoryModel.ItemDetails(invID, itemId);
                    JOptionPane.showMessageDialog(null, itemDetails, "Item Details", JOptionPane.INFORMATION_MESSAGE);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Invalid item ID.", "Error", JOptionPane.ERROR_MESSAGE);
                }
                finally {
                    new ItemMenuView(factoryModel, invID, worker);
                }
            }
            new ItemMenuView(factoryModel,invID, worker);
        }
        if (e.getSource() == view.minAmountProducts) {
            view.dispose();
            String missingRep = factoryModel.productsWithMinAmount(invID);
            if (missingRep == null) {
                JOptionPane.showMessageDialog(null, "There is no missing items", "missing items", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, missingRep, "missing items", JOptionPane.INFORMATION_MESSAGE);
            }
            new ItemMenuView(factoryModel,invID, worker);
        }
        if (e.getSource() == view.EditItemButton) {
            view.dispose();
            new EditItemInvView(factoryModel, invID, worker);
        }
        if (e.getSource() == view.RemoveSaleItemButton) {
            view.dispose();
            new RemoveSaleItemView(factoryModel, invID, worker);
        }
        if (e.getSource() == view.ReportDamagedItemButton) {
            view.dispose();
            new ReportDamagedItemView(factoryModel, invID, worker);
        }
        if (e.getSource() == view.finalPrice) {
            view.dispose();
            String id = JOptionPane.showInputDialog(null, "Enter Item ID:");
            if (id != null) {
                JOptionPane.showMessageDialog(null, factoryModel.finalPrice(invID, Integer.parseInt(id)));
            }
            new ItemMenuView(factoryModel,invID, worker);
        }
        if(e.getSource()==view.backButton){
            view.dispose();
            new InventoryView(worker);
        }
    }
}







