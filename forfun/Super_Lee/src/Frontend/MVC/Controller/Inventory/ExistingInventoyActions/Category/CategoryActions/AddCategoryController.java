package Frontend.MVC.Controller.Inventory.ExistingInventoyActions.Category.CategoryActions;

import Frontend.MVC.Model.FactoryModel;
import Frontend.MVC.View.Inventory.ExistingInventoryActions.Category.CategoryActions.AddCategoryView;
import Frontend.MVC.View.Inventory.InventoryView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddCategoryController implements ActionListener {
    private AddCategoryView view;
    private int invID;
    private FactoryModel factoryModel;
    private String worker;

    public AddCategoryController(FactoryModel factoryModel, AddCategoryView view, int invID, String worker){
        this.worker = worker;
        this.factoryModel = factoryModel;
        this.invID = invID;
        this.view = view;
    }

    public String getCategoryName() {
        return view.categoryNameField.getText();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==view.addButton){
            if(!getCategoryName().isEmpty()){
                String output = factoryModel.AddCategory(invID, getCategoryName());
                view.finish(output, worker);
            }
        }
        if(e.getSource()==view.backButton){
            view.dispose();
            new InventoryView(worker);
        }
    }
}
