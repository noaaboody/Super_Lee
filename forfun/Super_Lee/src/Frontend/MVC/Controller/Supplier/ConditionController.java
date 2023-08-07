package Frontend.MVC.Controller.Supplier;

import Backend.serviceLayer.SupplierService;
import Frontend.MVC.Model.Supplier.ConditionModel;
import Frontend.MVC.View.Supplier.AgreementView;
import Frontend.MVC.View.Supplier.ConditionView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ConditionController {
    ConditionModel model;
    ConditionView view;
    int supplierId;
    SupplierService service;
    String worker;

    public ConditionController(ConditionView view, SupplierService service, int supId, String worker)
    {
        this.worker=worker;
        this.supplierId=supId;
        this.view=view;
        model=new ConditionModel(service,supplierId);
        this.service=service;
        view.addBackButtonListener(new backButtonListener());
        view.addSeeCondButtonListener(new seeCondButtonListener());
    }


    class backButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            AgreementController ag= new AgreementController(new AgreementView(supplierId),service,supplierId,worker);
            view.dispose();

        }
    }
    class seeCondButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String condIdT = view.getCondId();
            try{
                int condId= Integer.parseInt(condIdT);
                model.importCond(condId);
                if(model.getCondID() != -1)
                {
                    view.setDiscountPercentageField(Double.toString(model.getDiscountPercentage()));
                    view.setThresholdField(Double.toString(model.getNeededAmount()));
                    view.setPriceOrQuantityComboBox(model.isForPrice()? view.priceEnum:view.QuanEnum);

                    Object[][] data = new Object[model.getItemIDs().size()][1];
                    int index = 0;

                    // Iterate over the items and populate the data array
                    for (Integer itemId : model.getItemIDs()) {
                        data[index][0] = itemId;
                        index++;
                    }

                    // Create a table model with the data
                    String[] columnNames = {"Item ID"};
                    DefaultTableModel tableModel = new DefaultTableModel(data, columnNames);



                    // Create the showItemsWindow with the itemListTable
                    view.setItemListTable(tableModel);

                }
                else {JOptionPane.showMessageDialog(view,"condition id not valid","error", 1);}

            }
            catch (NumberFormatException n){
                JOptionPane.showMessageDialog(view,"condition id should contain numbers only","error", 1);}


        }
    }
}
