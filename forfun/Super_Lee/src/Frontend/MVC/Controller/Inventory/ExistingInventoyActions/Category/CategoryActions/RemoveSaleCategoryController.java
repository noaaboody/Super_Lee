package Frontend.MVC.Controller.Inventory.ExistingInventoyActions.Category.CategoryActions;

import Frontend.MVC.Model.FactoryModel;
import Frontend.MVC.View.Inventory.ExistingInventoryActions.Category.CategoryActions.RemoveSaleCategoryView;
import Frontend.MVC.View.Inventory.InventoryView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RemoveSaleCategoryController implements ActionListener {
    private RemoveSaleCategoryView view;
    private int invID;
    private FactoryModel factoryModel;
    private String worker;

    public RemoveSaleCategoryController(FactoryModel factoryModel, RemoveSaleCategoryView view, int invID, String worker){
        this.worker = worker;
        this.factoryModel = factoryModel;
        this.invID = invID;
        this.view = view;
    }

    public String getCategoryName() {
        return view.categoryNameField.getText();
    }

    public int getSaleId() {
        return Integer.parseInt(view.saleIdField.getText());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==view.removeButton){
            if(!getCategoryName().isEmpty()){
                String output = factoryModel.RemoveSaleCategory(invID, getCategoryName(), getSaleId());
                view.finish(output, worker);
            }
        }
        if(e.getSource()==view.backButton){
            view.dispose();
            new InventoryView(worker);
        }
    }
}
