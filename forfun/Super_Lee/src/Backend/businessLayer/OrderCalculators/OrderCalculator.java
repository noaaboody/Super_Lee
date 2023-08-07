package Backend.businessLayer.OrderCalculators;

import Backend.businessLayer.Suppliers.Order;

public interface OrderCalculator {


    public void editOrder(Integer itemId, Integer quantity, Order order);

}
