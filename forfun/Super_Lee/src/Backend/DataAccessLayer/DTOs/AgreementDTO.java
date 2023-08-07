package Backend.DataAccessLayer.DTOs;

public class AgreementDTO extends DTO{

    private int supplierId;
    private boolean IsConstantSupply;
    private int conditionId;

    public AgreementDTO(int supplierId, int IsConstantSupply, int conditionId){
        this.supplierId = supplierId;
        this.conditionId = conditionId;
        this.IsConstantSupply = (IsConstantSupply == 1);
    }
    public int GetSupplierId(){return this.supplierId;}
    public int GetConditionId(){return this.conditionId;}
    public boolean GetIsConstSupply(){return this.IsConstantSupply;}
}
