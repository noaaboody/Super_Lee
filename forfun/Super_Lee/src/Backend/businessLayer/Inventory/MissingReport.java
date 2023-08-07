package Backend.businessLayer.Inventory;

import java.util.Map;

public class MissingReport extends Report{
    private Map<invItem, Integer> missingItems;

    public MissingReport(Map<invItem, Integer> missingItems) {
        super();
        this.missingItems = missingItems;
    }

    public String toString(){
        String missingReport = "Missing Report:\n" +
                "Date: " + getReportDate() + "\n" + "Missing Items:\n";
        for(Map.Entry<invItem, Integer> entry : missingItems.entrySet()){
            missingReport += "item ID: " + entry.getKey().GetId() + " \nitem Name: " + entry.getKey().GetName() +
                    " | Required Amount: " + entry.getKey().getRequiredAmount() + " | Amount to Order: " + entry.getValue() + "\n";
        }
        return missingReport;
    }
}
