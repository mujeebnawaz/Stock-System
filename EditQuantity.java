package stock; // Defines the package


import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 *
 * @author mn3458z
 */
public class EditQuantity extends JFrame implements ActionListener{
    /** 
     * Graphical User Interface constructors and instantiations  
    */
    
    String[] operation = {"Add", "Subtract" }; // creates an array
    JComboBox operationBox = new JComboBox(operation);
    JLabel itemKey = new JLabel("Item Number: ");
    JLabel newQuantity = new JLabel("Number of new items: ");
    JTextField key = new JTextField(4);
    JTextField updateVal = new JTextField(4);
    JTextArea result = new JTextArea(4,40);
    JButton updateBtn = new JButton("Update");
    JButton quit = new JButton("Exit");
    JPanel top = new JPanel();
    JPanel center = new JPanel();
    JPanel bottom = new JPanel(new FlowLayout(FlowLayout.LEFT));
    
  
    public EditQuantity(){
        setLayout(new BorderLayout());
        setSize(490, 150);
        setTitle("Edit Quantity");
        setVisible(true);   
     
        /*Button Theme*/
           ButtonsTheme.BlackTheme(updateBtn);
           ButtonsTheme.BlackTheme(quit);
     

        
        top.add(itemKey);
        top.add(key);
        top.add(newQuantity);
        
        top.add(updateVal);
        top.add(operationBox);
        updateBtn.addActionListener(this);
        quit.addActionListener(this);
        
        center.add(result);
        
        bottom.add(updateBtn);
        bottom.add(quit);
        
        top.setVisible(true);
        bottom.setVisible(true);
        
        operationBox.addActionListener(this);
        
        add("North",top);
        add("Center",center);
        add("South",bottom);
        
    }

    private void eventHandler(ActionEvent a){
       // JComboBox todo = (JComboBox)a.getSource();
        String operationPerform = String.valueOf(operationBox.getSelectedItem());
        if(a.getSource()==quit){
            dispose(); //exits the window
        }
        
        else if(a.getSource() == updateBtn){
            String fKey = key.getText(); 
            String check = StockData.getName(fKey);
            String quantityValidation = updateVal.getText();
            int updateV = 0;
            if(ErrorHandler.is_key_exist(fKey)){
                if(ErrorHandler.is_valid_quantity(quantityValidation)){
                    updateV = Integer.parseInt(updateVal.getText());
                    if(operationPerform == "Add"){
                        StockData.update(fKey, updateV); // updates the value with positive quantity, Add
                    }
                    else if(operationPerform == "Subtract"){
                        StockData.update(fKey, -updateV); // updates the value with negative quantity, Subtracts
                    }
                    int updated = StockData.getQuantity(fKey);
                    result.setText("New value for "+check+" is "+updated); // reports the update value
                }     
            }
            else{
                ErrorHandler.invalid("invalidKey"); // reports the invalid key error
            }
        }
    }
    
@Override
    public void actionPerformed(ActionEvent e) {
        try{
            eventHandler(e);
        }
        catch(Exception error){
            ErrorHandler.errorLogging(error,true); // generates the error log
        }
    }    
}
