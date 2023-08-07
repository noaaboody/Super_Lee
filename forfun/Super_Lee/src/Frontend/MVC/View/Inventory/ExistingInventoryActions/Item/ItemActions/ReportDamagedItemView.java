package Frontend.MVC.View.Inventory.ExistingInventoryActions.Item.ItemActions;

import Frontend.MVC.Controller.Inventory.ExistingInventoyActions.Item.ItemActions.ReportDamageItemController;
import Frontend.MVC.Model.FactoryModel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ReportDamagedItemView extends JFrame {
    private JTextField itemIdField;
    private JTextField damagedItemsArea;
    public JButton reportButton;
    public JButton backButton;

    private ReportDamageItemController controller;

    public ReportDamagedItemView(FactoryModel factoryModel, int invID, String worker) {
        this.controller = new ReportDamageItemController(this, factoryModel, invID, worker);

        setTitle("Report Damaged Item");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500, 500);
        setLocationRelativeTo(null); // Center the frame on the screen

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel formPanel = new JPanel(new GridLayout(4, 2, 10, 10));

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.anchor = GridBagConstraints.LINE_START;
        constraints.insets = new Insets(0, 0, 10, 0);

        constraints.gridx = 0;
        constraints.gridy = 0;
        JLabel itemIdLabel = new JLabel("Item ID:");
        constraints.gridx = 1;
        itemIdField = new JTextField(10);
        formPanel.add(itemIdLabel, constraints);
        formPanel.add(itemIdField, constraints);

        constraints.gridy = 1;
        JLabel damagedItemsLabel = new JLabel("List of Damaged Item IDs (one per line):");
        damagedItemsArea = new JTextField();
        JScrollPane damagedItemsScrollPane = new JScrollPane(damagedItemsArea);
        formPanel.add(damagedItemsLabel, constraints);
        formPanel.add(damagedItemsScrollPane, constraints);
        add(formPanel, BorderLayout.CENTER);


        constraints.gridy = 2;
        constraints.gridx = 2;
        reportButton = new JButton("Report");
        reportButton.addActionListener(controller);
        formPanel.add(reportButton, constraints);

        constraints.gridy = 2;
        constraints.gridx = 0;
        backButton = new JButton("<<");
        backButton.addActionListener(controller);
        formPanel.add(backButton, constraints);

        mainPanel.add(formPanel, BorderLayout.CENTER);
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

    public java.util.List<Integer> getDamagedItemIds() {
        String damagedItemsText = damagedItemsArea.getText();
        String[] damagedItemsArray = damagedItemsText.split("\\R");
        java.util.List<Integer> damagedItemsList = new ArrayList<>();
        for (String itemId : damagedItemsArray) {
            itemId = itemId.trim();
            if (!itemId.isEmpty()) {
                try {
                    damagedItemsList.add(Integer.parseInt(itemId));
                } catch (NumberFormatException e) {
                    // Ignore invalid entries
                }
            }
        }
        return damagedItemsList;
    }
}
