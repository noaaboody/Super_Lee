package Backend.DataAccessLayer.DTOs;

public class SubSubCategoryDTO {

    private int inventoryID;
    private int subSubCategoryID;
    private String subSubCategoryName;

    public SubSubCategoryDTO(int _inventoryID, int _subSubCategoryID, String _subSubCategoryName) {
        this.inventoryID = _inventoryID;
        this.subSubCategoryID = _subSubCategoryID;
        this.subSubCategoryName = _subSubCategoryName;
    }

    public int getInventoryID() {
        return inventoryID;
    }

    public int getSubSubCategoryID() {
        return subSubCategoryID;
    }

    public String getSubSubCategoryName() {
        return subSubCategoryName;
    }
}
