package Frontend.MVC.Controller.Supplier;

import Backend.serviceLayer.SupplierService;
import Frontend.MVC.Model.Supplier.AgreementModel;
import Frontend.MVC.View.Supplier.AddCondView;
import Frontend.MVC.View.Supplier.AgreementView;
import Frontend.MVC.View.Supplier.ConditionView;
import Frontend.MVC.View.Supplier.SupplierView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

public class AgreementController {
    AgreementView view;
    SupplierService service;

    AgreementModel model;
    String worker;
    public AgreementController(AgreementView view, SupplierService service, int id,String worker)
    {
        this.worker=worker;
        this.view= view;
        this.service=service;
        view.addItemButtonListener(new addItemButtonListener());
        view.setItemButtonListener(new setItemButtonListener());
        view.showItemsButtonListener(new showItemsButtonListener());
        view.showConditionsListener(new showConditionsListener());
        view.addConditionListener(new addConditionListener());
        view.backButtonListener(new backButtonListener());
        model= new AgreementModel(id, service);//initializing the agreement
        view.setIsConstant(model.isConstantSupply());//updating the check box to show the result


    }


    class addItemButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String idT= view.getItemId();
            String priceT= view.getItemPrice();
            String quantityT = view.getItemQuantity();
            String serialNumT= view.getSerialNum();
            try{
                int id= Integer.parseInt(idT);
                double price=Double.parseDouble(priceT);
                int quantity = Integer.parseInt(quantityT);
                int serial = Integer.parseInt(serialNumT);
                model.addItem(id,price,quantity,serial);

            }
            catch (NumberFormatException n) { JOptionPane.showMessageDialog(view,"text boxes should contain number only","error", 1);}
        }
    }


    class setItemButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String idT= view.getItemId();
            String priceT= view.getItemPrice();
            String quantityT = view.getItemQuantity();
            try{
                int id= Integer.parseInt(idT);
                double price=Double.parseDouble(priceT);
                int quantity = Integer.parseInt(quantityT);
                model.setItem(id,price,quantity);
            }
            catch (NumberFormatException n) {JOptionPane.showMessageDialog(view,"text boxes should contain number only","error", 1);}
        }
    }


    class showItemsButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Object[][] data = new Object[model.getItemsToPrice().size()][3];
            int index = 0;

            // Iterate over the items and populate the data array
            for (Map.Entry<Integer, Double> entry : model.getItemsToPrice().entrySet()) {
                int itemId = entry.getKey();
                double price = entry.getValue();
                int quantity = model.getItemsToQuantity().getOrDefault(itemId, 0);

                data[index][0] = itemId;
                data[index][1] = quantity;
                data[index][2] = price;
                index++;
            }

            // Create a table model with the data
            String[] columnNames = {"Item ID", "Quantity", "Price"};
            DefaultTableModel tableModel = new DefaultTableModel(data, columnNames);

            // Set the table model
            JTable itemListTable = new JTable(tableModel);
            itemListTable.setAutoCreateRowSorter(true);

            // Create the showItemsWindow with the itemListTable
            showItemsWindow w = new showItemsWindow(itemListTable);
        }

        private class showItemsWindow extends JFrame {

            private JScrollPane scrollPane;

            public showItemsWindow(JTable itemListTable1) {
                JTable itemListTable = itemListTable1;
                scrollPane = new JScrollPane(itemListTable);
                itemListTable.setEnabled(false);
                setLayout(new BorderLayout());
                add(scrollPane, BorderLayout.CENTER);
                setTitle("Item List");
                pack();
                setLocationRelativeTo(null);
                setVisible(true);
                setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            }
        }
    }


    class showConditionsListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            new ConditionController(new ConditionView(),service,model.getSupplierId(),worker);
            view.dispose();
        }
    }

    class backButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            SupplierControllerFront con= new SupplierControllerFront(new SupplierView(),service,worker);
            view.dispose();
        }
    }


    class addConditionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            AddConditionController c= new AddConditionController(new AddCondView(),service, model.getSupplierId(),worker);
            view.dispose();
        }
    }
}


