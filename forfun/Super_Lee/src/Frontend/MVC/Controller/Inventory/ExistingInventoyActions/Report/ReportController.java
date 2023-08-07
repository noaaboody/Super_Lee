package Frontend.MVC.Controller.Inventory.ExistingInventoyActions.Report;

import Frontend.MVC.Model.FactoryModel;
import Frontend.MVC.View.Inventory.ExistingInventoryActions.Item.ItemActions.ReportDamagedItemView;
import Frontend.MVC.View.Inventory.ExistingInventoryActions.Report.ExistingReportByCategoriesView;
import Frontend.MVC.View.Inventory.ExistingInventoryActions.Report.ReportView;
import Frontend.MVC.View.Inventory.ExistingInventoryView;
import Frontend.MVC.View.Inventory.InventoryView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

public class ReportController implements ActionListener {
    private ReportView view;
    private FactoryModel factoryModel;
    private int invID;
    private String worker;

    public ReportController(FactoryModel factoryModel, ReportView view, int invID, String worker) {
        this.worker = worker;
        this.factoryModel = factoryModel;
        this.invID = invID;
        this.view = view;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()== view.ReportDamagedItemButton){
            view.dispose();
            new ReportDamagedItemView(factoryModel, invID, worker);
        }
        if(e.getSource()== view.existingReport) {
            view.dispose();
            String existingReport = factoryModel.existingReport(invID);
            JOptionPane.showMessageDialog(null, existingReport, "Existing Report", JOptionPane.INFORMATION_MESSAGE);
            ExistingInventoryView existingInventoryView = new ExistingInventoryView(factoryModel, invID, worker);
            existingInventoryView.setVisible(true);
        }
        if(e.getSource()== view.existingReportByCategories){
            view.dispose();
            new ExistingReportByCategoriesView(factoryModel,invID, worker);
        }
        if(e.getSource()==view.missingReport){
            view.dispose();
            String missingRep = factoryModel.productsWithMinAmount(invID);
            if (missingRep == null){
                JOptionPane.showMessageDialog(null, "There is no missing items", "missing items", JOptionPane.INFORMATION_MESSAGE);
            }
            else {
                JOptionPane.showMessageDialog(null, missingRep , "missing items", JOptionPane.INFORMATION_MESSAGE);
            }
            new ReportView(factoryModel,invID, worker);
        }
        if (e.getSource() == view.DefectiveReport) {
            view.dispose();
            String report;
            try {
                String endDate = null;
                String startDate = JOptionPane.showInputDialog(view, "Enter Start Date (YYYY-MM-DD):");
                if (startDate == null) {
                    // User canceled the input dialog
                    new ReportView(factoryModel, invID, worker);
                }
                else {
                    endDate = JOptionPane.showInputDialog(view, "Enter End Date (YYYY-MM-DD):");
                    if (endDate == null) {
                        // User canceled the input dialog
                        new ReportView(factoryModel, invID, worker);
                    }
                }
                report = factoryModel.defectivesReport(invID, LocalDate.parse(startDate), LocalDate.parse(endDate));
            } catch (Exception exception) {
                report = exception.getMessage();
            }
            JOptionPane.showMessageDialog(null, report, "Missing Items Report", JOptionPane.INFORMATION_MESSAGE);
        }
        if(e.getSource()==view.backButton){
            view.dispose();
            new InventoryView(worker);
        }
    }
}



