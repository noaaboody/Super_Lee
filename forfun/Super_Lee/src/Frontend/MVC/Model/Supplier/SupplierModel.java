package Frontend.MVC.Model.Supplier;

import Backend.businessLayer.Suppliers.PaymentConditions;
import Backend.businessLayer.Suppliers.SupplierCard;
import Backend.serviceLayer.SupplierService;

import java.time.DayOfWeek;
import java.util.List;

public class SupplierModel {


    private int supplierID;// private company number is unique thus can be our supplierID

    private String name;
    private boolean isMobile;
    private int bankAccount;
    private PaymentConditions paymentCondition;
    private List<DayOfWeek> daysOfSupply;
    private SupplierService service;
    private boolean isConstant;
    private boolean init=false;

    public SupplierModel(SupplierService service)
    {
            this.service=service;
    }

    public boolean isInit()
    {
        return init;
    }
//+++++++++++++++++++++getters+++++++++++++++++++++++

    public int getSupplierID() {
        return supplierID;
    }
    public String getName() {
        return name;
    }

    public boolean isMobile() {
        return isMobile;
    }

    public int getBankAccount() {
        return bankAccount;
    }

    public PaymentConditions getPaymentCondition() {
        return paymentCondition;
    }
    public boolean getIsConstant() {
        return isConstant;
    }

    public List<DayOfWeek> getDaysOfSupply() {
        return daysOfSupply;
    }
//+++++++++++++++++++++setters+++++++++++++++++++++++
    public String GetSupplier(int id)
    {
        SupplierCard card = service.getSupplierByIdObj(id);
        if (card!=null) {
            init=true;
            this.isMobile = card.isMobile();
            this.supplierID = card.getSupplierId();
            this.name= card.getName();;
            this.paymentCondition=card.getPaymentCondition();
            this.bankAccount=card.getBankAccount();
            this.daysOfSupply = service.getSupplierAllDays(id);
            return "";
        }
        else init=false;
        return "id does not exist";

    }
public void setName(String name) {
    this.name = name;

}
    public void setIsConstant(boolean b) {
        isConstant= b;
    }

    public void setMobile(boolean mobile) {
        isMobile = mobile;
    }

    public void setBankAccount(int bankAccount) {
        this.bankAccount = bankAccount;
    }

    public void setPaymentCondition(PaymentConditions paymentCondition) {
        this.paymentCondition = paymentCondition;
    }
    public String addCard(int supplierId, String name, boolean isMobile,int  bankAccount,PaymentConditions paymentCondition, boolean isConst ,List<DayOfWeek> daysOfSupply)
    {
        if(service.isValidIdForAdd(supplierId))
        {
            init=true;
            this.isMobile = isMobile;
            this.supplierID = supplierId;
            this.name= name;
            this.paymentCondition=paymentCondition;
            this.bankAccount=bankAccount;
            this.daysOfSupply = daysOfSupply;
            return service.addSupplierCard(supplierId,name,isMobile,bankAccount,paymentCondition,isConst,daysOfSupply);
        }
        return "id already exist, choose another id";
    }

    public String setAll(int supplierId, boolean isMobile,int  bankAccount,PaymentConditions paymentCondition)
    {
        if(service.isValidIdForEdit(supplierId)) {
            String out = "";
            out += service.setSupplierIsMobile(supplierId, isMobile) + "\n";
            out += service.setSupplierBankAccount(supplierId, bankAccount) + "\n";
            out += service.setSupplierPaymentCondition(supplierId, paymentCondition) + "\n";
            return out;
        }
        return "id does not exist in the system, choose another one";

    }



}
