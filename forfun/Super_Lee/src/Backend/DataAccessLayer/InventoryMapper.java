package Backend.DataAccessLayer;
import Backend.DataAccessLayer.DTOs.DTO;
import Backend.DataAccessLayer.DTOs.InventoryDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

public class InventoryMapper extends AbstractMapper {
    private final String Inventory_TableName = "Inventory";
    private final String InventoryID_ColumnName = "ID";
    private final String InventoryAddress_ColumnName = "address";
    private final String CategoryIDCounter_ColumnName = "categoryIDCounter";
    private final String SaleIDCounter_ColumnName = "saleIDCounter";

    public boolean insertInventory(int inventoryID, String address) {
        String command =  MessageFormat.format("INSERT INTO " + Inventory_TableName +  " ({0} ,{1}, {2}, {3}) VALUES(?,?,?,?)"
                ,InventoryID_ColumnName, InventoryAddress_ColumnName, CategoryIDCounter_ColumnName, SaleIDCounter_ColumnName);
        try {
            Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(command);
            pstmt.setInt(1, inventoryID);
            pstmt.setString(2, address);
            pstmt.setInt(3, 0);
            pstmt.setInt(4, 0);
            pstmt.executeUpdate();
            conn.close();
            return true;
        } catch (Exception ex) {
            System.out.println(ex);
            return false;
        }
    }

    public boolean updateCategoryIDCounter(int inventoryID, int newCategoryIDCounter){
        String command = "UPDATE " + Inventory_TableName +  " set " + CategoryIDCounter_ColumnName + "=" + newCategoryIDCounter +
                " where " + InventoryID_ColumnName + "=" + inventoryID;
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

    public boolean updateSaleIDCounter(int inventoryID, int newSaleIDCounter){
        String command = "UPDATE " + Inventory_TableName +  " set " + SaleIDCounter_ColumnName + "=" + newSaleIDCounter +
                " where " + InventoryID_ColumnName + "=" + inventoryID;
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

    public List<DTO> selectAll(){
        String command = "select * from " + Inventory_TableName;
        List<DTO> dtos = new ArrayList<>();
        try {
            Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(command);
            ResultSet res = pstmt.executeQuery();
            while (res.next()) {
                DTO dto = new InventoryDTO(res.getInt(1), res.getString(2), res.getInt(3), res.getInt(4));
                dtos.add(dto);
            }
            res.close();
        } catch (Exception ex) {
            dtos.clear();
            System.out.println(ex);
        }
        return dtos;
    }

    public InventoryDTO selectByID(int ID){
        String command = "select * from " + Inventory_TableName +  " where " + InventoryID_ColumnName + "=" + ID;
        InventoryDTO dto = null;
        try {
            Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(command);
            ResultSet res = pstmt.executeQuery();
            while (res.next()) {
                dto = new InventoryDTO(res.getInt(1), res.getString(2), res.getInt(3), res.getInt(4));
            }
            res.close();
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return dto;
    }

    public int getLastInvID(){
        String command = "select max(" + InventoryID_ColumnName + ") from " + Inventory_TableName;
        int maxID = -1;
        try {
            Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(command);
            ResultSet res = pstmt.executeQuery();
            while (res.next()) {
                maxID = res.getInt(1);
            }
            res.close();
            conn.close();
        } catch (Exception ex) {
            System.out.println(ex);
        }

        return maxID;
    }

    public boolean removeInventory(int inventoryID){
        String sql = "DELETE FROM " + Inventory_TableName + " WHERE " + InventoryID_ColumnName + "=" + inventoryID;
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
}
