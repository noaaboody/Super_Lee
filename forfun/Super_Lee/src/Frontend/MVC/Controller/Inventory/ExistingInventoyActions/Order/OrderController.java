package Frontend.MVC.Controller.Inventory.ExistingInventoyActions.Order;

import Frontend.MVC.Model.FactoryModel;
import Frontend.MVC.View.Inventory.ExistingInventoryActions.Order.OrderActions.MakeMissingOrderView;
import Frontend.MVC.View.Inventory.ExistingInventoryActions.Order.OrderActions.MakePeriodOrderView;
import Frontend.MVC.View.Inventory.ExistingInventoryActions.Order.OrderView;
import Frontend.MVC.View.Inventory.InventoryView;
import Frontend.MVC.View.Inventory.PopUpView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OrderController implements ActionListener {
    private OrderView view;
    private FactoryModel factoryModel;
    private int invID;
    private String worker;

    public OrderController(FactoryModel factoryModel, OrderView view, int invID, String worker){
        this.worker = worker;
        this.factoryModel = factoryModel;
        this.invID = invID;
        this.view = view;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()== view.makeMissingOrder){
            view.dispose();
            new MakeMissingOrderView(factoryModel, invID, worker);
        }
        if(e.getSource()== view.makePeriodOrder){
            view.dispose();
            new MakePeriodOrderView(factoryModel, invID, worker);
        }
        if(e.getSource()== view.receiveTodayOrders){
            view.dispose();
            String output = factoryModel.receiveOrders(invID);
            new PopUpView(output, worker);
        }
        if(e.getSource()==view.getFutureOrders){
            view.dispose();
            String output = factoryModel.getFutureOrders(invID);
            new PopUpView(output, worker);
        }
        if(e.getSource()==view.backButton){
            view.dispose();
            new InventoryView(worker);
        }
    }
}
