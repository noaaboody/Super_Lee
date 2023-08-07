package Backend.businessLayer.Suppliers;

import Backend.businessLayer.Item;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class SupplierCard {
    private final int supplierID;// private company number is unique thus can be our supplierID
    private String name;
    private boolean isMobile;
    private int bankAccount;
    private final List<Contact> contactList;
    private PaymentConditions paymentCondition;

    private SupplierAgreement agreement;
    private Map<Item, Integer> ItemToSerialNum;

    public SupplierCard(int id,String name, boolean isMobile, int bankAccount, PaymentConditions paymentCondition,boolean isConstantSupply){
        this.supplierID = id;
        this.name=name;
        this.isMobile = isMobile;
        this.bankAccount = bankAccount;
        this.paymentCondition = paymentCondition;
        this.agreement = new SupplierAgreement(supplierID);
        agreement.setIsConstantSupply(isConstantSupply);

        contactList = new LinkedList<>();
        ItemToSerialNum = new HashMap<>();

    }

    public void AddContact(Contact contact){
        this.contactList.add(contact);
    }
    public boolean isContactOnList(Contact contact)  {return contactList.stream().anyMatch(contact1 -> (contact1.GetId() == contact.GetId()));}
    /**
     *this method is used to get a contact by its id from supplier
     * @ return the contact if found, null otherwise
     */



    public void AddItem(Item item, int serialNum, double price, int quantity){
        ItemToSerialNum.put(item, serialNum);
        agreement.AddItem(item.GetId(), price, quantity);
    }
    public void AddItemToAgreement(int itemId, Double price, int quantity){
        agreement.AddItem(itemId,price,quantity);
    }

    //this method sets a new Supplier Agreement, this method accepts the conditions terms and what are the items included in it,
    // what are the prices of each item,

    //this method sums and returns all the items that the supplier may sell in any agreement.

    public int addCondition(ConditionForDiscount cond)
    {
        return agreement.addCondition(cond);
    }

    public String toString()
    {
        return 	"\nname: "+name+
                "\n supplier id"+supplierID+
                "\n isMobile: "+isMobile+
                "\n payment Condition: "+paymentCondition.toString()+".";
    }
//----------------------------------------------------------------------------------------------------------------
//                    Set Functions
//----------------------------------------------------------------------------------------------------------------

    public void setPaymentCondition(PaymentConditions cond)
    {
        this.paymentCondition=cond;
    }
    public void setItemQuantity(int itemId, int quantity){
        agreement.setItemQuantity(itemId,quantity);
    }
    public void setItemPrice(int itemId, Double price){
        agreement.setItemPrice(itemId, price);
    }
    public void setIsMobile(boolean isMobile)  { this.isMobile=isMobile; }

    public void setName(String name)  { this.name=name; }
    public void setBankAccount(int bankAccount)  { this.bankAccount=bankAccount; }

    public void setAgreementCondId(int condId){ this.agreement.setConditionId(condId);}
    public void setAgreement(SupplierAgreement agreement){ this.agreement = agreement;}





//----------------------------------------------------------------------------------------------------------------
//                    Get Functions
//----------------------------------------------------------------------------------------------------------------


    public Contact getContactById(int id)
    {
        for (Contact contact : contactList) {
            if (contact.GetId() == id) return contact;
        }
        throw new RuntimeException("no contact with id: "+id+ "found in supplier: "+supplierID);
    }
    public int getSupplierId()  { return this.supplierID;}

    public SupplierAgreement getSupplierAgreement()
    {
        return agreement;
    }

    public String getName()
    {
        return name;
    }

    public PaymentConditions getPaymentCondition() {
        return paymentCondition;
    }
    public List<Contact> getContactsList() { return new LinkedList<>(contactList); }

    public int getNumOfContacts()
    {
        return contactList.size();
    }

    public boolean isMobile() { return isMobile; }
    public int getBankAccount()  { return bankAccount;}

    public Map<Item, Integer> getItemToSerialNum(){return new HashMap<>(ItemToSerialNum);}
    public boolean hasItem(int itemId){return ItemToSerialNum.keySet().stream().anyMatch(item -> (item.GetId() == itemId));}

}
