package Backend.businessLayer.Suppliers;

import Backend.DataAccessLayer.SupplierDALC;
import Backend.businessLayer.Item;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;

public class SupplierController {
    private Map<Integer,SupplierCard> suppliersMap;//mapping suppliers id to supplier card instance;
    private Map<DayOfWeek, List<SupplierCard>> daysToConstSupplier;// mapping suppliers id to each day of supply.
    private SupplierDALC DALC;

    private Map<Integer, Item> allItems;
    public SupplierController()
    {
        allItems= new HashMap<>();
        suppliersMap= new HashMap<>();
        DALC=SupplierDALC.getInstance();
        suppliersMap = DALC.uploadAllSuppliers();
        allItems = DALC.getAllItems();
        daysToConstSupplier= new HashMap<>();
        for (DayOfWeek day:DayOfWeek.values())
        {
            daysToConstSupplier.put(day,new LinkedList<>());
        }
        Map<DayOfWeek,List<Integer>> daysToSup = DALC.getDayToSupplier();
        for (Map.Entry<DayOfWeek,List<Integer>> dayToId: daysToSup.entrySet())
        {
            List<SupplierCard> sups= new LinkedList<>();
            for (Integer id: dayToId.getValue())  { sups.add(getSupplierByID(id)); }
            daysToConstSupplier.get(dayToId.getKey()).addAll(sups);
        }
    }




    public boolean isSupplierExists(int supplierId)
    {
        return suppliersMap.containsKey(supplierId);
    }

//===============================================================================================================
//                    ADD Functions
//===============================================================================================================

    /**
     *this method is used when adding a new supplier to the system/
     * @param  id the supplier's unique (ח.פ) identifier
     * @param name the suppliers name
     * @param isMobile  specifying if the supplier is delivering or should an order be picked up
     * @param bankAccount specifying the suppliers bank account number
     * @param paymentCondition specifying the supplier's payment condition
     * @throws RuntimeException, if id is already in the system.
     * @ return temporary return a string specifying the supplier's id, later returns a json
     */
    public String addSupplierCard(int id,String name, boolean isMobile, int bankAccount, PaymentConditions paymentCondition,boolean isConstantSupply, List<DayOfWeek> daysOfSupply)
    {
        String ret= Integer.toString(id);
        if(suppliersMap.containsKey(id)) throw  new RuntimeException("Supplier id already exists");
        if(daysOfSupply.isEmpty()&&isConstantSupply) throw  new RuntimeException("Supplier not supplying at any day, please enter day if isConstant supplier");
        SupplierCard sup= new SupplierCard(id,name,isMobile,bankAccount,paymentCondition,isConstantSupply);
        suppliersMap.put(id,sup);
        if(isConstantSupply)
            for(DayOfWeek day: daysOfSupply){
                this.daysToConstSupplier.get(day).add(sup);
                DALC.insertDayOfSupply(id, day);
            }

        DALC.insertSupplier(sup);
        return ret;
    }

    /**
     *this method is used when adding a new contact to supplier in the system/
     * @param  supplierId the supplier's unique (ח.פ) identifier
     * @param contactId  the contact's id
     * @param name the contact's name
     * @param phoneNumber the contacts phone number
     * @param email the contact's email
     * @throws RuntimeException, if id is already in the system for specified supplier.
     * @ return temporary return a string specifying the contact's id, later returns a json
     */
    public String addSupplierContact(int supplierId, int contactId, String name, String phoneNumber, String email)
    {
        String ret= Integer.toString(contactId);
        Contact c= new Contact(contactId,name,phoneNumber,email);
        SupplierCard supplier= suppliersMap.get(supplierId);
        if (supplier.isContactOnList(c)) throw  new RuntimeException("this contact already exists in the supplier's contacts list");
        supplier.AddContact(c);

        DALC.insertContact(c,supplierId);
        return ret;
    }

    /**
     *this method adds a condition to the supplier's agreement
     *  @param  supplierId the supplier's unique (ח.פ) identifier
     * @param itemIDs  the item's id that the condition apply on
     * @param isForQuantity determines if the condition is for quantity
     * @param isForPrice determines if the condition is for Price
     * @param discountPercentage the amount of discount that apply per item/bill
     * @throws RuntimeException, if id is not in the system for specified supplier or supplier not exists.
     * @ return temporary return a string specifying if the operation succeeded
     */
    public String addCondition(int supplierId, List<Integer> itemIDs,boolean isForQuantity,boolean isForPrice,double discountPercentage, double threshold)
    {
        if (!isSupplierExists(supplierId)) throw new RuntimeException("supplier id "+supplierId+ "does not exist in the system");
        ConditionForDiscount c= new ConditionForDiscount(itemIDs,isForPrice,isForQuantity,discountPercentage, threshold);
        int condId = suppliersMap.get(supplierId).addCondition(c);

        DALC.insertCondition(c,condId,supplierId);
        return "new condition for discount added successfully";
    }

    public String addItemToSystem(int itemId,String itemName, String manufacturer){
        if(this.allItems.containsKey(itemId)) throw new RuntimeException("item id: "+itemId +"already exist");
        Item i= new Item(itemId,itemName,manufacturer);
        allItems.put(itemId,i);

        DALC.insertItem(i);
        return i+" added successfully!";
    }

    public String addItemToSupplier(int supplierId,int itemId,int serialNum, double price, int quantity) {
        if (!isSupplierExists(supplierId)) throw new RuntimeException("supplier id "+supplierId+ " does not exist in the system");
        if(!this.allItems.containsKey(itemId)) {
            Item i = DALC.getItem(itemId);
            if(i == null)
                throw new RuntimeException("item id: "+itemId +" not in the system, please use add Item To System first.");
            else
                allItems.put(itemId,i);
        }
        if(suppliersMap.get(supplierId).hasItem(itemId)) throw new RuntimeException(suppliersMap.get(supplierId).getName()+" already supplies that item");
        suppliersMap.get(supplierId).AddItem(allItems.get(itemId),serialNum, price, quantity);

        DALC.insertSupItem(supplierId,itemId,serialNum, price, quantity);
        return "item id:"+itemId+" added to supplier: "+supplierId +" successfully";
    }

    /**
     *
     * @param supplierId the supplier's id
     * @param itemId the item added to the supplier
     * @param price the price of the item
     * @param quantity the quantity th e supplier must provide in each order
     * @return string indicator
     */
    public String addItemToSupplierAgreement(int supplierId, int itemId, Double price, int quantity){
        if (!isSupplierExists(supplierId)) throw new RuntimeException("supplier id "+supplierId+ " does not exist in the system");
        if(!this.allItems.containsKey(itemId)) throw new RuntimeException("item id: "+itemId +" not in the system, please use addItemTOSystem first.");

        suppliersMap.get(supplierId).AddItemToAgreement(itemId,price,quantity);
        //DALC.update ???
        return "item id: "+itemId+" added successfully";
    }

//===============================================================================================================
//                    Set Functions
//===============================================================================================================

    /**
     * changing the mobility field in supplier card
     * @param supplierId the suppliers id
     * @param isMobile indicates if an order should be delivered by the supplier
     * @throws RuntimeException, if id is not in the system for specified supplier or supplier not exists.
     * @return string indicating if successful
     */
    public String setSupplierIsMobile(int supplierId,boolean isMobile)
    {
        if(!isSupplierExists(supplierId)) throw new RuntimeException("Supplier id not existing in the system");
        suppliersMap.get(supplierId).setIsMobile(isMobile);

        DALC.updateSupplier(suppliersMap.get(supplierId));
        return ("supplier "+supplierId+" is Mobile condition changed to "+isMobile);
    }


    /**
     * setting the supplier bank account to a new number
     * @param supplierId the suppliers id
     * @param bankAccount the supplier's new bank account number
     * @throws RuntimeException, if id is not in the system for specified supplier or supplier not exists.
     * @return string indicator
     */
    public String setSupplierBankAccount(int supplierId,int bankAccount)
    {
        if(!isSupplierExists(supplierId)) throw new RuntimeException ("Supplier id not existing in the system");
        suppliersMap.get(supplierId).setBankAccount(bankAccount);

        DALC.updateSupplier(suppliersMap.get(supplierId));
        return ("supplier "+supplierId+" bank account number condition changed to "+bankAccount);
    }


    /**
     *this method changes a contacts email for a specific supplier
     * @param  supplierId the supplier's unique (ח.פ) identifier
     * @param contactId  the contact's id
     * @param email the contact's email
     * @throws RuntimeException, if id is not in the system for specified supplier or supplier not exists.
     * @ return temporary return a string specifying if the operation succeeded
     */
    public String setContactEmail(int supplierId,int contactId, String email)
    {
        if (!isSupplierExists(supplierId)) throw new RuntimeException("supplier id "+supplierId+ " does not exist in the system");
        Contact c = suppliersMap.get(supplierId).getContactById(contactId);
        c.SetEmail(email);

        DALC.updateContact(c,supplierId);
        return "contact:id"+contactId +" email changed to "+ email;
    }

    /**
     *this method changes a contacts phone number for a specific supplier
     * @param  supplierId the supplier's unique (ח.פ) identifier
     * @param contactId  the contact's id
     * @param phoneNum the contact's Phone number
     * @throws RuntimeException, if id is not in the system for specified supplier or supplier not exists.
     * @ return temporary return a string specifying if the operation succeeded
     */
    public String setContactPhone(int supplierId,int contactId, String phoneNum)
    {
        if (!isSupplierExists(supplierId)) throw new RuntimeException("supplier id "+supplierId+ " does not exist in the system");
        Contact c = suppliersMap.get(supplierId).getContactById(contactId);
        c.SetPhoneNumber(phoneNum);

        DALC.updateContact(c,supplierId);
        return "contact:id"+contactId +" phone number changed to "+ phoneNum;
    }

    /**
     *this method changes a contacts name for a specific supplier
     * @param  supplierId the supplier's unique (ח.פ) identifier
     * @param contactId  the contact's id
     * @param contactName the contact's name
     * @throws RuntimeException, if id is not in the system for specified supplier or supplier not exists.
     * @ return temporary return a string specifying if the operation succeeded
     */
    public String setContactName(int supplierId,int contactId, String contactName)
    {
        if (!isSupplierExists(supplierId)) throw new RuntimeException(" supplier id "+supplierId+ " does not exist in the system");
        Contact c = suppliersMap.get(supplierId).getContactById(contactId);
        c.setName(contactName);

        DALC.updateContact(c,supplierId);
        return "contact:id"+contactId +" name changed to "+ contactName;
    }

    /**
     *this method changes a supplier payment condition
     * @param  supplierId the supplier's unique (ח.פ) identifier
     * @param cond  the contact's id
     * @throws RuntimeException, if id is not in the system for specified supplier or supplier not exists.
     * @ return temporary return a string specifying if the operation succeeded
     */
    public String setSupplierPaymentCondition(int supplierId,PaymentConditions cond)
    {
        if (!isSupplierExists(supplierId)) throw new RuntimeException("supplier id "+supplierId+ " does not exist in the system");
        suppliersMap.get(supplierId).setPaymentCondition(cond);

        DALC.updateSupplier(suppliersMap.get(supplierId));
        return "supplier: "+supplierId+" payment condition changed to "+cond;
    }



    /**
     *this method changes the specified item of a supplier guaranteed quantity to supply
     * @param  supplierId the supplier's unique (ח.פ) identifier
     * @param itemId  the item's id
     * @param quantity the new quantity
     * @throws RuntimeException, if id is not in the system for specified supplier or supplier not exists.
     * @ return temporary return a string specifying if the operation succeeded
     */
    public String setItemQuantity(int supplierId, int itemId,int quantity)
    {
        if (!isSupplierExists(supplierId)) throw new RuntimeException("supplier id "+supplierId+ "does not exist in the system");
        suppliersMap.get(supplierId).setItemQuantity(itemId,quantity);

        int serialNum = suppliersMap.get(supplierId).getItemToSerialNum().get(allItems.get(itemId));
        double price = suppliersMap.get(supplierId).getSupplierAgreement().getItemPrice(itemId);
        DALC.updateSupItem(supplierId, itemId, serialNum, price, quantity);
        return "supplier: "+supplierId+" itemId: "+itemId+"quantity changed to "+quantity;
    }

    /**
     *this method changes the specified item of a supplier guaranteed price to supply
     * @param  supplierId the supplier's unique (ח.פ) identifier
     * @param itemId  the item's id
     * @param price the new price
     * @throws RuntimeException, if id is not in the system for specified supplier or supplier not exists.
     * @ return temporary return a string specifying if the operation succeeded
     */
    public String setItemPrice(int supplierId, int itemId,Double price)
    {
        if (!isSupplierExists(supplierId)) throw new RuntimeException("supplier id "+supplierId+ "does not exist in the system");
        suppliersMap.get(supplierId).setItemPrice(itemId,price);

        int serialNum = suppliersMap.get(supplierId).getItemToSerialNum().get(allItems.get(itemId));
        int quantity = suppliersMap.get(supplierId).getSupplierAgreement().getItemsToQuantity().get(allItems.get(itemId));
        DALC.updateSupItem(supplierId, itemId, serialNum, price, quantity);
        return "supplier: "+supplierId+" itemId: "+itemId+"price changed to "+price;
    }


//===============================================================================================================
//                    Get Functions
//===============================================================================================================

//**** Supplier gets*******
    /**
     *
     * @param supplierId the supplier's id
     * @return the supplier
     */
    public SupplierCard getSupplierByID(int supplierId)  {return suppliersMap.get(supplierId);}
    public SupplierCard getSupplierByIDObj(int supplierId)  {
        if(suppliersMap.containsKey(supplierId))
            return suppliersMap.get(supplierId);
        return null;
    }

    /**
     *
     * @return all the suppliers as a list
     */
    public List<SupplierCard> getSuppliers()  { return new LinkedList<>(suppliersMap.values()); }
    public  List<Contact> getSupplierContactList(int supplierId)
    {
        if (!isSupplierExists(supplierId)) throw new RuntimeException("supplier id "+supplierId+ "does not exist in the system");
        return suppliersMap.get(supplierId).getContactsList();
    }

    public PaymentConditions getSupplierPaymentConditions(int supplierId)
    {
        if (!isSupplierExists(supplierId)) throw new RuntimeException("supplier id "+supplierId+ "does not exist in the system");
        return suppliersMap.get(supplierId).getPaymentCondition();
    }
    public List<Contact> getSupplierContacts(int supplierId)
    {
        if (!isSupplierExists(supplierId)) throw new RuntimeException("supplier id "+supplierId+ "does not exist in the system");
        return suppliersMap.get(supplierId).getContactsList();
    }

    public Map<Item, Integer> getSupplierSerialNumToItem(int supplierId)
    {
        if (!isSupplierExists(supplierId)) throw new RuntimeException("supplier id "+supplierId+ "does not exist in the system");
        return suppliersMap.get(supplierId).getItemToSerialNum();
    }

    public Map<Integer, SupplierCard> getSuppliersMap() {
        return suppliersMap;
    }

    public Map<DayOfWeek, List<SupplierCard>> getDaysToConstSupplier() {
        return daysToConstSupplier;
    }


//****Agreement gets*******

    public SupplierAgreement getSupplierAgreement(int supplierId)
    {
        if (!isSupplierExists(supplierId)) throw new RuntimeException("supplier id "+supplierId+ "does not exist in the system");
        return suppliersMap.get(supplierId).getSupplierAgreement();
    }

    public Map<Integer, Integer> getSupplierItemsToQuantity(int supplierId)
    {
        if (!isSupplierExists(supplierId)) throw new RuntimeException("supplier id "+supplierId+ "does not exist in the system");
        return suppliersMap.get(supplierId).getSupplierAgreement().getItemsToQuantity();
    }

    public Map<Integer, Double> getSupplierItemsToPrice(int supplierId)
    {
        if (!isSupplierExists(supplierId)) throw new RuntimeException("supplier id "+supplierId+ "does not exist in the system");
        return suppliersMap.get(supplierId).getSupplierAgreement().getItemsToPrice();
    }

    public Map<Integer,ConditionForDiscount> getSupplierConditionsMap(int supplierId)
    {
        if (!isSupplierExists(supplierId)) throw new RuntimeException("supplier id "+supplierId+ "does not exist in the system");
        return suppliersMap.get(supplierId).getSupplierAgreement().getConditionsMap();
    }

    public boolean isConstantSupply(int supplierId ){
        if (!isSupplierExists(supplierId)) throw new RuntimeException("supplier id "+supplierId+ "does not exist in the system");
        return suppliersMap.get(supplierId).getSupplierAgreement().getSupplyType();
    }



    public Map<Integer, Item> getAllItems()  {return this.allItems;}

    public List<DayOfWeek> getSupplierDaysOfAvailableSupply(int supplierId)
    {
        if (getSuppliers().get(supplierId).getSupplierAgreement().isConstantSupply()) throw new RuntimeException("supplier is not a constant supplier pleas ask for an immediate order");
        List<DayOfWeek> ret= new LinkedList<>();
        for(DayOfWeek day: daysToConstSupplier.keySet())
        {
            if (daysToConstSupplier.get(day).stream().anyMatch(supplierCard -> supplierCard.getSupplierId()==supplierId)&&!day.equals(LocalDate.now().getDayOfWeek()))  ret.add(day);
        }
        return ret;
    }
    public List<DayOfWeek> getSupplierAllDays(int supplierId)
    {
        List<DayOfWeek> ret= new LinkedList<>();
        if(!suppliersMap.get(supplierId).getSupplierAgreement().isConstantSupply())
            ret.addAll(Arrays.stream(DayOfWeek.values()).toList());
        else {
            for (DayOfWeek day : daysToConstSupplier.keySet()) {
                if (daysToConstSupplier.get(day).stream().anyMatch(supplierCard -> supplierCard.getSupplierId() == supplierId))
                    ret.add(day);
            }
        }
        return ret;
    }


    /**
     * @param supplierId supplier id
     * @param itemId item id
     * @return quantity limit of a supplier
     */
    public int getSupplierItemLimit(int supplierId, int itemId){//TODO: IMPLEMENT THIS
        return suppliersMap.get(supplierId).getSupplierAgreement().getItemsToQuantity().get(itemId);
    }

    public Item getItemObj(int itemId) {
        if (!allItems.containsKey(itemId)) {
            allItems.put(itemId, DALC.LoadItem(itemId));
        }
        return allItems.get(itemId);
    }

    public String setItem(int itemId, String name, String manufacturer) {
        if(!allItems.containsKey(itemId) && DALC.getItem(itemId) == null)
            return "Item "+ itemId+ " does not exist, please add it";
        allItems.put(itemId,new Item(itemId,name, manufacturer));
        DALC.updateItem(allItems.get(itemId));
        return "Item Updated successfully";
    }


//TODO implement add category and remove category for supplier



}
