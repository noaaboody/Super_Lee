package Frontend.MVC.View.Inventory.ExistingInventoryActions.Report;

import Frontend.MVC.Controller.Inventory.ExistingInventoyActions.Report.ExistingReportByCategoriesController;
import Frontend.MVC.Model.FactoryModel;
import Frontend.MVC.View.Inventory.PopUpView;

import javax.swing.*;

public class ExistingReportByCategoriesView extends JFrame {
    public JLabel titleLabel;
    public JLabel categoryNameLabel1;
    public JLabel categoryNameLabel2;
    public JLabel categoryNameLabel3;
    public JLabel categoryNameLabel4;
    public JLabel categoryNameLabel5;
    public JLabel categoryNameLabel6;
    public JLabel categoryNameLabel7;
    public JLabel categoryNameLabel8;
    public JTextField categoryNameField1;
    public JTextField categoryNameField2;
    public JTextField categoryNameField3;
    public JTextField categoryNameField4;
    public JTextField categoryNameField5;
    public JTextField categoryNameField6;
    public JTextField categoryNameField7;
    public JTextField categoryNameField8;
    public JButton makeReportButton;
    private ExistingReportByCategoriesController controller;
    public JButton backButton;

    public ExistingReportByCategoriesView(FactoryModel factoryModel, int invID, String worker){
        controller = new ExistingReportByCategoriesController(factoryModel,this, invID, worker);

        titleLabel = new JLabel();
        titleLabel.setBounds(50,30,200,20);
        titleLabel.setText("Enter categories names to report");

        categoryNameLabel1 = new JLabel();
        categoryNameLabel1.setBounds(50,60,200,20);
        categoryNameLabel1.setText("Category 1:");

        categoryNameField1 = new JTextField();
        categoryNameField1.setBounds(150,60,250,20);
        categoryNameField1.addActionListener(controller);

        categoryNameLabel2 = new JLabel();
        categoryNameLabel2.setBounds(50,90,200,20);
        categoryNameLabel2.setText("Category 2:");

        categoryNameField2 = new JTextField();
        categoryNameField2.setBounds(150,90,250,20);
        categoryNameField2.addActionListener(controller);

        categoryNameLabel3 = new JLabel();
        categoryNameLabel3.setBounds(50,120,200,20);
        categoryNameLabel3.setText("Category 3:");

        categoryNameField3 = new JTextField();
        categoryNameField3.setBounds(150,120,250,20);
        categoryNameField3.addActionListener(controller);

        categoryNameLabel4 = new JLabel();
        categoryNameLabel4.setBounds(50,150,200,20);
        categoryNameLabel4.setText("Category 4:");

        categoryNameField4 = new JTextField();
        categoryNameField4.setBounds(150,150,250,20);
        categoryNameField4.addActionListener(controller);

        categoryNameLabel5 = new JLabel();
        categoryNameLabel5.setBounds(50,180,200,20);
        categoryNameLabel5.setText("Category 5:");

        categoryNameField5 = new JTextField();
        categoryNameField5.setBounds(150,180,250,20);
        categoryNameField5.addActionListener(controller);

        categoryNameLabel6 = new JLabel();
        categoryNameLabel6.setBounds(50,210,200,20);
        categoryNameLabel6.setText("Category 6:");

        categoryNameField6 = new JTextField();
        categoryNameField6.setBounds(150,210,250,20);
        categoryNameField6.addActionListener(controller);

        categoryNameLabel7 = new JLabel();
        categoryNameLabel7.setBounds(50,240,200,20);
        categoryNameLabel7.setText("Category 7:");

        categoryNameField7 = new JTextField();
        categoryNameField7.setBounds(150,240,250,20);
        categoryNameField7.addActionListener(controller);

        categoryNameLabel8 = new JLabel();
        categoryNameLabel8.setBounds(50,270,200,20);
        categoryNameLabel8.setText("Category 8:");

        categoryNameField8 = new JTextField();
        categoryNameField8.setBounds(150,270,250,20);
        categoryNameField8.addActionListener(controller);

        makeReportButton = new JButton();
        makeReportButton.setBounds(215,400,70,20);
        makeReportButton.addActionListener(controller);
        makeReportButton.setText("Make report");

        backButton = new JButton();
        backButton.setBounds(10,420,50,30);
        backButton.addActionListener(controller);
        backButton.setText("<<");

        this.setTitle("Existing report by categories");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setSize(500, 500);
        this.setVisible(true);
        this.add(categoryNameLabel1);
        this.add(categoryNameField1);
        this.add(categoryNameLabel2);
        this.add(categoryNameField2);
        this.add(categoryNameLabel3);
        this.add(categoryNameField3);
        this.add(categoryNameLabel4);
        this.add(categoryNameField4);
        this.add(categoryNameLabel5);
        this.add(categoryNameField5);
        this.add(categoryNameLabel6);
        this.add(categoryNameField6);
        this.add(categoryNameLabel7);
        this.add(categoryNameField7);
        this.add(categoryNameLabel8);
        this.add(categoryNameField8);
        this.add(makeReportButton);
        this.add(backButton);
    }

    public void finish(String output, String worker){
        this.dispose();
        new PopUpView(output, worker);
    }
}
