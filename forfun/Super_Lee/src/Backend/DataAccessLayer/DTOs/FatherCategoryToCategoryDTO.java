package Backend.DataAccessLayer.DTOs;

public class FatherCategoryToCategoryDTO extends DTO{

    private int inventoryID;
    private int fatherCategoryID;
    private int childCategoryID;

    public FatherCategoryToCategoryDTO(int _inventoryID, int _fatherCategoryID, int _childCategoryID) {
        this.inventoryID = _inventoryID;
        this.fatherCategoryID = _fatherCategoryID;
        this.childCategoryID = _childCategoryID;
    }

    public int getFatherCategory() {
        return fatherCategoryID;
    }

    public void setFatherCategory(int fatherCategory) {
        this.fatherCategoryID = fatherCategory;
    }

    public int getCategory() {
        return childCategoryID;
    }

    public void setCategory(int category) {
        this.childCategoryID = category;
    }
}
