package Frontend.MVC.View.Inventory.ExistingInventoryActions.Order.OrderActions;

import Frontend.MVC.Controller.Inventory.ExistingInventoyActions.Order.OrderActions.MakeMissingOrderController;
import Frontend.MVC.Model.FactoryModel;
import Frontend.MVC.View.Inventory.PopUpView;

import javax.swing.*;

public class MakeMissingOrderView extends JFrame {
    public JLabel itemIdLabel;
    public JLabel quantityLabel;
    public JLabel Label1;
    public JLabel Label2;
    public JLabel Label3;
    public JLabel Label4;
    public JLabel Label5;
    public JLabel Label6;
    public JLabel Label7;
    public JLabel Label8;
    public JTextField itemIdField1;
    public JTextField itemIdField2;
    public JTextField itemIdField3;
    public JTextField itemIdField4;
    public JTextField itemIdField5;
    public JTextField itemIdField6;
    public JTextField itemIdField7;
    public JTextField itemIdField8;
    public JTextField quantityField1;
    public JTextField quantityField2;
    public JTextField quantityField3;
    public JTextField quantityField4;
    public JTextField quantityField5;
    public JTextField quantityField6;
    public JTextField quantityField7;
    public JTextField quantityField8;
    public JButton makeOrderButton;
    private MakeMissingOrderController controller;
    public JButton backButton;

    public MakeMissingOrderView(FactoryModel factoryModel, int invID, String worker){
        controller = new MakeMissingOrderController(factoryModel,this, invID, worker);

        Label1 = new JLabel();
        Label1.setBounds(30,60,10,20);
        Label1.setText("1.");

        Label2 = new JLabel();
        Label2.setBounds(30,90,10,20);
        Label2.setText("2.");

        Label3 = new JLabel();
        Label3.setBounds(30,120,10,20);
        Label3.setText("3.");

        Label4 = new JLabel();
        Label4.setBounds(30,150,10,20);
        Label4.setText("4.");

        Label5 = new JLabel();
        Label5.setBounds(30,180,10,20);
        Label5.setText("5.");

        Label6 = new JLabel();
        Label6.setBounds(30,210,10,20);
        Label6.setText("6.");

        Label7 = new JLabel();
        Label7.setBounds(30,240,10,20);
        Label7.setText("7.");

        Label8 = new JLabel();
        Label8.setBounds(30,270,10,20);
        Label8.setText("8.");

        itemIdLabel = new JLabel();
        itemIdLabel.setBounds(50,30,150,20);
        itemIdLabel.setText("Item id");

        itemIdField1 = new JTextField();
        itemIdField1.setBounds(50,60,150,20);
        itemIdField1.addActionListener(controller);

        itemIdField2 = new JTextField();
        itemIdField2.setBounds(50,90,150,20);
        itemIdField2.addActionListener(controller);

        itemIdField3 = new JTextField();
        itemIdField3.setBounds(50,120,150,20);
        itemIdField3.addActionListener(controller);

        itemIdField4 = new JTextField();
        itemIdField4.setBounds(50,150,150,20);
        itemIdField4.addActionListener(controller);

        itemIdField5 = new JTextField();
        itemIdField5.setBounds(50,180,150,20);
        itemIdField5.addActionListener(controller);

        itemIdField6 = new JTextField();
        itemIdField6.setBounds(50,210,150,20);
        itemIdField6.addActionListener(controller);

        itemIdField7 = new JTextField();
        itemIdField7.setBounds(50,240,150,20);
        itemIdField7.addActionListener(controller);

        itemIdField8 = new JTextField();
        itemIdField8.setBounds(50,270,150,20);
        itemIdField8.addActionListener(controller);

        quantityLabel = new JLabel();
        quantityLabel.setBounds(220,30,150,20);
        quantityLabel.setText("Quantity");

        quantityField1 = new JTextField();
        quantityField1.setBounds(220,60,150,20);
        quantityField1.addActionListener(controller);

        quantityField2 = new JTextField();
        quantityField2.setBounds(220,90,150,20);
        quantityField2.addActionListener(controller);

        quantityField3 = new JTextField();
        quantityField3.setBounds(220,120,150,20);
        quantityField3.addActionListener(controller);

        quantityField4 = new JTextField();
        quantityField4.setBounds(220,150,150,20);
        quantityField4.addActionListener(controller);

        quantityField5 = new JTextField();
        quantityField5.setBounds(220,180,150,20);
        quantityField5.addActionListener(controller);

        quantityField6 = new JTextField();
        quantityField6.setBounds(220,210,150,20);
        quantityField6.addActionListener(controller);

        quantityField7 = new JTextField();
        quantityField7.setBounds(220,240,150,20);
        quantityField7.addActionListener(controller);

        quantityField8 = new JTextField();
        quantityField8.setBounds(220,270,150,20);
        quantityField8.addActionListener(controller);

        makeOrderButton = new JButton();
        makeOrderButton.setBounds(180,400,140,20);
        makeOrderButton.addActionListener(controller);
        makeOrderButton.setText("Make order");

        backButton = new JButton();
        backButton.setBounds(10,420,50,30);
        backButton.addActionListener(controller);
        backButton.setText("<<");

        this.setTitle("Make missing order");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setSize(500, 500);
        this.setVisible(true);
        this.add(Label1);
        this.add(Label2);
        this.add(Label3);
        this.add(Label4);
        this.add(Label5);
        this.add(Label6);
        this.add(Label7);
        this.add(Label8);
        this.add(itemIdLabel);
        this.add(itemIdField1);
        this.add(itemIdField2);
        this.add(itemIdField3);
        this.add(itemIdField4);
        this.add(itemIdField5);
        this.add(itemIdField6);
        this.add(itemIdField7);
        this.add(itemIdField8);
        this.add(quantityLabel);
        this.add(quantityField1);
        this.add(quantityField2);
        this.add(quantityField3);
        this.add(quantityField4);
        this.add(quantityField5);
        this.add(quantityField6);
        this.add(quantityField7);
        this.add(quantityField8);
        this.add(makeOrderButton);
        this.add(backButton);
    }

    public void finish(String output, String worker) {
        this.dispose();
        new PopUpView(output, worker);
    }
}
