package Backend.businessLayer.Inventory;

import java.time.LocalDate;

public class Report{
    private LocalDate reportDate;

    public Report(){
        this.reportDate = LocalDate.now();
    }

    public LocalDate getReportDate() {
        return reportDate;
    }
}