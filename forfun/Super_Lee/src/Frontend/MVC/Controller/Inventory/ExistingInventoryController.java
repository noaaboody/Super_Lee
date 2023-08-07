package Frontend.MVC.Controller.Inventory;

import Frontend.MVC.Model.FactoryModel;
import Frontend.MVC.View.Inventory.ExistingInventoryActions.Category.CategoryView;
import Frontend.MVC.View.Inventory.ExistingInventoryActions.Item.ItemMenuView;
import Frontend.MVC.View.Inventory.ExistingInventoryActions.Order.OrderView;
import Frontend.MVC.View.Inventory.ExistingInventoryActions.Report.ReportView;
import Frontend.MVC.View.Inventory.ExistingInventoryView;
import Frontend.MVC.View.Inventory.InventoryView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ExistingInventoryController implements ActionListener {
    private ExistingInventoryView view;
    private FactoryModel factoryModel;
    private int invID;
    private String worker;

    public ExistingInventoryController(FactoryModel factoryModel, ExistingInventoryView view, int invID, String worker){
        this.worker = worker;
        this.view = view;
        this.invID = invID;
        this.factoryModel = factoryModel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==view.ItemActionsButton){
            view.dispose();
            new ItemMenuView(factoryModel,invID, worker);
        }
        if(e.getSource()==view.CategoryActionsButton){
            view.dispose();
            new CategoryView(factoryModel, invID, worker);
        }
        if(e.getSource()==view.ReportActionsButton){
            view.dispose();
            new ReportView(factoryModel,invID, worker);
        }
        if(e.getSource()==view.OrderActionsButton){
            view.dispose();
            new OrderView(factoryModel,invID, worker);
        }
        if(e.getSource()==view.backButton){
            view.dispose();
            new InventoryView(worker);
        }
    }
}