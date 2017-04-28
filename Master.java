package stock; // Defines the package

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.io.*;
import javax.swing.WindowConstants;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;

/**
 *
 * @author mn3458z
 */


@SuppressWarnings("LeakingThisInConstructor")


public class Master extends JFrame implements ActionListener {

    JButton check = new JButton("Check Stock");
    JButton purchase = new JButton("Purchase Item");
    JButton stock = new JButton("Update Stock");
    JButton quit = new JButton("Exit");
    
    public static void main(String[] args) {
        if(StockData.is_database == false){
            ErrorHandler.invalid("startup");
            System.exit(4);
        }
        Master master = new Master();
    }

    public Master() {
        setLayout(new BorderLayout());
        setSize(500, 100);
        setTitle("Stock Control - Main");
        // close application only by clicking the quit button
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        JPanel top = new JPanel();
        top.add(new JLabel("Select an option by clicking one of the buttons below"));
        add("North", top);
        JPanel bottom = new JPanel();
        bottom.add(check);
        check.addActionListener(this);
        bottom.add(purchase);
        purchase.addActionListener(this);
        bottom.add(stock);
        stock.addActionListener(this);
        bottom.add(quit);
        quit.addActionListener(this);
        add("South", bottom);
        setResizable(false);
        setVisible(true);
        
        ButtonsTheme.BlackTheme(check);
        ButtonsTheme.BlackTheme(purchase);
        ButtonsTheme.BlackTheme(stock);
        ButtonsTheme.BlackTheme(quit);  
    }
    private void eventHandler(ActionEvent evt){
        if (evt.getSource() == check) {
            CheckStock checkStock = new CheckStock();
        } 
        else if(evt.getSource() == purchase){
            PurchaseItem purchase = new PurchaseItem(); // purchase item class constructor
        }
        else if(evt.getSource() == stock){
            if(StockData.auth == false){
                Login login = new Login();
            }
            else{
                UpdateStock update = new UpdateStock(); // update class constructor
            }
        }
        else if (evt.getSource() == quit) {
            StockData.close();
            System.exit(0);
        }    
    }
    @Override
    public void actionPerformed(ActionEvent evt) {
        try{
            eventHandler(evt);
        }
        catch(Exception error){
            ErrorHandler.errorLogging(error, true); // generates the error
        }
    }
}
