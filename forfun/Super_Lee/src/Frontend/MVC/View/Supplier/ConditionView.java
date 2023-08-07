package Frontend.MVC.View.Supplier;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;

public class ConditionView extends JFrame{
    public priceOrQuan priceEnum= priceOrQuan.Price;
    public priceOrQuan QuanEnum= priceOrQuan.Quantity;
    private JButton backButton=new JButton("Back");
    private JButton seeCondButton=new JButton("See Condition");
    private JTextField condIdField =new JTextField();
    private JLabel condIdLabel = new JLabel("Condition id:");
    private final JLabel thresholdLabel = new JLabel("threshold:");

    private JLabel thresholdField =new JLabel();

    private JComboBox<priceOrQuan> PriceOrQuantityComboBox= new JComboBox<>(priceOrQuan.values());
    private final JLabel discountPercentageLabel = new JLabel("Discount Percentage:");
    private JLabel discountPercentageField =new JLabel();



    private JPanel TablePanel;
    JTable itemListTable;
    public ConditionView()
    {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setSize(550,550);
        setTitle("condition of supplier");
        setLayout(null);
        this.setResizable(false);
        PriceOrQuantityComboBox.setEnabled(false);
        TablePanel= new JPanel();
        TablePanel.setLayout(new FlowLayout());
        TablePanel.add(new JScrollPane(itemListTable), BorderLayout.CENTER); // Add table to TablePanel
        add(TablePanel);
        TablePanel.setBounds(0,50,545,400);
        JPanel topPanel = new JPanel();
        topPanel.setBounds(0,0,545,50);
        topPanel.setLayout(null);
        add(topPanel);
        topPanel.add(thresholdLabel).setBounds(5,0,70,30);
        topPanel.add(thresholdField).setBounds(80,0,70,30);
        topPanel.add(discountPercentageLabel).setBounds(160,0,130,30);
        topPanel.add(discountPercentageField).setBounds(290,0,70,30);
        topPanel.add(PriceOrQuantityComboBox).setBounds(370,0,70,30);


        JPanel otherPanel= new JPanel();
        add(otherPanel);
        GridLayout lay = new GridLayout(1,4);
        lay.setHgap(15);
        otherPanel.setLayout(lay);
        otherPanel.setBounds(0,450,500,40);
        otherPanel.add(backButton);
        otherPanel.add(condIdLabel);
        otherPanel.add(condIdField);
        otherPanel.add(seeCondButton);

    }

    public void addBackButtonListener(ActionListener listener) {
        backButton.addActionListener(listener);
    }
    public void addSeeCondButtonListener(ActionListener listener) {
        seeCondButton.addActionListener(listener);
    }

    public String getCondId() {return  condIdField.getText();}
    public  void setItemListTable(DefaultTableModel table) {
        itemListTable= new JTable(table);
        itemListTable.setEnabled(false);
        itemListTable.setAutoCreateRowSorter(true);
        TablePanel.add(new JScrollPane(itemListTable), BorderLayout.CENTER);
        revalidate();
    }




    public void setThresholdField(String thresholdField) {
        this.thresholdField.setText(thresholdField);
    }

    public void setPriceOrQuantityComboBox(priceOrQuan value) {
        PriceOrQuantityComboBox.setSelectedItem(value);
    }

    public void setDiscountPercentageField(String discountPercentageField) {
        this.discountPercentageField.setText(discountPercentageField);
    }



}
