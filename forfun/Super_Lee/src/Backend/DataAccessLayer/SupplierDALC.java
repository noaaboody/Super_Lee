package Backend.DataAccessLayer;

import Backend.DataAccessLayer.DTOs.*;
import Backend.businessLayer.Item;
import Backend.businessLayer.Suppliers.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;

import static java.lang.Integer.parseInt;


public class SupplierDALC {

    private static SupplierDALC instance;

    private SupplierMapper supplierMapper;
    private OrderMapper orderMapper;
    private ItemMapper itemMapper;
    private AgreementMapper agreementMapper;
    private Map<Integer, Item> loadedItems;

    public SupplierDALC(){
        supplierMapper = new SupplierMapper();
        orderMapper = new OrderMapper();
        itemMapper = new ItemMapper();
        agreementMapper = new AgreementMapper();
        loadedItems = new HashMap<>();
    }

    public static SupplierDALC getInstance(){
        if(instance == null){
            instance = new SupplierDALC();
        }
        return instance;
    }

    public void CleanALL(){//TODO: IMPLEMENT THIS

    }

    public Map<Integer, SupplierCard> uploadAllSuppliers(){
        Map<Integer, SupplierCard> output = new HashMap<>();
        List<SupplierCardDTO> suppliers = supplierMapper.getAllSuppliers();
        for(SupplierCardDTO sup : suppliers){
            int supID = sup.GetId();
            //============================================= AGREEMENT AND CONDITIONS
            AgreementDTO agreementDTO = agreementMapper.getSupplierAgreement(supID);
            SupplierCard Scard = new SupplierCard(supID,sup.GetName(),sup.GetIsMobile(),sup.GetBankAccount(), sup.GetCond(),agreementDTO.GetIsConstSupply());
            SupplierAgreement agreement = new SupplierAgreement(supID);
            agreement.setConditionId(agreementDTO.GetConditionId()+1);
            agreement.setIsConstantSupply(agreementDTO.GetIsConstSupply());
            for(ConditionDetailsDTO cond : agreementMapper.getSupplierConditions(supID)) {
                List<Integer> itemIds = new ArrayList<>();
                for (ConditionItemsDTO condItem : agreementMapper.getSupplierCondItems(supID, cond.getConditionId())) {
                    itemIds.add(condItem.getItemId());
                }
                if(itemIds.size() > 0) {
                    ConditionForDiscount c = new ConditionForDiscount(itemIds, cond.isForPrice(), cond.isForQuantity(), cond.getPercentage(), cond.getNeededAmount());
                    agreement.addCondition(c, cond.getConditionId());
                }
            }
            Scard.setAgreement(agreement);
            //============================================== CONTACTS
            for(ContactsDTO cont : supplierMapper.getSupplierContacts(supID)){
                Scard.AddContact(new Contact(cont.getId(),cont.getName(),cont.getPhoneNumber(),cont.getEmail()));
            }
            //============================================== Items
            for(SupplierItemsDTO supItem: itemMapper.getSupplierItems(supID)){
                ItemDTO iDTO = itemMapper.getItemById(supItem.getItemId());
                if(iDTO != null) {
                    Item i = new Item(iDTO.getItemId(), iDTO.getName(), iDTO.getManufacturer());
                    loadedItems.put(i.GetId(), i);
                    Scard.AddItem(i, supItem.getSerialNum(), supItem.getPrice(), supItem.getQuantity());
//					Scard.AddItemToAgreement(supItem.getItemId(), supItem.getPrice(), supItem.getQuantity());
                }
            }
            output.put(supID, Scard);
        }
        return output;
    }

    public Map<Integer,Item> getAllItems(){
        return loadedItems;
    }

    //=========================================================================
    // supplier methods
//=========================================================================
    public boolean insertSupplier(SupplierCard supplier){
        int id = supplier.getSupplierId();
        String name = supplier.getName();
        int isMobile = 0 ;
        if(supplier.isMobile()) isMobile = 1;
        int payCond = 0;
        switch (supplier.getPaymentCondition()){
            case FLOW -> payCond = 0;
            case FLOW30 -> payCond = 30;
            case FLOW60 -> payCond = 60;
        }
        SupplierAgreement a = supplier.getSupplierAgreement();
        int constSupply = 0;
        if(a.isConstantSupply()) constSupply = 1;
        return supplierMapper.InsertSupplierCard(id,name,isMobile,payCond, supplier.getBankAccount())&&
                agreementMapper.insertAgreement(a.getSupplierId(),constSupply,a.getConditionId());
    }
    public boolean updateSupplier(SupplierCard supplier){
        int id = supplier.getSupplierId();
        String name = supplier.getName();
        int isMobile = 0 ;
        if(supplier.isMobile()) isMobile = 1;
        int payCond = 0;
        switch (supplier.getPaymentCondition()){
            case FLOW -> payCond = 0;
            case FLOW30 -> payCond = 30;
            case FLOW60 -> payCond = 60;
        }
        return supplierMapper.updateSupplierCard(id,name,isMobile,payCond, supplier.getBankAccount());
    }

    //=========================================================================
// contact methods
//=========================================================================
    public boolean insertContact(Contact contact, int supplierId){
        int id = contact.GetId();
        String name = contact.GetName();
        String phoneNum = contact.GetPhoneNumber();
        String email = contact.GetEmail();

        return supplierMapper.InsertContact(id,name,phoneNum,email,supplierId);
    }
    public boolean updateContact(Contact contact,int supplierId){//TODO: IMPLEMENT LATER
        return supplierMapper.updateContact(contact.GetId(),contact.GetName(),contact.GetPhoneNumber(),contact.GetEmail(),supplierId);
    }

    //=========================================================================
// condition methods
//=========================================================================
    public boolean insertCondition(ConditionForDiscount cond, int id, int supplierId){
        //if(agreementMapper.getConditionDetails(id, supplierId) != null) return false;
        boolean ret;
        List<Integer> items = cond.GetItemIds();
        int isForQuantity = 0;
        if(cond.isForQuantity()) isForQuantity = 1;
        int isForPrice = 0;
        if(cond.isForPrice()) isForPrice = 1;
        double neededAmount = cond.getNeededAmount();
        double percentage = cond.GetDiscountPercentage();
        ret = agreementMapper.insertConditionDetails(id, supplierId, isForPrice, isForQuantity,neededAmount,percentage);
        if(ret){
            for(int i : items){
                agreementMapper.insertConditionItems(supplierId,i,id);
            }
        }
        return ret;
    }


    //=========================================================================
// item methods
//=========================================================================
    public boolean insertItem(Item item){
        return itemMapper.insertItem(item.GetId(), item.GetName(), item.GetManufacturer());
    }
    public boolean updateItem(Item item){
        return itemMapper.updateItem(item.GetId(), item.GetName(), item.GetManufacturer());
    }

    public boolean insertSupItem(int supplierId, int itemId, int serialNum, double price, int quantity){
        return itemMapper.insertSupplierItem(supplierId,itemId,serialNum,price,quantity);
    }
    public boolean updateSupItem(int supplierId, int itemId, int serialNum,double price, int quantity){
        return itemMapper.updateSupplierItem(supplierId,itemId,serialNum,price,quantity);
    }

    public Item getItem(int itemId){
        ItemDTO iDTO=  itemMapper.getItemById(itemId);
        if(iDTO == null)
            return null;
        return new Item(iDTO.getItemId(),iDTO.getName(),iDTO.getManufacturer());
    }


    //=========================================================================
// order methods
//=========================================================================
    public boolean insertOrder(Order order){
        boolean ret = true;
        int executed = 1;
        if(order.getDueDate().isAfter(LocalDate.now())) executed = 0;
        ret = ret && orderMapper.insertOrder(order.GetOrderId(),order.getSupplierID(),order.getOrderAddress(),order.getTotalPrice()
                , order.getDueDate().toString(), executed);
        for(Integer itemId : order.getItemIdToName().keySet()){
            int quantity = order.getItemQuantity(itemId);
            double Iprice = order.GetItemInitialPrice(itemId);
            double Fprice = order.GetItemFinalPrice(itemId);
            String name = order.getItemIdToName().get(itemId);
            ret = ret && orderMapper.insertOrderItemDet(order.GetOrderId(),itemId,name,quantity,Iprice,Fprice);
        }

        return ret;
    }
//	public boolean UpdateOrder(Order order){
//		return false;
//	}

    public Order getOrder(int orderId){
        OrdersDTO ordDTO = orderMapper.getOrderById(orderId);
        return new Order(ordDTO.getOrderId(), ordDTO.getSupplierId(), ordDTO.getOrderAddress(), getDate(ordDTO.getDate()));
    }

    private LocalDate getDate(String dateString){
        String[] splittedString = dateString.split("-");
        int year = parseInt(splittedString[0]);
        int month = parseInt(splittedString[1]);
        int day = parseInt(splittedString[2].split("T")[0]);
        return LocalDate.of(year,month,day);
    }

    public boolean removeOrder(Order ord){
        return orderMapper.deleteOrder(ord.GetOrderId());
    }

    public Map<Integer, List<Order>> uploadFutureOrders(){
        Map<Integer, List<Order>> out = new HashMap<>();
        for(OrdersDTO ordersDTO : orderMapper.getNonExeOrders()){
            int supId = ordersDTO.getSupplierId();
            Order o = new Order(ordersDTO.getOrderId(),ordersDTO.getSupplierId(),ordersDTO.getOrderAddress(),getDate(ordersDTO.getDate()));
            for(OrderItemDetailsDTO oi : orderMapper.getOrderItems(o.GetOrderId())){
                o.addItem(oi.getItemId(),oi.getItemName(),oi.getQuantity(),oi.getInitialPrice(),oi.getFinalPrice());
            }
            if(out.containsKey(supId)){
                out.get(supId).add(o);
            }
            else{
                out.put(supId,new ArrayList<>());
                out.get(supId).add(o);
            }
        }
        return out;
    }

    public int getMaxOrderId(){
        return orderMapper.getMaxOrderId();
    }

    //======================================================== DaysToSupplier
    public Map<DayOfWeek,List<Integer>> getDayToSupplier() {
        Map<DayOfWeek, List<Integer>> out = new HashMap<>();
        for (DayOfWeek day:DayOfWeek.values())
        {
            out.put(day,new LinkedList<>());
        }
        for(SupplierToDayDTO dto: agreementMapper.getSupplierToDays()){
            switch (dto.getDay()){
                case 1 -> out.get(DayOfWeek.SUNDAY).add(dto.getSupplierId());
                case 2 -> out.get(DayOfWeek.MONDAY).add(dto.getSupplierId());
                case 3 -> out.get(DayOfWeek.TUESDAY).add(dto.getSupplierId());
                case 4 -> out.get(DayOfWeek.WEDNESDAY).add(dto.getSupplierId());
                case 5 -> out.get(DayOfWeek.THURSDAY).add(dto.getSupplierId());
                case 6 -> out.get(DayOfWeek.FRIDAY).add(dto.getSupplierId());
                default -> out.get(DayOfWeek.SATURDAY).add(dto.getSupplierId());
            }
        }
        return out;
    }

    public boolean insertDayOfSupply(int supplierId, DayOfWeek dayOfWeek){
        int day = 0;
        switch (dayOfWeek){
            case SUNDAY -> day = 1;
            case MONDAY -> day = 2;
            case TUESDAY -> day = 3;
            case WEDNESDAY -> day = 4;
            case THURSDAY -> day =  5;
            case FRIDAY -> day = 6;
            default -> day = 7;
        }
        return agreementMapper.insertSupplierDay(supplierId,day);
    }

    public Item LoadItem(int itemId) {
        ItemDTO iDTO = itemMapper.getItemById(itemId);
        Item i = null;
        if (iDTO != null) {
            i = new Item(iDTO.getItemId(), iDTO.getName(), iDTO.getManufacturer());
            loadedItems.put(i.GetId(), i);
        }
        return i;
    }
}
