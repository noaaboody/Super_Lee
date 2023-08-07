package Backend.DataAccessLayer;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class AbstractMapper {

    public Connection connect() {
        // SQLite's connection string
        String userDi = "C:\\Users\\noaab\\limud\\projects\\Super_Li_1";
        //String userDi = Paths.get("").toAbsolutePath().toString();
        String url = "jdbc:sqlite:" + userDi + "/Suppliers_Inventory.db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }
}
