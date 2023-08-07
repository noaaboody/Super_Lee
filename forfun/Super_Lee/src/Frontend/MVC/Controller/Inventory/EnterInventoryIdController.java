package Frontend.MVC.Controller.Inventory;

import Frontend.MVC.Model.FactoryModel;
import Frontend.MVC.View.Inventory.EnterInventoryIdView;
import Frontend.MVC.View.Inventory.ExistingInventoryView;
import Frontend.MVC.View.Inventory.InventoryView;
import Frontend.MVC.View.Inventory.PopUpView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EnterInventoryIdController implements ActionListener {
    private EnterInventoryIdView view;
    private FactoryModel factoryModel;
    private String worker;

    public EnterInventoryIdController(FactoryModel factoryModel, EnterInventoryIdView view, String worker){
        this.worker = worker;
        this.view = view;
        this.factoryModel = factoryModel;
    }

    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    public String getInvID() {
        return view.InventoryID.getText();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==view.OK){
            view.dispose();
            if(!isNumeric(getInvID())){
                new PopUpView("invalid input", worker);
            }
            else if(factoryModel.getInvById(Integer.parseInt(getInvID())) == null){
                new PopUpView("no such inventory id", worker);
            }
            else{
                new ExistingInventoryView(factoryModel, Integer.parseInt(getInvID()), worker);
            }
        }
        if(e.getSource()==view.backButton){
            view.dispose();
            new InventoryView(worker);
        }
    }
}