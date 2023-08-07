package Backend.DataAccessLayer.DTOs;


import Backend.businessLayer.Suppliers.PaymentConditions;

public class SupplierCardDTO extends DTO{

    private final int id;
    private String name;
    private boolean isMobile;
    private int bankAccount;
    private PaymentConditions paymentCond;

    public SupplierCardDTO(int id, String name, int isMobile, int paymentCond, int bankAccount){
        this.id = id;
        this.name = name;
        this.isMobile = (isMobile == 1);
        switch (paymentCond){
            case(1)->
                    this.paymentCond = PaymentConditions.FLOW30;
            case(2)->
                    this.paymentCond = PaymentConditions.FLOW60;
            default->
                    this.paymentCond = PaymentConditions.FLOW;
        }
        this.bankAccount = bankAccount;
    }

    public int GetId(){return this.id;}
    public String GetName(){return this.name;}
    public PaymentConditions GetCond(){return this.paymentCond;}
    public boolean GetIsMobile(){return this.isMobile;}

    public int GetBankAccount() {
        return this.bankAccount;
    }
}
