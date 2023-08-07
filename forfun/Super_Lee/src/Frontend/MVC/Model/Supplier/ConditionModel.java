package Frontend.MVC.Model.Supplier;

import Backend.businessLayer.Suppliers.ConditionForDiscount;
import Backend.serviceLayer.SupplierService;

import java.util.LinkedList;
import java.util.List;

public class ConditionModel {
    private List<Integer> itemIDs=new LinkedList<>();
    private boolean isForPrice=true;
    private double neededAmount=-1;
    private double discountPercentage=0;
    private SupplierService service;
    private int supplierId;
    private int condId=-1;

    public ConditionModel(SupplierService service,int supplierId) {
        this.service=service;
        this.supplierId=supplierId;
    }

    public void addItem(Integer itemId) {if(!itemIDs.contains(itemId)) itemIDs.add(itemId);}
    public void removeItem(Integer itemId){itemIDs.remove(itemId);}
    public void setIsForPrice(boolean isPrice){
        isForPrice=isPrice;//quantity is !price
    }
    public String setNeededAmount(double neededAmount){
        if(neededAmount>=0) {
            this.neededAmount = neededAmount;
            return "";
        }
        return "threshold must be greater than 0";
    }

    public String setDiscount(double discount){
        if(discount>=0) {
            this.discountPercentage = discount;
            return "";
        }
        return "discount must be greater than 0";
    }

    public List<Integer> getItemIDs() {
        return itemIDs;
    }

    public boolean isForPrice() {
        return isForPrice;
    }

    public double getNeededAmount() {
        return neededAmount;
    }

    public double getDiscountPercentage() {
        return discountPercentage;
    }

    public String addCond()
    {
        try{
            service.getSupplierController().addCondition(supplierId,itemIDs,!isForPrice,isForPrice,discountPercentage,neededAmount);
        return "added successfully";
        }
        catch (Exception e){return  e.getMessage();}

    }

    public String importCond(int condID)
    {
        try{
            ConditionForDiscount c = service.getSupplierController().getSupplierConditionsMap(supplierId).get(condID);
            this.condId=condID;
            itemIDs=c.GetItemIds();
            isForPrice=c.isForPrice();
            neededAmount=c.getNeededAmount();
            discountPercentage=c.GetDiscountPercentage();
            return "success";
        }
        catch (Exception e){
            this.condId=-1;
            return "Error: please enter Cond Id again";}

    }
public int getCondID()
{return  this.condId;}
}
