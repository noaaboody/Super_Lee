package Frontend.MVC.Controller.Inventory.ExistingInventoyActions.Category;

import Frontend.MVC.Model.FactoryModel;
import Frontend.MVC.View.Inventory.ExistingInventoryActions.Category.CategoryActions.*;
import Frontend.MVC.View.Inventory.ExistingInventoryActions.Category.CategoryView;
import Frontend.MVC.View.Inventory.InventoryView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CategoryController implements ActionListener {
    private CategoryView view;
    private FactoryModel factoryModel;
    private int invID;
    private String worker;

    public CategoryController(FactoryModel factoryModel, CategoryView categoryView, int invID, String worker){
        this.worker = worker;
        this.factoryModel = factoryModel;
        this.invID = invID;
        this.view = categoryView;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()== view.addCategory){
            view.dispose();
            new AddCategoryView(factoryModel, invID, worker);
        }
        if(e.getSource()== view.addSubCategory){
            view.dispose();
            new AddSubCategoryView(factoryModel, invID, worker);
        }
        if(e.getSource()== view.addSubSubCategory){
            view.dispose();
            new AddSubSubCategoryView(factoryModel, invID, worker);
        }
        if(e.getSource()==view.addSaleCategory){
            view.dispose();
            new AddSaleCategoryView(factoryModel, invID, worker);
        }
        if(e.getSource()==view.removeSaleCategory){
            view.dispose();
            new RemoveSaleCategoryView(factoryModel, invID, worker);
        }
        if(e.getSource()==view.backButton){
            view.dispose();
            new InventoryView(worker);
        }
    }
}
