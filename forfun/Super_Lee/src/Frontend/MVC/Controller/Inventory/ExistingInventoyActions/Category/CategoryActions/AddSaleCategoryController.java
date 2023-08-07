package Frontend.MVC.Controller.Inventory.ExistingInventoyActions.Category.CategoryActions;

import Frontend.MVC.Model.FactoryModel;
import Frontend.MVC.View.Inventory.ExistingInventoryActions.Category.CategoryActions.AddSaleCategoryView;
import Frontend.MVC.View.Inventory.InventoryView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

public class AddSaleCategoryController implements ActionListener {
    private FactoryModel factoryModel;
    private int invID;
    private AddSaleCategoryView view;
    private String worker;

    public AddSaleCategoryController(FactoryModel factoryModel, int invID, AddSaleCategoryView view, String worker){
        this.worker = worker;
        this.factoryModel = factoryModel;
        this.invID = invID;
        this.view = view;
    }

    public String getCategoryName() {
        return view.categoryNameField.getText();
    }

    public float getDiscount() {
        return Float.parseFloat(view.discountField.getText());
    }

    public LocalDate getStartDate() {
        int year = Integer.parseInt(view.startDateYearField.getText());
        int month = Integer.parseInt(view.startDateMonthField.getText());
        int day = Integer.parseInt(view.startDateDayField.getText());
        return LocalDate.of(year,month,day);
    }

    public LocalDate getEndDate() {
        int year = Integer.parseInt(view.endDateYearField.getText());
        int month = Integer.parseInt(view.endDateMonthField.getText());
        int day = Integer.parseInt(view.endDateDayField.getText());
        return LocalDate.of(year,month,day);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==view.addButton){
            String output = factoryModel.AddSaleCategory(invID, getCategoryName(), getDiscount(), getStartDate(), getEndDate());
            view.finish(output, worker);
        }
        if(e.getSource()==view.backButton){
            view.dispose();
            new InventoryView(worker);
        }
    }
}
