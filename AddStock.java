package stock; // Defines the package

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JOptionPane;
import java.text.DecimalFormat;
import java.awt.*;

/**
 *
 * @author mn3458z
 */

public class AddStock extends JFrame implements ActionListener {
    /** 
     * Graphical User Interface constructors and instantiations  
    */
    
    JLabel newItem = new JLabel("New Item Key");
    JTextField newKey = new JTextField(5);
    JLabel newName = new JLabel("Item Name:");
    JTextField itemName = new JTextField(20);
    JLabel newQuantity = new JLabel("Item Quantity: ");
    JTextField itemQuantity = new JTextField(10);
    JLabel newPrice = new JLabel("Item Price: ");
    JTextField itemPrice = new JTextField(10);
    JButton addItem = new JButton("Add");
    JButton quit = new JButton("Exit");
    JPanel top = new JPanel();
    JPanel newStock = new JPanel(new GridLayout(5,5,10,5));
    JPanel bottom = new JPanel();
    JPanel left = new JPanel();
    JPanel right = new JPanel();
    
    public AddStock(){
        setLayout(new BorderLayout());
        setSize(400, 180); 
        setTitle("Add Stock");
        
        newStock.add(newItem);
        newStock.add(newKey);
        newStock.add(newName);
        newStock.add(itemName);
        newStock.add(newQuantity);
        newStock.add(itemQuantity);
        newStock.add(newPrice);
        newStock.add(itemPrice);
        
        newStock.add(addItem);
        newStock.add(quit);
        
        quit.addActionListener(this);
        addItem.addActionListener(this);
        
        
        /* Button Theme */
        ButtonsTheme.BlackTheme(addItem);
        ButtonsTheme.BlackTheme(quit);
        
        add("North",top);
        add("Center",newStock);
        add("South",bottom);
        add("East",right);
        add("West",left); 
        setVisible(true);
        
    }
    private void eventHandler(ActionEvent evt){
        if(evt.getSource() == quit){
            dispose();
        }
        else if(evt.getSource() == addItem){
            double price = 0; // initiates the price to zero
            String key = newKey.getText();
            String name = itemName.getText();
            String checkPrice = itemPrice.getText();
            String checkQ = itemQuantity.getText();
            DecimalFormat priceFormat = new DecimalFormat("#.00");
            if(ErrorHandler.is_key_exist(key) || key == "") // Validates the key field
            {
                ErrorHandler.invalid("keyExist"); // Error Reporting for invalid key
            }
            else if(ErrorHandler.is_valid_name(name) && ErrorHandler.is_valid_price(checkPrice) && ErrorHandler.is_valid_quantity(checkQ)){ //Validates the data fields
            price = Float.parseFloat(itemPrice.getText());
            priceFormat.format(price);
            int quantity = Integer.parseInt(itemQuantity.getText());
            StockData.addAnItem(key, name, quantity, price); // Adds the data to the database
            JOptionPane.showMessageDialog (null, name+" added succesfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
            }  
        }
    }
    
    @Override // Overriding the JFrame
    public void actionPerformed(ActionEvent evt){
        try{ 
            eventHandler(evt); // Passes in the events to the event handler functions
        }
        catch(Exception error){
            ErrorHandler.errorLogging(error,true); // Generate the error log and report the user
        }
    }
}

 