package Backend.serviceLayer;

import Backend.businessLayer.Inventory.InventoryController;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class InventoryService {
    InventoryController inventory;

    public InventoryService(InventoryController inventory) {
        this.inventory = inventory;
    }

    public InventoryController getInventory() {
        return inventory;
    }

    public String addItem(int itemID , int stockLine, int stockShelf, int storeLine, int storeShelf, int stockQuantity,
                          int storeQuantity, int minimalAmount, int requiredAmount, float storePrice, Map<LocalDate, List<Integer>> expirationDates, String category, String subCategory, String subSubCategory) {
        String message;
        try{
            inventory.addItem(itemID , stockLine, stockShelf,storeLine ,storeShelf,  stockQuantity,
                    storeQuantity, minimalAmount,requiredAmount, storePrice, expirationDates, category,subCategory,subSubCategory);
            message = "ITEM ADDED\n" + inventory.showItemDetails(itemID) +
                    "Category:" + category + ", SubCategory:" + subCategory + ", SubSubCategory:" + subSubCategory + "\n";
            return message;
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public String addCategory(String categoryName){
        try {
            return inventory.addCategory(categoryName);
        }
        catch (Exception e){
            return e.getMessage();
        }
    }

    public String addSubCategory(String categoryName, String subCategoryName){
        try {
            return inventory.addSubCategory(categoryName, subCategoryName);
        }
        catch (Exception e){
            return e.getMessage();
        }
    }

    public String addSubSubCategory(String categoryName, String subCategoryName, String subSubCategoryName){
        try {
            return inventory.addSubSubCategory(categoryName, subCategoryName, subSubCategoryName);
        }
        catch (Exception e){
            return e.getMessage();
        }
    }

    public String makeMissingReport (Map<Integer, Integer> missingItems){
        try{
            return inventory.makeMissingReport(missingItems).toString();
        }
        catch (Exception e){
            return e.getMessage() + "\norder report was not created. returning to the main menu.";
        }
    }

    public String makeMissingOrder (Map<Integer, Integer> missingItems){
        try{
            return inventory.makeMissingOrder(missingItems);
        }
        catch (Exception e){
            return e.getMessage() + "\norder was not created. returning to the main menu.";
        }
    }

    public String makePeriodOrder (Map<Integer, Integer> missingItems, int supplierID){
        try{
            return inventory.makeRequiredOrder(missingItems,supplierID).toString();
        }
        catch (Exception e){
            return e.getMessage() + "\norder was not created. returning to the main menu.";
        }
    }

    public String makeExistingReportByCategories(List<String> categoryNames){
        try{
            return inventory.makeExistingReportByCategories(categoryNames).toString();
        }
        catch (Exception e){
            return e.getMessage() + "\nexisting report by categories was not created. returning to main menu";
        }
    }

    public String makeExistingReport() {
        try{
            return inventory.makeExistingReport().toString();
        }
        catch (Exception e){
            return e.getMessage() + "existing report was not created. returning to main menu";
        }
    }

    public String reportAboutDamagedItem(int itemID, List<Integer> productsIDs) {
        try {
            return inventory.reportAboutDamagedItem(itemID, productsIDs);
        } catch (Exception e) {
            return e.getMessage() + "report about a damaged item was not created. returning to main menu";
        }
    }

    public String makeDefectivesReport(LocalDate from, LocalDate until){
        try{
            return inventory.makeDefectivesReport(from, until).toString();
        }
        catch (Exception e){
            return e.getMessage() + "defectives report was not created. returning to the main menu";
        }
    }

    public String setMinimalAmount(int itemID, int newMinAmount){
        try{
            return inventory.setMinimalAmount(itemID,newMinAmount) + "\n\n" + inventory.showItemDetails(itemID);
        }
        catch (Exception e){
            return e.getMessage() + "minimal amount has not changed.";
        }
    }

    public String setRequiredAmount(int itemID, int newReqAmount){
        try{
            inventory.setRequiredAmount(itemID,newReqAmount);
            return inventory.showItemDetails(itemID);
        }
        catch (Exception e){
            return e.getMessage() + "minimal amount has not changed.";
        }
    }

    public String getFinalPrice(int itemID){
        try{
            return "" + inventory.getItemByID(itemID).getFinalPrice(inventory.getInventoryID());
        }
        catch (Exception e){
            return e.getMessage();
        }
    }

    public String removeItemsAfterSale(int itemID,List<Integer> productIDs){
        try{
            return inventory.removeItemsAfterSale(itemID,productIDs);
        }
        catch (Exception e){
            return e.getMessage();
        }
    }

    public String updateItemsOrderArrived(int itemID, List<Integer> productIDs, LocalDate date){
        try{
            return inventory.updateItemsOrderArrived(itemID,productIDs,date);
        }
        catch (Exception e){
            return e.getMessage();
        }
    }

    public String addSaleForItem(int itemID, float discount, LocalDate startDate, LocalDate endDate){
        try{
            return inventory.addSaleForItem(itemID, discount, startDate, endDate);
        }
        catch (Exception e){
            return e.getMessage();
        }
    }

    public String addSaleForCategory(String categoryName, float discount, LocalDate startDate, LocalDate endDate){
        try{
            return inventory.addSaleForCategory(categoryName, discount, startDate, endDate);
        }
        catch (Exception e){
            return e.getMessage();
        }
    }

    public String removeSaleFromItem(int saleID, int itemID){
        try{
            return inventory.removeSaleFromItem(saleID, itemID);
        }
        catch (Exception e){
            return e.getMessage();
        }
    }

    public String removeSaleFromCategory(int saleID, String categoryName){
        try{
            return inventory.removeSaleFromCategory(saleID, categoryName);
        }
        catch (Exception e){
            return e.getMessage();
        }
    }

    public String getProductsWithMinimalAmount(){
        try{
            return inventory.checkMinimalAmount();
        }
        catch (Exception e){
            return e.getMessage();
        }
    }

    public String getProductsWithRequiredAmount(){
        try{
            return inventory.checkRequiredAmount();
        }
        catch (Exception e){
            return e.getMessage();
        }
    }

    public String getItemDetails(int itemID){
        try{
            return inventory.showItemDetails(itemID);
        }
        catch (Exception e){
            return e.getMessage();
        }
    }

    public String itemsFromStoreToStock(int itemID, int quantity){
        try {
            return inventory.itemsFromStoreToStock(itemID,quantity);
        }
        catch (Exception e){
            return e.getMessage();
        }
    }

    public String itemsFromStockToStore(int itemID, int quantity){
        try {
            return inventory.itemsFromStockToStore(itemID,quantity);
        }
        catch (Exception e){
            return e.getMessage();
        }
    }

    public String getFutureOrders(){
        try {
            return inventory.getFutureOrders();
        }
        catch (Exception e){
            return e.getMessage();
        }
    }

//    public String makePeriodReportMon_Thu(String day){
//        try{
//            return inventory.makePeriodReportMon_Thu(day);
//        }
//        catch (Exception e){
//            return e.getMessage();
//        }
//    }
}
