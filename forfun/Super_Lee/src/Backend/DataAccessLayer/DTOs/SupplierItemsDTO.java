package Backend.DataAccessLayer.DTOs;

public class SupplierItemsDTO extends DTO{

    private int supplierId;
    private int itemId;
    private int serialNum;
    private double price;
    private int quantity;

    public SupplierItemsDTO(int supplierId, int itemId, int serialNum, double price, int quantity) {
        this.supplierId = supplierId;
        this.itemId = itemId;
        this.serialNum = serialNum;
        this.price = price;
        this.quantity = quantity;
    }

    public int getSupplierId() {
        return supplierId;
    }

    public int getItemId() {
        return itemId;
    }

    public int getSerialNum() {
        return serialNum;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }
}
