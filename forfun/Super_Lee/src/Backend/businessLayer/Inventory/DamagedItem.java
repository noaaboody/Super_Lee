package Backend.businessLayer.Inventory;

import Backend.DataAccessLayer.DTOs.DamageAndExpirationProductDTO;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DamagedItem {
    private LocalDate reportDate;
    private Integer itemID;
    private List<Integer> productIDs;
    private List<DamageAndExpirationProductDTO> damageAndExpirationProductDTO;

    public DamagedItem(int inventoryID,int itemID, List<Integer> productID){
        this.reportDate = LocalDate.now();
        this.itemID = itemID;
        this.productIDs = productID;
        damageAndExpirationProductDTO = new ArrayList<>();
        for (int id : productID){
            DamageAndExpirationProductDTO DTO = new DamageAndExpirationProductDTO(inventoryID,id,itemID,null,1);
            damageAndExpirationProductDTO.add(DTO);
        }

    }

    public int getItemID() {
        return itemID;
    }

    public void setItemID(Integer itemID) {
        this.itemID = itemID;
    }

    public List<Integer> getProductID() {
        return productIDs;
    }

    public void addProductID(int productID) {
        productIDs.add(productID);
    }


    public LocalDate getReportDate(){
        return reportDate;
    }

    public List<DamageAndExpirationProductDTO> getDamageAndExpirationProductDTO() {
        return damageAndExpirationProductDTO;
    }
}
