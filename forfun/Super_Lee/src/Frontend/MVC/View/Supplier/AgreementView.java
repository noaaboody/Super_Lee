package Frontend.MVC.View.Supplier;

import javax.swing.*;
import java.awt.event.ActionListener;

public class AgreementView extends JFrame {

    private JButton backButton=new JButton("Back");
    private JTextField itemIdField =new JTextField();
    private JTextField itemPriceField =new JTextField();
    private JTextField itemQuantityField =new JTextField();
    private JTextField itemSerialField =new JTextField();



    private JButton AddCondition=new JButton("Add condition");
    private JButton ShowConditions=new JButton("show condition");
    private  JButton showItems= new JButton("show items");
    private JButton addItem= new JButton("add item");
    private  JButton setItem= new JButton("set Item");
    private JCheckBox isConstantCheckBox = new JCheckBox("Constant");



    public AgreementView(int id)
    {	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setSize(630,200);
        setTitle("Agreement of supplier:: "+ id);
        setLayout(null);
        this.setResizable(false);
        isConstantCheckBox.setEnabled(false);
        JPanel panel = new JPanel();
        panel.setBounds(0,0,840,60);
        panel.setLayout(null);
        JPanel panel2 = new JPanel();
        panel2.setBounds(0,60,840,60);
        panel2.setLayout(null);
        JPanel panel3 = new JPanel();
        panel3.setBounds(0,120,840,60);
        panel3.setLayout(null);

        add(panel);
        panel.add(backButton).setBounds(40,0,100,40);
        panel.add(AddCondition).setBounds(150,0,150,40);
        panel.add(ShowConditions).setBounds(310,0,150,40);
        panel.add(showItems).setBounds(470,0,100,40);


        add(panel2);
        panel2.add(new JLabel("Item ID:")).setBounds(2,0,60,20);
        panel2.add(itemIdField).setBounds(64,0,80,20);
        panel2.add(new JLabel("Price:")).setBounds(146,0,60,20);
        panel2.add(itemPriceField).setBounds(206,0,80,20);
        panel2.add(new JLabel("Quantity:")).setBounds(290,0,80,20);
        panel2.add(itemQuantityField).setBounds(370,0,80,20);
        panel2.add(new JLabel("SerialNum:")).setBounds(450,0,80,20);
        panel2.add(itemSerialField).setBounds(530,0,80,20);
        panel2.add(isConstantCheckBox).setBounds(305,25,80,15);

        add(panel3);
        panel3.add(addItem).setBounds(210,0,100,40);
        panel3.add(setItem).setBounds(320,0,100,40);

    }
    public void  addConditionListener(ActionListener listener) {
        AddCondition.addActionListener(listener);
    }
    public void  backButtonListener(ActionListener listener) {
        backButton.addActionListener(listener);
    }

    public void  showConditionsListener(ActionListener listener) {
        ShowConditions.addActionListener(listener);
    }

    public void  showItemsButtonListener(ActionListener listener) {
        showItems.addActionListener(listener);
    }


    public void  setItemButtonListener(ActionListener listener) {
        setItem.addActionListener(listener);
    }
    public void  addItemButtonListener(ActionListener listener) {
        addItem.addActionListener(listener);
    }

    public void setIsConstant(boolean check) {
        isConstantCheckBox.setSelected(check);
    }

    public String getItemId() {return itemIdField.getText();}
    public String getItemQuantity() {return itemQuantityField.getText();}
    public String getItemPrice() {return itemQuantityField.getText();}
    public String getSerialNum() {return itemSerialField.getText();}




}
