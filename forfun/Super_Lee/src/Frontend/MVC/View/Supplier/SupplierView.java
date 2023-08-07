package Frontend.MVC.View.Supplier;


import Backend.businessLayer.Suppliers.PaymentConditions;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.time.DayOfWeek;
import java.util.LinkedList;
import java.util.List;

public class SupplierView extends JFrame {
	private JTextField supplierIdField= new JTextField();
	private JTextField nameField=new JTextField();
	private JCheckBox isMobileCheckBox= new JCheckBox();;
	private JCheckBox isConstantCheckBox= new JCheckBox("Constant"); ;

	private JTextField bankAccountField= new JTextField();
	private JComboBox<PaymentConditions> paymentConditionComboBox;
	private JButton addCardButton= new JButton("Add Supplier Card");;
	private JButton setButton = new JButton("Set");;


	private JButton backButton = new JButton("Back");
	private JButton agreementButton = new JButton("Supplier Agreement");;
	private  JButton getSupplierButton= new JButton("Get Supplier");



	//check boxes for days
	private JCheckBox mondayCheckBox = new JCheckBox("Monday");
	private JCheckBox tuesdayCheckBox = new JCheckBox("Tuesday");
	private JCheckBox wednesdayCheckBox = new JCheckBox("Wednesday");
	private JCheckBox thursdayCheckBox = new JCheckBox("Thursday");
	private JCheckBox fridayCheckBox = new JCheckBox("Friday");
	private JCheckBox saturdayCheckBox = new JCheckBox("Saturday");
	private JCheckBox sundayCheckBox = new JCheckBox("Sunday");


	public SupplierView(){
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(450,300);
		setTitle("Supplier Card");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//setLayout(new GridLayout(5, 2));
		setLayout(null);
		this.setResizable(false);
		JPanel panel = new JPanel();
		panel.setBounds(0,0,435,150);
		panel.setLayout(new FlowLayout());
		JPanel panel2 = new JPanel();
		panel2.setBounds(0,150,360,60);
		panel2.setLayout(new GridLayout(2,2));


		supplierIdField.setColumns(10);
		nameField.setColumns(10);
		isConstantCheckBox.setEnabled(false);
		bankAccountField.setColumns(10);
		paymentConditionComboBox = new JComboBox<>(PaymentConditions.values());


		//check boxes for days

		add(panel);
		add(panel2);
		panel.add(new JLabel("Supplier ID:"));
		panel.add(supplierIdField);
		panel.add(new JLabel("Name:"));
		panel.add(nameField);
		panel.add(new JLabel("Is Mobile:"));
		panel.add(isMobileCheckBox);
		panel.add(new JLabel("Bank Account:"));
		panel.add(bankAccountField);
		panel.add(new JLabel("Payment Condition:"));
		panel.add(paymentConditionComboBox);
		panel.add(addCardButton);
		panel.add(getSupplierButton);
		panel.add(setButton);
		panel.add(backButton);
		panel.add(agreementButton);


		panel2.add(mondayCheckBox);
		panel2.add(tuesdayCheckBox);
		panel2.add(wednesdayCheckBox);
		panel2.add(thursdayCheckBox);
		panel2.add(fridayCheckBox);
		panel2.add(saturdayCheckBox);
		panel2.add(sundayCheckBox);

		setVisible(true);
		//pack();
		//this.controller = controller;
	}


	public List<DayOfWeek> getSelectedDaysOfSupply() {
		List selectedDays = new LinkedList<>();

		// Check each checkbox and add the selected days to the list
		if (mondayCheckBox.isSelected()) {
			selectedDays.add(DayOfWeek.MONDAY);
		}
		if (tuesdayCheckBox.isSelected()) {
			selectedDays.add(DayOfWeek.TUESDAY);
		}
		if (wednesdayCheckBox.isSelected()) {
			selectedDays.add(DayOfWeek.WEDNESDAY);
		}
		if (thursdayCheckBox.isSelected()) {
			selectedDays.add(DayOfWeek.THURSDAY);
		}
		if (fridayCheckBox.isSelected()) {
			selectedDays.add(DayOfWeek.FRIDAY);
		}
		if (saturdayCheckBox.isSelected()) {
			selectedDays.add(DayOfWeek.SATURDAY);
		}
		if (sundayCheckBox.isSelected()) {
			selectedDays.add(DayOfWeek.SUNDAY);
		}

		return selectedDays;
	}


	public void addCardButtonListener(ActionListener listener) {
		addCardButton.addActionListener(listener);
	}
	public void getSupplierButtonListener(ActionListener listener) {
		getSupplierButton.addActionListener(listener);
	}


	public void setButtonListener(ActionListener listener) {
		setButton.addActionListener(listener);
	}

	public void addBackButtonListener(ActionListener listener) {
		backButton.addActionListener(listener);
	}

	public void addAgreementButtonListener(ActionListener listener) {agreementButton.addActionListener(listener);}

	public String getSupplierId() {
		return supplierIdField.getText();
	}

	public String getName() {
		return nameField.getText();
	}

	public boolean isMobileSelected() {
		return isMobileCheckBox.isSelected();
	}
	public boolean isConstantSelected()
	{
		return isConstantCheckBox.isSelected();
	}

	public String getBankAccount() {
		return bankAccountField.getText();
	}

	public PaymentConditions getPaymentCondition() {
		return (PaymentConditions) paymentConditionComboBox.getSelectedItem();
	}
	public void updateName(String result) {nameField.setText(result);}
	public void updateBankAccount(String result) {bankAccountField.setText(result);}
	public void updateIsMobile(boolean check) {isMobileCheckBox.setSelected(check);}
	public void updateIsConstantCheckBox(boolean check){isConstantCheckBox.setSelected(check);}
	public void updatePaymentConditionComboBox(PaymentConditions p){paymentConditionComboBox.setSelectedItem(p);}

	public void setMondayCheckBox(boolean mondayCheckBox) {
		this.mondayCheckBox.setSelected(mondayCheckBox);
	}

	public void setTuesdayCheckBox(boolean tuesdayCheckBox) {
		this.tuesdayCheckBox.setSelected(tuesdayCheckBox);
	}

	public void setWednesdayCheckBox(boolean wednesdayCheckBox) {
		this.wednesdayCheckBox.setSelected(wednesdayCheckBox);
	}

	public void setThursdayCheckBox(boolean thursdayCheckBox) {
		this.thursdayCheckBox.setSelected(thursdayCheckBox);
	}

	public void setFridayCheckBox(boolean fridayCheckBox) {
		this.fridayCheckBox.setSelected(fridayCheckBox);
	}

	public void setSaturdayCheckBox(boolean saturdayCheckBox) {
		this.saturdayCheckBox.setSelected(saturdayCheckBox);
	}

	public void setSundayCheckBox(boolean sundayCheckBox) {
		this.sundayCheckBox.setSelected(sundayCheckBox);
	}
}
