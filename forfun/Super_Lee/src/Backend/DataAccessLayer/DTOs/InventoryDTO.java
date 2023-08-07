package Backend.DataAccessLayer.DTOs;

public class InventoryDTO extends DTO{

    private int ID;
    private String address;
    private int categoryIDCounter;
    private int saleIDCounter;

    public InventoryDTO(int _ID, String _address, int _categoryIDCounter, int _saleIDCounter) {
        this.ID = _ID;
        this.address = _address;
        this.categoryIDCounter = _categoryIDCounter;
        this.saleIDCounter = _saleIDCounter;
    }

    public int getID() {
        return ID;
    }

    public String getAddress() {
        return address;
    }

    public int getCategoryIDCounter() {
        return categoryIDCounter;
    }

    public int getSaleIDCounter() {
        return saleIDCounter;
    }
}
