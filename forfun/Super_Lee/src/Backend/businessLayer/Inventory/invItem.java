package Backend.businessLayer.Inventory;

import Backend.DataAccessLayer.DTOs.DamageAndExpirationProductDTO;
import Backend.DataAccessLayer.DTOs.InvItemDTO;
import Backend.DataAccessLayer.DTOs.ItemDTO;
import Backend.DataAccessLayer.DTOs.SaleDTO;
import Backend.DataAccessLayer.SaleMapper;
import Backend.businessLayer.Item;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class invItem extends Item {
    private int stockLine;
    private int stockShelf;
    private int storeLine;
    private int storeShelf;
    private int stockQuantity;
    private int storeQuantity;
    private int minimalAmount;
    private int requiredAmount;
    private float storePrice;
    private Map<Integer,Sale> sales;
    private Map<LocalDate, List<Integer>> expirationDates;
    private List<DamageAndExpirationProductDTO> damageAndExpirationProductDTO;
    private SaleMapper saleMapper = new SaleMapper();

    public invItem(int inventoryID,int itemID, String name, String manufacturer, int stockLine, int stockShelf, int storeLine,int storeShelf , int stockQuantity, int storeQuantity, int minimalAmount, int requiredAmount, float storePrice, Map<LocalDate, List<Integer>> expirationDates) {
        super(itemID, name, manufacturer);
        this.stockLine = stockLine;
        this.stockShelf = stockShelf;
        this.storeLine = storeLine;
        this.storeShelf = storeShelf;
        this.stockQuantity = stockQuantity;
        this.storeQuantity = storeQuantity;
        this.minimalAmount = minimalAmount;
        this.requiredAmount = requiredAmount;
        this.storePrice = storePrice;
        this.sales = new HashMap<>();
        this.damageAndExpirationProductDTO = new ArrayList<>();
        if (expirationDates == null){
            this.expirationDates = new HashMap<>();
        }
        else {
            this.expirationDates = expirationDates;
            for (Map.Entry<LocalDate, List<Integer>> entry: expirationDates.entrySet()){
                for (int i:entry.getValue()){
                    DamageAndExpirationProductDTO dto = new DamageAndExpirationProductDTO(inventoryID,i,itemID,entry.getKey().toString(),0);
                    damageAndExpirationProductDTO.add(dto);
                }
            }
        }
    }

    public invItem(ItemDTO itemDTO,InvItemDTO invItemDTO, List<SaleDTO> saleDTOS, List<DamageAndExpirationProductDTO> DEdtos){
        super(itemDTO.getItemId(),itemDTO.getName(),itemDTO.getManufacturer());
        this.stockLine = invItemDTO.getStockLine();
        this.stockShelf = invItemDTO.getStockShelf();
        this.storeLine = invItemDTO.getStoreLine();
        this.storeShelf = invItemDTO.getStoreShelf();
        this.stockQuantity = invItemDTO.getStockQuantity();
        this.storeQuantity = invItemDTO.getStoreQuantity();
        this.minimalAmount = invItemDTO.getMinimalAmount();
        this.requiredAmount = invItemDTO.getRequiredAmount();
        this.storePrice = invItemDTO.getStorePrice();
        this.sales = new HashMap<>();
        this.expirationDates = new HashMap<>();
        if (!saleDTOS.isEmpty()){
            for (SaleDTO dto: saleDTOS) {
                Sale sale = new Sale(dto);
                sales.put(sale.getSaleID(),sale);
            }
        }
        if (!DEdtos.isEmpty()){
            for (DamageAndExpirationProductDTO dto: DEdtos) {
                if (dto.getExpirationDate() != null) {
                    LocalDate date = LocalDate.parse(dto.getExpirationDate());
                    if (!expirationDates.containsKey(date)){
                        List <Integer> products = new ArrayList<>();
                        products.add(dto.getProductID());
                        expirationDates.put(date,products);
                    }
                    else {
                        expirationDates.get(date).add(dto.getProductID());
                    }
                }
            }
        }
    }


    public Map<Integer, Sale> getSales() {
        return sales;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public int getStoreQuantity() {
        return storeQuantity;
    }

    public void setStoreQuantity(int storeQuantity) {
        this.storeQuantity = storeQuantity;
    }

    public int getMinimalAmount() {
        return minimalAmount;
    }

    public void setMinimalAmount(int minimalAmount) throws Exception {
        if (minimalAmount<0){
            throw new Exception("illegal minimal amount");
        }
        else if (minimalAmount>getRequiredAmount()){
            throw new Exception("minimal amount is smaller then required amount");
        }
        else {
            this.minimalAmount = minimalAmount;
        }
    }

    public float getStorePrice() {
        return storePrice;
    }

    public void setStorePrice(float storePrice) {
        this.storePrice = storePrice;
    }

    public void setSale(int saleID,Sale sale) {
        sales.put(saleID,sale);
    }

    public void removeSale(int saleID) throws Exception {
        if(sales.containsKey(saleID)){
            sales.remove(saleID);
        }
        else{
            throw new Exception("this item does not have this sale");
        }
    }

    public Map<LocalDate, List<Integer>> getExpirationDates() {
        return expirationDates;
    }

    public void removeFromExpirationDates(int productID) {
        for (Map.Entry<LocalDate,List<Integer>> entry : expirationDates.entrySet()){
            if (entry.getValue().contains(productID)){
                entry.getValue().remove(productID);
            }
        }
    }

    public void addToExpirationDates(LocalDate date, int productID){
        if (!expirationDates.containsKey(date)) {
            expirationDates.put(date, new ArrayList<>());
        }
        expirationDates.get(date).add(productID);
    }

    /*
    return the final price after the discount.
     */
    public float getFinalPrice (int inventoryID) {
        if (sales.size() != 0 ){
            float percent = 100-sales.entrySet().iterator().next().getValue().getReduction();
            return (storePrice*percent)/100;
        }
        return storePrice;
    }

    public int getStockLine() {
        return stockLine;
    }

    public void setStockLine(int stockLine) {
        this.stockLine = stockLine;
    }

    public int getStockShelf() {
        return stockShelf;
    }

    public void setStockShelf(int stockShelf) {
        this.stockShelf = stockShelf;
    }

    public int getStoreLine() {
        return storeLine;
    }

    public void setStoreLine(int storeLine) {
        this.storeLine = storeLine;
    }

    public int getStoreShelf() {
        return storeShelf;
    }

    public void setStoreShelf(int storeShelf) {
        this.storeShelf = storeShelf;
    }

    public int getRequiredAmount() {
        return requiredAmount;
    }

    public void setRequiredAmount(int requiredAmount) throws Exception {
        if (requiredAmount<getMinimalAmount()){
            throw new Exception("required amount is smaller then minimal amount");
        }
        this.requiredAmount = requiredAmount;
    }

    public String toString(){
        StringBuilder itemDetails = new StringBuilder();
        itemDetails.append("item id: ").append(this.GetId()).append("\n").append("name: ").append(this.GetName()).append("\n").append("location in stock: line ").append(this.getStockLine()).append(" shelf ").append(this.getStockShelf()).append("\n").append("location in store: line ").append(this.getStoreLine()).append(" shelf ").append(this.getStoreShelf()).append("\n").append("manufacturer: ").append(this.GetManufacturer()).append("\n").append("stock quantity: ").append(this.getStockQuantity()).append("\n").append("store quantity: ").append(this.getStoreQuantity()).append("\n").append("minimal amount: ").append(this.getMinimalAmount()).append("\n").append("required amount: ").append(this.getRequiredAmount()).append("\n").append("store price: ").append(this.getStorePrice()).append("\n");
        itemDetails.append("item sales:\n");
        for (Map.Entry<Integer,Sale> entry : this.getSales().entrySet()){
            Sale sale = entry.getValue();
            itemDetails.append("sale id: ").append(sale.getSaleID()).append(", reduction: ").append(sale.getReduction()).append(", type: ").append(sale.getSaleType()).append(", start date: ").append(sale.getStartDate()).append(", end date: ").append(sale.getEndDate()).append(".\n");
        }
        itemDetails.append("expiration dates:\n");
        for (Map.Entry<LocalDate, List<Integer>> entry : this.getExpirationDates().entrySet()){
            itemDetails.append("product id's that will be expired on ").append(entry.getKey().toString()).append(": ");
            List<Integer> productIds = entry.getValue();
            for (Integer productId : productIds) {
                itemDetails.append(productId).append(" | ");
            }
            itemDetails.append("\n");
        }
        return itemDetails.toString();
    }

    public List<DamageAndExpirationProductDTO> getDamageAndExpirationProductDTO() {
        return damageAndExpirationProductDTO;
    }
}
