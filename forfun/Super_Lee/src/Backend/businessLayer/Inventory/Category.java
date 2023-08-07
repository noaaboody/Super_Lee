package Backend.businessLayer.Inventory;

import Backend.DataAccessLayer.DTOs.CategoryDTO;
import Backend.DataAccessLayer.DTOs.DTO;

import java.util.HashMap;
import java.util.Map;

public class Category {

    private String name;
    private int categoryID;
    private Map<String,SubCategory> subCategories;
    private Sale sale;
    private CategoryDTO categoryDTO;

    /*
    constructor of Category that have subCategories.
    */
    public Category(int inventoryID ,String name,int categoryID){
        this.name = name;
        this.categoryID = categoryID;
        subCategories = new HashMap<>();
        this.sale = null;
        this.categoryDTO = new CategoryDTO(inventoryID,categoryID,name);
    }

    public Category(DTO dto){}

    public int getCategoryID(){
            return categoryID;
        }

    public Map<String,SubCategory> getSubCategories(){
            return subCategories;
        }

    /*
    set sale to Category.
    */
    public void setSale(int saleID,Sale sale){
        this.sale = sale;
        for (Map.Entry<String,SubCategory> entry : subCategories.entrySet()){
            entry.getValue().setSale(saleID,sale);
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
        for (Map.Entry<String,SubCategory> entry : subCategories.entrySet()){
            entry.getValue().removeSale(saleID);
        }
    }

    /*
    add sub category to fatherCategory
    */
    public void addSubCategory(String name,SubCategory subcategory){
        subCategories.put(name,subcategory);
    }
}

