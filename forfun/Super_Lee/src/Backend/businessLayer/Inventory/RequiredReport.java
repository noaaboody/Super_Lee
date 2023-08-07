package Backend.businessLayer.Inventory;

import java.util.Map;

public class RequiredReport extends Report{

    private Map<invItem, Integer> requiredItems;

    public RequiredReport(Map<invItem, Integer> missingItems) {
        super();
        this.requiredItems = missingItems;
    }

    public String toString(){
        return "Required Report:\n" +
                "Date: " + getReportDate() + "\n" +
                "Required Items: " + requiredItems;
    }

}