package Frontend.MVC.View.Inventory.ExistingInventoryActions.Category.CategoryActions;

import Frontend.MVC.Controller.Inventory.ExistingInventoyActions.Category.CategoryActions.AddSaleCategoryController;
import Frontend.MVC.Model.FactoryModel;
import Frontend.MVC.View.Inventory.PopUpView;

import javax.swing.*;

public class AddSaleCategoryView extends JFrame {
    public JLabel categoryNameLabel;
    public JLabel discountLabel;
    public JLabel startDateLabel;
    public JLabel startDateYearLabel;
    public JLabel startDateMonthLabel;
    public JLabel startDateDayLabel;
    public JLabel endDateLabel;
    public JLabel endDateYearLabel;
    public JLabel endDateMonthLabel;
    public JLabel endDateDayLabel;
    public JTextField categoryNameField;
    public JTextField discountField;
    public JTextField startDateYearField;
    public JTextField startDateMonthField;
    public JTextField startDateDayField;
    public JTextField endDateYearField;
    public JTextField endDateMonthField;
    public JTextField endDateDayField;
    public JButton addButton;
    private AddSaleCategoryController controller;
    public JButton backButton;

    public AddSaleCategoryView(FactoryModel factoryModel, int invID, String worker){
        this.controller = new AddSaleCategoryController(factoryModel, invID, this, worker);

        categoryNameLabel = new JLabel();
        categoryNameLabel.setBounds(50,30,200,20);
        categoryNameLabel.setText("Category name:");

        categoryNameField = new JTextField();
        categoryNameField.setBounds(150,30,250,20);
        categoryNameField.addActionListener(controller);

        discountLabel = new JLabel();
        discountLabel.setBounds(50,60,200,20);
        discountLabel.setText("Discount:");

        discountField = new JTextField();
        discountField.setBounds(150,60,250,20);
        discountField.addActionListener(controller);

        startDateLabel = new JLabel();
        startDateLabel.setBounds(50,90,200,20);
        startDateLabel.setText("Enter start date -");

        startDateYearLabel = new JLabel();
        startDateYearLabel.setBounds(50,120,200,20);
        startDateYearLabel.setText("Year:");

        startDateYearField = new JTextField();
        startDateYearField.setBounds(150,120,250,20);
        startDateYearField.addActionListener(controller);

        startDateMonthLabel = new JLabel();
        startDateMonthLabel.setBounds(50,150,200,20);
        startDateMonthLabel.setText("Month:");

        startDateMonthField = new JTextField();
        startDateMonthField.setBounds(150,150,250,20);
        startDateMonthField.addActionListener(controller);

        startDateDayLabel = new JLabel();
        startDateDayLabel.setBounds(50,180,200,20);
        startDateDayLabel.setText("Day:");

        startDateDayField = new JTextField();
        startDateDayField.setBounds(150,180,250,20);
        startDateDayField.addActionListener(controller);

        endDateLabel = new JLabel();
        endDateLabel.setBounds(50,210,200,20);
        endDateLabel.setText("Enter end date -");

        endDateYearLabel = new JLabel();
        endDateYearLabel.setBounds(50,240,200,20);
        endDateYearLabel.setText("Year:");

        endDateYearField = new JTextField();
        endDateYearField.setBounds(150,240,250,20);
        endDateYearField.addActionListener(controller);

        endDateMonthLabel = new JLabel();
        endDateMonthLabel.setBounds(50,270,200,20);
        endDateMonthLabel.setText("Month:");

        endDateMonthField = new JTextField();
        endDateMonthField.setBounds(150,270,250,20);
        endDateMonthField.addActionListener(controller);

        endDateDayLabel = new JLabel();
        endDateDayLabel.setBounds(50,300,200,20);
        endDateDayLabel.setText("Day:");

        endDateDayField = new JTextField();
        endDateDayField.setBounds(150,300,250,20);
        endDateDayField.addActionListener(controller);

        addButton = new JButton();
        addButton.setBounds(215,400,70,20);
        addButton.addActionListener(controller);
        addButton.setText("Add");

        backButton = new JButton();
        backButton.setBounds(10,420,50,30);
        backButton.addActionListener(controller);
        backButton.setText("<<");

        this.setTitle("Add category");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setSize(500, 500);
        this.setVisible(true);
        this.add(categoryNameLabel);
        this.add(discountLabel);
        this.add(startDateLabel);
        this.add(startDateYearLabel);
        this.add(startDateMonthLabel);
        this.add(startDateDayLabel);
        this.add(endDateLabel);
        this.add(endDateYearLabel);
        this.add(endDateMonthLabel);
        this.add(endDateDayLabel);
        this.add(categoryNameField);
        this.add(discountField);
        this.add(startDateYearField);
        this.add(startDateMonthField);
        this.add(startDateDayField);
        this.add(endDateYearField);
        this.add(endDateMonthField);
        this.add(endDateDayField);
        this.add(addButton);
        this.add(backButton);
    }

    public void finish(String output, String worker){
        this.dispose();
        new PopUpView(output, worker);
    }
}
