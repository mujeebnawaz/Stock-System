package stock; // Defines the package

import java.awt.BorderLayout; // imports the border layout library
import java.awt.event.ActionEvent; // imports the ActionEvent library
import java.awt.event.ActionListener; // imports the ActionListener Library, which contains the method definations for action listener
import java.text.DecimalFormat; // imports the DecimalFormat library
import javax.swing.JButton; // imports the JButton library
import javax.swing.JFrame; // imports the JFrame library
import javax.swing.JLabel; // imports the JLabel library
import javax.swing.JPanel; // imports the JPanel library
import javax.swing.JTextArea; // imports JTextArea library
import javax.swing.JTextField; // imports JTextField library
import javax.swing.JTextPane; // imports JTextPane library
import javax.swing.ImageIcon; // imports ImageIcon Library
/**
 *
 * @author mn3458z
 */


public class CheckStock extends JFrame implements ActionListener {
    
    /** 
     * Graphical User Interface constructors and instantiations  
    */
    
    JTextField stockNo = new JTextField(7); // instantiation of JTextField with parameter of 7
    JTextArea information = new JTextArea(6, 27); // instantiation of JTextArea with two parameters: 6 rows, 27 columns
    JButton check = new JButton("Search");  // instantiation of JButton with string parameter Search
    JButton exit = new JButton("Exit");
    JTextPane stockImage = new JTextPane();
    DecimalFormat pounds = new DecimalFormat("£#,##0.00"); // instantiation of DecimalFormat

    public CheckStock() {
        setLayout(new BorderLayout());
        setBounds(300, 100, 450, 180); //sets the position, width and height of the window
        
        /*Button Theme*/
        ButtonsTheme.BlackTheme(check);
        ButtonsTheme.BlackTheme(exit);
        
       
        setTitle("Check Stock"); // Sets title of the panel to Check Stock
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); //disables the close button
        JPanel top = new JPanel(); //initiates a Jpanel
        top.add(new JLabel("Enter Stock Number:")); // add Label to the panel
        top.add(stockNo); 
        top.add(check);
        top.add(exit);
        check.addActionListener(this); // add event listener to check
        exit.addActionListener(this);
        add("North", top);
        JPanel middle = new JPanel();
        middle.add(information);
        middle.add(stockImage);
        add("Center", middle);
        stockImage.insertIcon(new ImageIcon("images/d.png")); // Sets the default icon image
        setResizable(false);
        setVisible(true);
    }
    private void eventHandler(ActionEvent evt){
        if(evt.getSource() == exit){
            dispose();
        }
        else if(evt.getSource() == check){
            String key = stockNo.getText();
            String name = StockData.getName(key);
            String image_url = "images/"+key+".jpg";  // Prepares the image path using concatenation
            if (name == null){ // checks if key exists
                information.setText("No such item in stock");
                stockImage.setText("");
                stockImage.insertIcon(new ImageIcon("images/d.png"));
            } 
            else{
                stockImage.setText("");
                information.setText(name);
                information.append("\nPrice: " + pounds.format(StockData.getPrice(key)));
                information.append("\nNumber in stock: " + StockData.getQuantity(key));
                if(ErrorHandler.is_valid_image(image_url)){ // image size is valid shows the image
                    stockImage.insertIcon(new ImageIcon(image_url));
                }
                else{
                    stockImage.insertIcon(new ImageIcon("images/d.jpg")); // Sets the image to defaults
                    ErrorHandler.invalid("image");         // Reports the image error
                }
               
            }
        } 
    }
   
    @Override
    public void actionPerformed(ActionEvent e) {
        try{
            eventHandler(e); 
        }
        catch(Exception error){
            ErrorHandler.errorLogging(error,true); // Generates the error log
        }
    }
    }
