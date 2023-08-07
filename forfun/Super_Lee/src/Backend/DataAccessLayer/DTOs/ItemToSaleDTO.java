package Backend.DataAccessLayer.DTOs;

public class ItemToSaleDTO extends DTO{

    private int inventoryID;
    private int itemID;
    private int saleID;

    public ItemToSaleDTO(int _inventoryID, int _itemID, int _saleID) {
        this.inventoryID = _inventoryID;
        this.itemID = _itemID;
        this.saleID = _saleID;
    }
}
