package Backend.DataAccessLayer.DTOs;

public class SubCategoryDTO extends DTO{

    private int inventoryID;
    private int subCategoryID;
    private String subCategoryName;

    public SubCategoryDTO(int _inventoryID, int _subCategoryID, String _subCategoryName) {
        this.inventoryID = _inventoryID;
        this.subCategoryID = _subCategoryID;
        this.subCategoryName = _subCategoryName;
    }

    public int getInventoryID() {
        return inventoryID;
    }

    public void setInventoryID(int inventoryID) {
        this.inventoryID = inventoryID;
    }

    public int getSubCategoryID() {
        return subCategoryID;
    }

    public void setSubCategoryID(int subCategoryID) {
        this.subCategoryID = subCategoryID;
    }

    public String getSubCategoryName() {
        return subCategoryName;
    }

    public void setSubCategoryName(String subCategoryName) {
        this.subCategoryName = subCategoryName;
    }
}
