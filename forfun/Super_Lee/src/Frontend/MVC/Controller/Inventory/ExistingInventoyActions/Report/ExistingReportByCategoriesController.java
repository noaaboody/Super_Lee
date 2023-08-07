package Frontend.MVC.Controller.Inventory.ExistingInventoyActions.Report;

import Frontend.MVC.Model.FactoryModel;
import Frontend.MVC.View.Inventory.ExistingInventoryActions.Report.ExistingReportByCategoriesView;
import Frontend.MVC.View.Inventory.ExistingInventoryView;
import Frontend.MVC.View.Inventory.InventoryView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;

public class ExistingReportByCategoriesController implements ActionListener {
    private ExistingReportByCategoriesView view;
    private int invID;
    private FactoryModel factoryModel;
    private String worker;

    public ExistingReportByCategoriesController(FactoryModel factoryModel, ExistingReportByCategoriesView view, int invID, String worker){
        this.worker = worker;
        this.factoryModel = factoryModel;
        this.invID = invID;
        this.view = view;
    }

    public String getCategoryName1() {
        return view.categoryNameField1.getText();
    }

    public String getCategoryName2() {
        return view.categoryNameField2.getText();
    }

    public String getCategoryName3() {
        return view.categoryNameField3.getText();
    }

    public String getCategoryName4() {
        return view.categoryNameField4.getText();
    }

    public String getCategoryName5() {
        return view.categoryNameField5.getText();
    }

    public String getCategoryName6() {
        return view.categoryNameField6.getText();
    }

    public String getCategoryName7() {
        return view.categoryNameField7.getText();
    }

    public String getCategoryName8() {
        return view.categoryNameField8.getText();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==view.makeReportButton){
            List<String> categoryNames = new LinkedList<String>();
            if(!getCategoryName1().isEmpty()){
                categoryNames.add(getCategoryName1());
            }
            if(!getCategoryName2().isEmpty()){
                categoryNames.add(getCategoryName2());
            }
            if(!getCategoryName3().isEmpty()){
                categoryNames.add(getCategoryName3());
            }
            if(!getCategoryName4().isEmpty()){
                categoryNames.add(getCategoryName4());
            }
            if(!getCategoryName5().isEmpty()){
                categoryNames.add(getCategoryName5());
            }
            if(!getCategoryName6().isEmpty()){
                categoryNames.add(getCategoryName6());
            }
            if(!getCategoryName7().isEmpty()){
                categoryNames.add(getCategoryName7());
            }
            if(!getCategoryName8().isEmpty()){
                categoryNames.add(getCategoryName8());
            }
            String output = factoryModel.ExistingReportByCategories(invID, categoryNames);
            JOptionPane.showMessageDialog(null, output, "Existing By Categories Report", JOptionPane.INFORMATION_MESSAGE);
            ExistingInventoryView existingInventoryView = new ExistingInventoryView(factoryModel, invID, worker);
            existingInventoryView.setVisible(true);

            //view.finish(output);
        }
        if(e.getSource()==view.backButton){
            view.dispose();
            new InventoryView(worker);
        }
    }
}
