package Frontend.MVC.Controller.Supplier;

import Backend.serviceLayer.SupplierService;
import Frontend.MVC.Model.Supplier.ConditionModel;
import Frontend.MVC.View.Supplier.AddCondView;
import Frontend.MVC.View.Supplier.AgreementView;
import Frontend.MVC.View.Supplier.priceOrQuan;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddConditionController {
    ConditionModel model;
    AddCondView view;
    int supplierId;
    SupplierService service;
    String worker;

    public AddConditionController(AddCondView view, SupplierService service, int supId, String worker)
    {
        this.worker=worker;
        this.supplierId=supId;
        this.view=view;
        model=new ConditionModel(service,supplierId);
        this.service=service;
        view.addBackButtonListener(new backButtonListener());
        view.addItemButtonButtonListener(new AddItemButtonListener());
        view.addSubmitButtonListener(new submitButtonListener());
    }


    class backButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            AgreementController ag= new AgreementController(new AgreementView(supplierId),service,supplierId,worker);
            view.dispose();
        }
    }

    class submitButtonListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) {
            String discountT = view.getDiscountPercentageField();
            String thresholdT= view.getThresholdField();
            boolean isForPrice= view.getPriceOrQuantityComboBox().equals(priceOrQuan.Price);
            try{
                double threshold= Double.parseDouble(thresholdT);
                double discount= Double.parseDouble(discountT);
                model.setIsForPrice(isForPrice);
                model.setNeededAmount(threshold);
                model.setDiscount(discount);
                String result= model.addCond();
                JOptionPane.showMessageDialog(view,result,"success", 1);
                AgreementController ag= new AgreementController(new AgreementView(supplierId),service,supplierId,worker);
                view.dispose();

            }
            catch (NumberFormatException n){
                JOptionPane.showMessageDialog(view,"condition id should contain numbers only","error", 1);}
        }
    }

    class AddItemButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String itemIdT = view.getItemId();
            try{
                int itemId= Integer.parseInt(itemIdT);
                model.addItem(itemId);

                Object[][] data = new Object[model.getItemIDs().size()][1];
                int index = 0;

                // Iterate over the items and populate the data array
                for (Integer id : model.getItemIDs()) {
                    data[index][0] = id;
                    index++;
                }
                // Create a table model with the data
                String[] columnNames = {"Item ID"};
                DefaultTableModel tableModel = new DefaultTableModel(data, columnNames);
                // Create the showItemsWindow with the itemListTable
                view.setItemListTable(tableModel);

            }
            catch (NumberFormatException n){
                JOptionPane.showMessageDialog(view,"condition id should contain numbers only","error", 1);}

        }
    }
}
