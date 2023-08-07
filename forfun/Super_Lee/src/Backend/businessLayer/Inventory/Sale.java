package Backend.businessLayer.Inventory;

import Backend.DataAccessLayer.DTOs.SaleDTO;

import java.time.LocalDate;

import static java.lang.Integer.parseInt;

public class Sale{

    private int inventoryID;
    private int saleID;
    private float reduction;
    private String saleType;
    private LocalDate startDate;
    private LocalDate endDate;


    public Sale(int inventoryID, int saleID, String saleType, float discount, LocalDate startDate, LocalDate endDate) {
        this.inventoryID = inventoryID;
        this.saleID = saleID;
        this.saleType = saleType;
        this.reduction = discount;
        this.endDate = endDate;
        this.startDate = startDate;
    }

    public Sale(SaleDTO sale){
        this.inventoryID = sale.getInventoryID();
        this.saleID = sale.getSaleID();
        this.saleType = sale.getSaleType();
        this.reduction = sale.getReduction();
        this.endDate = getDate(sale.getEndDate());
        this.startDate = getDate(sale.getStartDate());
    }

    private static LocalDate getDate(String dateString){
        String[] splitedString = dateString.split("-");
        int year = parseInt(splitedString[0]);
        int month = parseInt(splitedString[1]);
        int day = parseInt(splitedString[2].split("T")[0]);
        return LocalDate.of(year,month,day);
    }

    public int getInventoryID() {
        return inventoryID;
    }

    public int getSaleID() {
        return saleID;
    }

    public String getSaleType() {
        return saleType;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public float getReduction() {
        return reduction;
    }

    public void setReduction(float reduction) {
        this.reduction = reduction;
    }
}