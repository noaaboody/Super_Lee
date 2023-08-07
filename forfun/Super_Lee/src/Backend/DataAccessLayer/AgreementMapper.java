package Backend.DataAccessLayer;

import Backend.DataAccessLayer.DTOs.AgreementDTO;
import Backend.DataAccessLayer.DTOs.ConditionDetailsDTO;
import Backend.DataAccessLayer.DTOs.ConditionItemsDTO;
import Backend.DataAccessLayer.DTOs.SupplierToDayDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;


public class AgreementMapper extends AbstractMapper{

    private final String Agreement_TName = "Agreement";
    private final String ASupplierId_CName = "SupplierId";
    private final String AIsConstSupply_CName = "IsConstantSupply";
    private final String ACondId_CName = "ConditionId";

    private final String CondDetails_TName = "Condition_Details";
    private final String CDCondId_CName = "ConditionId";
    private final String CDSupplierId_CName ="SupplierId";
    private final String CDIsForPrice_CName = "IsForPrice";
    private final String CDIsForQuan_CName = "IsForQuantity";
    private final String CDNeededAmount_CName = "NeededAmount";
    private final String CDPercentage_CName = "Percentage";

    private final String ConditionItems_TName = "Condition_Items";
    private final String CISupplierId_CName = "SupplierId";
    private final String CIItemId_CName = "ItemId";
    private final String CICondId_CName = "ConditionId";

    private final String SupplierToDay_TName = "SupplierToDay";
    private final String SDSupplierId_CName = "supplierId";
    private final String SDDay_CName = "day";

    public AgreementMapper(){}

    //===========================================================================================
    //                            Agreement table methods
    //===========================================================================================
    public boolean insertAgreement(int supplierId ,int isConstSupply, int condId){
        String command = MessageFormat.format("INSERT INTO "+ Agreement_TName + " ({0}, {1}, {2}) VALUES(?,?,?)",
                ASupplierId_CName, AIsConstSupply_CName, ACondId_CName);
        try{
            Connection conn = this.connect();
            PreparedStatement statement = conn.prepareStatement(command);
            statement.setInt(1, supplierId);
            statement.setInt(2, isConstSupply);
            statement.setInt(3, condId);
            statement.executeUpdate();
            statement.close();
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    public boolean updateAgreement(int supplierId, int isConstSupply, int condId){
        String command = MessageFormat.format("UPDATE "+ Agreement_TName + " SET {0} = {1}, {2} = {3} WHERE {4} = {5}",
                ACondId_CName, String.valueOf(condId), AIsConstSupply_CName, String.valueOf(isConstSupply), ASupplierId_CName, String.valueOf(supplierId));
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

    public AgreementDTO getSupplierAgreement(int supplierId){
        String command = "SELECT * FROM "+ Agreement_TName +" WHERE "+ ASupplierId_CName +" = " +supplierId ;

        AgreementDTO a;
        try{
            Connection conn = this.connect();
            PreparedStatement statement = conn.prepareStatement(command);
            ResultSet res = statement.executeQuery();
            a =  new AgreementDTO(res.getInt(1),res.getInt(2),res.getInt(3));
            res.close();
            return a;
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    //===========================================================================================
    //                           ConditionDetails methods
    //===========================================================================================
    public boolean insertConditionDetails(int condId, int supplierId, int isForPrice, int isForQuantity, double neededAmount, double percentage){
        String command = MessageFormat.format("INSERT INTO "+ CondDetails_TName + " ({0}, {1}, {2}, {3}, {4}, {5}) VALUES(?,?,?,?,?,?)",
                CDCondId_CName, CDSupplierId_CName, CDIsForPrice_CName, CDIsForQuan_CName,CDNeededAmount_CName,CDPercentage_CName);
        try{
            Connection conn = this.connect();
            PreparedStatement statement = conn.prepareStatement(command);
            statement.setInt(1, condId);
            statement.setInt(2, supplierId);
            statement.setInt(3, isForPrice);
            statement.setInt(4, isForQuantity);
            statement.setDouble(5,neededAmount);
            statement.setDouble(6,percentage);
            statement.executeUpdate();
            statement.close();
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    public boolean updateConditionDetails(int condId, int supplierId, int isForPrice, int isForQuantity, int neededAmount, double percentage){
        String command = MessageFormat.format("UPDATE "+ CondDetails_TName + " SET {0} = {1}, {2} = {3}, {4} = {5}, {6} = {7}, WHERE {8} = {9} AND {10} = {11}",
                CDIsForPrice_CName, String.valueOf(isForPrice), CDIsForQuan_CName, String.valueOf(isForQuantity), CDNeededAmount_CName, String.valueOf(neededAmount),
                CDPercentage_CName, String.valueOf(percentage), ACondId_CName, String.valueOf(condId), ASupplierId_CName, String.valueOf(supplierId));
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

    public ConditionDetailsDTO getConditionDetails(int condId, int supplierId){
        String command = "SELECT * FROM "+ CondDetails_TName +" WHERE "+ CDCondId_CName +" = "+ condId +
                " AND "+ CDSupplierId_CName +" = "+ supplierId ;

        ConditionDetailsDTO c;
        try{
            Connection conn = this.connect();
            PreparedStatement statement = conn.prepareStatement(command);
            ResultSet res = statement.executeQuery();
            c = new ConditionDetailsDTO(res.getInt(1),res.getInt(2),res.getInt(3),res.getInt(4),
                    res.getFloat(5),res.getFloat(6));
            res.close();
            return c;
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public List<ConditionDetailsDTO> getSupplierConditions(int supplierId){
        String command = "SELECT * FROM "+ CondDetails_TName +" WHERE "+ CDSupplierId_CName +" = "+ supplierId;
        List<ConditionDetailsDTO> out = new ArrayList<>();
        try{
            Connection conn = this.connect();
            PreparedStatement statement = conn.prepareStatement(command);
            ResultSet res = statement.executeQuery();
            while (res.next()){
                out.add(new ConditionDetailsDTO(res.getInt(1),res.getInt(2),res.getInt(3),res.getInt(4),res.getDouble(5),res.getDouble(6)));
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
    //                            ConditionItems methods
    //===========================================================================================
    public boolean insertConditionItems(int supplierId, int itemId, int condId){
        String command = MessageFormat.format("INSERT INTO "+ ConditionItems_TName + " ({0}, {1}, {2}) VALUES(?,?,?)",
                CISupplierId_CName, CIItemId_CName, CICondId_CName);
        try{
            Connection conn = this.connect();
            PreparedStatement statement = conn.prepareStatement(command);
            statement.setInt(1, supplierId);
            statement.setInt(2, itemId);
            statement.setInt(3, condId);
            statement.executeUpdate();
            statement.close();
            return true;
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean removeConditionItem(int supplierId, int itemId, int condId){
        String command = MessageFormat.format("DELETE FROM "+ ConditionItems_TName + "WHERE {0} = {1} AND {2} = {3} AND {4} = {5}",
                CISupplierId_CName, String.valueOf(supplierId), CIItemId_CName, String.valueOf(itemId), CICondId_CName, String.valueOf(condId));
        try{
            Connection conn = this.connect();
            PreparedStatement statement = conn.prepareStatement(command);
            statement.executeUpdate();
            statement.close();
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    public List<ConditionItemsDTO> getSupplierCondItems(int supplierId, int condId){
        String command = "SELECT * FROM "+ ConditionItems_TName +" WHERE " + CISupplierId_CName +" = "+ supplierId +
                " AND "+  CICondId_CName+" = " + condId;
        List<ConditionItemsDTO> out = new ArrayList<>();
        try{
            Connection conn = this.connect();
            PreparedStatement statement = conn.prepareStatement(command);
            ResultSet res = statement.executeQuery();
            while (res.next()){
                out.add(new ConditionItemsDTO(res.getInt(1),res.getInt(2),res.getInt(3)));
            }
            res.close();
            return out;
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
    //================================================================================
    // SupplierToDay methods
    //================================================================================

    public boolean insertSupplierDay(int supID, int day){
        String command = MessageFormat.format("INSERT INTO "+ SupplierToDay_TName + " ({0}, {1}) VALUES(?,?)",
                SDSupplierId_CName, SDDay_CName);
        try{
            Connection conn = this.connect();
            PreparedStatement statement = conn.prepareStatement(command);
            statement.setInt(1, supID);
            statement.setInt(2, day);
            statement.executeUpdate();
            statement.close();
            return true;
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean removeSupplierDay(int supId, int day){
        String command = "DELETE FROM "+ SupplierToDay_TName + "WHERE "+SDSupplierId_CName +" = " + supId +
                " AND "+ SDDay_CName +" = "+ day;
        try{
            Connection conn = this.connect();
            PreparedStatement statement = conn.prepareStatement(command);
            statement.executeUpdate();
            statement.close();
            return true;
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }

    public List<SupplierToDayDTO> getSupplierToDays(){
        String command = "SELECT * FROM "+ SupplierToDay_TName;
        List<SupplierToDayDTO> out = new ArrayList<>();
        try{
            Connection conn = this.connect();
            PreparedStatement statement = conn.prepareStatement(command);
            ResultSet res = statement.executeQuery();
            while (res.next()){
                out.add(new SupplierToDayDTO(res.getInt(1),res.getInt(2)));
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

