package Backend.serviceLayer;

import Backend.DataAccessLayer.*;
import Backend.DataAccessLayer.DTOs.InventoryDTO;
import Backend.businessLayer.Inventory.InventoryController;
import Backend.businessLayer.OrderHandler;

import java.util.HashMap;
import java.util.Map;

public class FactoryService {

    public Map<Integer, InventoryService> inventories;
    private int inventoryIDCounter;
    private InventoryMapper inventoryMapper;
    private DamageAndExpirationProductMapper damageAndExpirationProductMapper;
    private CategoryMapper categoryMapper;
    private SaleMapper saleMapper;
    private InvItemMapper invItemMapper;
    private ItemMapper itemMapper;
    private OrderHandler orderHandler;
    private SupplierMapper supplierMapper;

    public FactoryService(){
        inventories = new HashMap<>();
        inventoryMapper = new InventoryMapper();
        damageAndExpirationProductMapper = new DamageAndExpirationProductMapper();
        categoryMapper = new CategoryMapper();
        invItemMapper = new InvItemMapper();
        itemMapper = new ItemMapper();
        saleMapper = new SaleMapper();
        orderHandler = new OrderHandler();
        supplierMapper = new SupplierMapper();
        inventoryIDCounter = inventoryMapper.getLastInvID() + 1;
    }

    public InventoryService makeNewInv(String address){
        InventoryController newInventory = new InventoryController(inventoryIDCounter, address, inventoryMapper, damageAndExpirationProductMapper,
                categoryMapper, saleMapper, invItemMapper, itemMapper,orderHandler);
        inventoryIDCounter++;
        InventoryService inventoryService = new InventoryService(newInventory);
        inventories.put(newInventory.getInventoryID(), inventoryService);
        return inventoryService;
    }

    public InventoryService getExistingInv(int inventoryID){
        if(inventories.containsKey(inventoryID)) {
            return inventories.get(inventoryID);
        }
        InventoryDTO dto = inventoryMapper.selectByID(inventoryID);
        if(dto != null){
            InventoryController inventory = new InventoryController(dto.getID(), dto.getAddress(), dto.getCategoryIDCounter(), dto.getSaleIDCounter(),
                    inventoryMapper, damageAndExpirationProductMapper, categoryMapper, saleMapper, invItemMapper, itemMapper, orderHandler);
            return new InventoryService(inventory);
        }
        return null;
    }

    public void deleteInventory(int inventoryID){
        if (saleMapper.removeInventory(inventoryID) && damageAndExpirationProductMapper.removeInventory(inventoryID) && categoryMapper.removeInventory(inventoryID)
                && invItemMapper.removeInventory(inventoryID) && invItemMapper.removeInventory(inventoryID)){
            System.out.println("inventory details removed");
        }
        else {
            System.out.println("inventory details have not removed");
        }


    }
    public OrderHandler getOrderHandler()  {return orderHandler;}
}
