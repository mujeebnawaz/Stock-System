package stock; // Defines the package

//Libraries for graphical manipulation
import java.awt.Color;
import javax.swing.JButton; 


/**
 *
 * @author mn3458z
 */
public class ButtonsTheme {
    public ButtonsTheme(){
    
    }
    public static void BlackTheme(JButton button){ // Public Method for Black Theme, can be used by other clases
        button.setBackground(Color.decode("#333333")); // Defines the background color of a given button
        button.setOpaque(true); // Sets the button to Opaque
        button.setForeground(Color.white); // Sets the font color of the button
        button.setFocusPainted(false); // Disables the focus effect
        button.setBorderPainted(false); // Disables the border
    }
}
