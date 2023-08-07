package Backend.DataAccessLayer.DTOs;

public class OrdersDTO extends DTO{
    private int orderId;
    private String orderAddress;
    private int supplierId;
    private double totalPrice;
    private String date;
    private int executed;

    public OrdersDTO(int orderId, int supplierId, String orderAddress, double totalPrice , String date, int executed) {
        this.orderId = orderId;
        this.orderAddress = orderAddress;
        this.supplierId = supplierId;
        this.totalPrice = totalPrice;
        this.date = date;
        this.executed = executed;
    }

    public int getOrderId() {
        return orderId;
    }

    public String getOrderAddress() {
        return orderAddress;
    }

    public int getSupplierId() {
        return supplierId;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public String getDate(){return date;}
}
