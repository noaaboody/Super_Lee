package Frontend.MVC.Controller.Inventory.ExistingInventoyActions.Item.ItemActions;

import Frontend.MVC.Model.FactoryModel;
import Frontend.MVC.View.Inventory.ExistingInventoryActions.Item.ItemActions.AddSaleItemView;
import Frontend.MVC.View.Inventory.ExistingInventoryActions.Item.ItemMenuView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddSaleItemController implements ActionListener {
    private AddSaleItemView view;
    private FactoryModel model;
    private int invID;
    private String worker;

    public AddSaleItemController(AddSaleItemView view, FactoryModel factoryModel, int invID, String worker) {
        this.worker = worker;
        this.view = view;
        this.model = factoryModel;
        this.invID = invID;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==view.addButton){
            view.dispose();
            String message = model.addSaleItem(invID,view.getItemId(),view.getDiscount(),view.getStartDate(), view.getEndDate());
            JOptionPane.showMessageDialog(null,message);
            new ItemMenuView(model,invID, worker);
        }
        if(e.getSource()==view.backButton){
            view.dispose();
            new ItemMenuView(model,invID, worker);
        }
    }
}




