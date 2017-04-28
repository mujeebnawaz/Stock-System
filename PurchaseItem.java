package stock; // Defines the package

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JSpinner;


import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
/**
 *
 * @author mn3458z
 */
public class PurchaseItem extends JFrame implements ActionListener {
    
    String temp = null;
    int addedQ = 0;
    ArrayList<String> items = new ArrayList<String>(4); // initiates the array list for cart
    ArrayList<String> quantityArray = new ArrayList<String>(4);
    Set<String> unique = new HashSet<String>(items);
    JLabel cart = new JLabel("Cart");
    JLabel labelKey = new JLabel("Enter Key (Required): ");
    JTextField key = new JTextField(5);
    JLabel enterQuantity = new JLabel("Enter Quantity (Required):");
    JTextField quantity = new JTextField(2);
    JTextArea result = new JTextArea(6,30);
    JTextArea basket = new JTextArea(6,38);
    JButton addToCart = new JButton("Add to cart");
    JButton search = new JButton("Search");
    JButton quit = new JButton("Exit");
    JButton reset = new JButton("Reset");
    JButton checkout = new JButton("Checkout");
    JTextPane stockImage = new JTextPane ();
      
    JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT)); // alligns the panel using FlowLayout
    JPanel center = new JPanel(new FlowLayout(FlowLayout.LEFT));   
    JPanel bottom = new JPanel(new FlowLayout(FlowLayout.LEFT));
    
    JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT));

    DecimalFormat pounds = new DecimalFormat("£#,##0.00");
    
    
    public PurchaseItem(){
        setLayout(new BorderLayout());
        setSize(475, 330);
        setTitle("Purchase Item");
        
        top.add(labelKey);
        top.add(key);
        top.add(search);
        top.add(reset);
        top.add(quit);
        center.add(result);
        center.add(stockImage);
        center.add(cart);
        center.add(basket);
        bottom.add(enterQuantity);
        bottom.add(quantity);
        bottom.add(addToCart);
        bottom.add(checkout);
        
        center.setVisible(true);
        bottom.setVisible(true);
        
        add("North",top);
        add("East",right);
        add("Center",center);
        add("South",bottom);
        
        ButtonsTheme.BlackTheme(addToCart);
        ButtonsTheme.BlackTheme(search);
        ButtonsTheme.BlackTheme(quit);
        ButtonsTheme.BlackTheme(reset);
        ButtonsTheme.BlackTheme(checkout);
        
        stockImage.insertIcon(new ImageIcon("images/d.png"));
        
        search.addActionListener(this);
        addToCart.addActionListener(this);
        quit.addActionListener(this);
        reset.addActionListener(this);
        checkout.addActionListener(this);
        setResizable(false);
        setVisible(true);  
    }    
   
    private void checkOut(ArrayList<String> cart){ // checkout method, generates the bill 
            JFrame frame = new JFrame("Checkout - Bill"); 
            JTextArea bill = new JTextArea(5,20);
            bill.setText(" Following is your Total bill: \n");
            double total = 0; // initiates the total to zero
            for(int i = 0; i < items.size(); i++){ // loop to get the items from the array
                total = total+(StockData.getPrice(items.get(i))*Integer.parseInt(quantityArray.get(i))); // calculates the total
                bill.append("\n "+quantityArray.get(i)+" x "+StockData.getName(items.get(i))+" - "+pounds.format(StockData.getPrice(items.get(i))*Integer.parseInt(quantityArray.get(i))));          
                StockData.update(items.get(i), -Integer.parseInt(quantityArray.get(i))); // subtracts the purchased quantity from the database
            }
            bill.append("\n Total: "+String.valueOf(pounds.format(total))+"\n"); // appends the total
            bill.append("\n Thank you for shopping with us");
            frame.getContentPane().add(bill,BorderLayout.CENTER);
            frame.pack();
            frame.setVisible(true);
            frame.setSize(470, 235);
            
    }
    private void eventHandler(ActionEvent evt){
       if(evt.getSource()==quit){
           dispose();
       }
       else if(evt.getSource()==search){
           String itemKey = key.getText();
           String image_url = "images/"+itemKey+".jpg"; // image path
           String verifyItem = StockData.getName(itemKey);
           if(verifyItem != null){
               bottom.setVisible(true);
               String itemName = StockData.getName(itemKey);
               double itemPrice = StockData.getPrice(itemKey);
               int itemQuantity = StockData.getQuantity(itemKey);
               result.setText("Name: " + itemName + "\nPrice: " + pounds.format(itemPrice) +"\nQuantity Available: "+itemQuantity);
               temp = itemKey;
               if(ErrorHandler.is_valid_image(image_url)){
                    stockImage.setText("");
                    stockImage.insertIcon(new ImageIcon(image_url));
                }
                else{
                    stockImage.setText("");
                    stockImage.insertIcon(new ImageIcon("images/d.png"));
                    ErrorHandler.invalid("image");     
                }
           }
           else{
               result.setText("No such item exists");
           }
       }
       else if(evt.getSource()==addToCart){
            if(key.getText().equals(""))
            {
                ErrorHandler.invalid("invalidKey");
            }
            if(ErrorHandler.is_key_exist(key.getText())){ // validates the key
                temp = key.getText();
            }
            addedQ = Integer.parseInt(quantity.getText());
            int quantityCheck = StockData.getQuantity(temp);
            if(temp != null){
               if(addedQ == 0 || addedQ < 0){ // checks if the given quantity is correct
                   ErrorHandler.invalid("quantityError"); 
               }
               else if(ErrorHandler.is_valid_quantity(quantity.getText()) == false){
                        ErrorHandler.invalid("quantityError"); 
                        addedQ = 0;
               }
               else if(quantityCheck < addedQ){ // checks if we have enough quantity available in our stock
                   ErrorHandler.invalid("lowStock");
               }
               else if((addedQ != 0 || addedQ > 0) && quantityCheck >= addedQ){
                items.add(temp);  // adds the item to the arraylist
                quantityArray.add(String.valueOf(addedQ)); // adds the quantity to the array list
               }
           }
           if(items.size() <= 5){
                    int element = 0;
                   for(int i = 0; i < items.size(); i++){
                            element = i;      
                    }
                      basket.append(addedQ+" x "+StockData.getName(items.get(element))+"\n"); // shows the added items
                 }
        }
      else if(evt.getSource() == reset){ 
          // resets all fields
          quantity.setText("");
          key.setText("");
          result.setText(" ");
          items.clear();
          basket.setText("");
          stockImage.setText("");
          stockImage.insertIcon(new ImageIcon("images/d.png"));
      }
      else if(evt.getSource() == checkout){
          checkOut(items); // passes the item list to check out, which generates the bill
      }
            
    }
    @Override
    public void actionPerformed(ActionEvent evt) {
        try{
            eventHandler(evt);
        }
        catch(NumberFormatException error){ // handles exception if someone provides with wrong quantity
            ErrorHandler.invalid("quantityError");
            ErrorHandler.errorLogging(error,false);    
        }
        catch(Exception error){
            ErrorHandler.errorLogging(error,true);
        }
    }
}
