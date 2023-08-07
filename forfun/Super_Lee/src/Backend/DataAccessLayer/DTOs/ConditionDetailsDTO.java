package Backend.DataAccessLayer.DTOs;

public class ConditionDetailsDTO extends DTO{

    private int conditionId;
    private int supplierId;
    private boolean isForPrice;
    private boolean isForQuantity;
    private double neededAmount;
    private double percentage;

    public ConditionDetailsDTO(int conditionId, int supplierId, int isForPrice, int isForQuantity, double neededAmount, double percentage){
        this.conditionId = conditionId;
        this.supplierId = supplierId;
        this.isForPrice = (isForPrice == 1);
        this.isForQuantity = (isForQuantity == 1);
        this.neededAmount = neededAmount;
        this.percentage = percentage;
    }

    public int getConditionId() {
        return conditionId;
    }

    public int getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(int supplierId) {
        this.supplierId = supplierId;
    }

    public boolean isForPrice() {
        return isForPrice;
    }

    public void setForPrice(boolean forPrice) {
        isForPrice = forPrice;
    }

    public boolean isForQuantity() {
        return isForQuantity;
    }

    public void setForQuantity(boolean forQuantity) {
        isForQuantity = forQuantity;
    }

    public double getNeededAmount() {
        return neededAmount;
    }

    public void setNeededAmount(int neededAmount) {
        this.neededAmount = neededAmount;
    }

    public double getPercentage() {
        return percentage;
    }

    public void setPercentage(float percentage) {
        this.percentage = percentage;
    }
}
