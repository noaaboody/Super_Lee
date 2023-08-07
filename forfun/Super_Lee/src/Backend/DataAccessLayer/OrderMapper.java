package Backend.DataAccessLayer;

import Backend.DataAccessLayer.DTOs.OrderItemDetailsDTO;
import Backend.DataAccessLayer.DTOs.OrdersDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

public class OrderMapper extends AbstractMapper{
    private final String Orders_TName = "Orders";
    private final String OOrderId_CName ="OrderId";
    private final String OOrderAddress_CName = "OrderAddress";
    private final String OSupplierId_CName = "SupplierId";
    private final String OTotalPrice_CName = "Total_Price";
    private final String ODate_CName = "Date";
    private final String OExe_CName = "IsExecuted";

    private final String OrderItemDetails_TName = "Order_Items_Details";
    private final String OIOrderId_CName = "OrderId";
    private final String OIItemId_CName = "ItemId";
    private final String OIItemName_CName = "ItemName";
    private final String OIQuantity_CName ="Quantity";
    private final String OIInitialPrice_CName ="InitialPrice";
    private final String OIFinalPrice_CName = "FinalPrice";

    public OrderMapper(){}

    //===========================================================================================
    //                            Orders table methods
    //===========================================================================================
    public boolean insertOrder(int orderId, int supplierId, String orderAddress, double totalPrice, String date, int executed){
        String command = MessageFormat.format("INSERT INTO "+ Orders_TName + " ({0}, {1}, {2}, {3}, {4}, {5}) VALUES(?,?,?,?,?,?)",
                OOrderId_CName, OSupplierId_CName, OOrderAddress_CName, OTotalPrice_CName,ODate_CName,OExe_CName);
        try{
            Connection conn = this.connect();
            PreparedStatement statement = conn.prepareStatement(command);
            statement.setInt(1, orderId);
            statement.setInt(2, supplierId);
            statement.setString(3, orderAddress);
            statement.setDouble(4, Math.round(totalPrice*100.0)/100.0);
            statement.setString(5,date);
            statement.setInt(6,executed);
            statement.executeUpdate();
            statement.close();
            return true;
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean updateOrder(int orderId, int supplierId, String orderAddress, double totalPrice, String date, int executed){
        String command = MessageFormat.format("UPDATE "+ Orders_TName + " SET {0} = {1}, {2} = '{3}', {4} = {5}, {6} = '{7}', {8} = {9} WHERE {10} = {11}",
                OSupplierId_CName,String.valueOf(supplierId), OOrderAddress_CName,orderAddress, OTotalPrice_CName,String.valueOf(totalPrice), ODate_CName,date, OExe_CName,executed,  OOrderId_CName, String.valueOf(orderId));
        try{
            Connection conn = this.connect();
            PreparedStatement statement = conn.prepareStatement(command);
            statement.executeUpdate();
            statement.close();
            return true;
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean deleteOrder(int orderId){
        String command = "DELETE FROM "+ Orders_TName + "WHERE " + OOrderId_CName + " = "+ orderId;
        try{
            Connection conn = this.connect();
            PreparedStatement statement = conn.prepareStatement(command);
            statement.executeUpdate();
            statement.close();
            return true;
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
            return false;
        }
    }

    public OrdersDTO getOrderById(int orderId){
        String command = "SELECT * FROM "+ Orders_TName +" WHERE "+ OOrderId_CName + " = "+ orderId;
        OrdersDTO o;
        try{
            Connection conn = this.connect();
            PreparedStatement statement = conn.prepareStatement(command);
            ResultSet res = statement.executeQuery();
            o = new OrdersDTO(res.getInt(1),res.getInt(2),res.getString(3),res.getDouble(4),
                    res.getString(5), res.getInt(6));
            res.close();
            return o;
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public int getMaxOrderId(){
        String command = "SELECT Max("+ OOrderId_CName +") FROM " + Orders_TName;
        try{
            Connection conn = this.connect();
            PreparedStatement statement = conn.prepareStatement(command);
            ResultSet res = statement.executeQuery();
            int i =  res.getInt(1);
            res.close();
            return i;
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
            return 0;
        }
    }

    public List<OrdersDTO> getNonExeOrders(){
        String command = "SELECT * FROM "+ Orders_TName +" WHERE " + OExe_CName+ " = 0";
        List<OrdersDTO> out = new ArrayList<>();
        try{
            Connection conn = this.connect();
            PreparedStatement statement = conn.prepareStatement(command);
            ResultSet res = statement.executeQuery();
            while (res.next()){
                out.add(new OrdersDTO(res.getInt(1),res.getInt(2),res.getString(3),res.getDouble(4),res.getString(5), res.getInt(6)));
            }
            res.close();
            return out;
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    //===========================================================================================
    //                            Order_Item_Details table methods
    //===========================================================================================
    public boolean insertOrderItemDet(int orderId, int itemId, String itemName, int quantity, double initialPrice, double finalPrice){
        String command = MessageFormat.format("INSERT INTO "+ OrderItemDetails_TName + " ({0}, {1}, {2}, {3}, {4}, {5}) VALUES(?,?,?,?,?,?)",
                OIOrderId_CName, OIItemId_CName, OIItemName_CName, OIQuantity_CName, OIInitialPrice_CName, OIFinalPrice_CName);
        try{
            Connection conn = this.connect();
            PreparedStatement statement = conn.prepareStatement(command);
            statement.setInt(1, orderId);
            statement.setInt(2, itemId);
            statement.setString(3, itemName);
            statement.setInt(4, quantity);
            statement.setDouble(5, initialPrice);
            statement.setDouble(6,finalPrice);
            statement.executeUpdate();
            statement.close();
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    public boolean deleteOrderItems(int orderId){
        String command = MessageFormat.format("DELETE FROM "+ OrderItemDetails_TName + "WHERE {0} = {1}", OIOrderId_CName, orderId);
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

    public List<OrderItemDetailsDTO> getOrderItems (int orderId){
        String command = "SELECT * FROM "+ OrderItemDetails_TName +" WHERE "+OOrderId_CName+" = " + orderId;
        List<OrderItemDetailsDTO> out = new ArrayList<>();
        try{
            Connection conn = this.connect();
            PreparedStatement statement = conn.prepareStatement(command);
            ResultSet res = statement.executeQuery();
            while (res.next()){
                out.add(new OrderItemDetailsDTO(res.getInt(1),res.getInt(2),res.getString(3),res.getInt(4),res.getDouble(5), res.getDouble(6)));
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
