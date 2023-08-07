package Backend.DataAccessLayer;

import Backend.DataAccessLayer.DTOs.ItemDTO;
import Backend.DataAccessLayer.DTOs.SupplierItemsDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

public class ItemMapper extends AbstractMapper{

    private final String Items_TName = "Items";
    private final String IId_CName = "Id";
    private final String IName_CName = "Name";
    private final String IManufacturer_CName = "Manufacturer";

    private final String SupplierItem_TName = "Suppliers_Items";
    private final String SId_CName = "SupplierId";
    private final String SItemId_CName ="ItemId";
    private final String SSerialNum_CName = "SerialNum";
    private final String SPrice_CName = "Price";
    private final String SQuantity_CName = "Quantity";

    public ItemMapper(){}

    //===========================================================================================
    //                            Items table methods
    //===========================================================================================
    public boolean insertItem(int id, String name, String manufacturer ){
        String command = MessageFormat.format("INSERT INTO "+ Items_TName + " ({0}, {1}, {2}) VALUES(?,?,?)",
                IId_CName, IName_CName, IManufacturer_CName);
        try{
            Connection conn = this.connect();
            PreparedStatement statement = conn.prepareStatement(command);
            statement.setInt(1, id);
            statement.setString(2, name);
            statement.setString(3, manufacturer);
            statement.executeUpdate();
            statement.close();
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    public boolean updateItem(int id, String newName, String newManufacturer){
        String command = MessageFormat.format("UPDATE "+ Items_TName + " SET {0} = ''{1}'', {2} = ''{3}''  WHERE "+ IId_CName +" = "+id,
                IName_CName,newName , IManufacturer_CName, newManufacturer);
        System.out.println(command);
        try{
            Connection conn = this.connect();
            PreparedStatement statement = conn.prepareStatement(command);
            statement.executeUpdate();
            statement.close();
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    public ItemDTO getItemById(int id){
        String command = "SELECT * FROM "+ Items_TName +" WHERE "+IId_CName+" = " + id;
        ItemDTO i = null;
        try{
            Connection conn = this.connect();
            PreparedStatement statement = conn.prepareStatement(command);
            ResultSet res = statement.executeQuery();
            i =  new ItemDTO(res.getInt(1),res.getString(2),res.getString(3));
            res.close();
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        finally {
            return i;
        }

    }

    public boolean deleteItem(int itemID){
        String sql = "DELETE FROM " + Items_TName + " WHERE " + IId_CName + "=" + itemID;
        try {
            Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.executeUpdate();
            pstmt.close();
            return true;
        }
        catch (Exception ex){
            System.out.println(ex);
            return false;
        }
    }



    //===========================================================================================
    //                            SupplierItems table methods
    //===========================================================================================
    public boolean insertSupplierItem(int supplierId, int itemId, int serialNum, double price, int quantity ){
        String command = MessageFormat.format("INSERT INTO "+ SupplierItem_TName + " ({0}, {1}, {2}, {3},{4}) VALUES(?,?,?,?,?)",
                SId_CName, SItemId_CName, SSerialNum_CName,SPrice_CName, SQuantity_CName);
        try{
            Connection conn = this.connect();
            PreparedStatement statement = conn.prepareStatement(command);
            statement.setInt(1, supplierId);
            statement.setInt(2, itemId);
            statement.setInt(3, serialNum);
            statement.setDouble(4, price);
            statement.setInt(5,quantity);
            statement.executeUpdate();
            statement.close();
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    public boolean updateSupplierItem(int supplierId, int itemId, int serialNum, double price, int quantity){
        String command = MessageFormat.format("UPDATE "+ SupplierItem_TName + " SET {0} = {1}, {2} = {3}, {4} = {5}  WHERE {6} = {7} AND {8} = {9}",
                SSerialNum_CName, String.valueOf(serialNum) , SPrice_CName, String.valueOf(price), SQuantity_CName, String.valueOf(quantity), SId_CName, String.valueOf(supplierId), SItemId_CName, String.valueOf(itemId));
        try{
            Connection conn = this.connect();
            PreparedStatement statement = conn.prepareStatement(command);
            statement.executeUpdate();
            statement.close();
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    public List<SupplierItemsDTO> getSupplierItems(int supplierId){
        String command = MessageFormat.format("SELECT * FROM "+ SupplierItem_TName +" WHERE {0} = {1}",
                SId_CName, String.valueOf(supplierId));
        List<SupplierItemsDTO> out = new ArrayList<>();
        try{
            Connection conn = this.connect();
            PreparedStatement statement = conn.prepareStatement(command);
            ResultSet res = statement.executeQuery();
            while (res.next()){
                out.add(new SupplierItemsDTO(res.getInt(1),res.getInt(2),res.getInt(3),res.getDouble(4),res.getInt(5)));
            }
            res.close();
            return out;
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

}
