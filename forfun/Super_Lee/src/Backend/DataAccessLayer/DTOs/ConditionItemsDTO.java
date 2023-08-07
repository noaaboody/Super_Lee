package Backend.DataAccessLayer.DTOs;

public class ConditionItemsDTO extends DTO{

    private int supplierId;
    private int itemId;
    private int conditionId;

    public ConditionItemsDTO(int supplierId, int itemId, int conditionId) {
        this.supplierId = supplierId;
        this.itemId = itemId;
        this.conditionId = conditionId;
    }

    public int getSupplierId() {
        return supplierId;
    }

    public int getItemId() {
        return itemId;
    }

    public int getConditionId() {
        return conditionId;
    }
}
