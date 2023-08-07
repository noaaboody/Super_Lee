package Frontend.MVC.View.Supplier;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;

public class AddCondView extends JFrame {

    private JButton backButton=new JButton("Back");
    private JButton addItemButton=new JButton("Add");

    private JButton submitButton=new JButton("Submit");

    private JTextField itemIdField =new JTextField();
    private JLabel itemIdLabel = new JLabel("Item id:");
    private final JLabel thresholdLabel = new JLabel("threshold:");

    private JTextField thresholdField =new JTextField();

    private JComboBox<priceOrQuan> PriceOrQuantityComboBox= new JComboBox<>(priceOrQuan.values());
    private final JLabel discountPercentageLabel = new JLabel("Discount Percentage:");
    private JTextField discountPercentageField =new JTextField();



    private JPanel TablePanel;
    JTable itemListTable;
    public AddCondView()
    {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setSize(560,550);
        setTitle("condition of supplier");
        setLayout(null);
        this.setResizable(false);
        TablePanel= new JPanel();
        TablePanel.setLayout(new FlowLayout());
        TablePanel.add(new JScrollPane(itemListTable), BorderLayout.CENTER); // Add table to TablePanel
        add(TablePanel);
        TablePanel.setBounds(0,50,545,400);
        JPanel topPanel = new JPanel();
        topPanel.setBounds(0,0,560,50);
        topPanel.setLayout(null);
        add(topPanel);
        topPanel.add(thresholdLabel).setBounds(5,0,70,30);
        topPanel.add(thresholdField).setBounds(80,0,70,30);
        topPanel.add(discountPercentageLabel).setBounds(160,0,130,30);
        topPanel.add(discountPercentageField).setBounds(290,0,70,30);
        topPanel.add(PriceOrQuantityComboBox).setBounds(370,0,70,30);
        topPanel.add(submitButton).setBounds(450,0,90,30);


        JPanel otherPanel= new JPanel();
        add(otherPanel);
        GridLayout lay = new GridLayout(1,4);
        lay.setHgap(15);
        otherPanel.setLayout(lay);
        otherPanel.setBounds(0,450,500,40);
        otherPanel.add(backButton);
        otherPanel.add(itemIdLabel);
        otherPanel.add(itemIdField);
        otherPanel.add(addItemButton);

    }

    public void addBackButtonListener(ActionListener listener) {
        backButton.addActionListener(listener);
    }
    public void addItemButtonButtonListener(ActionListener listener) {
        addItemButton.addActionListener(listener);
    }
    public void addSubmitButtonListener(ActionListener listener){submitButton.addActionListener(listener);}

    public String getItemId() {return  itemIdField.getText();}
    public  void setItemListTable(DefaultTableModel table) {
        if (itemListTable == null) {
            itemListTable = new JTable();
            itemListTable.setEnabled(false);
            itemListTable.setAutoCreateRowSorter(true);
            TablePanel.add(new JScrollPane(itemListTable), BorderLayout.CENTER);
            itemListTable.setModel(table);
            revalidate();
           } else {
            itemListTable.setModel(table); // Update the table model
          }
    }



    public String getThresholdField() {return thresholdField.getText();}

    public priceOrQuan getPriceOrQuantityComboBox() {return (priceOrQuan) PriceOrQuantityComboBox.getSelectedItem();}

    public String getDiscountPercentageField() {return discountPercentageField.getText();}



}
