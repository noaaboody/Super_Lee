package Backend.DataAccessLayer.DTOs;

public class InvItemDTO extends DTO{
    private int inventoryID;
    private int itemID;
    private int stockLine;
    private int stockShelf;
    private int storeLine;
    private int storeShelf;
    private int stockQuantity;

    public int getInventoryID() {
        return inventoryID;
    }

    public void setInventoryID(int inventoryID) {
        this.inventoryID = inventoryID;
    }

    public int getItemID() {
        return itemID;
    }

    public void setItemID(int itemID) {
        this.itemID = itemID;
    }

    public int getStockLine() {
        return stockLine;
    }

    public void setStockLine(int stockLine) {
        this.stockLine = stockLine;
    }

    public int getStockShelf() {
        return stockShelf;
    }

    public void setStockShelf(int stockShelf) {
        this.stockShelf = stockShelf;
    }

    public int getStoreLine() {
        return storeLine;
    }

    public void setStoreLine(int storeLine) {
        this.storeLine = storeLine;
    }

    public int getStoreShelf() {
        return storeShelf;
    }

    public void setStoreShelf(int storeShelf) {
        this.storeShelf = storeShelf;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public int getStoreQuantity() {
        return storeQuantity;
    }

    public void setStoreQuantity(int storeQuantity) {
        this.storeQuantity = storeQuantity;
    }

    public int getMinimalAmount() {
        return minimalAmount;
    }

    public void setMinimalAmount(int minimalAmount) {
        this.minimalAmount = minimalAmount;
    }

    public int getRequiredAmount() {
        return requiredAmount;
    }

    public void setRequiredAmount(int requiredAmount) {
        this.requiredAmount = requiredAmount;
    }

    public float getStorePrice() {
        return storePrice;
    }

    public void setStorePrice(float storePrice) {
        this.storePrice = storePrice;
    }

    private int storeQuantity;
    private int minimalAmount;
    private int requiredAmount;
    private float storePrice;

    public InvItemDTO(int _inventoryID, int _itemID, int _stockLine, int _stockShelf, int _storeLine, int _storeShelf, int stockQuantity, int storeQuantity, int minimalAmount, int requiredAmount, float storePrice) {
        this.inventoryID = _inventoryID;
        this.itemID = _itemID;
        this.stockLine = _stockLine;
        this.stockShelf = _stockShelf;
        this.storeLine = _storeLine;
        this.storeShelf = _storeShelf;
        this.stockQuantity = stockQuantity;
        this.storeQuantity = storeQuantity;
        this.minimalAmount = minimalAmount;
        this.requiredAmount = requiredAmount;
        this.storePrice = storePrice;
    }

}
