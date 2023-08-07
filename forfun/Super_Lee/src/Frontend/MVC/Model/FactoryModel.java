package Frontend.MVC.Model;

import Backend.serviceLayer.FactoryService;
import Backend.serviceLayer.InventoryService;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class FactoryModel {
    public FactoryService factoryService;

    public FactoryModel(){
        factoryService = new FactoryService();
    }

    public InventoryService getInvById(int invID){
        return factoryService.getExistingInv(invID);
    }

    public String AddCategory(int invID, String name){
        return getInvById(invID).addCategory(name);
    }

    public String AddSaleCategory(int invID, String categoryName, float discount, LocalDate startDate, LocalDate endDate){
        return getInvById(invID).addSaleForCategory(categoryName, discount, startDate, endDate);
    }

    public String AddNewInventory(String address){
        InventoryService inventoryService = factoryService.makeNewInv(address);
        return "new inventory was added. id: " + inventoryService.getInventory().getInventoryID();
    }

    public String AddSubCategory(int invID, String categoryName, String subCategoryName) {
        return getInvById(invID).addSubCategory(categoryName, subCategoryName);
    }

    public String AddSubSubCategory(int invID, String categoryName, String subCategoryName, String subSubCategoryName) {
        return getInvById(invID).addSubSubCategory(categoryName, subCategoryName, subSubCategoryName);
    }

    public String RemoveSaleCategory(int invID, String categoryName, int saleId) {
        return getInvById(invID).removeSaleFromCategory(saleId, categoryName);
    }

    public String ExistingReportByCategories(int invID, List<String> categoryNames){
        return getInvById(invID).makeExistingReportByCategories(categoryNames);
    }

    // todo delete from here items
    public String AddItem(int invID, int itemID , int stockLine, int stockShelf, int storeLine, int storeShelf, int stockQuantity,
                          int storeQuantity, int minimalAmount, int requiredAmount, float storePrice, Map<LocalDate, List<Integer>> expirationDates, String category, String subCategory, String subSubCategory){
        return getInvById(invID).addItem(itemID ,stockLine,stockShelf, storeLine, storeShelf, stockQuantity,storeQuantity,minimalAmount, requiredAmount, storePrice, expirationDates, category, subCategory, subSubCategory);
    }

    public String changeMinAmount(int invID, int itemID, int newMinAmount){
        return getInvById(invID).setMinimalAmount(itemID,newMinAmount);
    }

    public String finalPrice(int invID, int itemID){
        return getInvById(invID).getFinalPrice(itemID);
    }

    public String addSaleItem(int invID, int itemID, float discount, LocalDate startDate,  LocalDate endDate){
        return getInvById(invID).addSaleForItem(itemID,discount,startDate,endDate);
    }

    public String removeSaleItem(int invID, int itemID, int saleID){
        return getInvById(invID).removeSaleFromItem(itemID,saleID);
    }

    public String damagedItem(int invID, int itemID, List<Integer> products){
        return getInvById(invID).reportAboutDamagedItem(itemID,products);
    }

    public String productsWithMinAmount(int invID){
        return null;
    }

    public String ItemDetails(int invID, int itemID){
        return getInvById(invID).getItemDetails(itemID);
    }

    public String removeItemsAfterSale(int invID, int itemID, List<Integer> products){
        return getInvById(invID).removeItemsAfterSale(itemID, products);

    }

    public String updateItemsOrderArrived(int invID, int itemID, List<Integer> products, LocalDate date){
        return getInvById(invID).updateItemsOrderArrived(itemID,products,date);
    }

    public String itemsFromStockToStore(int invID, int itemID, int amount){
        return getInvById(invID).itemsFromStockToStore(itemID,amount);

    }

    public String itemsFromStoreToStock(int invID, int itemID, int amount){
        return getInvById(invID).itemsFromStoreToStock(itemID,amount);
    }

    public String changeRequiredAmount(int invID, int itemID, int newReq){
        return getInvById(invID).setRequiredAmount(itemID, newReq);

    }

    //report actions

    public String existingReport(int invID){
        return getInvById(invID).makeExistingReport();
    }

    public String defectivesReport(int invID, LocalDate startDate, LocalDate endDate){
        return getInvById(invID).makeDefectivesReport(startDate,endDate);
    }

    // order actions

    public String makeMissingReport(int invID, Map<Integer, Integer> missingItems){
        return getInvById(invID).makeMissingOrder(missingItems);
    }

    public String makePeriodReport(int invID, Map<Integer, Integer> missingItems, int supplierID){
        return getInvById(invID).makePeriodOrder(missingItems, supplierID);
    }

    public String receiveOrders(int invID){
        try{
            getInvById(invID).getInventory().receiveOrders();
            return "today's orders received and quantities updated accordingly";
        }
        catch(Exception e){
            return "error occurred";
        }
    }

    public String getFutureOrders(int invID){
        return getInvById(invID).getFutureOrders();
    }
}
