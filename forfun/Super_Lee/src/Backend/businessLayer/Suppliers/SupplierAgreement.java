package Backend.businessLayer.Suppliers;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class SupplierAgreement {


    private boolean isConstantSupply;
    private Map<Integer, Double> itemsToPrice;
    private Map<Integer, Integer> itemsToQuantity;
    private Map<Integer,ConditionForDiscount> conditions;

    private int conditionId;
    private final int supplierId;

    public int getConditionId() {
        return conditionId;
    }

    public SupplierAgreement(int supplierId){
        this.supplierId = supplierId;
        itemsToPrice = new HashMap<>();
        itemsToQuantity = new HashMap<>();
        conditions = new HashMap<>();
        conditionId=0;
    }

    public void AddItem(int itemId, Double price, int quantity){
        itemsToQuantity.put(itemId,quantity);
        itemsToPrice.put(itemId, price);
    }



    public int addCondition(ConditionForDiscount cond){
        conditions.put(conditionId,cond);
        int ret = conditionId;
        conditionId++;
        return ret;
    }
    public void addCondition(ConditionForDiscount cond,int conditionId){
        conditions.put(conditionId,cond);
        conditionId++;
    }

//===============================================================================================================
//                    Set Functions
//===============================================================================================================

    public void setIsConstantSupply(boolean isConstSupply){
        this.isConstantSupply = isConstSupply;
    }

    public void setItemQuantity(int itemId, int quantity) {itemsToQuantity.replace(itemId,quantity);}
    public void setItemPrice(int itemId, Double price) { itemsToPrice.replace(itemId,price);}

    public void removeCondition(Integer conditionId){conditions.remove(conditionId);}

    public void setConditionId(int conditionId) {this.conditionId = conditionId;}


//===============================================================================================================
//                    Get Functions
//===============================================================================================================


    public Map<Integer, Integer> getItemsToQuantity()  {return itemsToQuantity;}

    public Map<Integer, Double> getItemsToPrice() { return itemsToPrice ;}

    public int getSupplierId(){return this.supplierId;}
    public Map<Integer,ConditionForDiscount> getConditionsMap()  { return conditions; }

    public List<ConditionForDiscount> getConditionsList() { return  conditions.values().stream().toList();}
    public Double GetItemPrice(int itemId){return itemsToPrice.get(itemId);}
    public List<Integer> getItems() { return new LinkedList(itemsToPrice.keySet());}
    public boolean getSupplyType(){return this.isConstantSupply;}

    public boolean isConstantSupply() {
        return isConstantSupply;
    }

    public double getItemPrice(int itemId){
        return itemsToPrice.get(itemId);
    }


    public String toString()
    {
        return "\n is constant supply: "+isConstantSupply+
                "\n item to Price:"+ itemsToPrice.toString()+
                "\n item to quantity+ "+itemsToQuantity.toString()+
                "\n conditions of discount "+conditions.toString();
    }
}
