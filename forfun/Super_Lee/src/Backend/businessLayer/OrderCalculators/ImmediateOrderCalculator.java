package Backend.businessLayer.OrderCalculators;

import Backend.businessLayer.OrderHandler;
import Backend.businessLayer.Suppliers.ConditionForDiscount;
import Backend.businessLayer.Suppliers.Order;
import Backend.businessLayer.Suppliers.Pair;
import Backend.businessLayer.Suppliers.SupplierAgreement;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ImmediateOrderCalculator implements OrderCalculator {
    OrderHandler orderHandler;
    LocalDate today;
    LocalDate tomorrow;
    String address;


    public ImmediateOrderCalculator(OrderHandler orderHandler , String address) {
        this.orderHandler = orderHandler;
        this.address=address;
        today=LocalDate.now();
        tomorrow= LocalDate.now().plusDays(1);
    }

    /**
     * makes new orders
     * @param itemToQuantity item to quantity mao
     * @param address address of branch
     * @return a map of all orders newly made or due to tomorrow
     */
    public Map<Integer, Order> MakeOrder(Map<Integer, Integer> itemToQuantity, String address) {
        Map<Integer,Order> res= new HashMap<>();

        for (Integer itemId: itemToQuantity.keySet()) {
            Pair<List<SupplierAgreement>,List<Order>> pair=orderHandler.getFastestSupplierForItem(itemId,itemToQuantity.get(itemId),address);
            List<SupplierAgreement> validAgreements = pair.first;
            List<Order> validOrders= pair.second;
            boolean isConstant = (validOrders!=null);//if valid orders is null that means that the order is from a non-constant supplier
            if (isConstant)
            {
                for(Order order: validOrders)
                {
                    res.put(order.GetOrderId(),order);//adds order due to tomorrow to the map
                }
                Integer id =findBestSupplierNoSplit(itemId,itemToQuantity.get(itemId),validAgreements,validOrders);

                boolean foundNoSplit = (id != -1);
                if(foundNoSplit){  addItemToOrderMap(id,itemId,itemToQuantity.get(itemId),res); }//one order only for the item was found
                else
                {
                    Map<Integer,Integer> idToAmount= findBestSuppliersSplit(itemId, itemToQuantity.get(itemId), validAgreements, validOrders);
                    updateOrderMap(res,idToAmount,itemId);
                }
            }
            else {
                Integer id =findBestSupplierNoSplit(itemId,itemToQuantity.get(itemId),validAgreements,null);
                boolean foundNoSplit = (id != -1);
                if (foundNoSplit) {  addItemToOrderMap(id,itemId,itemToQuantity.get(itemId),res); }
                else
                {
                    Map<Integer,Integer> idToAmount= findBestSuppliersSplit(itemId, itemToQuantity.get(itemId), validAgreements, null);
                    updateOrderMap(res,idToAmount,itemId);
                }
            }
        }
        return res;
    }

    @Override
    public void editOrder(Integer itemId, Integer quantity, Order order) {
        int supplierId= order.getSupplierID();
        boolean notExists=!order.GetItemToQuantity().containsKey(itemId);
        if (notExists)  order.addItem(itemId,orderHandler.getSupplierController().getAllItems().get(itemId).GetName(),quantity);
        else   order.setItemQuantity(itemId, order.GetItemToQuantity().get(itemId) + quantity);
        order.setItemInitialPrice(itemId,orderHandler.getSupplierController().getSupplierByID(supplierId).getSupplierAgreement().getItemPrice(itemId));
        order.setItemIdToName(itemId,orderHandler.getSupplierController().getAllItems().get(itemId).GetName());
        calculateOrderPrice(order,orderHandler.getSupplierController().getSupplierAgreement(supplierId));
    }


    public void addItemToOrderMap(int supplierId, int itemId, int quantity,Map<Integer,Order> orders) {
        LocalDate dueDate;

        if (orders.containsKey(supplierId)) { editOrder(itemId,quantity,orders.get(supplierId));}
        else
        {
            //make new order and add it to the map
            dueDate = LocalDate.now();
            Order newOrder = new Order(orderHandler.getOrderId(), supplierId, address, dueDate.plusDays(1));
            editOrder(itemId, quantity, newOrder);
            orders.put(supplierId, newOrder);
            orderHandler.setOrderId(orderHandler.getOrderId() + 1);
        }
    }


    /**\
     *
     * @param itemId the items id
     * @param quantity quantity
     * @param agreementList valid agreements that only apply to the date and item needed
     * @param validOrders null if agreement contains non constant suppliers
     * @return a pair object containing a boolean the
     */
    public Integer findBestSupplierNoSplit(int itemId, int quantity, List<SupplierAgreement> agreementList,List<Order> validOrders) {
        boolean isConstant= (validOrders!=null);
        int res= -1;
        double minPrice=Double.MAX_VALUE;
        Map<Integer,Integer> idToAmount =findAmountOfEachSupplier(itemId,validOrders,agreementList);
        for(SupplierAgreement agreement:agreementList)
        {
            if(isConstant&&idToAmount.get(agreement.getSupplierId())<quantity)
            {
                Order order= getOrderBySupplierId(validOrders,agreement.getSupplierId());
                double price = calculateItemPrice(itemId,quantity+order.GetItemQuantity(itemId),agreement);
                if (price<minPrice) {
                    res = agreement.getSupplierId();
                    minPrice=price;
                }
            }
            else if (!isConstant&&idToAmount.get(agreement.getSupplierId())<quantity)
            {
                double price = calculateItemPrice(itemId,quantity,agreement);
                if (price<minPrice) {
                    res = agreement.getSupplierId();
                    minPrice=price;
                }
            }
        }
        return res;
    }

    /**
     *
     * @param itemId item id
     * @param quantity quantity
     * @param agreementList agreement List
     * @param validOrders valid orders
     * @return returns a map representing the best split(he who can supply the most will get more amount)
     */
    public Map<Integer,Integer> findBestSuppliersSplit(int itemId, int quantity, List<SupplierAgreement> agreementList,List<Order> validOrders) {
        Map<Integer,Integer> idToAmountInOrder = new HashMap<>();
        int leftAmount=quantity;
        Map<Integer,Integer> idToAvailableAmount = findAmountOfEachSupplier(itemId,validOrders,agreementList);
        while(leftAmount>0)
        {
            Integer maxAmount=0;
            for (Integer amount:idToAvailableAmount.values())//search for maximum amount to order from supplier
            {
                if (amount>maxAmount)
                {
                    if (amount>leftAmount)
                        maxAmount=leftAmount;
                    else maxAmount=amount;
                }
            }

            int minPriceMaxAmountId=(Integer) idToAvailableAmount.keySet().toArray()[0];
            for (Integer key: idToAvailableAmount.keySet())//compare all prices in respect to max amount
            {
                if(idToAvailableAmount.get(key)>=maxAmount)
                {
                    SupplierAgreement a1=orderHandler.getSupplierController().getSupplierAgreement(minPriceMaxAmountId);
                    SupplierAgreement a2=orderHandler.getSupplierController().getSupplierAgreement(key);
                    minPriceMaxAmountId = whoIsCheaper(a1,a2,itemId,maxAmount);
                }
            }
            idToAmountInOrder.put(minPriceMaxAmountId,maxAmount);
            idToAvailableAmount.remove(minPriceMaxAmountId);
            leftAmount-=maxAmount;
        }


        return idToAmountInOrder;

    }

    /**
     *
     * @param itemId item id

     * @param ordersTOEdit orders that need to be edited
     * @param agreements agreements of relevant suppliers
     * @return return a map<id,amount> which says what amount each supplier can provide in respect to existing order
     */
    private Map<Integer,Integer> findAmountOfEachSupplier(Integer itemId, List<Order> ordersTOEdit, List<SupplierAgreement> agreements) {
        Map<Integer,Integer> idToAmount=new HashMap<>();
        for(SupplierAgreement agreement:agreements)
        {
            if (agreement!=null&&agreement.getItemsToQuantity().containsKey(itemId))
                idToAmount.put(agreement.getSupplierId(),agreement.getItemsToQuantity().get(itemId));
        }
        if (ordersTOEdit!=null) {
            for (Order order : ordersTOEdit) {
                idToAmount.replace(order.getSupplierID(), idToAmount.get(order.getSupplierID()) - order.GetItemToQuantity().get(itemId));
            }
        }
        return idToAmount;
    }

    /**
     *
     * @param order order which price needs to be calculated
     * @param agreement the agreement of the supplier that supplies the order
     */
    private void calculateOrderPrice(Order order,SupplierAgreement agreement)
    {
        Map<Integer,Integer> itemsToQuan= order.GetItemToQuantity();
        for (Integer itemId:itemsToQuan.keySet())
        {
            double itemPrice=calculateItemPrice(itemId,itemsToQuan.get(itemId),agreement);
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

    private double calculateItemPrice (int itemId,int quantity, SupplierAgreement agreement)
    {
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
    private Order getOrderBySupplierId(List<Order> orders,int supplierId)
    {
        if (orders==null) return null;
        Order order= new Order();
        for(Order o:orders)
        {
            if(o.getSupplierID()==supplierId)
                order=o;
        }

        return order;
    }

    private int whoIsCheaper(SupplierAgreement agreement1, SupplierAgreement agreement2,int itemId,int quantity)
    {
        double firstPrice = calculateItemPrice(itemId,quantity,agreement1);
        double secondPrice =calculateItemPrice(itemId,quantity,agreement2);
        return firstPrice<secondPrice?agreement1.getSupplierId():agreement2.getSupplierId();
    }

    /**
     *
     * @param idToOrder id to order map
     * @param idToAmount supplier id to amount of a single product
     */
    private void updateOrderMap( Map<Integer,Order> idToOrder, Map<Integer,Integer>idToAmount,int itemId)
    {
        for (Integer supplierId:idToAmount.keySet()) { addItemToOrderMap(supplierId,itemId,idToAmount.get(supplierId),idToOrder); }//call ad item to order iteratively to update the map
    }

}
