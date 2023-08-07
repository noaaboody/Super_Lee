package Backend.DataAccessLayer.DTOs;

public class SupplierToDayDTO extends DTO{
    private int supplierId;
    private int day;

    public SupplierToDayDTO(int supplierId, int day) {
        this.supplierId = supplierId;
        this.day = day;
    }

    public int getSupplierId() {
        return supplierId;
    }

    public int getDay() {
        return day;
    }
}
