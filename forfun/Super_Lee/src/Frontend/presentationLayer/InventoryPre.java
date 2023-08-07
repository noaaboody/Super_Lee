package Frontend.presentationLayer;

import Backend.businessLayer.OrderHandler;
import Backend.serviceLayer.FactoryService;
import Backend.serviceLayer.InventoryService;

import java.time.LocalDate;
import java.util.*;

public class InventoryPre {

    private FactoryService factoryService;
    private static Scanner input = new Scanner(System.in);
    private Map<String, String> messages = new HashMap<>();

    public InventoryPre(FactoryService factoryService) {
        this.factoryService = factoryService;
    }

    public void start(){
        defineMessages(messages);
        System.out.println(messages.get("main"));
        int choice = input.nextInt();
        while(choice != -1){
            switch (choice) {
                case 1 -> existingInventoryActions();
                case 2 -> makeNewInventory();
                default -> {}
            }
            System.out.println(messages.get("main"));
            choice = input.nextInt();
        }
        System.out.println("goodbye");
    }

    public void makeNewInventory(){
        System.out.println("enter inventory address");
        input.nextLine();
        String address = input.nextLine();
        InventoryService inventoryService = factoryService.makeNewInv(address);
        System.out.println("new inventory was added. inventoryID: " + inventoryService.getInventory().getInventoryID());
    }

    public void existingInventoryActions(){
        System.out.println("enter inventory id");
        int inventoryID = input.nextInt();
        InventoryService inventoryService = factoryService.getExistingInv(inventoryID);
        if(inventoryService != null){
            System.out.println(messages.get("existingInventoryActions"));
            int actionKind = input.nextInt();
            if(actionKind != -1) {
                switch (actionKind) {
                    case 1 -> itemActions(inventoryService);
                    case 2 -> categoryActions(inventoryService);
                    case 3 -> reportActions(inventoryService);
                    case 4 -> orderActions(inventoryService);
                    //case 5 -> initialize(inventoryService);
                    //case 6 -> dayToday(inventoryService);
                    default -> {
                    }
                }
                System.out.println(messages.get("main"));
                actionKind = input.nextInt();
            }
        }
        else{
            System.out.println("no such inventory id");
        }
    }

//    public void dayToday(InventoryService inventoryService){
//        System.out.println("Hello, first please enter the day today: ");
//        String today = input.next();
//        System.out.println(inventoryService.getInventory().makePeriodReportMon_Thu(today));
//    }

    private void defineMessages(Map<String, String> messages) {
        String main = """
                Choose action kind (or -1 to finish):
                1. actions on existing inventory
                2. create new inventory""";
        String existingInventoryActions = """
                Choose action kind (or -1 to go back):
                1. item actions
                2. category actions
                3. report actions
                4. order actions""";
        String itemActions = """
                Choose item action (or -1 to go back):
                1. add item
                2. change minimal amount for an item
                3. get final price of an item
                4. add sale for an item
                5. remove sale for an item
                6. report about a damaged item
                7. get products with minimal amount
                8. get item details
                9. update amount after sale
                10. update amount after order arrived
                11. move items from stock to store
                12. move items from store to stock
                13. change required amount for an item""";
        String categoryActions = """
                Choose category action (or -1 to go back):
                1. add sale for a category/subCategory/subSubCategory
                2. remove sale for a category/subCategory/subSubCategory
                3. create existing report by categories
                4. add category
                5. add sub category
                6. add sub sub category""";
        String reportActions = """
                Choose report action (or -1 to go back):
                1. create order report
                2. create existing report
                3. create existing report by categories
                4. report about a damaged item
                5. create defectives report""";
        String orderActions = """
                Choose order action (or -1 to go back):
                1. create missing order
                2. update periodic order
                3. receive orders due today
                4. get future orders""";
        messages.put("main", main);
        messages.put("existingInventoryActions", existingInventoryActions);
        messages.put("itemActions", itemActions);
        messages.put("categoryActions", categoryActions);
        messages.put("reportActions", reportActions);
        messages.put("orderActions", orderActions);
    }

    private void  itemActions(InventoryService inventoryService){
        System.out.println(messages.get("itemActions"));
        int itemAction = input.nextInt();
        if(itemAction != -1){
            switch (itemAction) {
                case 1 -> addItem(inventoryService);
                case 2 -> changeMinAmount(inventoryService);
                case 3 -> finalPrice(inventoryService);
                case 4 -> addSaleItem(inventoryService);
                case 5 -> removeSaleItem(inventoryService);
                case 6 -> damagedItem(inventoryService);
                case 7 -> productsWithMinAmount(inventoryService);
                case 8 -> ItemDetails(inventoryService);
                case 9 -> removeItemsAfterSale(inventoryService);
                case 10 -> updateItemsOrderArrived(inventoryService);
                case 11 -> itemsFromStockToStore(inventoryService);
                case 12 -> itemsFromStoreToStock(inventoryService);
                case 13 -> changeRequiredAmount(inventoryService);
                default -> {}
            }
        }
    }

    private void categoryActions(InventoryService inventoryService){
        System.out.println(messages.get("categoryActions"));
        int categoryAction = input.nextInt();
        if(categoryAction != -1) {
            switch (categoryAction) {
                case 1 -> addSaleCategory(inventoryService);
                case 2 -> removeSaleCategory(inventoryService);
                case 3 -> existingReportByCategories(inventoryService);
                case 4 -> addCategory(inventoryService);
                case 5 -> addSubCategory(inventoryService);
                case 6 -> addSubSubCategory(inventoryService);
                default -> {}
            }
        }
    }

    private void reportActions(InventoryService inventoryService){
        System.out.println(messages.get("reportActions"));
        int reportAction = input.nextInt();
        if(reportAction != -1) {
            switch (reportAction) {
                case 1 -> orderReport(inventoryService);
                case 2 -> existingReport(inventoryService);
                case 3 -> existingReportByCategories(inventoryService);
                case 4 -> damagedItem(inventoryService);
                case 5 -> defectivesReport(inventoryService);
                default -> {}
            }
        }
    }

    private void orderActions(InventoryService inventoryService){
        System.out.println(messages.get("orderActions"));
        int orderAction = input.nextInt();
        if(orderAction != -1) {
            switch (orderAction) {
                case 1 -> makeMissingOrder(inventoryService);
                case 2 -> makePeriodOrder(inventoryService);
                case 3 -> receiveTodayOrders(inventoryService);
                case 4 -> getFutureOrders(inventoryService);
                default -> {}
            }
        }
    }

    private void getFutureOrders(InventoryService inventoryService){
        System.out.println(inventoryService.getFutureOrders());
    }

    private void receiveTodayOrders(InventoryService inventoryService) {
        inventoryService.getInventory().receiveOrders();
    }

    public void makePeriodOrder(InventoryService inventoryService){
        //supplierID
        System.out.println("enter supplier id");
        int supplierID = input.nextInt();

        // missingItems
        Map<Integer, Integer> missingItems = new HashMap<>();
        System.out.println("enter item id or -1 to finish");
        int itemID = input.nextInt();
        while(itemID != -1){
            //invItem item = inventoryService.getInventory().getItems().get(itemID);
            System.out.println("what is the missing quantity?");
            Integer missingQuantity = input.nextInt();
            missingItems.put(itemID, missingQuantity);
            System.out.println("enter item id or -1 to finish");
            itemID = input.nextInt();
        }
        System.out.println(inventoryService.makePeriodOrder(missingItems, supplierID));
    }


    public void makeMissingOrder(InventoryService inventoryService){
        // missingItems
        Map<Integer, Integer> missingItems = new HashMap<>();
        System.out.println("enter item id or -1 to finish");
        int itemID = input.nextInt();
        while(itemID != -1){
            System.out.println("what is the missing quantity?");
            Integer missingQuantity = input.nextInt();
            missingItems.put(itemID, missingQuantity);
            System.out.println("enter item id or -1 to finish");
            itemID = input.nextInt();
        }
        System.out.println(inventoryService.makeMissingOrder(missingItems));
    }


    public void addCategory(InventoryService inventoryService){
        System.out.println("enter category name to add");
        String categoryName = input.next();
        System.out.println(inventoryService.addCategory(categoryName));
    }

    public void addSubCategory(InventoryService inventoryService){
        System.out.println("enter category name");
        String categoryName = input.next();
        System.out.println("enter sub category name to add");
        String subCategoryName = input.next();
        System.out.println(inventoryService.addSubCategory(categoryName, subCategoryName));
    }

    public void addSubSubCategory(InventoryService inventoryService){
        System.out.println("enter category name");
        String categoryName = input.next();
        System.out.println("enter sub category name");
        String subCategoryName = input.next();
        System.out.println("enter sub sub category name to add");
        String subSubCategoryName = input.next();
        System.out.println(inventoryService.addSubSubCategory(categoryName, subCategoryName, subSubCategoryName));
    }

    public void updateItemsOrderArrived(InventoryService inventoryService) {
        // Date
        System.out.println("enter arriving order date");
        LocalDate date = getDate();

        // itemID
        System.out.println("enter item id");
        int itemID = input.nextInt();

        // productsIDs
        List<Integer> productsIDs = new LinkedList<>();
        System.out.println("enter product id or -1 to finish");
        int productID = input.nextInt();
        while(productID != -1){
            productsIDs.add(productID);
            productID = input.nextInt();
        }
        String s = inventoryService.updateItemsOrderArrived(itemID, productsIDs, date);
        System.out.println(s);
        start();
    }

    public void removeItemsAfterSale(InventoryService inventoryService){
        // itemID
        System.out.println("enter item id");
        int itemID = input.nextInt();

        // productsIDs
        List<Integer> productsIDs = new LinkedList<>();
        System.out.println("enter product id or -1 to finish");
        int productID = input.nextInt();
        while(productID != -1){
            productsIDs.add(productID);
            productID = input.nextInt();
        }
        String s = inventoryService.removeItemsAfterSale(itemID,productsIDs);
        System.out.println(s);
        start();
    }

    public void ItemDetails(InventoryService inventoryService){
        System.out.println("enter item id");
        int itemID = input.nextInt();
        String s = inventoryService.getItemDetails(itemID);
        System.out.println(s);

        start();
    }

    public void productsWithMinAmount(InventoryService inventoryService){
        System.out.println(inventoryService.getProductsWithMinimalAmount());
    }

    public void defectivesReport(InventoryService inventoryService){
        // from
        System.out.println("choose the dates you want to get report about");
        System.out.println("enter start date");
        LocalDate from = getDate();

        // until
        System.out.println("enter end date");
        LocalDate until = getDate();

        System.out.println(inventoryService.makeDefectivesReport(from, until));

        start();
    }

    public void damagedItem(InventoryService inventoryService){
        // itemID
        System.out.println("enter item id");
        int itemID = input.nextInt();

        // productsIDs
        List<Integer> productsIDs = new LinkedList<>();
        System.out.println("enter product id or -1 to finish");
        int productID = input.nextInt();
        while(productID != -1){
            productsIDs.add(productID);
            productID = input.nextInt();
        }

        System.out.println(inventoryService.reportAboutDamagedItem(itemID, productsIDs));

        start();
    }

    public void existingReport(InventoryService inventoryService){
        System.out.println(inventoryService.makeExistingReport());

        start();
    }


    public void existingReportByCategories(InventoryService inventoryService){
        // categories
        List<String> categoryNames = new LinkedList<>();
        System.out.println("enter categories name. enter FIN to finish");
        String categoryName = input.next();
        while(!categoryName.equals("FIN")){
            categoryNames.add(categoryName);
            categoryName = input.next();
        }

        System.out.println(inventoryService.makeExistingReportByCategories(categoryNames));

        start();
    }

    public void removeSaleCategory(InventoryService inventoryService){
        //category/subCategory/subSubCategory name
        System.out.println("enter category/subCategory/subSubCategory name");
        String categoryName = input.next();

        //saleID
        System.out.println("enter sale id");
        int saleID = input.nextInt();

        System.out.println(inventoryService.removeSaleFromCategory(saleID, categoryName));
    }

    public void removeSaleItem(InventoryService inventoryService){
        //ID
        System.out.println("enter item id");
        int itemID = input.nextInt();

        //saleID
        System.out.println("enter sale id");
        int saleID = input.nextInt();

        System.out.println(inventoryService.removeSaleFromItem(saleID, itemID));
    }

    public void addSaleCategory(InventoryService inventoryService){
        // category/subCategory/subSubCategory name
        System.out.println("enter category/subCategory/subSubCategory name");
        String categoryName = input.next();

        // discount
        System.out.println("enter discount");
        float discount = input.nextFloat();

        // startDate
        System.out.println("enter start date");
        LocalDate startDate = getDate();

        // endDate
        System.out.println("enter end date");
        LocalDate endDate = getDate();

        System.out.println(inventoryService.addSaleForCategory(categoryName, discount, startDate, endDate));
    }

    public void addSaleItem(InventoryService inventoryService){
        //ID
        System.out.println("enter item id");
        int itemID = input.nextInt();

        // discount
        System.out.println("enter discount");
        float discount = input.nextFloat();

        // startDate
        System.out.println("enter start date");
        LocalDate startDate = getDate();

        // endDate
        System.out.println("enter end date");
        LocalDate endDate = getDate();

        System.out.println(inventoryService.addSaleForItem(itemID, discount, startDate, endDate));
    }

    public void finalPrice(InventoryService inventoryService){
        System.out.println("enter item id");
        int itemID = input.nextInt();
        System.out.println("final price of item id " + itemID + " is: " + inventoryService.getFinalPrice(itemID));
    }

    public void changeMinAmount(InventoryService inventoryService){
        System.out.println("enter item id");
        int itemID = input.nextInt();
        System.out.println("enter new minimal amount for the item");
        int newMinAmount = input.nextInt();
        System.out.println(inventoryService.setMinimalAmount(itemID, newMinAmount));
        start();
    }

    public void changeRequiredAmount(InventoryService inventoryService){
        System.out.println("enter item id");
        int itemID = input.nextInt();
        System.out.println("enter new required amount for the item");
        int newReqAmount = input.nextInt();
        System.out.println(inventoryService.setRequiredAmount(itemID, newReqAmount));
        start();
    }

    public void itemsFromStoreToStock(InventoryService inventoryService){
        System.out.println("enter item id");
        int itemID = input.nextInt();
        System.out.println("enter quantity of items from store to stock");
        int quantity = input.nextInt();
        System.out.println(inventoryService.itemsFromStoreToStock(itemID, quantity));
        start();
    }

    public void itemsFromStockToStore(InventoryService inventoryService){
        System.out.println("enter item id");
        int itemID = input.nextInt();
        System.out.println("enter quantity of items from stock to store");
        int quantity = input.nextInt();
        System.out.println(inventoryService.itemsFromStockToStore(itemID, quantity));
        start();
    }

    public void orderReport(InventoryService inventoryService){
        // missingItems
        Map<Integer, Integer> missingItems = new HashMap<>();
        System.out.println("enter item id or -1 to finish");
        int itemID = input.nextInt();
        while(itemID != -1){
            //invItem item = inventoryService.getInventory().getItems().get(itemID);
            System.out.println("what is the missing quantity?");
            Integer missingQuantity = input.nextInt();
            missingItems.put(itemID, missingQuantity);
            System.out.println("enter item id or -1 to finish");
            itemID = input.nextInt();
        }
        System.out.println(inventoryService.makeMissingReport(missingItems));
    }

    public void addItem(InventoryService inventoryService){
        // id
        System.out.println("enter item's id");
        int itemId = input.nextInt();

        // name
//        System.out.println("enter the item's name");
//        String name = input.next();

        // locationInStock
        System.out.println("enter the line number of the location in stock");
        int stockLine = input.nextInt();
        System.out.println("enter the shelf number of the location in stock");
        int stockShelf = input.nextInt();

        // locationInStore
        System.out.println("enter the line number of the location in store");
        int storeLine = input.nextInt();
        System.out.println("enter the shelf number of the location in store");
        int storeShelf = input.nextInt();

        // manufacturer
//        System.out.println("enter manufacturer name");
//        String manufacturer = input.next();

        // StockQuantity
        System.out.println("enter stock quantity");
        int stockQuantity = input.nextInt();

        // storeQuantity
        System.out.println("enter store quantity");
        int storeQuantity = input.nextInt();

        // minimalAmount
        System.out.println("enter minimal amount");
        int minimalAmount = input.nextInt();

        // requiredAmount
        System.out.println("enter required amount");
        int requiredAmount = input.nextInt();

        // storePrice
        System.out.println("enter store price");
        float storePrice = input.nextFloat();

        // Map<Date, List<Integer>> expirationDates
        Map<LocalDate, List<Integer>> expirationDates = new HashMap<>();
        System.out.println("enter 'Y' to enter expiration date or 'N' to continue");
        String expirationChoice = input.next();
        while(expirationChoice.equals("Y")) {
            LocalDate expirationDate = getDate();
            List<Integer> productIDs = new LinkedList<>();
            System.out.println("enter products id's and then '-1' to finish");
            int productID = input.nextInt();
            while (productID != -1) {
                productIDs.add(productID);
                productID = input.nextInt();
            }
            expirationDates.put(expirationDate, productIDs);
            System.out.println("enter 'Y' to enter expiration date or 'N' to continue");
            expirationChoice = input.next();
        }

        // categories
        System.out.println("enter main category");
        String categoryName = input.next();

        System.out.println("enter sub category");
        String subCategoryName = input.next();

        System.out.println("enter sub sub category");
        String subSubcategoryName = input.next();

        System.out.println(inventoryService.addItem(itemId,stockLine ,stockShelf,storeLine, storeShelf, stockQuantity,
                storeQuantity, minimalAmount,requiredAmount, storePrice, expirationDates, categoryName, subCategoryName, subSubcategoryName));
        start();
    }

//    public void initialize(InventoryService inventoryService){
//        LocalDateTime april25 = LocalDateTime.of(2023, 4, 25,8,8);
//        LocalDateTime may12 = LocalDateTime.of(2023, 5, 12,8,8);
//
//        Map<LocalDateTime, List<Integer>> expirationDatesDanone = new HashMap<>();
//
//        List<Integer> april25products = new LinkedList<>();
//        april25products.add(0);
//        april25products.add(1);
//
//        List<Integer> may12products = new LinkedList<>();
//        may12products.add(2);
//        may12products.add(3);
//        may12products.add(4);
//
//        expirationDatesDanone.put(april25, april25products);
//        expirationDatesDanone.put(may12, may12products);
//
//        inventoryService.addItem(0,"danone","Strauss", 0, 0,0, 0,
//                2, 3, 1,3, (float) 3.6, expirationDatesDanone, "milkProducts", "yogurt","200");
//        inventoryService.addItem(1,"shampoo","Pinuk", 1, 1,1 ,1,
//                6, 7, 2,4, (float) 12.3, null, "cleaningProducts","beauty","750");
//        inventoryService.addItem(2,"deodorant","Dove", 2, 2,2 ,2,
//                4, 8, 1,2, (float) 6.8, null, "cleaningProducts","beauty", "200");
//
//        System.out.println("inventory " + inventoryService.getInventory().getInventoryID() + " was initialized");
//    }

    private static LocalDate getDate(){
        System.out.println("enter year");
        int year = input.nextInt();
        System.out.println("enter month");
        int month = input.nextInt();
        System.out.println("enter day");
        int day = input.nextInt();
        return LocalDate.of(year,month,day);
    }

    public OrderHandler getInvOrderHandler(int invId) {
        return factoryService.getExistingInv(invId).getInventory().getOrderHandler();
    }
}
