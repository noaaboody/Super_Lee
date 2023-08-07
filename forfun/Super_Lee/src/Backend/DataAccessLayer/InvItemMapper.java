package Backend.DataAccessLayer;

import Backend.DataAccessLayer.DTOs.DTO;
import Backend.DataAccessLayer.DTOs.InvItemDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

public class InvItemMapper extends AbstractMapper {

    private final String InvItem_TableName = "InvItem";
    private final String InventoryID_ColumnName = "inventoryID";
    private final String ItemID_ColumnName = "itemID";
    private final String StockLine_ColumnName = "stockLine";
    private final String StockShelf_ColumnName = "stockShelf";
    private final String StoreLine_ColumnName = "storeLine";
    private final String StoreShelf_ColumnName = "StoreShelf";
    private final String StockQuantity_ColumnName = "stockQuantity";
    private final String StoreQuantity_ColumnName = "storeQuantity";
    private final String MinimalAmount_ColumnName = "minimalAmount";
    private final String RequiredAmount_ColumnName = "requiredAmount";
    private final String StorePrice_ColumnName = "storePrice";

    private final String ItemToSubSubCategory_TableName = "ItemToSubSubCategory";
    private final String SubSubCategoryID_ColumnName = "subSubCategoryID";

    public boolean insertItemToCategory(int inventoryID, int itemID, int subSubCategoryID) {
        String command =  MessageFormat.format("INSERT INTO " + ItemToSubSubCategory_TableName + " ({0},{1},{2}) VALUES(?,?,?)"
                ,InventoryID_ColumnName, ItemID_ColumnName, SubSubCategoryID_ColumnName);
        try {
            Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(command);
            pstmt.setInt(1, inventoryID);
            pstmt.setInt(2, itemID);
            pstmt.setInt(3, subSubCategoryID);
            pstmt.executeUpdate();
            return true;
        } catch (Exception ex) {
            System.out.println(ex);
            return false;
        }
    }

    public boolean insertInvItem(int inventoryID, int itemID, int stockLine, int stockShelf, int storeLine, int StoreShelf,
                                 int stockQuantity, int storeQuantity, int minimalAmount, int requiredAmount, float storePrice) {
        String command =  MessageFormat.format("INSERT INTO " + InvItem_TableName +  " ({0},{1},{2},{3},{4},{5},{6},{7},{8},{9},{10}) VALUES(?,?,?,?,?,?,?,?,?,?,?)"
                ,InventoryID_ColumnName, ItemID_ColumnName, StockLine_ColumnName,StockShelf_ColumnName,StoreLine_ColumnName,StoreShelf_ColumnName,StockQuantity_ColumnName,StoreQuantity_ColumnName,
                MinimalAmount_ColumnName,RequiredAmount_ColumnName,StorePrice_ColumnName);
        try {
            Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(command);
            pstmt.setInt(1, inventoryID);
            pstmt.setInt(2, itemID);
            pstmt.setInt(3, stockLine);
            pstmt.setInt(4, stockShelf);
            pstmt.setInt(5, storeLine);
            pstmt.setInt(6, StoreShelf);
            pstmt.setInt(7, stockQuantity);
            pstmt.setInt(8, storeQuantity);
            pstmt.setInt(9, minimalAmount);
            pstmt.setInt(10, requiredAmount);
            pstmt.setFloat(11, storePrice);
            pstmt.executeUpdate();
            return true;
        } catch (Exception ex) {
            System.out.println(ex);
            return false;
        }
    }

    public boolean updateMinAmount(int inventoryID, int itemID, int minimalAmount){
        String command = "UPDATE " + InvItem_TableName +  " set "+ MinimalAmount_ColumnName +"="+ minimalAmount + " where "
                +InventoryID_ColumnName+"="+inventoryID +" and " + ItemID_ColumnName+"="+itemID;
        try {
            Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(command);
            pstmt.executeUpdate();
            return true;
        } catch (Exception ex) {
            System.out.println(ex);
            return false;
        }
    }
    public boolean updateReqAmount(int inventoryID, int itemID, int requiredAmount){
        String command = "UPDATE " + InvItem_TableName +  " set "+ RequiredAmount_ColumnName +"="+ requiredAmount + " where "
                +InventoryID_ColumnName+"="+inventoryID +" and " + ItemID_ColumnName+"="+itemID;
        try {
            Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(command);
            pstmt.executeUpdate();
            return true;
        } catch (Exception ex) {
            System.out.println(ex);
            return false;
        }
    }
    public boolean updateQuantityInStoreAndStock(int inventoryID, int itemID, int storeQuantity,int stockQuantity){
        String command = "UPDATE " + InvItem_TableName +  " set "+ StoreQuantity_ColumnName +"="+ storeQuantity + " , " + StockQuantity_ColumnName +"="+ stockQuantity + " where "
                +InventoryID_ColumnName+"="+inventoryID +" and " + ItemID_ColumnName+"="+itemID;
        try{
            Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(command);
            pstmt.executeUpdate();
            return true;
        } catch (Exception ex) {
            System.out.println(ex);
            return false;
        }
    }
    public List<InvItemDTO> selectAllItemsInv(int inventoryID){
        String command = "SELECT * from " + InvItem_TableName + " where " + InventoryID_ColumnName+"="+inventoryID;
        List<InvItemDTO> result = new ArrayList<>();
        try {
            Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(command);
            ResultSet res = pstmt.executeQuery();
            while (res.next()) {
                InvItemDTO invItemDTO = new InvItemDTO(res.getInt(1), res.getInt(2), res.getInt(3), res.getInt(4),
                        res.getInt(5), res.getInt(6), res.getInt(7), res.getInt(8), res.getInt(9), res.getInt(10), res.getFloat(11));
                result.add(invItemDTO);
            }
            res.close();
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return result;
    }
    public DTO selectItemInvByID(int inventoryID, int itemID){
        String command = "SELECT * from " + InvItem_TableName + " where " + InventoryID_ColumnName+"="+inventoryID + " and " + ItemID_ColumnName + "=" + itemID;
        DTO result = null;
        try {
            Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(command);
            ResultSet res = pstmt.executeQuery();
            while (res.next()) {
                result = new InvItemDTO(res.getInt(1), res.getInt(2), res.getInt(3), res.getInt(4),
                        res.getInt(5), res.getInt(6), res.getInt(7), res.getInt(8), res.getInt(9), res.getInt(10), res.getFloat(11));
            }
            //res.close();
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return result;
    }
    public List<InvItemDTO> selectItemsInvMinAmount(int inventoryID){
        String command = "SELECT * from " + InvItem_TableName + " where " + InventoryID_ColumnName+"="+inventoryID + " and " + StockQuantity_ColumnName + "+" + StoreQuantity_ColumnName
                + " < " + MinimalAmount_ColumnName;
        List<InvItemDTO> result = new ArrayList<>();
        try {
            Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(command);
            ResultSet res = pstmt.executeQuery();
            while (res.next()) {
                InvItemDTO invItemDTO = new InvItemDTO(res.getInt(1), res.getInt(2), res.getInt(3), res.getInt(4),
                        res.getInt(5), res.getInt(6), res.getInt(7), res.getInt(8), res.getInt(9), res.getInt(10), res.getFloat(11));
                result.add(invItemDTO);
            }
            res.close();
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return result;
    }
    public List<InvItemDTO> selectItemsInvReqAmount(int inventoryID){
        String command = "SELECT * from " + InvItem_TableName + " where " + InventoryID_ColumnName+"="+inventoryID + " and " + StockQuantity_ColumnName + "+" + StoreQuantity_ColumnName
                + " >= " + MinimalAmount_ColumnName + " and " + StockQuantity_ColumnName + "+" + StoreQuantity_ColumnName + " < " + RequiredAmount_ColumnName;
        List<InvItemDTO> result = new ArrayList<>();
        try {
            Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(command);
            ResultSet res = pstmt.executeQuery();
            while (res.next()) {
                InvItemDTO invItemDTO = new InvItemDTO(res.getInt(1), res.getInt(2), res.getInt(3), res.getInt(4),
                            res.getInt(5), res.getInt(6), res.getInt(7), res.getInt(8), res.getInt(9), res.getInt(10), res.getFloat(11));
                    result.add(invItemDTO);
                }
                res.close();
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return result;
    }

    public boolean removeInventoryInvItem(int inventoryID){
        String sql = "DELETE FROM " + InvItem_TableName + " WHERE " + InventoryID_ColumnName + "=" + inventoryID;
        try {
            Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.executeUpdate();
            return true;
        }
        catch (Exception ex){
            System.out.println(ex);
            return false;
        }
    }

    public boolean removeInventoryItemToSubSub(int inventoryID){
        String sql = "DELETE FROM " + ItemToSubSubCategory_TableName + " WHERE " + InventoryID_ColumnName + "=" + inventoryID;
        try {
            Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.executeUpdate();
            return true;
        }
        catch (Exception ex){
            System.out.println(ex);
            return false;
        }
    }
    public boolean removeInventory(int inventory){
        return removeInventoryItemToSubSub(inventory) && removeInventoryInvItem(inventory);
    }
}
