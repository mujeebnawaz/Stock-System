package stock;// Defines the package

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import javax.swing.*;
import javax.swing.JRadioButton;
import java.awt.event.ActionEvent;

/**
 *
 * @author mn3458z
 */
public class UpdateStock extends JFrame implements ActionListener{
    
    JButton editStock = new JButton("Edit Quantity");
    JLabel editStockLabel = new JLabel("Add or Subtract the quantity of an item");
    JButton addStock = new JButton("Add Stock");
    JLabel addStockLabel = new JLabel("Add items to your stock database");
    JButton editItem = new JButton("Edit Item");
    JLabel editItemLabel = new JLabel("Edit name or price of existing item");
    JButton quit = new JButton("Exit");
    JLabel quitLabel = new JLabel("Exit the window");
    JButton logout = new JButton("Logout");
    JLabel loggedIn = new JLabel("Logged in as "+StockData.username);
    JPanel top = new JPanel();
    JPanel center = new JPanel(new GridLayout(4,2,10,10));
    JPanel bottom = new JPanel();
    JPanel right = new JPanel();
    JPanel left = new JPanel();
    
    

    
    public UpdateStock(){
        setLayout(new BorderLayout());
        setSize(450, 250);
        setTitle("Update Stock");
        
        ButtonsTheme.BlackTheme(editStock);
        ButtonsTheme.BlackTheme(addStock);
        ButtonsTheme.BlackTheme(editItem);
        ButtonsTheme.BlackTheme(quit);
        ButtonsTheme.BlackTheme(logout);
        
        center.add(addStock);
        center.add(addStockLabel);
        center.add(editItem);
        center.add(editItemLabel);
        center.add(editStock);
        center.add(editStockLabel);
        center.add(quit);
        center.add(quitLabel);
        bottom.add(loggedIn);
        bottom.add(logout);
        
       
        editStock.addActionListener(this);
        addStock.addActionListener(this);
        editItem.addActionListener(this);
        quit.addActionListener(this);
        logout.addActionListener(this);
        setVisible(true);
        bottom.setVisible(false);
        add("North",top);
        add("Center",center);
        add("East",right);
        add("West",left); 
        add("South",bottom);
        
        if(StockData.auth == true){
             bottom.setVisible(true);
         }
    }
    private void eventHandler(ActionEvent evt){
        if(evt.getSource() == quit){
            dispose();
        }
        else if(evt.getSource() == logout && StockData.auth == true){
            StockData.auth = false;
            dispose();
        }
        else if(evt.getSource() == addStock){
            AddStock addStock = new AddStock();
        }
        else if(evt.getSource() == editItem){
            EditItem editItem = new EditItem();
        }
        else if(evt.getSource() == editStock){      
            EditQuantity edit = new EditQuantity();
        }
    }
    @Override
    public void actionPerformed(ActionEvent evt){
        try{
            eventHandler(evt);
        }
        catch(Exception error){
            ErrorHandler.errorLogging(error,true);
        }
    }
}