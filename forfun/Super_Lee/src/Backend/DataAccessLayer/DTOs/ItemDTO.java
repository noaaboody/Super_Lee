package Backend.DataAccessLayer.DTOs;

public class ItemDTO extends DTO{

    private int itemId;
    private String name;
    private String manufacturer;

    public ItemDTO(int itemId, String name, String manufacturer) {
        this.itemId = itemId;
        this.name = name;
        this.manufacturer = manufacturer;
    }

    public int getItemId() {
        return itemId;
    }

    public String getName() {
        return name;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    @Override
    public String toString() {
        return "ItemDTO{" +
                "itemId=" + itemId +
                ", name='" + name + '\'' +
                ", manufacturer='" + manufacturer + '\'' +
                '}';
    }
}
