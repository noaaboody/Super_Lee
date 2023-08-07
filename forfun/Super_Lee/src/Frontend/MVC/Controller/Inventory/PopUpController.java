package Frontend.MVC.Controller.Inventory;

import Frontend.MVC.View.Inventory.InventoryView;
import Frontend.MVC.View.Inventory.PopUpView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PopUpController implements ActionListener {
    private PopUpView view;
    private String worker;

    public PopUpController(PopUpView view, String worker){
        this.worker = worker;
        this.view = view;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==view.continueButton){
            view.dispose();
            new InventoryView(worker);
        }
    }
}
