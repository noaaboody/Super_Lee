package Frontend.MVC.Controller.Inventory.ExistingInventoyActions.Item.ItemActions;

import Frontend.MVC.Model.FactoryModel;
import Frontend.MVC.View.Inventory.ExistingInventoryActions.Item.ItemActions.AddItemView;
import Frontend.MVC.View.Inventory.ExistingInventoryActions.Item.ItemMenuView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddItemController implements ActionListener{
    private AddItemView view;
    private FactoryModel model;
    private int invID;
    private String worker;


    public AddItemController(AddItemView view, FactoryModel factoryModel, int invID, String worker) {
        this.view = view;
        this.model = factoryModel;
        this.invID = invID;
        this.worker = worker;
        //this.view.setController(this);
    }

    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==view.addButton){
            view.dispose();
            try {
                model.AddItem(invID,view.getItemId(),view.getStockLine(),view.getStockShelf(), view.getStoreLine(),view.getStoreShelf(),view.getStoreQuantity(),
                        view.getStockQuantity(),view.getMinimalAmount(),view.getRequiredAmount(),view.getStorePrice(),view.getExpirationDateToProductIds(),view.getCategory(), view.getSubCategory(), view.getSubSubCategory());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null,ex.getMessage());
            }
            new ItemMenuView(model,invID,worker);
        }
        if(e.getSource()==view.backButton){
            view.dispose();
            new ItemMenuView(model,invID,worker);
        }

    }

//    public static boolean isNumeric(int strNum) {
//        if (strNum == null) {
//            return false;
//        }
//        try {
//            double d = Double.parseDouble(strNum);
//        } catch (NumberFormatException nfe) {
//            return false;
//        }
//        return true;
//    }
}
