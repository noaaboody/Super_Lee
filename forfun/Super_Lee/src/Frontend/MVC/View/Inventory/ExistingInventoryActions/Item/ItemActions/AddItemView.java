package Frontend.MVC.View.Inventory.ExistingInventoryActions.Item.ItemActions;

import Frontend.MVC.Controller.Inventory.ExistingInventoyActions.Item.ItemActions.AddItemController;
import Frontend.MVC.Model.FactoryModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddItemView extends JFrame {
    private JTextField itemIdField;
    private JTextField stockLineField;
    private JTextField stockShelfField;
    private JTextField storeLineField;
    private JTextField storeShelfField;
    private JTextField stockQuantityField;
    private JTextField storeQuantityField;
    private JTextField minimalAmountField;
    private JTextField requiredAmountField;
    private JTextField storePriceField;
    private JTextField categoryField;
    private JTextField subCategoryField;
    private JTextField subSubCategoryField;
    public JButton addFieldButton;
    public JButton backButton;
    public JTextField expirationDateField;
    private Map<LocalDate, List<Integer>> expirationDateToProductIds;
    public JButton addButton;
    private AddItemController controller;

    public AddItemView(FactoryModel factoryModel, int invID, String worker) {
        this.controller = new AddItemController(this, factoryModel, invID, worker);

        setTitle("Add Item");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 700);
        setLocationRelativeTo(null); // Center the frame on the screen

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

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

        // Stock Line field
        constraints.gridx = 0;
        constraints.gridy = 1;
        JLabel stockLineLabel = new JLabel("Stock Line:");
        formPanel.add(stockLineLabel, constraints);

        constraints.gridx = 1;
        stockLineField = new JTextField(15);
        formPanel.add(stockLineField, constraints);

        // Stock Shelf field
        constraints.gridx = 0;
        constraints.gridy = 2;
        JLabel stockShelfLabel = new JLabel("Stock Shelf:");
        formPanel.add(stockShelfLabel, constraints);

        constraints.gridx = 1;
        stockShelfField = new JTextField(15);
        formPanel.add(stockShelfField, constraints);

        // Store Line field
        constraints.gridx = 0;
        constraints.gridy = 3;
        JLabel storeLineLabel = new JLabel("Store Line:");
        formPanel.add(storeLineLabel, constraints);

        constraints.gridx = 1;
        storeLineField = new JTextField(15);
        formPanel.add(storeLineField, constraints);

        // Store Shelf field
        constraints.gridx = 0;
        constraints.gridy = 4;
        JLabel storeShelfLabel = new JLabel("Store Shelf:");
        formPanel.add(storeShelfLabel, constraints);

        constraints.gridx = 1;
        storeShelfField = new JTextField(15);
        formPanel.add(storeShelfField, constraints);

        // Stock Quantity field
        constraints.gridx = 0;
        constraints.gridy = 5;
        JLabel stockQuantityLabel = new JLabel("Stock Quantity:");
        formPanel.add(stockQuantityLabel, constraints);

        constraints.gridx = 1;
        stockQuantityField = new JTextField(15);
        formPanel.add(stockQuantityField, constraints);

        // Store Quantity field
        constraints.gridx = 0;
        constraints.gridy = 6;
        JLabel storeQuantityLabel = new JLabel("Store Quantity:");
        formPanel.add(storeQuantityLabel, constraints);

        constraints.gridx = 1;
        storeQuantityField = new JTextField(15);
        formPanel.add(storeQuantityField, constraints);

        // Minimal Amount field
        constraints.gridx = 0;
        constraints.gridy = 7;
        JLabel minimalAmountLabel = new JLabel("Minimal Amount:");
        formPanel.add(minimalAmountLabel, constraints);

        constraints.gridx = 1;
        minimalAmountField = new JTextField(15);
        formPanel.add(minimalAmountField, constraints);

        // Required Amount field
        constraints.gridx = 0;
        constraints.gridy = 8;
        JLabel requiredAmountLabel = new JLabel("Required Amount:");
        formPanel.add(requiredAmountLabel, constraints);

        constraints.gridx = 1;
        requiredAmountField = new JTextField(15);
        formPanel.add(requiredAmountField, constraints);

        // Store Price field
        constraints.gridx = 0;
        constraints.gridy = 9;
        JLabel storePriceLabel = new JLabel("Store Price:");
        formPanel.add(storePriceLabel, constraints);

        constraints.gridx = 1;
        storePriceField = new JTextField(15);
        formPanel.add(storePriceField, constraints);

        // Category field
        constraints.gridx = 0;
        constraints.gridy = 10;
        JLabel categoryLabel = new JLabel("Category:");
        formPanel.add(categoryLabel, constraints);

        constraints.gridx = 1;
        categoryField = new JTextField(15);
        formPanel.add(categoryField, constraints);

        // Sub-Category field
        constraints.gridx = 0;
        constraints.gridy = 11;
        JLabel subCategoryLabel = new JLabel("Sub-Category:");
        formPanel.add(subCategoryLabel, constraints);

        constraints.gridx = 1;
        subCategoryField = new JTextField(15);
        formPanel.add(subCategoryField, constraints);

        // Sub-Sub-Category field
        constraints.gridx = 0;
        constraints.gridy = 12;
        JLabel subSubCategoryLabel = new JLabel("Sub-Sub-Category:");
        formPanel.add(subSubCategoryLabel, constraints);

        constraints.gridx = 1;
        subSubCategoryField = new JTextField(15);
        formPanel.add(subSubCategoryField, constraints);

        // Initialize expiration date and product ID fields
        expirationDateToProductIds = new HashMap<>();

        addFieldButton = new JButton("Add new expiration date");
        addFieldButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Prompt the user to enter expiration date and product IDs
                String expirationDate = JOptionPane.showInputDialog("Enter Expiration Date (YYYY-MM-DD):");
                String productIds = JOptionPane.showInputDialog("Enter Product IDs (comma-separated):");

                if (expirationDate != null && productIds != null) {
                    // Split the product IDs string into individual IDs
                    String[] productIdArray = productIds.split(",");
                    List<Integer> productIdList = new ArrayList<>();

                    // Parse the product IDs and add them to the list
                    for (String productId : productIdArray) {
                        productId = productId.trim();
                        try {
                            productIdList.add(Integer.parseInt(productId));
                        } catch (NumberFormatException ex) {
                        }
                    }
                    LocalDate expirationDateObj = LocalDate.parse(expirationDate);
                    expirationDateToProductIds.put(expirationDateObj, productIdList);
                    JOptionPane.showMessageDialog(null, "Expiration Date and Product IDs added successfully.");
                }
            }
        });

        // Add the "Add Field" button to the formPanel
        constraints.gridx = 1;
        constraints.gridy = 13;
        constraints.gridwidth = 2;
        formPanel.add(addFieldButton, constraints);

        // Add button
        constraints.gridx = 1;
        constraints.gridy = 14;
        addButton = new JButton("Add");
        addButton.addActionListener(controller);
        formPanel.add(addButton, constraints);

        // Back button
        backButton = new JButton("<<");
        backButton.addActionListener(controller);
        constraints.gridx = 0;
        constraints.gridy = 15;
        formPanel.add(backButton, constraints);

        formPanel.revalidate();
        formPanel.repaint();

        mainPanel.add(formPanel, BorderLayout.CENTER);

        getContentPane().add(mainPanel);
        setVisible(true);
    }

    public int getItemId() throws Exception {
        try{
            return Integer.parseInt(itemIdField.getText());
        }
        catch (Exception e){
            throw new Exception(e);
        }
    }

    public int getStockLine() throws Exception {
        try {
            return Integer.parseInt(stockLineField.getText());
        }
        catch (Exception e){
            throw new Exception(e);
        }
    }

    public int getStockShelf() throws Exception {
        try {
            return Integer.parseInt(stockShelfField.getText());
        }
        catch (Exception e){
            throw new Exception(e);
        }
    }

    public int getStoreLine() throws Exception {
        try{
            return Integer.parseInt(storeLineField.getText());
        }
        catch (Exception e){
            throw new Exception(e);
        }
    }

    public int getStoreShelf() throws Exception {
        try{
            return Integer.parseInt(storeShelfField.getText());
        }
        catch (Exception e){
            throw new Exception(e);
        }
    }

    public int getStockQuantity() throws Exception {
        try {
            return Integer.parseInt(stockQuantityField.getText());
        }
        catch (Exception e){
            throw new Exception(e);
        }
    }

    public int getStoreQuantity() throws Exception {
        try{
            return Integer.parseInt(storeQuantityField.getText());
        }
        catch (Exception e){
            throw new Exception(e);
        }
    }

    public int getMinimalAmount() throws Exception {
        try{
            return Integer.parseInt(minimalAmountField.getText());
        }
        catch (Exception e){
            throw new Exception(e);
        }
    }

    public int getRequiredAmount() throws Exception {
        try{
            return Integer.parseInt(requiredAmountField.getText());
        }
        catch (Exception e){
            throw new Exception(e);
        }
    }

    public float getStorePrice() throws Exception {
        try{
            return Float.parseFloat(storePriceField.getText());
        }
        catch (Exception e){
            throw new Exception(e);
        }
    }

    public String getCategory() throws Exception {
        try{
            return categoryField.getText();
        }
        catch (Exception e){
            throw new Exception(e);
        }
    }

    public String getSubCategory() throws Exception {
        try{
            return subCategoryField.getText();
        }
        catch (Exception e){
            throw new Exception(e);
        }
    }

    public String getSubSubCategory() throws Exception {
        try{
            return subSubCategoryField.getText();
        }
        catch (Exception e){
            throw new Exception(e);
        }
    }

    public Map<LocalDate, List<Integer>> getExpirationDateToProductIds() throws Exception {
        try{
            return expirationDateToProductIds;
        }
        catch (Exception e){
            throw new Exception(e);
        }
    }

    public void setController(AddItemController controller) {
        this.controller = controller;
    }

}
