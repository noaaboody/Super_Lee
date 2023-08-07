package Backend.businessLayer.Suppliers;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class Order {
    private final int orderID;
    private final String orderAddress;

    private final int supplierID;
    private final Map<Integer, Integer> itemToQuantity;
    private final Map<Integer, Double> itemToInitialPrice;
    private final Map<Integer, Double> itemToFinalPrice;
    private final Map<Integer, String> itemIdToName;
    private double totalPrice;

    LocalDate dueDate;


    public Order(int orderId, int supplierID, String address, LocalDate dueDate) {
        this.orderID = orderId;
        this.supplierID = supplierID;
        this.orderAddress = address;
        this.itemToQuantity = new HashMap<>();
        this.itemToInitialPrice = new HashMap<>();
        this.itemToFinalPrice = new HashMap<>();
        this.itemIdToName = new HashMap<>();
        this.totalPrice = 0;
        this.dueDate = dueDate;

    }
    public Order() {
        orderID=-1;
        orderAddress="";
        supplierID=-1;
        this.itemToQuantity = new HashMap<>();
        this.itemToInitialPrice = new HashMap<>();
        this.itemToFinalPrice = new HashMap<>();
        this.itemIdToName = new HashMap<>();
    }


//===============================================================================================================
//                    get Functions
//===============================================================================================================

    public String getOrderAddress() {
        return orderAddress;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public int GetItemQuantity(int itemID) {
        if(itemToQuantity.containsKey(itemID))
            return itemToQuantity.get(itemID);
        else return 0;
    }

    public Map<Integer, Integer> GetItemToQuantity() {
        return this.itemToQuantity;
    }

    public Map<Integer, Double> GetItemTOFinalPrice() {
        return this.itemToFinalPrice;
    }

    public Map<Integer, String> getItemIdToName() {
        return itemIdToName;
    }

    public int GetOrderId() {
        return this.orderID;
    }

    public int getItemQuantity(int itemId){return itemToQuantity.get(itemId);}
    public Double GetItemInitialPrice(int itemID) {
        return itemToInitialPrice.get(itemID);
    }
    public Double GetItemFinalPrice(int itemID) {
        return itemToFinalPrice.get(itemID);
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public int getSupplierID() {
        return supplierID;
    }

    //===============================================================================================================
//                    add Functions
//===============================================================================================================
    public void addItem(int itemID, String itemName, int quantity, Double initialPrice, Double finalPrice) {
        itemToQuantity.put(itemID, quantity);
        itemIdToName.put(itemID, itemName);
        itemToFinalPrice.put(itemID, finalPrice);
        itemToInitialPrice.put(itemID, initialPrice);
    }
    public void addItem(int itemID, String itemName, int quantity) {
        itemToQuantity.put(itemID, quantity);
        itemIdToName.put(itemID, itemName);
        itemToFinalPrice.put(itemID, 0.0);
        itemToInitialPrice.put(itemID, 0.0);
    }

//===============================================================================================================
//                    Set Functions
//===============================================================================================================

    public void putItemFinalPrice(int itemId, double finalPrice) {
        itemToFinalPrice.put(itemId, finalPrice);
    }


    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;

    }

    public void setItemToQuantity(Map<Integer, Integer> itemToQuantity) {
        for (Integer itemId : itemToQuantity.keySet()) {
            if (this.itemToQuantity.containsKey(itemId)) {

                this.itemToQuantity.replace(itemId, itemToQuantity.get(itemId));
            } else {
                this.itemToQuantity.put(itemId, itemToQuantity.get(itemId));
            }
        }
    }

    public boolean setItemInitialPrice(int itemId, double initialPrice)
    {
        if(itemToQuantity.containsKey(itemId)){
            itemToInitialPrice.put(itemId,initialPrice); return true;
        }
        return false;
    }

    public void setItemQuantity(int itemId, int quantity) {
        if (quantity==0)
            itemToQuantity.remove(itemId);
        else
            itemToQuantity.put(itemId, quantity);
    }
    public void setItemIdToName(Integer itemId,String name)
    {
        if (itemToQuantity.containsKey(itemId))
            itemIdToName.put(itemId,name);
    }

//===============================================================================================================
//                    Functional Functions
//===============================================================================================================

    public void applyTotalDiscount(Double discount) {

        for (Integer itemId: itemToFinalPrice.keySet())
        {
            double price= itemToFinalPrice.get(itemId);
            itemToFinalPrice.replace(itemId,price * (1 - discount/100));
        }
        calculateTotalPrice();
    }

    public String toString() {
        double discount;
        String str = "";
        str += "Order Id: " + orderID + "\n";
        str += "from supplier No." + supplierID + " to address:" + orderAddress + ".\n";
        for (Integer itemId : itemToQuantity.keySet()) {
            discount = calculateDiscount(itemToInitialPrice.get(itemId), itemToFinalPrice.get(itemId));
            str += "item No.: " + itemId + " | item name: " + itemIdToName.get(itemId) + " | quantity: " + itemToQuantity.get(itemId) +
                    " | Initial price: " + itemToInitialPrice.get(itemId) + " | discount: " + discount + "%" +
                    " | Final price: " + itemToFinalPrice.get(itemId) + "\n";
        }

        str += "Total Price: " + this.totalPrice;
        return str;
    }

    private Double calculateDiscount(Double initialP, Double finalP) {
        return (double) (((initialP - finalP) / initialP) * 1000.0) / 10;
    }


    public void calculateTotalPrice()
    {
        totalPrice=0;
        for (Integer itemId:itemToQuantity.keySet())
        {
            totalPrice+=(itemToQuantity.get(itemId)*(itemToFinalPrice.get(itemId)));
        }
    }
}
