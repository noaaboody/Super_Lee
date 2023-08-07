package Frontend.MVC.Controller.Inventory;

import Frontend.MVC.Model.FactoryModel;
import Frontend.MVC.View.Inventory.AddInventoryView;
import Frontend.MVC.View.Inventory.InventoryView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddInventoryController implements ActionListener {
    private AddInventoryView view;
    private FactoryModel factoryModel;
    private String worker;

    public AddInventoryController(FactoryModel factoryModel, AddInventoryView view, String worker){
        this.worker = worker;
        this.factoryModel = factoryModel;
        this.view = view;
    }

    public String getAddress() {
        return view.addressField.getText();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==view.addButton){
            if(!getAddress().isEmpty()){
                String output = factoryModel.AddNewInventory(getAddress());
                view.finish(output, worker);
            }
        }
        if(e.getSource()==view.backButton){
            view.dispose();
            new InventoryView(worker);
        }
    }
}