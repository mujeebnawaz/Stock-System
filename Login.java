package stock; // Defines the package

import java.awt.BorderLayout;
import javax.swing.BorderFactory;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import java.awt.*;

/**
 *
 * @author mn3458z
 */

public class Login extends JFrame implements ActionListener {
    JLabel username = new JLabel("Username: ");
    JLabel password = new JLabel("Password: ");
    JLabel empt = new JLabel("");
    JTextField user = new JTextField(10);
    JTextField pass = new JTextField(10);
    JButton login = new JButton("Login");
    JButton exit = new JButton("Exit");
    JCheckBox rememberMe = new JCheckBox("Remember Me");
    JPanel LoginSection = new JPanel(new GridLayout(4,1,3,3));
    JPanel bottom = new JPanel();
    JPanel top = new JPanel();
    JPanel left = new JPanel();
    JPanel right = new JPanel();
    
    public Login() {
        setLayout(new BorderLayout());
        setSize(350, 165);
        setTitle("Login");
        LoginSection.add(username);
        LoginSection.add(user);
        LoginSection.add(password);
        LoginSection.add(pass);
        LoginSection.add(rememberMe);
        LoginSection.add(empt);
        LoginSection.add(login);
        LoginSection.add(exit);
        add("East",left);
        add("West",right);
        add("North",top);
        add("Center",LoginSection);
        add("South",bottom);
        
        
        /*Button Theme*/
        ButtonsTheme.BlackTheme(login);
        ButtonsTheme.BlackTheme(exit);
        
        login.addActionListener(this);
        rememberMe.addActionListener(this);
        exit.addActionListener(this);
        setVisible(true);   
    }
    private void eventHandler(ActionEvent evt){
        String username = user.getText();
        String password = pass.getText();
        
        if(evt.getSource() == login && rememberMe.isSelected()){ // checks if remember me is selected
            if(username.equals(StockData.username) && password.equals(StockData.password)) // checks if username and password is correct
            {
                StockData.auth = true; //if remember me is selected, authorizes the user for long time
                dispose();
                UpdateStock updateStock = new UpdateStock();
            }
            else{
                ErrorHandler.invalid("login");
            }
        }
        else if(evt.getSource() == login){
            if(username.equals(StockData.username) && password.equals(StockData.password)){
                dispose();
                UpdateStock a = new UpdateStock();
            }
            else{
                final JPanel panel = new JPanel();
                JOptionPane.showMessageDialog(panel, "Invalid Username or Password", "Error", JOptionPane.ERROR_MESSAGE); //reports the error
            }
        }
        else if(evt.getSource() == exit){
            dispose();
        }
    }
    @Override
    public void actionPerformed(ActionEvent evt) {
        try{
            eventHandler(evt);
        }
        catch(Exception error){
            ErrorHandler.errorLogging(error, true); // generates the error log
        }
    }
}
