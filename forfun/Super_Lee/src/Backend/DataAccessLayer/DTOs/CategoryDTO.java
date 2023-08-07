package Backend.DataAccessLayer.DTOs;

public class CategoryDTO extends DTO {

    private int inventoryID;
    private int categoryID;
    private String categoryName;

    public CategoryDTO(int inventoryID, int categoryID, String categoryName){
        this.inventoryID = inventoryID;
        this.categoryID = categoryID;
        this.categoryName = categoryName;
    }

    public int getInventoryID() {
        return inventoryID;
    }

    public void setInventoryID(int inventoryID) {
        this.inventoryID = inventoryID;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

}
