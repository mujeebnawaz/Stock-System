package stock; // Defines the package


import java.sql.*; // DB handling package
import java.io.*;
import org.apache.derby.drda.NetworkServerControl;
import java.lang.Object;

/**
 *
 * @author mn3458z
 */

public class StockData {
    
    /**
    Definitions of important variables used by all classes
     **/
    public static boolean is_database = false;
    public static boolean auth = false; // Authorisation variable, keeps the track of user i.e.if user is logged in. Typed boolean.
    public static final String username = "student"; //Username value for Login Class. Typed String.
    public static final String password = "1234"; //Password value for Login Class. Typed Char.
    
    private static Connection connection;
    private static Statement stmt;
    

    static {
        try {
            // database configuration
            NetworkServerControl server = new NetworkServerControl();
            server.start(null);
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
            String sourceURL = "jdbc:derby://localhost:1527/"
                    + new File("UserDB").getAbsolutePath() + ";"; // database path defination
            connection = DriverManager.getConnection(sourceURL, "use", "use"); // username and password for the database
            stmt = connection.createStatement();
            is_database = true;
        } 
        catch (ClassNotFoundException cnfe) {
            System.out.println(cnfe);
            ErrorHandler.errorLogging(cnfe,false);
        } catch (SQLException sqle) {
            System.out.println(sqle);
            ErrorHandler.errorLogging(sqle,false);
        } catch (Exception e) {
            System.out.println(e);
            ErrorHandler.errorLogging(e,false);
        }

    }
    public static String getName(String key) {
        try {
            ResultSet res = stmt.executeQuery("SELECT * FROM Stock WHERE STOCKKEY = '" + key + "'");
            if (res.next()) {
                return res.getString(2);
            } else {
                return null;
            }
        } 
        catch (SQLException e) {
            ErrorHandler.errorLogging(e,false);
            System.out.println(e);
            return null;
        }
    }

    public static double getPrice(String key) {
        try{
            ResultSet res = stmt.executeQuery("SELECT * FROM Stock WHERE STOCKKEY = '" + key + "'");
            if (res.next()) { 
                return res.getDouble(4);
            } 
             else {
                return 0;
            }
        } 
        catch (SQLException e) {
            ErrorHandler.errorLogging(e,false);
            System.out.println(e);
            return -1.0;
        }
    }

    public static int getQuantity(String key) {
         try{
            ResultSet res = stmt.executeQuery("SELECT * FROM Stock WHERE STOCKKEY = '" + key + "'");
            if (res.next()) { 
                return res.getInt(3);
            }
            else {
                return 0;
            }
          }
         catch (SQLException e) {
            ErrorHandler.errorLogging(e,false);
            System.out.println(e);
            return -1;
        }
    }
    public static void update(String key, int extra) {
        String updateStr = "UPDATE Stock SET stockQuantity = stockQuantity + " + extra + " WHERE stockKey = '" + key + "'";
        System.out.println(updateStr);
        try {
            stmt.executeUpdate(updateStr);
        } 
        catch (SQLException e) {
            ErrorHandler.errorLogging(e,false);
            System.out.println(e);
        }
    }
    public static void updateDetail(String key, String name, double price) {
        String updateStr = "UPDATE Stock SET STOCKNAME ='"+name+"',STOCKPRICE = "+price+" WHERE STOCKKEY = '" + key + "'";
        System.out.println(updateStr);
        try {
            stmt.executeUpdate(updateStr);
        } 
        catch (SQLException e) {
            ErrorHandler.errorLogging(e,false);
            System.out.println(e);
        }
    }
    // method for adding an item to the database
    public static void addAnItem(String key, String name, int quantity, double price){    
        String query = "INSERT INTO USE.STOCK (STOCKKEY, STOCKNAME, STOCKPRICE, STOCKQUANTITY) \n" +
                        "VALUES ('"+key+"','"+name+"',"+price+","+quantity+")";
        System.out.println(query);
         try {
                 stmt.executeUpdate(query);
        } 
        catch (SQLIntegrityConstraintViolationException e){
            ErrorHandler.invalid("keyExist");
            ErrorHandler.errorLogging(e,false);
            System.out.println(e);
        }
        catch (SQLException e) {
            ErrorHandler.errorLogging(e,false);
            System.out.println(e);
        }
    }
    public static void close() {
        try {
            connection.close();
        }
        catch (NullPointerException a){
            ErrorHandler.invalid("database");
            ErrorHandler.errorLogging(a,false);
            System.exit(1);
        }
        catch (SQLException e) {
            System.out.println(e);
            ErrorHandler.errorLogging(e,false);
        }
    }
}
