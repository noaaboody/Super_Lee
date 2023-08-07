package Frontend.MVC.Controller.Supplier;

import Backend.businessLayer.Suppliers.PaymentConditions;
import Backend.serviceLayer.SupplierService;
import Frontend.MVC.Model.Supplier.SupplierModel;
import Frontend.MVC.View.Supplier.AgreementView;
import Frontend.MVC.View.Supplier.SupMainView;
import Frontend.MVC.View.Supplier.SupplierView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.DayOfWeek;
import java.util.List;

public class SupplierControllerFront {
    private SupplierView view;

    private SupplierService service;

    private SupplierModel model;
    String worker;

    public SupplierControllerFront(SupplierView view, SupplierService service,String worker) {
        this.view = view;
        this.model= new SupplierModel(service);
        this.service=service;
        this.worker=worker;
        // Set action listeners for view buttons
        view.addCardButtonListener(new AddCardButtonListener());
        view.setButtonListener(new SetButtonListener());
        view.addBackButtonListener(new BackButtonListener());
        view.addAgreementButtonListener(new AgreementButtonListener());
        view.getSupplierButtonListener(new getSupplierButtonListener());
    }

    // Action listener for Add Card button
    class AddCardButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int supplierId=0;
            try { supplierId = Integer.parseInt(view.getSupplierId());}
            catch (NumberFormatException n) {JOptionPane.showMessageDialog(view,"supplier id textBox should contain number only","error", 1);}
            String name = view.getName();
            boolean isMobile = view.isMobileSelected();
            int bankAccount=0;
            try {
                bankAccount = Integer.parseInt(view.getBankAccount());
                PaymentConditions paymentCondition = view.getPaymentCondition();
                List<DayOfWeek> daysOfSupply = view.getSelectedDaysOfSupply();
                boolean isConstant= view.isConstantSelected();

                String result= model.addCard(supplierId, name, isMobile, bankAccount, paymentCondition, isConstant, daysOfSupply);
                JOptionPane.showMessageDialog(view,result,"Supplier",1);
            }
            catch (NumberFormatException n) {JOptionPane.showMessageDialog(view,"BankAccount textBox should contain number only","error", 1);}




            // Update view with the result
        }
    }

    // Action listener for Set Is Mobile button
    class SetButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int supplierId=0;
            try { supplierId = Integer.parseInt(view.getSupplierId());}
            catch (NumberFormatException n) {JOptionPane.showMessageDialog(view,"supplier id textBox should contain number only","error", 1);}
            boolean isMobile = view.isMobileSelected();
            int bankAccount=0;
            try {
                bankAccount = Integer.parseInt(view.getBankAccount());
                PaymentConditions paymentCondition = view.getPaymentCondition();
                String result = model.setAll(supplierId,isMobile,bankAccount,paymentCondition);

                // Update view with the result
                JOptionPane.showMessageDialog(view,result,"Supplier",1);
            }
            catch (NumberFormatException n) {JOptionPane.showMessageDialog(view,"BankAccount textBox should contain number only","error", 1);}

        }
    }



    class getSupplierButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int supplierId=0;
            try {
                supplierId = Integer.parseInt(view.getSupplierId());
                String result = model.GetSupplier(supplierId);
                view.updateName(model.getName());
                view.updateBankAccount(Integer.toString(model.getBankAccount()));
                view.updateIsMobile(model.isMobile());
                view.updateIsConstantCheckBox(model.getIsConstant());
                view.updatePaymentConditionComboBox(model.getPaymentCondition());
                for(DayOfWeek day: model.getDaysOfSupply())
                {
                    if(day.equals(DayOfWeek.SUNDAY)) view.setSundayCheckBox(true);
                    if(day.equals(DayOfWeek.MONDAY)) view.setMondayCheckBox(true);
                    if(day.equals(DayOfWeek.TUESDAY)) view.setTuesdayCheckBox(true);
                    if(day.equals(DayOfWeek.WEDNESDAY)) view.setWednesdayCheckBox(true);
                    if(day.equals(DayOfWeek.THURSDAY)) view.setThursdayCheckBox(true);
                    if(day.equals(DayOfWeek.FRIDAY)) view.setFridayCheckBox(true);
                    if(day.equals(DayOfWeek.SATURDAY)) view.setSaturdayCheckBox(true);
                }
                result= result.equals("")? "success":result;
                JOptionPane.showMessageDialog(view,result,"Supplier",1);
            }
            catch (NumberFormatException n) {JOptionPane.showMessageDialog(view,"supplier id textBox should contain number only","error", 1);}
        }
    }


    // Action listener for Back button
    class BackButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            SupMainController c = new SupMainController(new SupMainView(), service,worker);
            view.dispose();
        }
    }

    // Action listener for Agreement button
    class AgreementButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int supplierId=0;
            try {
                supplierId = Integer.parseInt(view.getSupplierId());
                AgreementController a= new AgreementController(new AgreementView(supplierId), service, supplierId,worker);
                view.dispose();
            }
            catch (NumberFormatException n) {JOptionPane.showMessageDialog(view,"supplier id textBox should contain number only","error", 1);}

        }
    }
}
