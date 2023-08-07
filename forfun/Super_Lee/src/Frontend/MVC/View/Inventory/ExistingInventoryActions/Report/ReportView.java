package Frontend.MVC.View.Inventory.ExistingInventoryActions.Report;


import Frontend.MVC.Controller.Inventory.ExistingInventoyActions.Report.ReportController;
import Frontend.MVC.Model.FactoryModel;

import javax.swing.*;

public class ReportView extends JFrame {
    public ReportController controller;
    public JButton ReportDamagedItemButton;
    public JButton missingReport;
    public JButton existingReport;
    public JButton existingReportByCategories;
    public JButton DefectiveReport;
    public JButton backButton;

    public ReportView(FactoryModel factoryModel, int invID, String worker) {
        controller = new ReportController(factoryModel, this, invID, worker);

        ReportDamagedItemButton = new JButton();
        ReportDamagedItemButton.setBounds(100,50,250,50);
        ReportDamagedItemButton.addActionListener(controller);
        ReportDamagedItemButton.setText("Report About Damaged Item");

        missingReport = new JButton();
        missingReport.setBounds(100,100,250,50);
        missingReport.addActionListener(controller);
        missingReport.setText("get missing items Report");

        existingReport = new JButton();
        existingReport.setBounds(100,150,250,50);
        existingReport.addActionListener(controller);
        existingReport.setText("get existing items Report");

        existingReportByCategories = new JButton();
        existingReportByCategories.setBounds(100,200,250,50);
        existingReportByCategories.addActionListener(controller);
        existingReportByCategories.setText("get existing items Report By Categories");

        DefectiveReport = new JButton();
        DefectiveReport.setBounds(100,250,250,50);
        DefectiveReport.addActionListener(controller);
        DefectiveReport.setText("get Defectives items Report");

        backButton = new JButton();
        backButton.setBounds(10,420,50,30);
        backButton.addActionListener(controller);
        backButton.setText("<<");

        this.setTitle("Report Actions Window");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLayout(null);
        this.setSize(500, 500);
        this.setVisible(true);
        this.add(ReportDamagedItemButton);
        this.add(missingReport);
        this.add(existingReport);
        this.add(existingReportByCategories);
        this.add(DefectiveReport);
        this.add(backButton);
    }
}
