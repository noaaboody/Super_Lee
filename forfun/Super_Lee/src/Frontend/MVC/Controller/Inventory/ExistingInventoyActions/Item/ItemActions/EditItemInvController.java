package Frontend.MVC.Controller.Inventory.ExistingInventoyActions.Item.ItemActions;

import Frontend.MVC.Model.FactoryModel;
import Frontend.MVC.View.Inventory.ExistingInventoryActions.Item.ItemActions.EditItemInvView;
import Frontend.MVC.View.Inventory.ExistingInventoryActions.Item.ItemMenuView;
import Frontend.MVC.View.Inventory.ExistingInventoryView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class EditItemInvController implements ActionListener  {
    private EditItemInvView view;
    private FactoryModel model;
    public int invID;
    private String worker;

    public EditItemInvController(EditItemInvView view, FactoryModel model, int invID, String worker) {
        this.worker = worker;
        this.view = view;
        this.model = model;
        this.invID = invID;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==view.OKButton){
            view.dispose();
            int itemId = 0;
            try {
                itemId = view.getItemIdField();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(view, ex.getMessage());
            }
            String selectedOption = (String) view.getOptionComboBox().getSelectedItem();
            ExistingInventoryView existingInventoryView = new ExistingInventoryView(model, invID, worker);
            existingInventoryView.setVisible(false);
            switch (selectedOption) {
                case "Change Minimal Amount":
                    String newMinimalAmount = JOptionPane.showInputDialog(view, "Enter new minimal amount:");
                    if (newMinimalAmount != null) {
                        try{
                            int minAmo = Integer.parseInt(newMinimalAmount);
                            JOptionPane.showMessageDialog(view, model.changeMinAmount(invID,itemId, minAmo));
                        }
                        catch (Exception ex){
                            JOptionPane.showMessageDialog(view, ex.getMessage());
                        }
                    }
                    existingInventoryView.setVisible(true);
                    break;
                case "Change Required Amount":
                    String newRequiredAmount = JOptionPane.showInputDialog(view, "Enter new required amount:");
                    if (newRequiredAmount != null) {
                        JOptionPane.showMessageDialog(view, model.changeRequiredAmount(invID,itemId, Integer.parseInt(newRequiredAmount)));
                    }
                    existingInventoryView.setVisible(true);
                    break;
                case "Change Store Quantity After Sale":
                    String soldProductsInput = JOptionPane.showInputDialog(null, "Enter the IDs of products that have been sold (comma-separated):");
                    if (soldProductsInput != null) {
                        String[] soldProductIds = soldProductsInput.split(",");
                        List<Integer> soldProductIdsList = new ArrayList<>();
                        for (String productId : soldProductIds) {
                            productId = productId.trim();
                            if (!productId.isEmpty()) {
                                try {
                                    soldProductIdsList.add(Integer.parseInt(productId));
                                } catch (NumberFormatException exception) {
                                    JOptionPane.showMessageDialog(null, exception.getMessage());
                                    existingInventoryView.setVisible(true);
                                }
                            }
                        }
                        try {
                            String output = model.removeItemsAfterSale(invID, itemId, soldProductIdsList);
                            JOptionPane.showMessageDialog(null, output);
                        } catch (Exception exception) {
                            JOptionPane.showMessageDialog(null, exception.getMessage());
                        }
                    }
                    existingInventoryView.setVisible(true);
                    break;
                case "Change Stock Quantity After Order Arrived":
                    String arrivalDateInput = JOptionPane.showInputDialog(null, "Enter the arrival date (YYYY-MM-DD):");
                    if (arrivalDateInput != null) {
                        LocalDate arrivalDate = null;
                        try {
                            arrivalDate = LocalDate.parse(arrivalDateInput);
                        } catch (DateTimeParseException exception) {
                            JOptionPane.showMessageDialog(null, exception.getMessage());
                            existingInventoryView.setVisible(true);
                        }
                        String productIdsInput = JOptionPane.showInputDialog(null, "Enter the IDs of products that have arrived (comma-separated):");
                        List<Integer> productIdsList = new ArrayList<>();
                        if (productIdsInput != null) {
                            String[] productIds = productIdsInput.split(",");
                            for (String productId : productIds) {
                                productId = productId.trim();
                                if (!productId.isEmpty()) {
                                    try {
                                        productIdsList.add(Integer.parseInt(productId));
                                    } catch (Exception exception) {
                                        JOptionPane.showMessageDialog(null, exception.getMessage());
                                        existingInventoryView.setVisible(true);
                                    }
                                }
                            }
                        }
                        try {
                            String output = model.updateItemsOrderArrived(invID, itemId, productIdsList, arrivalDate);
                            JOptionPane.showMessageDialog(null, output);
                        } catch (Exception exception) {
                            JOptionPane.showMessageDialog(null, exception.getMessage());
                            existingInventoryView.setVisible(true);
                        }
                    }
                    existingInventoryView.setVisible(true);
                    break;
                case "Move from store to stock":
                    String amountInput = JOptionPane.showInputDialog(null, "Enter the amount to move:");
                    if (amountInput != null) {
                        int amount = 0;
                        try {
                            amount = Integer.parseInt(amountInput);
                        } catch (Exception exception) {
                            JOptionPane.showMessageDialog(null, exception.getMessage());
                            existingInventoryView.setVisible(true);
                        }
                        JOptionPane.showMessageDialog(null,   model.itemsFromStoreToStock(invID,itemId, amount));
                    }
                    existingInventoryView.setVisible(true);
                    break;
                case "Move from stock to store":
                    String amountInput2 = JOptionPane.showInputDialog(null, "Enter the amount to move:");
                    if (amountInput2 != null) {
                        int amount = 0;
                        try {
                            amount = Integer.parseInt(amountInput2);
                        } catch (Exception exception) {
                            JOptionPane.showMessageDialog(null, exception.getMessage());
                            existingInventoryView.setVisible(true);
                        }
                        JOptionPane.showMessageDialog(null,model.itemsFromStockToStore(invID,itemId, amount));
                    }
                    existingInventoryView.setVisible(true);
                    break;
                default:
                    existingInventoryView.setVisible(true);
                    break;
            }
        }
        if(e.getSource()==view.backButton){
            view.dispose();
            new ItemMenuView(model,invID, worker);
        }
    }
}
