package Backend.businessLayer.Inventory;

import Backend.DataAccessLayer.DTOs.SubCategoryDTO;

import java.util.HashMap;
import java.util.Map;

public class SubCategory {

    private String name;
    private int categoryID;
    private Map<String,SubSubCategory> subSubCategories;
    private Sale sale;
    private SubCategoryDTO subCategoryDTO;

    public SubCategory(String name, int categoryID) {
        this.name = name;
        this.categoryID = categoryID;
        this.subSubCategories = new HashMap<>();
        this.sale = null;
    }

    public Map<String,SubSubCategory> getSubSubCategories(){
        return subSubCategories;
    }

    public void addSubSubCategory(String name, SubSubCategory subSubCategory){
        this.subSubCategories.put(name,subSubCategory);
    }

    public void setSale(int saleID, Sale sale){
        this.sale = sale;
        for (Map.Entry<String,SubSubCategory> entry : subSubCategories.entrySet()){
            entry.getValue().setSale(saleID,sale);
        }
    }

    public int getCategoryID() {
        return categoryID;
    }

    public Sale getSale() {
        return sale;
    }

    /*
    remove sale from Category.
    */
    public void removeSale(int saleID) throws Exception {
        this.sale = null;
        for (Map.Entry<String,SubSubCategory> entry : subSubCategories.entrySet()){
            entry.getValue().removeSale(saleID);
        }
    }

}
