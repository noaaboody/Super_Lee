package Backend.DataAccessLayer.DTOs;

public class ItemToSubSubCategoryDTO extends DTO{

    private int itemID;
    private int subSubCategoryID;

    public ItemToSubSubCategoryDTO(int itemID, int subSubCategoryID) {
        this.itemID = itemID;
        this.subSubCategoryID = subSubCategoryID;
    }
}
