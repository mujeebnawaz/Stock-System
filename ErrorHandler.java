package stock; // Defines the package

import java.io.FileWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.*;

/**
 *
 * @author mn3458z
 */
public class ErrorHandler {
   
    public ErrorHandler(){}

    public static void invalid(String issue){
        
        String invalid = "";
        switch(issue){
            case "price":
                invalid = "Please enter a valid price, use numeric values";
                break;
            case "quantityError":    
                invalid = "Please enter a valid quantity, use numeric values";
                break;
            case "startup":
                invalid = "Please connect to the database in order to run program. \nProgram will exit.";
                break;
            case "lowStock":
                invalid = "We are low on stock, please subtract the quantity";
                break;
            case "login":
                invalid = "Invalid Username or Password";
                break;
            case "Uncaught":
                invalid = "Uncaught Exception: Please refer to error_log. Relaunch the program.";
                break;
            case "keyExist":
                invalid = "Entered key alread exists, Please change the key and try again";
                break;
            case "database":
                invalid = "Database Error: Please see Error Log";
                break;
            case "invalidKey":
                invalid = "Please enter a valid Key";
                break;
            case "Required":
                invalid = "All fields are required";
                break;
            case "Name":
                invalid = "Invalid Name - Please use Alphabets [A - Z]";
                break;
            case "image":
                invalid = "Invalid or No Image for this product";
                break;
        }
        JPanel invalidQuantity = new JPanel();
        JOptionPane.showMessageDialog(invalidQuantity, invalid, "Error", JOptionPane.ERROR_MESSAGE);
    }
 
    public static void errorLogging(Object log, boolean q){
        try{
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss"); // Date Format constructor
            Date date = new Date(); // Stores the Date
            String timeStamp = dateFormat.format(date); // Stores the formatted date and time on variable timeStamp
            FileWriter fw = new FileWriter("error_logs.txt", true); // initiates a error_log using FileWriter constructor
            StackTraceElement[] stackTrack = Thread.currentThread().getStackTrace(); // Gets the Stack trace (Exception)
            String stringLog = log.toString(); //converts the object to string
            fw.append("\n\n["+timeStamp+"] "+stringLog+"\nJava Class: "+stackTrack[2]); // Adds the information to the file
            fw.close(); // closes the file
            JPanel uncaught = new JPanel();
            if(q == true){
                invalid("Uncaught"); //if q is true, reports the error
            }
        }
        catch(Exception error){
            System.out.println(error);
        }
    } 
    public static boolean is_key_exist(String var_key){ // validates if key exist
        String test_var = StockData.getName(var_key);  // gets the name from database and stores it on test_var
        if(test_var != null) 
        {
            return true;
        }
        else{// if database has no name associated with key, return false
            return false;
        }
    }
    public static boolean is_valid_name(String var_name){
        String trimmed = var_name.replaceAll("\\s+","");
        if((var_name.length() <= 20) && (trimmed.matches("[a-zA-Z]+"))){ // checks if entered name is just alphabets
           return true;
        }
        else{
            invalid("Name"); // reports the error
            return false;
        }
       
    }
    public static boolean is_valid_quantity(String var_quantity){
        try{
            int q = Integer.parseInt(var_quantity); // checks if the entered value is an integer
            if(var_quantity.length() >= 5 || q <= 0){ // checks if the quantity is processable
                invalid("quantityError");
                return false;
            }
            return true;
        }
        catch(Exception e){
            invalid("quantityError"); // reports the error
            return false;
        }
    }
    public static boolean is_valid_price(String var_price){
        try{    
            Double p = Double.parseDouble(var_price); // checks if the price is decimal
            if(p < 0){ //checks if the price is not a negative value
                invalid("price");
                return false;
            }
            return true;
        }
        catch(NumberFormatException v){
            errorLogging(v,false);
            invalid("price");
            return false;
        }
    }
    public static boolean is_valid_image(String path){
            BufferedImage img = null;
            int width = 0;
            int height = 0;
        try {
            img = ImageIO.read(new File(path));
            width = img.getWidth();
            height = img.getHeight();
        }       
        catch (Exception e) {
            System.out.println(e);
        }
        return width == 92 && height == 92; // checks if width and height of the image is 92
        
    }

            
    
}
