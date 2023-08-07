package Backend.DataAccessLayer.DTOs;

public class OrderItemDetailsDTO extends DTO{

    private int orderId;
    private int itemId;
    private String itemName;
    private int quantity;
    private double initialPrice;
    private double finalPrice;

    public OrderItemDetailsDTO(int orderId, int itemId, String itemName, int quantity, double initialPrice, double finalPrice) {
        this.orderId = orderId;
        this.itemId = itemId;
        this.itemName = itemName;
        this.quantity = quantity;
        this.initialPrice = initialPrice;
        this.finalPrice = finalPrice;
    }

    public int getOrderId() {
        return orderId;
    }

    public int getItemId() {
        return itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getInitialPrice() {
        return initialPrice;
    }

    public double getFinalPrice() {
        return finalPrice;
    }
}
