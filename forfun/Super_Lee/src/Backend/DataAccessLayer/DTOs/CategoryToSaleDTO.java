package Backend.DataAccessLayer.DTOs;

public class CategoryToSaleDTO extends DTO{

    private int inventoryID;
    private int categoryID;
    private int saleID;

    public CategoryToSaleDTO(int _inventoryID, int _categoryID, int _saleID) {
        this.inventoryID = _inventoryID;
        this.categoryID = _categoryID;
        this.saleID = _saleID;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    public int getSaleID() {
        return saleID;
    }

    public void setSaleID(int saleID) {
        this.saleID = saleID;
    }
}
