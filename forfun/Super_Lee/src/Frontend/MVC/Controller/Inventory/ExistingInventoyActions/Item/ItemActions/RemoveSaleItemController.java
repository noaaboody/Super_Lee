package Frontend.MVC.Controller.Inventory.ExistingInventoyActions.Item.ItemActions;

import Frontend.MVC.Model.FactoryModel;
import Frontend.MVC.View.Inventory.ExistingInventoryActions.Item.ItemActions.RemoveSaleItemView;
import Frontend.MVC.View.Inventory.ExistingInventoryActions.Item.ItemMenuView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RemoveSaleItemController implements ActionListener {
    private RemoveSaleItemView view;
    private FactoryModel model;
    private int invID;
    private String worker;

    public RemoveSaleItemController(RemoveSaleItemView view, FactoryModel factoryModel, int invID, String worker) {
        this.worker = worker;
        this.view = view;
        this.model = factoryModel;
        this.invID = invID;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==view.removeButton){
            view.dispose();
            JOptionPane.showMessageDialog(null,model.removeSaleItem(invID,view.getItemId(),view.getSaleID()));
            new ItemMenuView(model,invID, worker);
        }
        if(e.getSource()==view.backButton){
            view.dispose();
            new ItemMenuView(model,invID, worker);
        }
    }
}
