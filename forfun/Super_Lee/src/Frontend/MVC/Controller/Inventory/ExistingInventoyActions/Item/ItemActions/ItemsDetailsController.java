package Frontend.MVC.Controller.Inventory.ExistingInventoyActions.Item.ItemActions;

import Frontend.MVC.Model.FactoryModel;
import Frontend.MVC.View.Inventory.ExistingInventoryActions.Item.ItemActions.ItemsDetailsView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ItemsDetailsController implements ActionListener {
    private ItemsDetailsView view;
    private FactoryModel model;
    private int invID;
    private String worker;

    public ItemsDetailsController(ItemsDetailsView view, FactoryModel factoryModel, int invID, String worker) {
        this.worker = worker;
        this.view = view;
        this.model = factoryModel;
        this.invID = invID;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
