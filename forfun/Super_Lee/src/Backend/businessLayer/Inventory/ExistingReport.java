package Backend.businessLayer.Inventory;

import java.util.Map;

public class ExistingReport extends Report{
    private Map<invItem,Integer> existingItems;

    public ExistingReport(Map<invItem,Integer> existingItems) {
        super();
        this.existingItems = existingItems;
    }

    public String toString(){
        String existingReport = "Existing Report:\n" +
                "Date: " + getReportDate() + "\n" +
                "Existing Items: \n";
        for (Map.Entry<invItem,Integer> entry : existingItems.entrySet()){
            existingReport += entry.getKey().GetId() + " - " + entry.getKey().GetName() + " - Total amount: " + entry.getValue() + "\n";
        }
        return existingReport;

    }
}
