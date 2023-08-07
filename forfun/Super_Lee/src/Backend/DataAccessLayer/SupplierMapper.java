package Backend.DataAccessLayer;

import Backend.DataAccessLayer.DTOs.ContactsDTO;
import Backend.DataAccessLayer.DTOs.SupplierCardDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

public class SupplierMapper extends AbstractMapper {

    private final String SCard_TName = "Supplier_Card";
    private final String SSupplierId_CName = "Id";
    private final String SSupplierName_CName = "Name";
    private final String SISMobile_CName = "IsMobile";
    private final String SPaymentCond_CName = "PaymentCondition";
    private final String SBankAccount_CName = "BankAccount";

    private final String Contacts_TName = "Contacts";
    private final String CId_CName = "Id";
    private final String CName_CName = "Name";
    private final String CPhoneNumber_CName = "PhoneNumber";
    private final String CEmail_CName = "email";
    private final String CSupplierId_CName = "SupplierId";

    public SupplierMapper(){super();}

    //===========================================================================================
    //                            Supplier_Card methods
    //===========================================================================================
    public boolean InsertSupplierCard(int id, String name, int isMobile, int paymentCondition, int bankAccount){
        String command = MessageFormat.format("INSERT INTO "+ SCard_TName + " ({0}, {1}, {2}, {3}, {4}) VALUES(?,?,?,?,?)",
                SSupplierId_CName, SSupplierName_CName, SISMobile_CName, SPaymentCond_CName,SBankAccount_CName);
        try{
            Connection conn = this.connect();
            PreparedStatement statement = conn.prepareStatement(command);
            statement.setInt(1, id);
            statement.setString(2, name);
            statement.setInt(3, isMobile);
            statement.setInt(4,paymentCondition);
            statement.setInt(5,bankAccount);
            statement.executeUpdate();
            statement.close();
            return true;
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean updateSupplierCard(int id, String name, int isMobile, int paymentCondition, int bankAccount){
        String command = MessageFormat.format("UPDATE "+ SCard_TName + " SET {0} = ''{1}'', {2} = ''{3}'', {4} = {5}, {6} = {7} WHERE {8} = {9}",
                SSupplierName_CName,name, SISMobile_CName,isMobile, SPaymentCond_CName,paymentCondition, SBankAccount_CName,String.valueOf(bankAccount), SSupplierId_CName,String.valueOf(id));
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

    public SupplierCardDTO getSupplierCard(int id){
        String command = MessageFormat.format("SELECT * FROM "+ SCard_TName +" WHERE {0} = {1}",
                SSupplierId_CName, String.valueOf(id));
        SupplierCardDTO s  = null;
        try{
            Connection conn = this.connect();
            PreparedStatement statement = conn.prepareStatement(command);
            ResultSet res = statement.executeQuery();
            s = new SupplierCardDTO(res.getInt(1),res.getString(2),res.getInt(3),res.getInt(4),res.getInt(5));
            res.close();
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
        finally {
            return s;
        }
    }

    public List<SupplierCardDTO> getAllSuppliers(){
        String command = "SELECT * FROM "+ SCard_TName;
        List<SupplierCardDTO> out = new ArrayList<>();
        try{
            Connection conn = this.connect();
            PreparedStatement statement = conn.prepareStatement(command);
            ResultSet res = statement.executeQuery();
            while (res.next()){
                out.add(new SupplierCardDTO(res.getInt(1),res.getString(2),res.getInt(3),res.getInt(4),res.getInt(5)));
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
    //                            Contacts methods
    //===========================================================================================
    public boolean InsertContact(int id,String name, String phoneNum, String email, int supplierId){
        String command = MessageFormat.format("INSERT INTO "+ Contacts_TName + " ({0}, {1}, {2}, {3}, {4}) VALUES(?,?,?,?,?)",
                CId_CName, CName_CName, CPhoneNumber_CName, CEmail_CName, CSupplierId_CName);
        try{
            Connection conn = this.connect();
            PreparedStatement statement = conn.prepareStatement(command);
            statement.setInt(1, id);
            statement.setString(2,name);
            statement.setString(3, phoneNum);
            statement.setString(4, email);
            statement.setInt(5, supplierId);
            statement.executeUpdate();
            statement.close();
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }
    public boolean updateContact(int id,String name, String phoneNum, String email, int supplierId){
        String command = MessageFormat.format("UPDATE "+ Contacts_TName + " SET {0} = ''{1}'', {2} = ''{3}'', {4} = ''{5}''   WHERE {6} = {7} AND {8} = {9}",
                CName_CName,name, CPhoneNumber_CName,phoneNum, CEmail_CName,email, CId_CName,String.valueOf(id), CSupplierId_CName,String.valueOf(supplierId));
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
    public List<ContactsDTO> getSupplierContacts(int supplierId){
        String command = MessageFormat.format("SELECT * FROM "+ Contacts_TName +" WHERE {0} = {1}",
                CSupplierId_CName, String.valueOf(supplierId));
        List<ContactsDTO> out = new ArrayList<>();
        try{
            Connection conn = this.connect();
            PreparedStatement statement = conn.prepareStatement(command);
            ResultSet res = statement.executeQuery();
            while (res.next()){
                out.add(new ContactsDTO(res.getInt(1),res.getString(2),res.getString(3),res.getString(4),res.getInt(5)));
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
