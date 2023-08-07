package Frontend.MVC.View.Inventory.ExistingInventoryActions.Item.ItemActions;

import Frontend.MVC.Controller.Inventory.ExistingInventoyActions.Item.ItemActions.EditItemInvController;
import Frontend.MVC.Model.FactoryModel;

import javax.swing.*;
import java.awt.*;

public class EditItemInvView extends JFrame {
    public JTextField itemIdField;
    private JComboBox<String> optionComboBox;
    public JButton OKButton;
    public JButton backButton;

    private EditItemInvController controller;

    public EditItemInvView(FactoryModel factoryModel, int invID, String worker) {
        this.controller = new EditItemInvController(this,factoryModel,invID, worker);

        setTitle("Update Item");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 200);
        setLocationRelativeTo(null); // Center the frame on the screen

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.insets = new Insets(5, 5, 5, 5);

        // Item ID field
        JLabel itemIdLabel = new JLabel("Item ID:");
        formPanel.add(itemIdLabel, constraints);

        constraints.gridx = 1;
        itemIdField = new JTextField(15);
        formPanel.add(itemIdField, constraints);

        // Option Combo Box
        constraints.gridx = 0;
        constraints.gridy = 1;
        JLabel optionLabel = new JLabel("Select an option:");
        formPanel.add(optionLabel, constraints);
        constraints.gridx = 1;
        optionComboBox = new JComboBox<>(new String[]{"Change Minimal Amount", "Change Required Amount", "Change Store Quantity After Sale",
                "Change Stock Quantity After Order Arrived", "Move from store to stock", "Move from stock to store"});
        formPanel.add(optionComboBox, constraints);

// Update button
        constraints.gridx = 0;
        constraints.gridy = 2;
        backButton = new JButton("<<");
        backButton.addActionListener(controller);
        formPanel.add(backButton, constraints);

        constraints.gridx = 1;
        OKButton = new JButton("OK");
        OKButton.addActionListener(controller);
        formPanel.add(OKButton, constraints);

        mainPanel.add(formPanel, BorderLayout.CENTER);

        getContentPane().add(mainPanel);
        setVisible(true);
    }

    public int getItemIdField() throws Exception {
        try {
            return Integer.parseInt(itemIdField.getText());
        }
        catch (Exception e){
            throw new Exception(e);
        }
    }

    public JComboBox<String> getOptionComboBox() {
        return optionComboBox;
    }
}


