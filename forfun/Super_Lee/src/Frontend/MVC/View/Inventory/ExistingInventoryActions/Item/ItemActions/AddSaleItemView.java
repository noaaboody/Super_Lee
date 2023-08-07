package Frontend.MVC.View.Inventory.ExistingInventoryActions.Item.ItemActions;

import Frontend.MVC.Controller.Inventory.ExistingInventoyActions.Item.ItemActions.AddSaleItemController;
import Frontend.MVC.Model.FactoryModel;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

public class AddSaleItemView extends JFrame {
    private JTextField itemIdField;
    private JTextField discountField;
    private JTextField startDateField;
    private JTextField endDateField;
    public JButton addButton;
    public JButton backButton;

    private AddSaleItemController controller;

    public AddSaleItemView(FactoryModel factoryModel, int invID, String worker) {
        this.controller = new AddSaleItemController(this, factoryModel, invID, worker);

        setTitle("Add Sale for Item");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500, 200);
        setLocationRelativeTo(null); // Center the frame on the screen

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel formPanel = new JPanel(new GridLayout(4, 2, 10, 10));

        JLabel itemIdLabel = new JLabel("Item ID:");
        itemIdField = new JTextField();
        JLabel discountLabel = new JLabel("Discount:");
        discountField = new JTextField();
        JLabel startDateLabel = new JLabel("Start Date (YYYY-MM-DD):");
        startDateField = new JTextField();
        JLabel endDateLabel = new JLabel("End Date (YYYY-MM-DD):");
        endDateField = new JTextField();

        formPanel.add(itemIdLabel);
        formPanel.add(itemIdField);
        formPanel.add(discountLabel);
        formPanel.add(discountField);
        formPanel.add(startDateLabel);
        formPanel.add(startDateField);
        formPanel.add(endDateLabel);
        formPanel.add(endDateField);

        mainPanel.add(formPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

        backButton = new JButton("<<");
        backButton.addActionListener(controller);
        buttonPanel.add(backButton);

        addButton = new JButton("Add Sale");
        addButton.addActionListener(controller);
        buttonPanel.add(addButton);

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

    public float getDiscount() {
        String discountText = discountField.getText();
        try {
            return Float.parseFloat(discountText);
        } catch (NumberFormatException e) {
            return -1; // Return -1 if the input is not a valid float
        }
    }

    public LocalDate getStartDate() {
        String startDateText = startDateField.getText();
        try {
            return LocalDate.parse(startDateText);
        } catch (Exception e) {
            return null; // Return null if the input is not a valid date
        }
    }

    public LocalDate getEndDate() {
        String endDateText = endDateField.getText();
        try {
            return LocalDate.parse(endDateText);
        } catch (Exception e) {
            return null; // Return null if the input is not a valid date
        }
    }
}
