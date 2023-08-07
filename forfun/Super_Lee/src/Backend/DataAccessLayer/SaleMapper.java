package Backend.DataAccessLayer;

import Backend.DataAccessLayer.DTOs.SaleDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.MessageFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SaleMapper extends AbstractMapper{

    private final String Sale_TableName = "Sale";
    private final String InventoryID_ColumnName = "inventoryID";
    private final String SaleID_ColumnName = "saleID";
    private final String reduction_ColumnName = "reduction";
    private final String saleType_ColumnName = "saleType";
    private final String startDate_ColumnName = "startDate";
    private final String endDate_ColumnName = "endDate";
    private final String CategoryToSale_TableName = "CategoryToSale";
    private final String CategoryID_ColumnName = "categoryID";
    private final String ItemToSale_TableName = "ItemToSale";
    private final String ItemID_ColumnName = "itemID";

    public boolean insertItemToExistingSale(int inventoryID, int itemID, int saleID){
        String command =  MessageFormat.format("INSERT INTO " + CategoryToSale_TableName +  " ({0} ,{1}, {2}) VALUES(?,?,?)",
                InventoryID_ColumnName,ItemID_ColumnName, SaleID_ColumnName);
        try {
            Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(command);
            pstmt.setInt(1, inventoryID);
            pstmt.setInt(2, itemID);
            pstmt.setInt(2, saleID);
            pstmt.executeUpdate();
            return true;
        } catch (Exception ex) {
            System.out.println(ex);
            return false;
        }
    }

    public boolean insertSaleForCategory(int inventoryID, int saleID, float reduction, String saleType, LocalDate startDate, LocalDate endDate, int categoryID) {
        if(insertToSaleTable(inventoryID, saleID, reduction, saleType, startDate, endDate)){
            String command =  MessageFormat.format("INSERT INTO " + CategoryToSale_TableName +  " ({0} ,{1}, {2}) VALUES(?,?,?)",
                    InventoryID_ColumnName,CategoryID_ColumnName, SaleID_ColumnName);
            try {
                Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(command);
                pstmt.setInt(1, inventoryID);
                pstmt.setInt(2, categoryID);
                pstmt.setInt(3, saleID);
                pstmt.executeUpdate();
                return true;
            } catch (Exception ex) {
                System.out.println(ex);
                return false;
            }
        }
        else{
            return false;
        }
    }

    public boolean insertSaleForItem(int inventoryID, int saleID, float reduction, String saleType, LocalDate startDate, LocalDate endDate, int itemID) {
        if(insertToSaleTable(inventoryID, saleID, reduction, saleType, startDate, endDate)){
            String command =  MessageFormat.format("INSERT INTO " + ItemToSale_TableName +  " ({0} ,{1}, {2}) VALUES(?,?,?)"
                    ,InventoryID_ColumnName, ItemID_ColumnName, SaleID_ColumnName);
            try {
                Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(command);
                pstmt.setInt(1, inventoryID);
                pstmt.setInt(2, itemID);
                pstmt.setInt(3, saleID);
                pstmt.executeUpdate();
                return true;
            } catch (Exception ex) {
                System.out.println(ex);
                return false;
            }
        }
        else{
            return false;
        }
    }

    public boolean insertToSaleTable(int inventoryID, int saleID, float reduction, String saleType, LocalDate startDate, LocalDate endDate){
        String command =  MessageFormat.format("INSERT INTO " + Sale_TableName +  " ({0} ,{1}, {2}, {3}, {4}, {5}) VALUES(?,?,?,?,?,?)"
                ,InventoryID_ColumnName, SaleID_ColumnName, reduction_ColumnName, saleType_ColumnName, startDate_ColumnName, endDate_ColumnName);
        try {
            Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(command);
            pstmt.setInt(1, inventoryID);
            pstmt.setInt(2, saleID);
            pstmt.setFloat(3, reduction);
            pstmt.setString(4, saleType);
            pstmt.setString(5, startDate.toString());
            pstmt.setString(6, endDate.toString());
            pstmt.executeUpdate();
            return true;
        } catch (Exception ex) {
            System.out.println(ex);
            return false;
        }
    }

    public boolean removeSaleFromItem(int inventoryID, int saleID) {
        if(removeFromSaleTable(inventoryID, saleID)){
            String command = "DELETE from " +  ItemToSale_TableName + " where " + InventoryID_ColumnName+"="+inventoryID + " and " + SaleID_ColumnName+ "=" + saleID;
            try {
                Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(command);
                pstmt.execute();
                return true;
            } catch (Exception ex) {
                System.out.println(ex);
                return false;
            }
        }
        else{
            return false;
        }
    }

    public boolean removeSaleFromCategory(int inventoryID, int saleID) {
        if(removeFromSaleTable(inventoryID, saleID)){
            String command = "DELETE from " +  CategoryToSale_TableName + " where " + InventoryID_ColumnName+"="+inventoryID + " and " + SaleID_ColumnName+ "=" + saleID;
            try {
                Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(command);
                pstmt.execute();
                return true;
            } catch (Exception ex) {
                System.out.println(ex);
                return false;
            }
        }
        else{
            return false;
        }
    }

    public boolean removeFromSaleTable(int inventoryID, int saleID){
        String command = "DELETE from " +  Sale_TableName + " where " + InventoryID_ColumnName+"="+inventoryID + " and " + SaleID_ColumnName+ "=" + saleID;
        try {
            Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(command);
            pstmt.execute();
            return true;
        } catch (Exception ex) {
            System.out.println(ex);
            return false;
        }
    }

    public List<SaleDTO> selectAllSalesByID(int inventoryID, int itemID){
        String command = "SELECT * from " +  Sale_TableName + " where " + SaleID_ColumnName + " IN (select " + SaleID_ColumnName + " from " +  ItemToSale_TableName + " where " + InventoryID_ColumnName+"="+inventoryID + " and " + ItemID_ColumnName+ "=" + itemID +")";
        List<SaleDTO> result = new ArrayList<>();
        try {
            Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(command);
            ResultSet res = pstmt.executeQuery();
            while (res.next()) {
                SaleDTO saleDTO = new SaleDTO(res.getInt(1), res.getInt(2), res.getFloat(3), res.getString(4),
                        res.getString(5), res.getString(6));
                result.add(saleDTO);
            }
            res.close();
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return result;
    }

    public SaleDTO getSaleByID(int inventoryID, int saleID){
        String command = "SELECT * from " +  Sale_TableName + " where " + InventoryID_ColumnName+"="+inventoryID + " and " + SaleID_ColumnName+ "=" + saleID;
        SaleDTO saleDTO = null;
        try {
            Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(command);
            ResultSet res = pstmt.executeQuery();
            while (res.next()) {
                saleDTO = new SaleDTO(res.getInt(1), res.getInt(2), res.getFloat(3), res.getString(4),
                        res.getString(5), res.getString(6));
            }
            res.close();
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return saleDTO;
    }

    public boolean removeInventorySale(int inventoryID){
        String sql = "DELETE FROM " + Sale_TableName + " WHERE " + InventoryID_ColumnName + "=" + inventoryID;
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

    public boolean removeInventoryCategoryToSale(int inventoryID){
        String sql = "DELETE FROM " + CategoryToSale_TableName + " WHERE " + InventoryID_ColumnName + "=" + inventoryID;
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

    public boolean removeInventoryItemToSale(int inventoryID){
        String sql = "DELETE FROM " + ItemToSale_TableName + " WHERE " + InventoryID_ColumnName + "=" + inventoryID;
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
        return removeInventoryCategoryToSale(inventory) && removeInventoryItemToSale(inventory) && removeInventorySale(inventory);
    }
}
