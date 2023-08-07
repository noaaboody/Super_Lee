package Frontend.MVC.Controller.Inventory.ExistingInventoyActions.Item.ItemActions;

import Frontend.MVC.Model.FactoryModel;
import Frontend.MVC.View.Inventory.ExistingInventoryActions.Item.ItemActions.ReportDamagedItemView;
import Frontend.MVC.View.Inventory.ExistingInventoryActions.Item.ItemMenuView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ReportDamageItemController implements ActionListener {
    private ReportDamagedItemView view;
    private FactoryModel model;
    private int invID;
    private String worker;

    public ReportDamageItemController(ReportDamagedItemView view, FactoryModel factoryModel, int invID, String worker) {
        this.worker = worker;
        this.view = view;
        this.model = factoryModel;
        this.invID = invID;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==view.reportButton){
            view.dispose();
            String resultMessage = model.damagedItem(invID, view.getItemId(), view.getDamagedItemIds());
            JOptionPane.showMessageDialog(view, resultMessage);
            new ItemMenuView(model,invID, worker);
        }
        if(e.getSource()==view.backButton){
            view.dispose();
            new ItemMenuView(model,invID, worker);
        }
    }
}
