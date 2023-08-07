package Frontend.MVC.Controller.Inventory.ExistingInventoyActions.Item.ItemActions;

import Frontend.MVC.Model.FactoryModel;
import Frontend.MVC.View.Inventory.ExistingInventoryActions.Item.ItemActions.ItemsMinAmountView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ItemsMinAmountController implements ActionListener {
    private ItemsMinAmountView view;
    private FactoryModel model;
    private int invID;
    private String worker;

    public ItemsMinAmountController(ItemsMinAmountView view, FactoryModel factoryModel, int invID, String worker) {
        this.worker = worker;
        this.view = view;
        this.model = factoryModel;
        this.invID = invID;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
