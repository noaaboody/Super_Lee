package Backend.businessLayer;

import Backend.DataAccessLayer.SupplierDALC;
import Backend.businessLayer.OrderCalculators.ConstantOrderCalculator;
import Backend.businessLayer.OrderCalculators.ImmediateOrderCalculator;
import Backend.businessLayer.Suppliers.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

//this class meant to connect between the modules of Supplier and Inventory
//TODO: add acceptReport method;
//acceptReport gets a missingItemsReport and calls makeOrder in SupplierPack accordingly
//saving the data of the order should occur in this class
public class OrderHandler {

    Map<Integer,List<Order>> idToPrevOrder;
    Map<Integer, List<Order>> idToFutureOrder;
    private final SupplierController supplierController;
    private SupplierDALC DALC;
    private int orderId;


    public OrderHandler(){
        supplierController = new SupplierController();
        idToPrevOrder= new HashMap<>();

        DALC = DALC.getInstance();
        orderId = DALC.getMaxOrderId() + 1;
        idToFutureOrder = DALC.uploadFutureOrders();
        if (idToFutureOrder == null){
            idToFutureOrder= new HashMap<>();
        }
    }

//===============================================================================================================
//                    Functional Functions
//===============================================================================================================
    /**
     *
     * @param itemToQuantity item to quantity
     * @param address the address of the branch who's committing the order
     * @return a mpa of edited and newly made orders
     */
    public Map<Integer,Order> commitInstantOrder(Map<Integer, Integer> itemToQuantity, String address)//TODO TODO TODO
    {
        ImmediateOrderCalculator cal= new ImmediateOrderCalculator(this,address);
        return cal.MakeOrder(itemToQuantity,address);
    }

    /**
     *
     * @param itemToQuan item to quantity
     * @param address address of order
     */
    public void acceptRequiredReport(Map<Integer,Integer> itemToQuan,String address, int supplierId){// TODO: IMPLEMENT THIS
        ConstantOrderCalculator cal= new ConstantOrderCalculator(this,address,supplierId);
        Map<DayOfWeek,Order>orders = cal.MakeOrder(itemToQuan);
        for (Order order:orders.values())
        {
            Map<Integer,Order> map= new HashMap<>();
            map.put(order.GetOrderId(),order);
            handleNewOrderMap(map);
        }

    }

    public List<Order>  acceptMissingReport(Map<Integer,Integer> itemToQuan,String address){// TODO: IMPLEMENT THIS
        Map<Integer,Order> orders = commitInstantOrder(itemToQuan, address);
        List<Order> ret= orders.values().stream().filter(order -> order.getDueDate().getDayOfWeek().equals(LocalDate.now().getDayOfWeek())).toList();
        handleNewOrderMap(orders);
        return ret;
    }


    /**\
     *
     * @param orders new orders to handle, add/ update orders from map
     */
    private void handleNewOrderMap(Map<Integer,Order> orders)
    {
        for (Integer supplierId : orders.keySet())
        {
            Order order = orders.get(supplierId);
            if (order.getDueDate().isAfter(LocalDate.now()))
            {
                if (idToFutureOrder.containsKey(supplierId))
                {
                    idToFutureOrder.get(supplierId).removeIf(order1 -> order1.GetOrderId()==order.GetOrderId());
                    idToFutureOrder.get(supplierId).add(order);
                    DALC.removeOrder(order);
                    DALC.insertOrder(order);
                }
                else
                {
                    List<Order> list= new LinkedList<>();
                    list.add(order);
                    idToFutureOrder.put(supplierId,list);
                    DALC.insertOrder(order);
                }
            }
            else
            {
                if (idToPrevOrder.containsKey(supplierId))
                {
                    idToPrevOrder.get(supplierId).removeIf(order1 -> order1.GetOrderId()==order.GetOrderId());
                    idToPrevOrder.get(supplierId).add(order);
                    idToPrevOrder.get(supplierId).add(order);
                    DALC.removeOrder(order);
                    DALC.insertOrder(order);
                }
                else
                {
                    List<Order> list= new LinkedList<>();
                    list.add(order);
                    idToPrevOrder.put(supplierId,list);
                    DALC.insertOrder(order);
                }
            }
        }
    }

    /**
     * removes the quantity given if exists, less or non if not exists in order
     * @param itemId the item id
     * @param quantity the quantity needed to be decreased
     * @param orderId the order id
     * @return string indicating the outcome of the procedure
     */
    public String decreaseItemQuanFromOrder(int itemId, int quantity, int orderId)
    {
        boolean removed=false;
        for (List<Order> orderList : idToFutureOrder.values())
        {
            for(Order order: orderList)
            {
                if(order.GetOrderId()==orderId)
                {
                    int inOrder= order.getItemQuantity(itemId);
                    int decreased;
                    if (inOrder < quantity) {
                        order.setItemQuantity(itemId, 0);
                        decreased=inOrder;
                    }
                    else {
                        order.setItemQuantity(itemId, inOrder - quantity);
                        decreased=quantity;
                    }
                    calculateOrderPrice(order,supplierController.getSupplierAgreement(order.getSupplierID()));
                    return  "removed "+decreased+" pieces of item id: "+itemId;
                }
            }
        }
        if (!removed)
            throw new RuntimeException("no order was found under id: "+orderId);
        return"";
    }




//===============================================================================================================
//                    add Functions
//===============================================================================================================











//===============================================================================================================
//                    Get Functions
//===============================================================================================================

    public List<Order> getPreviousOrders(Integer supplierId)
    {
        if (!supplierController.isSupplierExists(supplierId)) throw new RuntimeException("supplier id "+supplierId+ " does not exist in the system");
        if(!idToPrevOrder.containsKey(supplierId)) throw new RuntimeException("supplier has no previous orders in the system");
        return idToPrevOrder.get(supplierId);

    }


    public SupplierController getSupplierController()
    {
        return  this.supplierController;
    }
    public Map<Integer, List<Order>> getIdToFutureOrder()
    {
        return idToFutureOrder;
    }


    /**
     *
     * @param itemId the items id
     * @param quantity the items needed quantity
     * @param address the address
     * @return a pair of supplier agreement List and orders list that can provide the item tomorrow ot today
     */
    public Pair<List<SupplierAgreement>,List<Order>> getFastestSupplierForItem(int itemId, int quantity, String address)
    {
        //phase 1: well find immediate suppliers
        List<SupplierCard> canSupplyToday = supplierController.getSuppliersMap().values().stream().filter(supplierCard  -> !supplierController.isConstantSupply(supplierCard.getSupplierId())).toList();//finding all suppliers that can supply tomorrow
        Pair<List<SupplierAgreement>,Integer> p = filterSuppliersByItem(canSupplyToday,itemId);
        if(p.second >=quantity) return new Pair<>(p.first,null);

        //phase 2, well find suppliers due to tomorrow
        DayOfWeek tomorrow= LocalDate.now().getDayOfWeek().plus(1);
        List<SupplierCard> canSupplyTomorrow = supplierController.getDaysToConstSupplier().get(tomorrow);
        p=filterSuppliersByItem(canSupplyTomorrow,itemId);
        int amountCanProvide= p.second;
        List<SupplierAgreement> agreements=p.first;
        List<Order>relevantOrders= new LinkedList<>();
        for (List<Order> orders: idToFutureOrder.values())//int j=0; j<idToFutureOrder.values();j++
        {
            for(Order order:orders)
            {
                if (order.getDueDate().getDayOfWeek().equals(tomorrow)) {
                    if (order.getOrderAddress().equals(address) && agreements.stream().anyMatch(agreement -> agreement!=null && agreement.getSupplierId() == order.getSupplierID())) {
                        amountCanProvide -= order.GetItemToQuantity().get(itemId);
                        relevantOrders.add(order);
                    }
                }
            }
        }
        if (amountCanProvide<quantity) throw new RuntimeException("no supplier can supply the amount of itemId: "+ itemId+" by tomorrow");
        return new Pair<>(agreements,relevantOrders);
    }

//getAllItemSuppliers


    public int getOrderId() {
        return orderId;
    }

    public List<Order> getOrdersDueToday()
    {
        List<Order> ret= new LinkedList<>();
        for(List<Order> orders:idToFutureOrder.values())
        {
            ret.addAll(orders.stream().filter(order -> order.getDueDate().getDayOfWeek().equals(LocalDate.now().getDayOfWeek())).toList());
        }
        for (Order order:ret)
        {
            idToFutureOrder.remove(order.getSupplierID());
            if (!idToPrevOrder.containsKey(order.getSupplierID())) idToPrevOrder.put(order.getSupplierID(),new LinkedList<>());//only if not on map

            idToPrevOrder.get(order.getSupplierID()).add(order);//do for all
        }
        return  ret;
    }

//===============================================================================================================
//                    Set Functions
//===============================================================================================================


    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }


//===============================================================================================================
//                    private Functions
//===============================================================================================================

    /**
     *
     * @param sups list of suppliers
     * @return list of agreements for suppliers that can supply the product and the amount they provide.
     */
    private Pair<List<SupplierAgreement>,Integer> filterSuppliersByItem(List<SupplierCard> sups, int itemId)
    {
        List<SupplierAgreement> supplierAgreementList = new LinkedList<>();
        int totalAvailableAmount=0;
        SupplierAgreement agreement;
        for (SupplierCard card: sups) {
            agreement = card.getSupplierAgreement();
            if (agreement != null && agreement.getItems().contains(itemId)){

                supplierAgreementList.add(agreement);
                totalAvailableAmount+=supplierController.getSupplierItemLimit(agreement.getSupplierId(),itemId);
            }


        }
        return new Pair<>(supplierAgreementList,totalAvailableAmount);
    }

    private void calculateOrderPrice(Order order, SupplierAgreement agreement)
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
}