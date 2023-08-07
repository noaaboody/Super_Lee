package Backend.DataAccessLayer.DTOs;

public class DamageAndExpirationProductDTO extends DTO {

    private int inventoryID;
    private  int productID;
    private  int itemID;
    private  String expirationDate;
    private  int isDamage;

    public DamageAndExpirationProductDTO(int inventoryID,int productID, int itemID, String expirationDate, int isDamage){
        this.productID = productID;
        this.itemID = itemID;
        this.expirationDate = expirationDate;
        this.isDamage = isDamage;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public int getItemID() {
        return itemID;
    }

    public void setItemID(int itemID) {
        this.itemID = itemID;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    public int isDamage() {
        return isDamage;
    }

    public void setDamage(int damage) {
        this.isDamage = damage;
    }
}
