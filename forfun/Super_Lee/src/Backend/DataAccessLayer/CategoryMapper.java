package Backend.DataAccessLayer;

import Backend.DataAccessLayer.DTOs.CategoryDTO;
import Backend.DataAccessLayer.DTOs.InvItemDTO;
import Backend.DataAccessLayer.DTOs.SubCategoryDTO;
import Backend.DataAccessLayer.DTOs.SubSubCategoryDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

public class CategoryMapper extends AbstractMapper {

    private final String Category_TableName = "Category";
    private final String SubCategory_TableName = "SubCategory";
    private final String SubSubCategory_TableName = "SubSubCategory";
    private final String ItemToSubSubCategory_TableName = "ItemToSubSubCategory";
    private final String fatherCategoryToCategory_TableName = "FatherCategoryToCategory";
    private final String InvItem_TableName = "InvItem";

    private final String InventoryID_ColumnName = "inventoryID";
    private final String CategoryID_ColumnName = "categoryID";
    private final String SubCategoryID_ColumnName = "subCategoryID";
    private final String SubSubCategoryID_ColumnName = "subSubCategoryID";
    private final String CategoryName_ColumnName = "categoryName";
    private final String SubCategoryName_ColumnName = "subCategoryName";
    private final String SubSubCategoryName_ColumnName = "subSubCategoryName";
    private final String ItemID_ColumnName = "itemID";
    private final String FatherCategoryID_ColumnName = "fatherCategoryID";
    private final String ChildCategoryID_ColumnName = "childCategoryID";

    public CategoryMapper() {
        super();
    }

    public boolean insertCategory(int inventoryID, int categoryID, String categoryName) {
        String command = MessageFormat.format("INSERT INTO " + Category_TableName + " ({0} ,{1}, {2}) VALUES(?,?,?)"
                , InventoryID_ColumnName, CategoryID_ColumnName, CategoryName_ColumnName);
        try {
            Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(command);
            pstmt.setInt(1, inventoryID);
            pstmt.setInt(2, categoryID);
            pstmt.setString(3, categoryName);
            pstmt.executeUpdate();
            return true;
        } catch (Exception ex) {
            System.out.println(ex);
            return false;
        }
    }

    public boolean insertSubCategory(int inventoryID, int subCategoryID, String subCategoryName, int fatherCategoryID) {
        String command1 = MessageFormat.format("INSERT INTO " + SubCategory_TableName + " ({0} ,{1}, {2}) VALUES(?,?,?)"
                , InventoryID_ColumnName, SubCategoryID_ColumnName, SubCategoryName_ColumnName);
        String command2 = MessageFormat.format("INSERT INTO " + fatherCategoryToCategory_TableName + " ({0} ,{1}, {2}) VALUES(?,?,?)"
                , InventoryID_ColumnName, ChildCategoryID_ColumnName, FatherCategoryID_ColumnName);
        try {
            Connection conn = this.connect();
            // add to subCategory table
            PreparedStatement pstmt1 = conn.prepareStatement(command1);
            pstmt1.setInt(1, inventoryID);
            pstmt1.setInt(2, subCategoryID);
            pstmt1.setString(3, subCategoryName);
            pstmt1.executeUpdate();
            // add to fatherCategoryToCategory table
            PreparedStatement pstmt2 = conn.prepareStatement(command2);
            pstmt2.setInt(1, inventoryID);
            pstmt2.setInt(2, subCategoryID);
            pstmt2.setInt(3, fatherCategoryID);
            pstmt2.executeUpdate();
            return true;
        } catch (Exception ex) {
            System.out.println(ex);
            return false;
        }
    }

    public boolean insertSubSubCategory(int inventoryID, int subSubCategoryID, String subSubCategoryName, int fatherCategoryID) {
        String command1 = MessageFormat.format("INSERT INTO " + SubSubCategory_TableName + " ({0} ,{1}, {2}) VALUES(?,?,?)"
                , InventoryID_ColumnName, SubSubCategoryID_ColumnName, SubSubCategoryName_ColumnName);
        String command2 = MessageFormat.format("INSERT INTO " + fatherCategoryToCategory_TableName + " ({0} ,{1}, {2}) VALUES(?,?,?)"
                , InventoryID_ColumnName, ChildCategoryID_ColumnName, FatherCategoryID_ColumnName);
        try {
            Connection conn = this.connect();
            // add to subSubCategory table
            PreparedStatement pstmt1 = conn.prepareStatement(command1);
            pstmt1.setInt(1, inventoryID);
            pstmt1.setInt(2, subSubCategoryID);
            pstmt1.setString(3, subSubCategoryName);
            pstmt1.executeUpdate();
            // add to fatherCategoryToCategory table
            PreparedStatement pstmt2 = conn.prepareStatement(command2);
            pstmt2.setInt(1, inventoryID);
            pstmt2.setInt(2, subSubCategoryID);
            pstmt2.setInt(3, fatherCategoryID);
            pstmt2.executeUpdate();
            return true;
        } catch (Exception ex) {
            System.out.println(ex);
            return false;
        }
    }

    public CategoryDTO getCategoryByName(int inventoryID, String categoryName) {
        String command = "SELECT * " +
                " from " + Category_TableName +
                " where " + InventoryID_ColumnName + " = " + inventoryID +
                " and " + CategoryName_ColumnName + " = '" + categoryName + "'";
        CategoryDTO result = null;
        try {
            Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(command);
            ResultSet res = pstmt.executeQuery();
            while (res.next()) {
                result = new CategoryDTO(res.getInt(1), res.getInt(2), res.getString(3));
            }
            res.close();
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return result;
    }

    public List<CategoryDTO> getAllCategories(int inventoryID) {
        String command = "SELECT * " +
                " from " + Category_TableName +
                " where " + InventoryID_ColumnName + " = " + inventoryID;
        List<CategoryDTO> result = new ArrayList<>();
        try {
            Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(command);
            ResultSet res = pstmt.executeQuery();
            while (res.next()) {
                CategoryDTO categoryDTO = new CategoryDTO(res.getInt(1), res.getInt(2), res.getString(3));
                result.add(categoryDTO);
            }
            res.close();
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return result;
    }

    public List<SubCategoryDTO> getSubCategoriesOfCategory(int inventoryID, int categoryID) {
        String command = "SELECT * " +
                " from " + SubCategory_TableName +
                " where " + InventoryID_ColumnName + " = " + inventoryID +
                " and " + SubCategoryID_ColumnName + " IN (SELECT " + ChildCategoryID_ColumnName +
                " from " + fatherCategoryToCategory_TableName +
                " where " + InventoryID_ColumnName + " = " + inventoryID +
                " and " + FatherCategoryID_ColumnName + " = " + categoryID + ")";
        List<SubCategoryDTO> result = new ArrayList<>();
        try {
            Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(command);
            ResultSet res = pstmt.executeQuery();
            while (res.next()) {
                SubCategoryDTO subCategoryDTO = new SubCategoryDTO(res.getInt(1), res.getInt(2), res.getString(3));
                result.add(subCategoryDTO);
            }
            res.close();
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return result;
    }

    public List<SubSubCategoryDTO> getSubSubCategoriesOfSubCategory(int inventoryID, int subCategoryID) {
        String command = "SELECT * " +
                " from " + SubSubCategory_TableName +
                " where " + InventoryID_ColumnName + " = " + inventoryID +
                " and " + SubSubCategoryID_ColumnName + " IN (SELECT " + ChildCategoryID_ColumnName +
                " from " + fatherCategoryToCategory_TableName +
                " where " + InventoryID_ColumnName + " = " + inventoryID +
                " and " + FatherCategoryID_ColumnName + " = " + subCategoryID + ")";
        List<SubSubCategoryDTO> result = new ArrayList<>();
        try {
            Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(command);
            ResultSet res = pstmt.executeQuery();
            while (res.next()) {
                SubSubCategoryDTO subSubCategoryDTO = new SubSubCategoryDTO(res.getInt(1), res.getInt(2), res.getString(3));
                result.add(subSubCategoryDTO);
            }
            res.close();
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return result;
    }

    public List<InvItemDTO> getItemsOfSubSubCategory(int inventoryID, int subSubCategoryID) {
        String command = "SELECT * " +
                " from " + InvItem_TableName +
                " where " + InventoryID_ColumnName + " = " + inventoryID +
                " and " + ItemID_ColumnName + " IN (SELECT " + ItemID_ColumnName +
                " from " + ItemToSubSubCategory_TableName +
                " where " + InventoryID_ColumnName + " = " + inventoryID +
                " and " + SubSubCategoryID_ColumnName + "=" + subSubCategoryID + ")";
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

    public SubCategoryDTO getSubCategoryByName(int inventoryID, String subCategoryName) {
        String command = "SELECT * " +
                " from " + SubCategory_TableName +
                " where " + InventoryID_ColumnName + " = " + inventoryID +
                " and " + SubCategoryName_ColumnName + " = '" + subCategoryName + "'";
        SubCategoryDTO result = null;
        try {
            Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(command);
            ResultSet res = pstmt.executeQuery();
            while (res.next()) {
                result = new SubCategoryDTO(res.getInt(1), res.getInt(2), res.getString(3));
            }
            res.close();
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return result;
    }

    public SubSubCategoryDTO getSubSubCategoryByName(int inventoryID, String subSubCategoryName) {
        String command = "SELECT * " +
                " from " + SubSubCategory_TableName +
                " where " + InventoryID_ColumnName + " = " + inventoryID +
                " and " + SubSubCategoryName_ColumnName + " = '" + subSubCategoryName + "'";
        SubSubCategoryDTO result = null;
        try {
            Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(command);
            ResultSet res = pstmt.executeQuery();
            while (res.next()) {
                result = new SubSubCategoryDTO(res.getInt(1), res.getInt(2), res.getString(3));
            }
            res.close();
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return result;
    }

    public boolean removeInventoryCategory(int inventoryID) {
        String sql = "DELETE FROM " + Category_TableName + " WHERE " + InventoryID_ColumnName + "=" + inventoryID;
        try {
            Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.executeUpdate();
            return true;
        } catch (Exception ex) {
            System.out.println(ex);
            return false;
        }
    }

    public boolean removeInventoryFatherToCategory(int inventoryID) {
        String sql = "DELETE FROM " + fatherCategoryToCategory_TableName + " WHERE " + InventoryID_ColumnName + "=" + inventoryID;
        try {
            Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.executeUpdate();
            return true;
        } catch (Exception ex) {
            System.out.println(ex);
            return false;
        }
    }

    public boolean removeInventorySubSubCategory(int inventoryID) {
        String sql = "DELETE FROM " + SubSubCategory_TableName + " WHERE " + InventoryID_ColumnName + "=" + inventoryID;
        try {
            Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.executeUpdate();
            return true;
        } catch (Exception ex) {
            System.out.println(ex);
            return false;
        }
    }

    public boolean removeInventorySubCategory(int inventoryID) {
        String sql = "DELETE FROM " + ItemToSubSubCategory_TableName + " WHERE " + InventoryID_ColumnName + "=" + inventoryID;
        try {
            Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.executeUpdate();
            return true;
        } catch (Exception ex) {
            System.out.println(ex);
            return false;
        }
    }
    public boolean removeInventory(int inventory){
            return removeInventoryCategory(inventory) && removeInventorySubCategory(inventory) && removeInventorySubSubCategory(inventory) && removeInventoryFatherToCategory(inventory);
    }
}


