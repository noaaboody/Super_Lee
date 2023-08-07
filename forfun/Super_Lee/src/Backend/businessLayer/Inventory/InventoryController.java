package Backend.businessLayer.Inventory;

import Backend.DataAccessLayer.*;
import Backend.DataAccessLayer.DTOs.*;
import Backend.businessLayer.OrderHandler;
import Backend.businessLayer.Suppliers.Order;

import java.time.LocalDate;
import java.util.*;

import static java.lang.Integer.parseInt;

public class InventoryController {
    private final int inventoryID;
    private final String address;

    private final OrderHandler orderHandler;
    private int categoryIDCounter;
    private int saleIDCounter;
    private Map<Integer,invItem> items;
    private Map<String,Category> Categories;
    private Map<Integer, Sale> Sales;
    private List<DamagedItem> DamagedItems;
    private final DamageAndExpirationProductMapper damageAndExpirationProductMapper;
    private final CategoryMapper categoryMapper;
    private final SaleMapper saleMapper;
    private final InvItemMapper invItemMapper;
    private final InventoryMapper inventoryMapper;
    private final ItemMapper itemMapper;


    public InventoryController(int branchID, String address, InventoryMapper inventoryMapper,
                     DamageAndExpirationProductMapper damageAndExpirationProductMapper,
                     CategoryMapper categoryMapper, SaleMapper saleMapper, InvItemMapper invItemMapper, ItemMapper itemMapper,OrderHandler orderHandler) {
        this.inventoryID = branchID;
        this.address = address;
        this.orderHandler = orderHandler;
        this.categoryIDCounter = 0;
        this.saleIDCounter = 0;
        this.items = new HashMap<>();
        this.Categories = new HashMap<>();
        this.Sales = new HashMap<>();
        this.DamagedItems = new ArrayList<>();
        this.damageAndExpirationProductMapper = damageAndExpirationProductMapper;
        this.categoryMapper = categoryMapper;
        this.invItemMapper = invItemMapper;
        this.itemMapper = itemMapper;
        this.saleMapper = saleMapper;
        this.inventoryMapper = inventoryMapper;
        inventoryMapper.insertInventory(branchID, address);
    }

    public InventoryController (int branchID, String address, int categoryIDCounter, int saleIDCounter, InventoryMapper inventoryMapper,
                      DamageAndExpirationProductMapper damageAndExpirationProductMapper,
                      CategoryMapper categoryMapper, SaleMapper saleMapper, InvItemMapper invItemMapper, ItemMapper itemMapper,OrderHandler orderHandler){
        this.inventoryID = branchID;
        this.address = address;
        this.orderHandler = orderHandler;
        this.categoryIDCounter = categoryIDCounter;
        this.saleIDCounter = saleIDCounter;
        this.items = new HashMap<>();
        this.Categories = new HashMap<>();
        this.Sales = new HashMap<>();
        this.DamagedItems = new ArrayList<>();
        this.damageAndExpirationProductMapper = damageAndExpirationProductMapper;
        this.categoryMapper = categoryMapper;
        this.invItemMapper = invItemMapper;
        this.itemMapper = itemMapper;
        this.saleMapper = saleMapper;
        this.inventoryMapper = inventoryMapper;
    }

    public void increaseCategoryIDCounter() {
        this.categoryIDCounter++;
        inventoryMapper.updateCategoryIDCounter(inventoryID, this.categoryIDCounter);
    }

    public void increaseSaleIDCounter() {
        this.saleIDCounter++;
        inventoryMapper.updateSaleIDCounter(inventoryID, this.saleIDCounter);
    }

    //===============================================================================================================
//                    Item Functions
//===============================================================================================================

    public String setMinimalAmount(int itemID, int newMinAmount) throws Exception {
        //the itemID exist in business layer
        if (items.containsKey(itemID)){
            getItems().get(itemID).setMinimalAmount(newMinAmount);
            if(invItemMapper.updateMinAmount(inventoryID,itemID,newMinAmount)){
                return checkMinimalAmount() + "\n\nitem's minimal amount was updated";
            }
        }
        //load item from db
        else if (invItemMapper.selectItemInvByID(inventoryID,itemID) != null){
            if(invItemMapper.updateMinAmount(inventoryID,itemID,newMinAmount)){
                loadNewItem(itemID);
                return checkMinimalAmount() + "\n\nitem's minimal amount was updated";
            }
        }
        else {
            //return "item's minimal amount was not updated";
            throw new Exception ("item id: " + itemID + " does not exist");
        }
        return "item's minimal amount was not updated";
    }

    public String setRequiredAmount(int itemID, int newReqAmount) throws Exception {
        //the itemID exist in business layer
        if (items.containsKey(itemID)){
            getItems().get(itemID).setRequiredAmount(newReqAmount);
            if (invItemMapper.updateReqAmount(inventoryID,itemID,newReqAmount)){
                return "item's required amount was updated";
            }
        }
        else if (invItemMapper.selectItemInvByID(inventoryID,itemID) != null){
            if(invItemMapper.updateReqAmount(inventoryID,itemID,newReqAmount)){
                loadNewItem(itemID);
                return "item's required amount was updated";
            }
        }
        else {
            throw new Exception ("item id: " + itemID + " does not exist");
        }
        return "item's required amount was not updated";
    }

    public String checkAmount(){
        String reports = checkMinimalAmount();
        reports += "\n\n" + checkRequiredAmount();
        return reports;
    }

    public String checkRequiredAmount () {
        loadListOfItems(invItemMapper.selectItemsInvReqAmount(inventoryID));
        Map<invItem,Integer> requiredItems = new HashMap<>();
        for (Map.Entry<Integer,invItem>entry : items.entrySet()){
            int allQuantity = entry.getValue().getStoreQuantity()+entry.getValue().getStockQuantity();
            if (entry.getValue().getRequiredAmount() >= allQuantity && entry.getValue().getMinimalAmount() <= allQuantity){
                requiredItems.put(entry.getValue(),entry.getValue().getRequiredAmount()-allQuantity);
            }
        }
        if (!requiredItems.isEmpty()){
            return makeRequiredReport(requiredItems).toString();
        }
        else {
            return "All items are in the required amount";
        }
    }

    /*
    check if there is item with minimal amount and print message if there is.
     */
    public String checkMinimalAmount () {
        loadListOfItems(invItemMapper.selectItemsInvMinAmount(inventoryID));
        Map<Integer,Integer> missingItems = new HashMap<>();
        for (Map.Entry<Integer,invItem>entry : items.entrySet()){
            int allQuantity = entry.getValue().getStoreQuantity()+entry.getValue().getStockQuantity();
            if (entry.getValue().getMinimalAmount() >= allQuantity){
                missingItems.put(entry.getKey(),entry.getValue().getRequiredAmount()-allQuantity);
            }
        }
        if (!missingItems.isEmpty()){
            return makeMissingReport(missingItems).toString();
        }
        else {
            return "There is no items with minimal amount";
        }
    }

    /*
    adding new item to the inventory.
     */
    public String addItem(int itemID , int stockLine, int stockShelf, int storeLine, int storeShelf, int stockQuantity,
                          int storeQuantity, int minimalAmount, int requiredAmount, float storePrice, Map<LocalDate, List<Integer>> expirationDates, String categoryName, String subCategoryName, String subSubCategoryName) throws Exception {
        ItemDTO itemDTO = itemMapper.getItemById(itemID);
        if(itemDTO ==  null) throw new RuntimeException("Please add Item No."+ itemID+" to Supplier system first");
        //the item exist in the business layer
        if (items.containsKey(itemID)){
            throw new Exception("Item with this ID already exist");
        }
        else if(invItemMapper.selectItemInvByID(inventoryID,itemID) != null){
            loadNewItem(itemID);
            throw new Exception("Item with this ID already exist");
        }
        else {
            invItem newItem = new invItem(inventoryID,itemID, itemDTO.getName(), itemDTO.getManufacturer(), stockLine,stockShelf,storeLine,storeShelf,stockQuantity,storeQuantity,minimalAmount,requiredAmount,storePrice, expirationDates);
            if (!newItem.getExpirationDates().isEmpty()){
                for (DamageAndExpirationProductDTO dto:newItem.getDamageAndExpirationProductDTO()) {
                    damageAndExpirationProductMapper.addExpiredProduct(inventoryID, dto.getProductID(), itemID,dto.getExpirationDate(), dto.isDamage());
                }
            }
            //insertToDB
            if (invItemMapper.insertInvItem(inventoryID,itemID,stockLine,stockShelf,storeLine,storeShelf,stockQuantity,storeQuantity,minimalAmount,requiredAmount,storePrice)){
                Category category;
                SubCategory subCategory;
                SubSubCategory subSubCategory;
                category = loadCategory(categoryName);
                if (category != null) {
                    subCategory = loadSubCategory(subCategoryName, category.getSubCategories());
                    if (subCategory != null){
                        subSubCategory = loadSubSubCategory(subSubCategoryName, subCategory.getSubSubCategories());
                        if(subSubCategory == null){
                            addSubSubCategory(categoryName, subCategoryName, subSubCategoryName);
                        }
                    }
                    else {
                        addSubCategory(categoryName, subCategoryName);
                        addSubSubCategory(categoryName, subCategoryName, subSubCategoryName);
                    }
                }
                else {
                    addCategory(categoryName);
                    addSubCategory(categoryName, subCategoryName);
                    addSubSubCategory(categoryName, subCategoryName, subSubCategoryName);
                }
                category = Categories.get(categoryName);
                subCategory = category.getSubCategories().get(subCategoryName);
                subSubCategory = subCategory.getSubSubCategories().get(subSubCategoryName);
                subSubCategory.addItem(newItem);
                if (invItemMapper.insertItemToCategory(inventoryID,itemID,subSubCategory.getCategoryID())){
                    Sale foundSale = findSaleForNewItem(category, subCategory, subSubCategory);
                    if(foundSale != null){
                        if(saleMapper.insertItemToExistingSale(inventoryID, itemID, foundSale.getSaleID())){
                            newItem.setSale(foundSale.getSaleID(), foundSale);
                        }
                        else{
                            return "sale wasn't added for the new item";
                        }
                    }
                }
                items.put(itemID, newItem);
                return "itemID:" + itemID + ", itemName:" + itemDTO.getName() + ", under Category:" + categoryName + " , SubCategory: " + subCategoryName + " , SubSubCategory: " + subSubCategoryName;
            }
            else {
                return "item not added";
            }
        }
    }

    /*
    return item's detail.
    */
    public String showItemDetails(int itemID) throws Exception {
        if(!items.containsKey(itemID)){
            if (invItemMapper.selectItemInvByID(inventoryID,itemID) == null){
                throw new Exception ("item ID does not exist.");
            }
            else {
                loadNewItem(itemID);
            }
        }
        invItem item = items.get(itemID);
        return item.toString();
    }

    /*
    removes items after selling them.
    */
    public String removeItemsAfterSale(int itemID, List<Integer> productIDs) throws Exception {
        if (!items.containsKey(itemID)){
            if (invItemMapper.selectItemInvByID(inventoryID,itemID) == null){
                throw new Exception("item id is not correct");
            }
            else {
                loadNewItem(itemID);
            }
        }
        if(getItemByID(itemID).getStoreQuantity() < productIDs.size()){
            throw new Exception("there are not enough products of this item");
        }
        else{
            int storeQuantity = getItemByID(itemID).getStoreQuantity();
            int stockQuantity = getItemByID(itemID).getStockQuantity();
            if (invItemMapper.updateQuantityInStoreAndStock(inventoryID,itemID,storeQuantity- productIDs.size(),stockQuantity)){
                for (int productID : productIDs) {
                    if (damageAndExpirationProductMapper.removeProduct(inventoryID,itemID,productID)){
                        getItemByID(itemID).removeFromExpirationDates(productID);
                    }
                }
                getItemByID(itemID).setStoreQuantity(storeQuantity - productIDs.size());
            }
        }
        return checkMinimalAmount()+"\n\ninventory updated after sale";
    }

    /*
    adding items after order arrived.
     */
    public String updateItemsOrderArrived(int itemID, List<Integer> productIDs, LocalDate date) throws Exception {
        if (!items.containsKey(itemID)){
            if (invItemMapper.selectItemInvByID(inventoryID,itemID) == null){
                throw new Exception("item id is not correct");
            }
            else {
                loadNewItem(itemID);
            }
        }
        int storeQuantity = getItemByID(itemID).getStoreQuantity();
        int stockQuantity = getItemByID(itemID).getStockQuantity();
        if (invItemMapper.updateQuantityInStoreAndStock(inventoryID,itemID,storeQuantity,stockQuantity + productIDs.size())){
            for (int productID: productIDs) {
                if (damageAndExpirationProductMapper.addExpiredProduct(inventoryID,productID,itemID,date.toString(),0)){
                    getItemByID(itemID).addToExpirationDates(date,productID);
                }
            }
            getItemByID(itemID).setStockQuantity(stockQuantity + productIDs.size());
        }
        return "inventory updated according to arrived order";
    }

    public String itemsFromStockToStore(int itemID, int quantity) throws Exception {
        if (!items.containsKey(itemID)){
            if (invItemMapper.selectItemInvByID(inventoryID,itemID) == null){
                throw new Exception("item ID is not exist");
            }
            else {
                loadNewItem(itemID);
            }
        }
        if (quantity > getItemByID(itemID).getStockQuantity()){
            throw new Exception("The quantity is bigger then the quantity in the stock.");
        }
        else {
            int storeQuantity = getItemByID(itemID).getStoreQuantity();
            int stockQuantity = getItemByID(itemID).getStockQuantity();
            if (invItemMapper.updateQuantityInStoreAndStock(inventoryID,itemID,storeQuantity+quantity, stockQuantity+quantity)){
                getItemByID(itemID).setStockQuantity(stockQuantity-quantity);
                getItemByID(itemID).setStoreQuantity(storeQuantity+quantity);
                return "item id: " + itemID + ". item name: " + getItemByID(itemID).GetName() + ". quantity in stock: " +
                        getItemByID(itemID).getStockQuantity() + ". quantity in store: " + getItemByID(itemID).getStoreQuantity() + ".";
            }
            else {
                return "Quantity didn't change";
            }
        }
    }

    public String itemsFromStoreToStock(int itemID, int quantity) throws Exception {
        if (!items.containsKey(itemID)){
            if (invItemMapper.selectItemInvByID(inventoryID,itemID) == null){
                throw new Exception("item ID is not exist");
            }
            else {
                loadNewItem(itemID);
            }
        }
        if (quantity > getItemByID(itemID).getStoreQuantity()){
            throw new Exception("The quantity is bigger then the quantity in the store.");
        }
        else {
            int storeQuantity = getItemByID(itemID).getStoreQuantity();
            int stockQuantity = getItemByID(itemID).getStockQuantity();
            if (invItemMapper.updateQuantityInStoreAndStock(inventoryID,itemID,storeQuantity+quantity, stockQuantity+quantity)){
                getItemByID(itemID).setStockQuantity(getItemByID(itemID).getStockQuantity()+quantity);
                getItemByID(itemID).setStoreQuantity(getItemByID(itemID).getStoreQuantity()-quantity);
                return "item id: " + itemID + ". item name: " + getItemByID(itemID).GetName() + ". quantity in stock: " +
                        getItemByID(itemID).getStockQuantity() + ". quantity in store: " + getItemByID(itemID).getStoreQuantity() + ".";
            }
            else {
                return "Quantity didn't change";
            }

        }
    }

//===============================================================================================================
//                    Report Functions
//===============================================================================================================

    /*
    return report of missing items according worker report.
    */
    public Report makeMissingReport (Map<Integer, Integer> missingItems) {
        Map<invItem, Integer> missingItemsMap = new HashMap<>();
        for (Map.Entry<Integer, Integer> entry : missingItems.entrySet()){
            if (!items.containsKey(entry.getKey())){
                loadNewItem(entry.getKey());
            }
            missingItemsMap.put(items.get(entry.getKey()),entry.getValue());
        }
        Report report = new MissingReport(missingItemsMap);
        return report;
    }

    public Report makeDefectivesReport(LocalDate from, LocalDate until) throws Exception {
        if (from.isAfter(until)) {
            throw new Exception("Illegal from and until dates");
        } else {
            loadListOfItems(invItemMapper.selectAllItemsInv(inventoryID));
            loadListOfDamage(damageAndExpirationProductMapper.getDamagedProduct());

            List<DamagedItem> damagedItemsByPeriod = new LinkedList<>();
            for (DamagedItem damagedItem : DamagedItems) {
                if (from.isBefore(damagedItem.getReportDate()) && until.isAfter(damagedItem.getReportDate())) {
                    damagedItemsByPeriod.add(damagedItem);
                }
            }

            Map<LocalDate, Map<Integer, List<Integer>>> expiredProducts = new HashMap<>();
            for (Map.Entry<Integer, invItem> item : items.entrySet()) {
                Map<LocalDate, List<Integer>> expirationDates;
                if (item.getValue().getExpirationDates() != null) {
                    expirationDates = item.getValue().getExpirationDates();
                    for (Map.Entry<LocalDate, List<Integer>> entry : expirationDates.entrySet()) {
                        if (from.isBefore(entry.getKey()) && until.isAfter(entry.getKey())) {
                            Map<Integer, List<Integer>> itemToProductExpiration = new HashMap<>();
                            List<Integer> productIds = entry.getValue() != null ? entry.getValue() : new ArrayList<>();
                            itemToProductExpiration.put(item.getKey(), productIds);
                            expiredProducts.put(entry.getKey(), itemToProductExpiration);
                        }
                    }
                }
            }

            return new DefectivesReport(from, until, getDamagedItems(), expiredProducts);
        }
    }


    public Report makeExistingReport () throws Exception {
        loadListOfItems(invItemMapper.selectAllItemsInv(inventoryID));
        Map<invItem, Integer> invItems = new HashMap<>();
        for (Map.Entry<Integer,invItem> entry : items.entrySet()) {
            invItems.put(entry.getValue(), entry.getValue().getStockQuantity()+entry.getValue().getStoreQuantity());
        }
        if (invItems.isEmpty()){
            throw new Exception("there is no items in inventory.");
        }
        else {
            return new ExistingReport(invItems);
        }
    }

    /*
    return report of existing items by categories.
     */
    public Report makeExistingReportByCategories(List<String> categoryNames) throws Exception {
        loadListOfItems(invItemMapper.selectAllItemsInv(inventoryID));
        Map<invItem, Integer> itemsToReport = new HashMap<>();
        loadAllCategories();
        boolean stop = false;
        boolean found;
        for (int i=0 ; i<categoryNames.size() & !stop; i++) {
            found = false;
            String categoryName = categoryNames.get(i);
            Category category = loadCategory(categoryName);
            if(category != null){
                itemsOfCategory(category, itemsToReport);
                found = true;
            }
            else{
                for (Map.Entry<String,Category> entryCat : Categories.entrySet()){
                    Map<String,SubCategory> subCategories = entryCat.getValue().getSubCategories();
                    SubCategory subCategory = loadSubCategory(categoryName, subCategories);
                    if(subCategory != null){
                        itemsOfSubCategory(subCategory, itemsToReport);
                        found = true;
                    }
                    else{
                        for (Map.Entry<String,SubCategory> entrySub : subCategories.entrySet()){
                            Map<String,SubSubCategory> subSubCategories = entrySub.getValue().getSubSubCategories();
                            SubSubCategory subSubCategory = loadSubSubCategory(categoryName, subSubCategories);
                            if(subSubCategory != null){
                                itemsOfSubSubCategory(subSubCategories.get(categoryName), itemsToReport);
                                found = true;
                            }
                        }
                    }
                }
            }
            if(!found){
                stop = true;
            }
        }
        if (stop) {
            throw new Exception("category name was not found");
        }
        else{
            return new ExistingReport(itemsToReport);
        }
    }

//    public String makePeriodReportMon_Thu(String day){
//        if (day.equals("Monday")){
//            return checkRequiredAmount();
//        }
//        else if (day.equals("Thursday")){
//            return checkRequiredAmount();
//        }
//        return "Today is not Monday or Thursday, period report is not updated.";
//    }

    public Report makeRequiredReport (Map<invItem, Integer> requiredItems) {
        Report report = new RequiredReport(requiredItems);
        Map<Integer, Integer> missingItemsByID = new HashMap<>();
        for (Map.Entry<invItem, Integer> entry : requiredItems.entrySet()){
            missingItemsByID.put(entry.getKey().GetId(),entry.getValue());
        }
        return report;
    }

    public void itemsOfCategory(Category category, Map<invItem, Integer> itemsToReport){
        Map<String,SubCategory> subCategories = category.getSubCategories();
        for (Map.Entry<String,SubCategory> entry : subCategories.entrySet()){
            itemsOfSubCategory(entry.getValue(), itemsToReport);
        }
    }

    public void itemsOfSubCategory(SubCategory subCategory, Map<invItem, Integer> itemsToReport){
        Map<String,SubSubCategory> subSubCategories = subCategory.getSubSubCategories();
        for (Map.Entry<String,SubSubCategory> entry : subSubCategories.entrySet()){
            itemsOfSubSubCategory(entry.getValue(), itemsToReport);
        }
    }

    public void itemsOfSubSubCategory(SubSubCategory subSubCategory, Map<invItem, Integer> itemsToReport){
        List<invItem> items = subSubCategory.getItems();
        for(invItem item: items){
            if(!itemsToReport.containsKey(item)){
                itemsToReport.put(item, item.getStoreQuantity()+item.getStockQuantity());
            }
        }
    }

    /*
    worker report about a damaged item
     */
    public String reportAboutDamagedItem(int itemID, List<Integer> productsIDs) throws Exception {
        if(!items.containsKey(itemID)) {
            if (invItemMapper.selectItemInvByID(inventoryID, itemID) == null) {
                throw new Exception("item ID does not exist.");
            } else {
                loadNewItem(itemID);
            }
        }
        DamagedItem newDamagedItem = new DamagedItem(inventoryID,itemID, productsIDs);
        for (Integer productID: newDamagedItem.getProductID()){
            if(!damageAndExpirationProductMapper.addDamagedProduct(inventoryID,productID,itemID, 1)){
                throw new Exception("there was problem to add into the db");
            }
        }
        DamagedItems.add(newDamagedItem);
        return "report date: " + newDamagedItem.getReportDate() + "\n" + "item ID: " + itemID + "\n" + "products IDs: " + productsIDs + "\n";
    }

    //===============================================================================================================
    //                    Order Functions
    //===============================================================================================================

    public String makeMissingOrder (Map<Integer, Integer> missingItems) throws Exception {
        String output;
        for (Map.Entry<Integer, Integer> entry: missingItems.entrySet()){
            if (!items.containsKey(entry.getKey())){
                loadNewItem(entry.getKey());
            }
            int quantity = items.get(entry.getKey()).getStockQuantity() + items.get(entry.getKey()).getStoreQuantity();
            if (quantity+entry.getValue() < items.get(entry.getKey()).getMinimalAmount()){
                throw new Exception("Quantity ordered :" + entry.getValue() + "\n" + "Quantity in inventory : " + quantity + "\nTotal : " +
                        (entry.getValue()+quantity) + "\nMinimal amount : " + items.get(entry.getKey()).getMinimalAmount()
                        + "\nOrder failed, the total quantity should be bigger then the minimal!");
            }
        }
        List<Order> acceptedOrders = orderHandler.acceptMissingReport(missingItems,address);
        updateInvItemQuantity(acceptedOrders);
        output =  "missing items send to supplier's system\n\n";
        for (Order or : acceptedOrders){
            output += or.toString() + "\n";
        }
        return output;
    }

    /**
     * updates all quantities of items in respect to received quantity from order that arrived to today
     * @param acceptedOrders order due to today
     */
    public void updateInvItemQuantity(List<Order> acceptedOrders)
    {
        Map<Integer,Integer> itemToQuantity=new HashMap<>();
        for (Order order: acceptedOrders)
        {
            for (Integer itemId:order.GetItemToQuantity().keySet())
            {
                Integer OrderItemQuantity= order.GetItemToQuantity().get(itemId);
                if (itemToQuantity.containsKey(itemId))
                {
                    itemToQuantity.replace(itemId,itemToQuantity.get(itemId)+OrderItemQuantity);
                }
                else  { itemToQuantity.put(itemId,OrderItemQuantity); }
            }
        }
        for (Map.Entry<Integer, Integer> entry : itemToQuantity.entrySet()){
            invItem item = items.get(entry.getKey());
            item.setStockQuantity(item.getStockQuantity() + entry.getValue());
            invItemMapper.updateQuantityInStoreAndStock(inventoryID, item.GetId(), item.getStoreQuantity(), item.getStockQuantity());
        }
    }

    public String makeRequiredOrder (Map<Integer, Integer> requiredItems, int supplierID) {
//        Report report = new RequiredReport(requiredItems);
//        Map<Integer, Integer> missingItemsByID = new HashMap<>();
//        for (Map.Entry<invItem, Integer> entry : requiredItems.entrySet()){
//            missingItemsByID.put(entry.getKey().GetId(),entry.getValue());
//        }
        orderHandler.acceptRequiredReport(requiredItems,address, supplierID);
        return "required items send to supplier's system";
    }

    public void receiveOrders()  { updateInvItemQuantity(orderHandler.getOrdersDueToday()); }

    public String getFutureOrders(){
        String futureOrdersString = "";
        Map<Integer,List<Order>> futureOrders = orderHandler.getIdToFutureOrder();
        for (Map.Entry<Integer,List<Order>> orders : futureOrders.entrySet()){
            futureOrdersString += "orders from supplier ID: " + orders.getKey() + "\n\n";
            for (Order order: orders.getValue()){
                futureOrdersString += order.toString() + "\n\n";
            }
        }
        if (futureOrders.isEmpty()){
            return "no future orders";
        }
        else {
            return futureOrdersString;
        }
    }

//===============================================================================================================
//                    Category Functions
//===============================================================================================================

    public String addCategory(String categoryName) throws Exception {
        if(categoryMapper.insertCategory(inventoryID, categoryIDCounter, categoryName)){
            Category newCategory = new Category(inventoryID,categoryName, categoryIDCounter);
            Categories.put(categoryName, newCategory);
            increaseCategoryIDCounter();
            return "Category was added";
        }
        throw new Exception("failed to add category to DB");
    }

    public String addSubCategory(String categoryName, String subCategoryName) throws Exception {
        Category category = loadCategory(categoryName);
        if(category == null){
            throw new Exception("no such category name");
        }
        else{
            if(categoryMapper.insertSubCategory(inventoryID, categoryIDCounter, subCategoryName, Categories.get(categoryName).getCategoryID())){
                SubCategory newSubCategory = new SubCategory(subCategoryName, categoryIDCounter);
                category.addSubCategory(subCategoryName, newSubCategory);
                increaseCategoryIDCounter();
                return "Sub category was added";
            }
            else{
                throw new Exception("failed to add subCategory to DB");
            }
        }
    }

    public String addSubSubCategory(String categoryName, String subCategoryName, String subSubCategoryName) throws Exception {
        Category category = loadCategory(categoryName);
        if(category == null){
            throw new Exception("no such category name");
        }
        else{
            SubCategory subCategory = loadSubCategory(subCategoryName, category.getSubCategories());
            if(subCategory == null){
                throw new Exception("no such subCategory name");
            }
            else if(categoryMapper.insertSubSubCategory(inventoryID, categoryIDCounter, subSubCategoryName,
                    subCategory.getCategoryID())){
                category.addSubCategory(subCategoryName, subCategory);
                SubSubCategory newSubSubCategory = new SubSubCategory(subSubCategoryName, categoryIDCounter);
                subCategory.addSubSubCategory(subSubCategoryName, newSubSubCategory);
                increaseCategoryIDCounter();
                return "Sub sub category was added";
            }
            else{
                throw new Exception("failed to add subSubCategory to DB");
            }
        }
    }

//===============================================================================================================
//                    Sale Functions
//===============================================================================================================

    public String addSaleForItem(int itemID, float discount, LocalDate startDate, LocalDate endDate) throws Exception{
        if (startDate.isAfter(endDate)){
            throw new Exception("start date is after end date");
        }
        if (discount<0 | discount>100){
            throw new Exception("discount illegal");
        }
        Sale newSale = new Sale(inventoryID, saleIDCounter,"Item",discount,startDate,endDate);
        if(!items.containsKey(itemID)){
            if (invItemMapper.selectItemInvByID(inventoryID,itemID) == null){
                throw new Exception ("item ID does not exist.");
            }
            else {
                loadNewItem(itemID);
            }
        }
        if(saleMapper.insertSaleForItem(inventoryID, saleIDCounter, discount, "Item", startDate, endDate, itemID)){
            getItemByID(itemID).setSale(saleIDCounter,newSale);
            Sales.put(saleIDCounter, newSale);
            increaseSaleIDCounter();
            return "sale id is : " + newSale.getSaleID();
        }
        else{
            throw new Exception("failed to add Sale to DB");
        }
    }

    public String addSaleForCategory(String name, float discount, LocalDate startDate, LocalDate endDate) throws Exception {
        if (startDate.isAfter(endDate)) {
            throw new Exception("start date is after end date");
        }
        if (discount < 0 | discount > 100) {
            throw new Exception("discount illegal");
        }
        Sale newSale = new Sale(inventoryID, saleIDCounter, "Category", discount, startDate, endDate);
        loadAllCategories();
        int categoryIDfound = -999;
        Category category = loadCategory(name);
        if(category != null){
            category.setSale(saleIDCounter, newSale);
            categoryIDfound = category.getCategoryID();
        }
        else{
            for (Map.Entry<String,Category> entryCat : Categories.entrySet()){
                Map<String,SubCategory> subCategories = entryCat.getValue().getSubCategories();
                SubCategory subCategory = loadSubCategory(name, subCategories);
                if(subCategory != null){
                    subCategory.setSale(saleIDCounter, newSale);
                    categoryIDfound = subCategory.getCategoryID();
                }
                else{
                    for (Map.Entry<String,SubCategory> entrySub : subCategories.entrySet()){
                        Map<String,SubSubCategory> subSubCategories = entrySub.getValue().getSubSubCategories();
                        SubSubCategory subSubCategory = loadSubSubCategory(name, subSubCategories);
                        if(subSubCategory != null){
                            subSubCategory.setSale(saleIDCounter, newSale);
                            categoryIDfound = subSubCategory.getCategoryID();
                        }
                    }
                }
            }
        }
        if (categoryIDfound == -999) {
            throw new Exception("category name was not found");
        }
        else if(saleMapper.insertSaleForCategory(inventoryID, saleIDCounter, discount, "Category", startDate, endDate, categoryIDfound)){
            Sales.put(saleIDCounter, newSale);
            increaseSaleIDCounter();
            return "sale id is : " + newSale.getSaleID();
        }
        else{
            throw new Exception("failed to add Sale to DB");
        }
    }

    public String removeSaleFromItem(int itemID, int saleID) throws Exception {
        if(!items.containsKey(itemID)){
            if (invItemMapper.selectItemInvByID(inventoryID,itemID) == null){
                throw new Exception ("item ID does not exist.");
            }
            else {
                loadNewItem(itemID);
            }
        }
        if(saleMapper.removeSaleFromItem(inventoryID, saleID)){
            getItemByID(itemID).removeSale(saleID);
            Sales.remove(saleID);
            return "Sale " + saleID + " removed";
        }
        else{
            throw new Exception("failed to delete Sale from DB");
        }
    }

    public String removeSaleFromCategory(int saleID, String categoryName) throws Exception {
        boolean found = false;
        Sale sale = loadSale(saleID);
        if(sale == null){
            throw new Exception("sale id was not found");
        }
        else{
            loadAllCategories();
            Category category = loadCategory(categoryName);
            if(category != null){
                Categories.get(categoryName).removeSale(saleID);
                found = true;
            }
            else {
                for (Map.Entry<String, Category> entryCat : Categories.entrySet()) {
                    Map<String, SubCategory> subCategories = entryCat.getValue().getSubCategories();
                    SubCategory subCategory = loadSubCategory(categoryName, subCategories);
                    if (subCategory != null) {
                        subCategory.removeSale(saleID);
                        found = true;
                    }
                    else {
                        for (Map.Entry<String, SubCategory> entrySub : subCategories.entrySet()) {
                            Map<String, SubSubCategory> subSubCategories = entrySub.getValue().getSubSubCategories();
                            SubSubCategory subSubCategory = loadSubSubCategory(categoryName, subSubCategories);
                            if (subSubCategory != null) {
                                subSubCategory.removeSale(saleID);
                                found = true;
                            }
                        }
                    }
                }
            }
            if (!found) {
                throw new Exception("category name was not found");
            }
            else if(saleMapper.removeSaleFromCategory(inventoryID, saleID)){
                Sales.remove(saleID);
                return "Sale " + saleID + " removed";
            }
            else{
                throw new Exception("failed to delete Sale from DB");
            }
        }
    }

    private Sale loadSale(int saleID){
        Sale sale = null;
        if(Sales.containsKey(saleID)){
            sale = Sales.get(saleID);
        }
        else{
            SaleDTO dto = saleMapper.getSaleByID(inventoryID, saleID);
            if(dto != null){
                sale = new Sale(dto.getInventoryID(), dto.getSaleID(), dto.getSaleType(), dto.getReduction(),
                        getDate(dto.getStartDate()), getDate(dto.getEndDate()));
            }
        }
        return sale;
    }

    private static LocalDate getDate(String dateString){
        String[] splitedString = dateString.split("-");
        int year = parseInt(splitedString[0]);
        int month = parseInt(splitedString[1]);
        int day = parseInt(splitedString[2].split("T")[0]);
        return LocalDate.of(year,month,day);
    }

    private void loadAllCategories(){
        List<CategoryDTO> categoryDTOs = categoryMapper.getAllCategories(inventoryID);
        for (CategoryDTO categoryDTO : categoryDTOs) {
            Category category = new Category(categoryDTO.getInventoryID(), categoryDTO.getCategoryName(), categoryDTO.getCategoryID());
            Categories.put(categoryDTO.getCategoryName(), category);
            List<SubCategoryDTO> subCategoryDTOs = categoryMapper.getSubCategoriesOfCategory(inventoryID, category.getCategoryID());
            for (SubCategoryDTO subCategoryDTO : subCategoryDTOs) {
                SubCategory subCategory = new SubCategory(subCategoryDTO.getSubCategoryName(), subCategoryDTO.getSubCategoryID());
                category.addSubCategory(subCategoryDTO.getSubCategoryName(), subCategory);
                List<SubSubCategoryDTO> subSubCategoryDTOs = categoryMapper.getSubSubCategoriesOfSubCategory(inventoryID, subCategory.getCategoryID());
                for (SubSubCategoryDTO subSubCategoryDTO : subSubCategoryDTOs) {
                    SubSubCategory subSubCategory = new SubSubCategory(subSubCategoryDTO.getSubSubCategoryName(), subSubCategoryDTO.getSubSubCategoryID());
                    subCategory.addSubSubCategory(subSubCategoryDTO.getSubSubCategoryName(), subSubCategory);
                    List<InvItemDTO> InvItemDTOs = categoryMapper.getItemsOfSubSubCategory(inventoryID, subSubCategory.getCategoryID());
                    for (InvItemDTO invItemDTO : InvItemDTOs) {
                        loadNewItem(invItemDTO.getItemID());
                        subSubCategory.addItem(items.get(invItemDTO.getItemID()));
                    }
                }
            }
        }
    }

//    private void loadSubCategoriesOfCategory(Category category){
//        List<SubCategoryDTO> subCategoryDTOs = categoryMapper.getSubCategoriesOfCategory(inventoryID, category.getCategoryID());
//        for (SubCategoryDTO dto : subCategoryDTOs) {
//            SubCategory subCategory = new SubCategory(dto.getSubCategoryName(), dto.getSubCategoryID());
//            category.addSubCategory(dto.getSubCategoryName(), subCategory);
//        }
//    }
//
//    private void loadSubSubCategoriesOfSubCategory(SubCategory subCategory){
//        List<SubSubCategoryDTO> subSubCategoryDTOs = categoryMapper.getSubSubCategoriesOfSubCategory(inventoryID, subCategory.getCategoryID());
//        for (SubSubCategoryDTO dto : subSubCategoryDTOs) {
//            SubSubCategory subSubCategory = new SubSubCategory(dto.getSubSubCategoryName(), dto.getSubSubCategoryID());
//            subCategory.addSubSubCategory(dto.getSubSubCategoryName(), subSubCategory);
//        }
//    }
//
//    private void loadItemsOfSubSubCategory(SubSubCategory subSubCategory){
//        List<InvItemDTO> InvItemDTOs = categoryMapper.getItemsOfSubSubCategory(inventoryID, subSubCategory.getCategoryID());
//        for (InvItemDTO dto : InvItemDTOs) {
//            loadNewItem(dto.getItemID());
//            subSubCategory.addItem(items.get(dto.getItemID()));
//        }
//    }

    private Category loadCategory(String categoryName){
        Category category = null;
        if(Categories.containsKey(categoryName)){
            category = Categories.get(categoryName);
        }
        else{
            CategoryDTO dto = categoryMapper.getCategoryByName(inventoryID, categoryName);
            if(dto != null){
                category = new Category(dto.getInventoryID(), dto.getCategoryName(), dto.getCategoryID());
                Categories.put(categoryName, category);
            }
        }
        return category;
    }

    private SubCategory loadSubCategory(String subCategoryName, Map<String,SubCategory> subCategories){
        SubCategory subCategory = null;
        if(subCategories.containsKey(subCategoryName)){
            subCategory = subCategories.get(subCategoryName);
        }
        else{
            SubCategoryDTO dto = categoryMapper.getSubCategoryByName(inventoryID, subCategoryName);
            if(dto != null){
                subCategory = new SubCategory(dto.getSubCategoryName(), dto.getSubCategoryID());
                subCategories.put(subCategoryName, subCategory);
            }
        }
        return subCategory;
    }

    private SubSubCategory loadSubSubCategory(String subSubCategoryName, Map<String,SubSubCategory> subSubCategories){
        SubSubCategory subSubCategory = null;
        if(subSubCategories.containsKey(subSubCategoryName)){
            subSubCategory = subSubCategories.get(subSubCategoryName);
        }
        else{
            SubSubCategoryDTO dto = categoryMapper.getSubSubCategoryByName(inventoryID, subSubCategoryName);
            if(dto != null){
                subSubCategory = new SubSubCategory(dto.getSubSubCategoryName(), dto.getSubSubCategoryID());
                subSubCategories.put(subSubCategoryName, subSubCategory);
            }
        }
        return subSubCategory;
    }

    /*
    find sale for the new item added (form its new category/subCategory/subSubCategory).
    */
    private Sale findSaleForNewItem(Category category, SubCategory subCategory, SubSubCategory subSubCategory){
        if(category.getSale() != null){
            return category.getSale();
        }
        if(subCategory.getSale() != null){
            return subCategory.getSale();
        }
        if(subSubCategory.getSale() != null){
            return subSubCategory.getSale();
        }
        return null;
    }

    public Map<String, Category> getCategories() {
        return Categories;
    }

    public Map<Integer, invItem> getItems() {
        return items;
    }

    public void setItems(Map<Integer, invItem> items) {
        this.items = items;
    }

    public List<DamagedItem> getDamagedItems() {
        return DamagedItems;
    }

    public invItem getItemByID(int ID){
        if(!items.containsKey(ID)){
            loadNewItem(ID);
        }
        return items.get(ID);
    }

    public String getAddress() {
        return address;
    }

    public Category getCategoryByName(String CategoryName){
        return Categories.get(CategoryName);
    }

    public int getInventoryID() {
        return inventoryID;
    }
    public OrderHandler getOrderHandler() {return orderHandler;}

    public void loadNewItem(int itemID){
        ItemDTO itemDTO = itemMapper.getItemById(itemID);
        InvItemDTO invItemDTO = (InvItemDTO)invItemMapper.selectItemInvByID(inventoryID,itemID);
        List<SaleDTO> saleDTO = saleMapper.selectAllSalesByID(inventoryID,itemID);
        List<DamageAndExpirationProductDTO> damageAndExpirationProductDTO = damageAndExpirationProductMapper.getProductWithExpirationDateByID(inventoryID,itemID);
        invItem invItem = new invItem(itemDTO,invItemDTO,saleDTO, damageAndExpirationProductDTO);
        items.put(itemID,invItem);
    }

    public void loadListOfItems(List<InvItemDTO> dtos){
        for (InvItemDTO dto : dtos){
            if (!items.containsKey(dto.getItemID())){
                ItemDTO itemDTO = itemMapper.getItemById(dto.getItemID());
                InvItemDTO invItemDTO = (InvItemDTO)invItemMapper.selectItemInvByID(inventoryID,dto.getItemID());
                List<SaleDTO> saleDTO = saleMapper.selectAllSalesByID(inventoryID,dto.getItemID());
                List<DamageAndExpirationProductDTO> damageAndExpirationProductDTO = damageAndExpirationProductMapper.getProductWithExpirationDateByID(inventoryID,dto.getItemID());
                invItem invItem = new invItem(itemDTO,invItemDTO,saleDTO, damageAndExpirationProductDTO);
                items.put(dto.getItemID(),invItem);
            }
        }
    }

    public void loadListOfDamage(List<DamageAndExpirationProductDTO> dtos) {
        for (DamageAndExpirationProductDTO dto : dtos) {
            boolean added = false;
            for (DamagedItem damagedItem : DamagedItems){
                if (damagedItem.getItemID() == dto.getItemID()){
                    if (dto.isDamage() == 1){
                        damagedItem.addProductID(dto.getProductID());
                        added = true;
                    }
                }
            }
            if (!added){
                List products = new ArrayList();
                products.add(dto.getProductID());
                DamagedItem damagedItem = new DamagedItem(inventoryID,dto.getItemID(),products);
                DamagedItems.add(damagedItem);
            }
        }
    }
}
