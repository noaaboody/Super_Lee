package Frontend.MVC.Model;

public class ItemModel {
    private int itemId;
    private int stockLine;
    private int stockShelf;

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
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
}