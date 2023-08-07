package Backend.businessLayer.Inventory;

import Backend.DataAccessLayer.DTOs.ItemToSubSubCategoryDTO;
import Backend.DataAccessLayer.DTOs.SubSubCategoryDTO;

import java.util.ArrayList;
import java.util.List;

public class SubSubCategory {

    private String name;
    private int categoryID;
    private List<invItem> items;
    private Sale sale;
    private SubSubCategoryDTO subSubCategoryDTO;
    private ItemToSubSubCategoryDTO itemToSubSubCategoryDTO;

    public SubSubCategory(String name, int categoryID) {
        this.name = name;
        this.categoryID = categoryID;
        this.items = new ArrayList<>();
        this.sale = null;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public List<invItem> getItems() {
        return items;
    }

    public void setSale(int saleID,Sale sale){
        this.sale = sale;
        if (items.size() != 0){
            for (invItem item : items) {
                item.setSale(saleID, sale);
            }
        }
    }

    public Sale getSale() {
        return sale;
    }

    /*
    remove sale from Category.
    */
    public void removeSale(int saleID) throws Exception {
        this.sale = null;
        if (items.size() != 0){
            for (invItem item : items) {
                item.removeSale(saleID);
            }
        }
    }

    public void addItem(invItem item){
        items.add(item);
    }
}
