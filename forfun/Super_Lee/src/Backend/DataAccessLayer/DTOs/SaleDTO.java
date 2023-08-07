package Backend.DataAccessLayer.DTOs;

public class SaleDTO extends DTO{

    private int inventoryID;
    private int saleID;
    private float reduction;
    private String saleType;
    private String startDate;
    private String endDate;

    public SaleDTO(int _inventoryID, int _saleID, float _reduction, String _saleType, String _startDate, String _endDate) {
        this.inventoryID = _inventoryID;
        this.saleID = _saleID;
        this.reduction = _reduction;
        this.saleType = _saleType;
        this.startDate = _startDate;
        this.endDate = _endDate;
    }

    public int getInventoryID() {
        return inventoryID;
    }

    public int getSaleID() {
        return saleID;
    }

    public float getReduction() {
        return reduction;
    }

    public String getSaleType() {
        return saleType;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }
}
