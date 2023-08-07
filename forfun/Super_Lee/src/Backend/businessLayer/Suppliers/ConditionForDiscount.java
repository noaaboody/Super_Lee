package Backend.businessLayer.Suppliers;

import java.util.LinkedList;
import java.util.List;

public class ConditionForDiscount {
    private List<Integer> itemIDs;
    private boolean isForQuantity;
    private boolean isForPrice;
    private double neededAmount;
    private double discountPercentage;

    public ConditionForDiscount(){
        this.itemIDs = new LinkedList<>();
    }
    public ConditionForDiscount(List<Integer> itemIDs, boolean isForPrice, boolean isForQuantity, double discount, double threshold)
    {
        this.itemIDs = itemIDs;
        this.isForQuantity = isForQuantity;
        this.isForPrice = isForPrice;
        this.discountPercentage = discount;
        this.neededAmount = threshold;
    }


    public boolean isForPrice() { return this.isForPrice; }

    public double getNeededAmount() {
        return neededAmount;
    }

    public boolean isForQuantity() { return this.isForQuantity;}
    public double GetDiscountPercentage(){ return this.discountPercentage; }

    public void SetIsForPrice(boolean isForPrice){ this.isForPrice = isForPrice; }
    public void SetIsForQuantity(boolean isForQuantity){
        this.isForQuantity = isForQuantity;
    }

    public void SetDiscountPercentage(float discount){
        this.discountPercentage = discount;
    }

    public List<Integer> GetItemIds(){return this.itemIDs;}

    public double GetDiscountForItem(int itemId, int quantity){
        if(isForQuantity && itemIDs.contains(itemId) && quantity >= neededAmount)
            return discountPercentage;
        return 0;
    }
    public double getDiscountForPrice(Double price)
    {
        if (isForPrice&&price>=this.neededAmount)
            return discountPercentage;
        return 0;

    }

    public float GetDiscountForItemList(List<Integer> ItemIds){
        return 0;
    }

    public String toString()
    {
        return "\n items on sale: "+ itemIDs.toString()+
                "\n is for quantity: "+ isForQuantity+
                "\n is for price: "+ isForPrice+
                "\n needed amount: "+neededAmount+
                "\n discount percentage: "+discountPercentage;
    }
}
