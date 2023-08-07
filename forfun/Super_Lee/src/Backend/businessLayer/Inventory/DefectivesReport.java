package Backend.businessLayer.Inventory;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class DefectivesReport extends Report{
    private LocalDate from;
    private LocalDate until;
    private List<DamagedItem> damagedItemsByPeriod;
    private Map<LocalDate,Map<Integer,List<Integer>>> expiredProducts;

    public DefectivesReport(LocalDate from, LocalDate until,
                            List<DamagedItem> damagedItemsByPeriod, Map<LocalDate,Map<Integer,List<Integer>>> expiredProducts) {
        super();
        this.from = from;
        this.until = until;
        this.damagedItemsByPeriod = damagedItemsByPeriod;
        if (expiredProducts != null){
            this.expiredProducts = expiredProducts;
        }
    }

    public String toString(){
        StringBuilder defectiveReport = new StringBuilder("Defectives Report:\n" + "report date: " + getReportDate() + "\n" +
                "start date: " + from + "\n" +
                "end date: " + until + "\n" + "defective items:\n");
        for (DamagedItem entry : damagedItemsByPeriod) {
            defectiveReport.append(entry.getItemID()).append(": ").append(entry.getProductID()).append("\n");
        }
        defectiveReport.append("expiration dates:\n");
        for (Map.Entry<LocalDate,Map<Integer,List<Integer>>> entry : expiredProducts.entrySet()) {
            defectiveReport.append(entry).append(" :\n");
            for (Map.Entry<Integer,List<Integer>> entry1 : entry.getValue().entrySet()){
                defectiveReport.append("item ID: ").append(entry1.getKey()).append(" products IDs : ").append(entry1.getValue()).append("\n");
            }
        }
        return defectiveReport.toString();

    }




}
