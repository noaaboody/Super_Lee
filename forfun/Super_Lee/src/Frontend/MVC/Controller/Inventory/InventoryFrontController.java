package Frontend.MVC.Controller.Inventory;

import Backend.serviceLayer.FactoryService;
import Backend.serviceLayer.SupplierService;
import Frontend.MVC.Controller.WelcomeController;
import Frontend.MVC.Model.FactoryModel;
import Frontend.MVC.View.Inventory.AddInventoryView;
import Frontend.MVC.View.Inventory.EnterInventoryIdView;
import Frontend.MVC.View.Inventory.InventoryView;
import Frontend.MVC.View.WelcomePageView;
import Frontend.presentationLayer.InventoryPre;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InventoryFrontController implements ActionListener {
    private InventoryView view;
    private FactoryModel factoryModel;
    private String worker;

    public InventoryFrontController(FactoryModel factoryModel, InventoryView view, String worker){
        this.worker = worker;
        this.view = view;
        this.factoryModel = factoryModel;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==view.AddNewInventory){
            view.dispose();
            new AddInventoryView(factoryModel, worker);
        }
        if(e.getSource()==view.ExistingInventory){
            view.dispose();
            new EnterInventoryIdView(factoryModel, worker);
        }
        if(e.getSource()==view.backButton){
            view.dispose();
            FactoryService fc = new FactoryService();
            InventoryPre pre = new InventoryPre(fc);
            SupplierService s= new SupplierService(fc.getOrderHandler().getSupplierController());
            new WelcomeController(new WelcomePageView(),s, worker);
        }
    }
}