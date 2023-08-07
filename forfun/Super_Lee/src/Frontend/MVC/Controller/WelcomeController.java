package Frontend.MVC.Controller;

import Backend.serviceLayer.FactoryService;
import Backend.serviceLayer.SupplierService;
import Frontend.MVC.Controller.Supplier.SupMainController;
import Frontend.MVC.View.Inventory.InventoryView;
import Frontend.MVC.View.Supplier.SupMainView;
import Frontend.MVC.View.WelcomePageView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WelcomeController {

    private SupplierService supplierService;
    private FactoryService fs;
    private String worker;
    private WelcomePageView view;
    //private model;

    public WelcomeController(WelcomePageView view, SupplierService supServ, String worker){
        this.view = view;
        this.worker = worker;
        this.supplierService = supServ;
        this.fs = fs;

        view.addInventoryBListener(new InvBListener());
        view.addSupplierBListener(new SupplierBListener());
    }

    private class InvBListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e){
            if(!worker.equals("SupplierWorker")){
                new InventoryView(worker);
                view.dispose();
            }
        }
    }
    private class SupplierBListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            if(!worker.equals("InventoryWorker")){
                new SupMainController(new SupMainView(), supplierService,worker);
                view.dispose();
            }
        }
    }
}
