package Backend.DataAccessLayer;

import Backend.DataAccessLayer.DTOs.DTO;
import Backend.DataAccessLayer.DTOs.DamageAndExpirationProductDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

public class DamageAndExpirationProductMapper extends AbstractMapper {

    private final String TableName = "DamageAndExpirationProduct";
    private final String inventoryID_ColumnName = "inventoryID";
    private final String productID_ColumnName = "productID";
    private final String itemID_ColumnName = "itemID";
    private final String expirationDate_ColumnName = "expirationDate";
    private final String isDamage_ColumnName = "isDamaged";

    public DamageAndExpirationProductMapper() {
    }

    public boolean addExpiredProduct(int inventoryID, int productID, int itemID, String expirationDate, int isDamage) {
        if (!checkIfInvItemProductExist(inventoryID, productID, itemID)) {
            String command = MessageFormat.format("INSERT INTO " + TableName + " ({0} ,{1}, {2}, {3} ,{4}) VALUES(?,?,?,?,?)"
                    , inventoryID_ColumnName, productID_ColumnName, itemID_ColumnName, expirationDate_ColumnName, isDamage_ColumnName);
            try {
                Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(command);
                pstmt.setInt(1, inventoryID);
                pstmt.setInt(2, productID);
                pstmt.setInt(3, itemID);
                pstmt.setString(4, expirationDate);
                pstmt.setInt(5, isDamage);
                pstmt.executeUpdate();
                return true;
            } catch (Exception ex) {
                System.out.println(ex);
                return false;
            }
        } else {
            return false;
        }
    }

    public boolean addDamagedProduct(int inventoryID, int productID, int itemID, int isDamage) {
        if (!checkIfInvItemProductExist(inventoryID, productID, itemID)) {
            String command = MessageFormat.format("INSERT INTO " + TableName + " ({0} ,{1}, {2}, {3} ,{4}) VALUES(?,?,?,?,?)"
                    , inventoryID_ColumnName, productID_ColumnName, itemID_ColumnName, expirationDate_ColumnName, isDamage_ColumnName);
            try {
                Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(command);
                pstmt.setInt(1, inventoryID);
                pstmt.setInt(2, productID);
                pstmt.setInt(3, itemID);
                pstmt.setString(4, null);
                pstmt.setInt(5, isDamage);
                pstmt.executeUpdate();
                return true;
            } catch (Exception ex) {
                System.out.println(ex);
                return false;
            }
        } else {
            return updateDamagedProduct(inventoryID, productID, itemID, isDamage);
        }
    }

    private boolean checkIfInvItemProductExist(int inventoryID, int productID, int itemID) {
        String command = "SELECT * from " + TableName + " where " + inventoryID_ColumnName + "=" + inventoryID + " and " + productID_ColumnName + "=" + productID + " and " + itemID_ColumnName + "=" + itemID;
        List<DTO> result = new ArrayList<>();
        try {
            Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(command);
            ResultSet res = pstmt.executeQuery();
            while (res.next()) {
                DamageAndExpirationProductDTO damageAndExpirationProductDTO = new DamageAndExpirationProductDTO(res.getInt(1), res.getInt(2), res.getInt(3), res.getString(4),
                        res.getInt(5));
                result.add(damageAndExpirationProductDTO);
            }
            res.close();
        } catch (Exception ex) {
            System.out.println(ex);
            return false;
        }
        return !result.isEmpty();
    }

    public boolean updateDamagedProduct(int inventoryID, int productID, int itemID, int isDamaged) {
        String command = "UPDATE " + TableName + " set " + itemID_ColumnName + "=" + isDamaged + " where "
                + inventoryID_ColumnName + "=" + inventoryID + " and " + productID_ColumnName + "=" + productID + " and " + itemID_ColumnName + "=" + itemID;
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
    public List<DamageAndExpirationProductDTO> getDamagedProduct(){
        String command = "SELECT * from " + TableName + " where " + isDamage_ColumnName+ "=" + 1;
        List<DamageAndExpirationProductDTO> result = new ArrayList<>();
        try {
            Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(command);
            ResultSet res = pstmt.executeQuery();
            while (res.next()) {
                DamageAndExpirationProductDTO dto = new DamageAndExpirationProductDTO(res.getInt(1), res.getInt(2), res.getInt(3), res.getString(4),
                        res.getInt(5));
                result.add(dto);
            }
            res.close();
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return result;
    }
    public List<DamageAndExpirationProductDTO> getProductWithExpirationDateByID(int inventoryID, int itemID){
        String command = "SELECT * from " + TableName + " where " + inventoryID_ColumnName+"=" + inventoryID + " and "+ itemID_ColumnName+"=" + itemID;
        List<DamageAndExpirationProductDTO> result = new ArrayList<>();
        try {
            Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(command);
            ResultSet res = pstmt.executeQuery();
            while (res.next()) {
                DamageAndExpirationProductDTO dto = new DamageAndExpirationProductDTO(res.getInt(1), res.getInt(2), res.getInt(3), res.getString(4),
                        res.getInt(5));
                result.add(dto);
            }
            res.close();
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return result;
    }

    public boolean removeProduct(int inventoryID, int itemID, int productID){
        String sql = "DELETE FROM " + TableName + " WHERE " + inventoryID_ColumnName + "=" + inventoryID + " and " +
                itemID_ColumnName + "=" + itemID + " and " + productID_ColumnName + "=" + productID;
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
    public boolean removeInventory(int inventoryID){
        String sql = "DELETE FROM " + TableName + " WHERE " + inventoryID_ColumnName + "=" + inventoryID;
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
