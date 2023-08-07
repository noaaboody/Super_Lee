package Frontend.MVC.Controller.Supplier;

import Backend.businessLayer.Suppliers.Contact;
import Backend.serviceLayer.SupplierService;
import Frontend.MVC.Model.Supplier.ContactsModel;
import Frontend.MVC.View.Supplier.ContactsView;
import Frontend.MVC.View.Supplier.SupMainView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ContactsController {
    ContactsView view;
    SupplierService service;
    ContactsModel model;
    String worker;


    public ContactsController(ContactsView view, SupplierService service,String worker)
    {
        this.worker= worker;
        this.view=view;
        model=new ContactsModel(service);
        this.service=service;
        view.addBackButtonListener(new backButtonListener());
        view.addSeeButtonButtonListener(new seeButtonListener());
        view.addAddButtonListener(new addAddButtonListener());
        view.addSetButtonListener(new setButtonListener());
    }

    public void refreshTable()
    {
        Object[][] data = new Object[model.getContacts().size()][4];
        int index = 0;

        // Iterate over the items and populate the data array
        for (Contact contact : model.getContacts()) {
            data[index][0] = contact.GetId();
            data[index][1]=contact.GetName();
            data[index][2]= contact.GetPhoneNumber();
            data[index][3]= contact.GetEmail();
            index++;
        }
        // Create a table model with the data
        String[] columnNames = {"ID","Name","Phone","Email"};
        DefaultTableModel tableModel = new DefaultTableModel(data, columnNames);
        // Create the showItemsWindow with the itemListTable
        view.setContactsListTable(tableModel);
    }


    class backButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            new SupMainController(new SupMainView(),service,worker);
            view.dispose();
        }
    }
    class setButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String cIdT = view.getContactID();
            String nameT= view.getContactName();
            String email= view.getContactEmail();
            String phoneNumT= view.getContactPhone();
            try{
                int cId= Integer.parseInt(cIdT);
                int phoneNum= Integer.parseInt(phoneNumT);
                String res = model.setContact(cId,nameT,email,Integer.toString(phoneNum));
                JOptionPane.showMessageDialog(view,res,"", 1);
                refreshTable();
            }
            catch (NumberFormatException n){JOptionPane.showMessageDialog(view,"contact id and phone number should contain numbers only","error", 1);}
            catch (Exception ex){JOptionPane.showMessageDialog(view,ex.getMessage(),"error", 1);}
        }
    }
    class addAddButtonListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) {
            String cIdT = view.getContactID();
            String nameT= view.getContactName();
            String email= view.getContactEmail();
            String phoneNumT= view.getContactPhone();
            try{
                int cId= Integer.parseInt(cIdT);
                int phoneNum= Integer.parseInt(phoneNumT);
                model.addContact(cId,nameT,email,Integer.toString(phoneNum));
                refreshTable();
            }
            catch (NumberFormatException n){JOptionPane.showMessageDialog(view,"contact id and phone number should contain numbers only","error", 1);}
            catch (Exception ex){JOptionPane.showMessageDialog(view,ex.getMessage(),"error", 1);}

        }
    }

    class seeButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String supplierIdT = view.getSupplierId();
            try{
                int supplierId= Integer.parseInt(supplierIdT);
                model.setId(supplierId);
                refreshTable();
            }
            catch (NumberFormatException n){JOptionPane.showMessageDialog(view,"condition id should contain numbers only","error", 1);}
            catch (Exception ex){JOptionPane.showMessageDialog(view,ex.getMessage(),"error", 1);}

        }
    }

}
