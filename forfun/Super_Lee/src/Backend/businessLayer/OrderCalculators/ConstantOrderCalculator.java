package Backend.businessLayer.OrderCalculators;

import Backend.businessLayer.OrderHandler;
import Backend.businessLayer.Suppliers.ConditionForDiscount;
import Backend.businessLayer.Suppliers.Order;
import Backend.businessLayer.Suppliers.SupplierAgreement;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class  ConstantOrderCalculator implements OrderCalculator {

    OrderHandler orderHandler;
    String address;
    Map<DayOfWeek,Order> ordersByDay;
    int supplierId;

    public ConstantOrderCalculator(OrderHandler orderHandler , String address, int supplierId)
    {
        this.orderHandler = orderHandler;
        this.address=address;
        ordersByDay = getSuppliersFutureOrders(supplierId,address);
        this.supplierId=supplierId;

    }
    public Map<DayOfWeek, Order> MakeOrder(Map<Integer, Integer> itemToQuantity)
    {
        List<DayOfWeek> days= orderHandler.getSupplierController().getSupplierDaysOfAvailableSupply(supplierId);

        for (Integer itemId:itemToQuantity.keySet())
        {
            if (!canSupplyAmountOfItem(itemId,itemToQuantity.get(itemId),days)) throw new RuntimeException("supplier cant supply current amount of itemId:"+ itemId+" please enter a smaller amount");
            Map<DayOfWeek,Integer> dayToAmount = findBestDaysToOrder(itemId,itemToQuantity.get(itemId));
            for (DayOfWeek day:dayToAmount.keySet())
            {
                updateOrderMap(day,itemId,dayToAmount.get(day));
            }
        }
        return ordersByDay;
    }

    public void updateOrderMap(DayOfWeek day,int itemId,int quantity)
    {
        if (ordersByDay.containsKey(day))
            editOrder(itemId,quantity,ordersByDay.get(day));
        else addItemToOrderMap(itemId,quantity,day);
    }
    @Override
    public void editOrder(Integer itemId, Integer quantity, Order order)
    {
        boolean notExists=!order.GetItemToQuantity().containsKey(itemId);
        if (notExists)  order.addItem(itemId,orderHandler.getSupplierController().getAllItems().get(itemId).GetName(),quantity);
        else   order.setItemQuantity(itemId, order.GetItemToQuantity().get(itemId) + quantity);
        order.setItemInitialPrice(itemId,orderHandler.getSupplierController().getSuppliers().get(supplierId).getSupplierAgreement().getItemPrice(itemId));
        order.setItemIdToName(itemId,orderHandler.getSupplierController().getAllItems().get(itemId).GetName());
        calculateOrderPrice(order);
    }

    public void addItemToOrderMap( int itemId, int quantity,DayOfWeek day)
    {
        Order newOrder = new Order(orderHandler.getOrderId(),supplierId,address,findDateByDay(day));
        newOrder.addItem(itemId,orderHandler.getSupplierController().getAllItems().get(itemId).GetName(),quantity);
        newOrder.setItemInitialPrice(itemId,orderHandler.getSupplierController().getSupplierByID(supplierId).getSupplierAgreement().getItemPrice(itemId));
        orderHandler.setOrderId(orderHandler.getOrderId()+1);
        calculateOrderPrice(newOrder);
        ordersByDay.put(day,newOrder);
    }


    private void calculateOrderPrice(Order order)
    {
        SupplierAgreement agreement = orderHandler.getSupplierController().getSupplierByID(supplierId).getSupplierAgreement();
        Map<Integer,Integer> itemsToQuan= order.GetItemToQuantity();
        for (Integer itemId:itemsToQuan.keySet())
        {
            double itemPrice=calculateItemPrice(itemId,itemsToQuan.get(itemId));
            order.putItemFinalPrice(itemId,itemPrice);
        }

        order.calculateTotalPrice();
        double maxDiscount=0.0;
        for(ConditionForDiscount c: agreement.getConditionsList())
        {
            double discount= c.getDiscountForPrice(order.getTotalPrice());
            if (discount>maxDiscount)
                maxDiscount=discount;
        }
        order.applyTotalDiscount(maxDiscount);
    }

    private double calculateItemPrice (int itemId,int quantity)
    {
        SupplierAgreement agreement = orderHandler.getSupplierController().getSupplierByID(supplierId).getSupplierAgreement();
        double maxDiscount=0;
        for(ConditionForDiscount c: agreement.getConditionsList())
        {
            if(c.isForQuantity()) {
                double discount = c.GetDiscountForItem(itemId, quantity);
                if (discount > maxDiscount)
                    maxDiscount = discount;
            }
        }
        return (1-maxDiscount/100)*(agreement.getItemsToPrice().get(itemId));
    }

    private Map<DayOfWeek,Order> getSuppliersFutureOrders(int supplierId, String address)
    {
        Map<DayOfWeek,Order> ret= new HashMap<>();
        List<Order> orders= orderHandler.getIdToFutureOrder().get(supplierId);
        if (orders != null){
            for (Order order: orders)
            {
                if (order.getOrderAddress().equals(address))
                    ret.put(order.getDueDate().getDayOfWeek(),order);
            }
        }
        return ret;
    }

    private Map<DayOfWeek,Integer> findBestDaysToOrder(int itemId, int quantity)
    {
        Map<DayOfWeek,Integer> dayToAmount=new HashMap<>();
        List<DayOfWeek> days= orderHandler.getSupplierController().getSupplierDaysOfAvailableSupply(supplierId);
        if (days.isEmpty()) throw new RuntimeException("no available day of supply for supplier: "+supplierId);
        if (canSupplyInOneOrder(itemId,quantity,days))  dayToAmount.put(findBestSingleDay(itemId,quantity,days),quantity);
        else  dayToAmount = findBestSplitDay(itemId,quantity,days);

        return dayToAmount;
    }

    private boolean canSupplyInOneOrder(int itemId, int quantity,List<DayOfWeek> days)
    {

        int maxAmountForItem= orderHandler.getSupplierController().getSuppliers().get(supplierId).getSupplierAgreement().getItemsToQuantity().get(itemId);
        for (DayOfWeek day:days)
        {
            if (ordersByDay.containsKey(day))
            {
                if(ordersByDay.get(day).GetItemQuantity(itemId)+quantity<= maxAmountForItem)
                    return true;
            }
            else if (maxAmountForItem>=quantity) return true;
        }
        return false;
    }

    private DayOfWeek findBestSingleDay(int itemId, int quantity,List<DayOfWeek> days)
    {
        int maxAmountForItem= orderHandler.getSupplierController().getSuppliers().get(supplierId).getSupplierAgreement().getItemsToQuantity().get(itemId);
        double minPrice=Double.MAX_VALUE;
        DayOfWeek bestDay=days.get(0);
        for (DayOfWeek day: days)
        {
            Order newOrder = new Order(1,supplierId,address,LocalDate.now());
            if(ordersByDay.containsKey(day)&& ordersByDay.get(day)!=null)
                newOrder.setItemToQuantity(ordersByDay.get(day).GetItemToQuantity());

            if(newOrder.GetItemQuantity(itemId)+quantity<= maxAmountForItem) {
                newOrder.setItemQuantity(itemId, newOrder.GetItemQuantity(itemId) + quantity);
                newOrder.putItemFinalPrice(itemId,calculateItemPrice(itemId,newOrder.GetItemQuantity(itemId)));
                calculateOrderPrice(newOrder);
                double price= newOrder.getTotalPrice();
                if (price<minPrice) bestDay=day;
            }
        }
        return bestDay;
    }

    //TODO
    private Map<DayOfWeek,Integer> findBestSplitDay(int itemId, int quantity,List<DayOfWeek> days)
    {
        Map<DayOfWeek,Integer> ret = new HashMap<>();
        Map<DayOfWeek,Integer> dayToAvailableAmount= new HashMap<>();


        for (DayOfWeek day:days)
        {
            dayToAvailableAmount.put(day,AmountLeftInOrderForItem(itemId,day));
        }

        int leftAmount=quantity;
        while (leftAmount>0)
        {
            Integer maxAmount=0;
            for (Integer amount:dayToAvailableAmount.values())//search for maximum amount to order from Day
            {
                if (amount>maxAmount)
                {
                    if (amount>leftAmount)
                        maxAmount=leftAmount;
                    else maxAmount=amount;
                }
            }

            DayOfWeek bestDay= days.get(0);
            double minPrice=Double.MAX_VALUE;
            for (DayOfWeek day: dayToAvailableAmount.keySet())//compare all prices in respect to max amount
            {
                if(dayToAvailableAmount.get(day)>=maxAmount)
                {
                    Order newOrder = new Order(1,supplierId,address,LocalDate.now());
                    if(ordersByDay.containsKey(day)&& ordersByDay.get(day)!=null)
                        newOrder.setItemToQuantity(ordersByDay.get(day).GetItemToQuantity());

                    newOrder.setItemQuantity(itemId, newOrder.GetItemQuantity(itemId) + quantity);
                    newOrder.putItemFinalPrice(itemId,calculateItemPrice(itemId,newOrder.GetItemQuantity(itemId)));
                    calculateOrderPrice(newOrder);
                    double price= newOrder.getTotalPrice();
                    if (price<minPrice) bestDay=day;

                }
            }
            ret.put(bestDay,maxAmount);//update maps
            dayToAvailableAmount.remove(bestDay);
            leftAmount =leftAmount - maxAmount;
        }
        return  ret;
    }

    private int AmountLeftInOrderForItem(int itemId,DayOfWeek day)
    {
        if (ordersByDay.containsKey(day)&&ordersByDay.get(day).GetItemToQuantity().containsKey(itemId))
            return this.orderHandler.getSupplierController().getSupplierByID(supplierId).getSupplierAgreement().
                    getItemsToQuantity().get(itemId) - ordersByDay.get(day).GetItemToQuantity().get(itemId);

        else return this.orderHandler.getSupplierController().getSupplierByID(supplierId).getSupplierAgreement().
                getItemsToQuantity().get(itemId);
    }

    private LocalDate findDateByDay(DayOfWeek day)
    {
        LocalDate today = LocalDate.now();
        return today.with(TemporalAdjusters.next(day));
    }

    private boolean canSupplyAmountOfItem(int itemId, int quantity,List<DayOfWeek> days)
    {
        int amount=0;
        for (DayOfWeek day:days)
        {
            amount+=AmountLeftInOrderForItem(itemId,day);
        }
        return (quantity<= amount);
    }





}
