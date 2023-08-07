package Frontend.MVC.View.Inventory.ExistingInventoryActions.Item.ItemActions;



import Frontend.MVC.Controller.Inventory.ExistingInventoyActions.Item.ItemActions.RemoveSaleItemController;
import Frontend.MVC.Model.FactoryModel;

import javax.swing.*;
import java.awt.*;

public class RemoveSaleItemView extends JFrame {
    private JTextField itemIdField;
    private JTextField saleIdField;
    public JButton removeButton;
    public JButton backButton;

    private RemoveSaleItemController controller;

    public RemoveSaleItemView(FactoryModel factoryModel, int invID, String worker) {
        this.controller = new RemoveSaleItemController(this, factoryModel, invID, worker);

        setTitle("Remove Sale for Item");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500, 200);
        setLocationRelativeTo(null); // Center the frame on the screen

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel formPanel = new JPanel(new GridLayout(4, 2, 10, 10));

        JLabel itemIdLabel = new JLabel("Item ID:");
        itemIdField = new JTextField();
        JLabel saleIDLabel = new JLabel("Sale ID:");
        saleIdField = new JTextField();

        formPanel.add(itemIdLabel);
        formPanel.add(itemIdField);
        formPanel.add(saleIDLabel);
        formPanel.add(saleIdField);

        mainPanel.add(formPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

        backButton = new JButton("<<");
        backButton.addActionListener(controller);
        buttonPanel.add(backButton);

        removeButton = new JButton("Remove Sale");
        removeButton.addActionListener(controller);
        buttonPanel.add(removeButton);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);

        pack();
        setVisible(true);
    }

    public int getItemId() {
        String itemIdText = itemIdField.getText();
        try {
            return Integer.parseInt(itemIdText);
        } catch (NumberFormatException e) {
            return -1; // Return -1 if the input is not a valid integer
        }
    }

    public int getSaleID() {
        String discountText = saleIdField.getText();
        try {
            return Integer.parseInt(discountText);
        } catch (NumberFormatException e) {
            return -1; // Return -1 if the input is not a valid float
        }
    }
}
