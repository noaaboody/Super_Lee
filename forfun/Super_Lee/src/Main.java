import Backend.serviceLayer.FactoryService;
import Backend.serviceLayer.SupplierService;
import Frontend.MVC.Controller.Supplier.SupMainController;
import Frontend.MVC.Controller.WelcomeController;
import Frontend.MVC.View.Inventory.InventoryView;
import Frontend.MVC.View.Supplier.SupMainView;
import Frontend.MVC.View.WelcomePageView;
import Frontend.presentationLayer.InventoryPre;
import Frontend.presentationLayer.SupplierPre;
import Frontend.presentationLayer.UI;


public class Main {
    public static void main(String[] args) {
        String[] args1 = new String[2];
        args1[0] = "GUI";
        args1[1] = "StoreManager";
        FactoryService fc = new FactoryService();
        SupplierService s= new SupplierService(fc.getOrderHandler().getSupplierController());
        String worker = args1[1];
        if(args1[0].equals("CLI")){
            CLI(s, fc, worker);
        }
        else if(args1[0].equals("GUI")){
            GUI(s, worker);
        }
    }

    public static void CLI(SupplierService s, FactoryService fc, String worker){
        InventoryPre pre = new InventoryPre(fc);
        SupplierPre TUI= new SupplierPre(s);
        TUI.loadDemoData();
        UI Ui= new UI(pre,TUI, worker);
        Ui.run();
    }

    public static void GUI(SupplierService s, String worker){
        if(worker.equals("StoreManager")) new WelcomeController(new WelcomePageView(), s, worker);
        if (worker.equals("SupplierWorker")) new SupMainController(new SupMainView(),s,worker);
        if (worker.equals("InventoryWorker")) new InventoryView(worker);
    }
}
