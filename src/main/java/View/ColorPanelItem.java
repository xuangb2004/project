package View;


import View.WrapLayout;
import View.WrapLayout;
import java.awt.Color;
import javax.swing.JPanel;
/**
 *
 * @author quoct
 */
public class ColorPanelItem extends JPanel{

    public ColorPanelItem() {
        setBackground(Color.WHITE);
        setLayout(new WrapLayout(WrapLayout.LEFT, 20, 20)); 
    }
    
}
