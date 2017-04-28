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
import java.awt.GridLayout;

/**
 *
 * @author mn3458z
 */

public class EditItem extends JFrame implements ActionListener {
    /** 
     * Graphical User Interface constructors and instantiations  
    */
    
    
    JLabel searchKey = new JLabel("Key (Required): ");
    JTextField key = new JTextField(5);
    JLabel newName = new JLabel("New Name (Required):");
    JTextField itemName = new JTextField(20);
    JLabel newPrice = new JLabel("New Price (Required): ");
    JTextField itemPrice = new JTextField(10);
    JButton updateItem = new JButton("Update");
    JButton quit = new JButton("Exit");
    JPanel top = new JPanel();
    JPanel edit = new JPanel(new GridLayout(5,5,10,5));
    JPanel bottom = new JPanel();
    JPanel left = new JPanel();
    JPanel right = new JPanel();
    
    public EditItem(){
        setLayout(new BorderLayout());
        setSize(400, 180);
        setTitle("Edit Item");
        
        edit.add(searchKey);
        edit.add(key);
        edit.add(newName);
        edit.add(itemName);
        edit.add(newPrice);
        edit.add(itemPrice);
        
        edit.add(updateItem);
        edit.add(quit);
        
        quit.addActionListener(this);
        updateItem.addActionListener(this);
        
        
        /*Button Theme*/
        ButtonsTheme.BlackTheme(quit);
        ButtonsTheme.BlackTheme(updateItem);
        
        add("North",top);
        add("Center",edit);
        add("South",bottom);
        add("East",right);
        add("West",left); 
        setVisible(true);
        
    }
    private void eventHandler(ActionEvent evt){
        if(evt.getSource() == quit){
            dispose();
        }
        else if(evt.getSource() == updateItem){
            double price = 0;
            String checkKey = key.getText();
            String name = itemName.getText();
            String newprice = itemPrice.getText();
            DecimalFormat priceFormat = new DecimalFormat("#.00");
            if(ErrorHandler.is_key_exist(checkKey)){
                if(ErrorHandler.is_valid_price(newprice) && ErrorHandler.is_valid_name(name)){ // validates the input
                    price = Double.parseDouble(newprice);
                    priceFormat.format(price);
                    StockData.updateDetail(checkKey, name, price); // update detail with given parameters
                    JOptionPane.showMessageDialog (null, "Item Updated", "Success", JOptionPane.INFORMATION_MESSAGE);
                }
            }
            else{
                ErrorHandler.invalid("invalidKey");
            }
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

 