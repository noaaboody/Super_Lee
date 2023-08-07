package Frontend.MVC.Model.Supplier;

import Backend.businessLayer.Suppliers.ConditionForDiscount;
import Backend.businessLayer.Suppliers.SupplierAgreement;
import Backend.serviceLayer.SupplierService;

import java.util.Map;

public class AgreementModel {
    boolean isInit=false;



    private boolean isConstantSupply;
    private Map<Integer, Double> itemsToPrice;
    private Map<Integer, Integer> itemsToQuantity;
    private Map<Integer, ConditionForDiscount> conditions;
    private final int supplierId;
    SupplierService service;

    public AgreementModel(int id, SupplierService service)
    {
        supplierId=id;
        this.service=service;
        if(service.isValidIdForEdit(id))
        {
            SupplierAgreement a= service.getFullAgreement(id);
            isConstantSupply=a.isConstantSupply();
            itemsToPrice=a.getItemsToPrice();
            itemsToQuantity=a.getItemsToQuantity();
            conditions=a.getConditionsMap();
            isInit=true;
        }


    }

//++++++++++++getters+++++++++++++++++++++++++

    public boolean isInit() {
        return isInit;
    }

    public boolean isConstantSupply() {
        return isConstantSupply;
    }

    public Map<Integer, Double> getItemsToPrice() {
        return itemsToPrice;
    }

    public Map<Integer, Integer> getItemsToQuantity() {
        return itemsToQuantity;
    }

    public Map<Integer, ConditionForDiscount> getConditions() {
        return conditions;
    }

    public int getSupplierId() {
        return supplierId;
    }



//++++++++++++setters+++++++++++++++++++++++++


    public void setConditions(Map<Integer, ConditionForDiscount> conditions) {
        this.conditions = conditions;
    }
    public String setItem(int id, double price, int quantity)
    {
        try{
            if (quantity<0||price<0)
                return "price and quantity cant be of negative values";

            String result="";
            result +=service.getSupplierController().setItemPrice(supplierId,id,price)+"\n";
            result +=service.getSupplierController().setItemQuantity(supplierId,id,quantity)+"\n";


            if(quantity==0)
            {
                itemsToQuantity.remove(id);
                itemsToPrice.remove(id);
            }
            else
            {
                itemsToQuantity.replace(id,quantity);
                itemsToPrice.replace(id,price);
            }
            return result;
        }
        catch (Exception e){return e.getMessage();}
    }

    public String addItem(int id, double price, int quantity,int serialNum)
    {
        try{

            if (quantity<0||price<0)
                return "price and quantity cant be of negative values";

            if(quantity==0)
            {
                return "quantity was 0 therefor was no added to Agreement";
            }
            else
            {
                String res= service.getSupplierController().addItemToSupplier(supplierId,id,serialNum,price,quantity);
                itemsToQuantity.replace(id,quantity);
                itemsToPrice.replace(id,price);
                return res;
            }

        }
        catch (Exception e){return e.getMessage();}
    }
}
