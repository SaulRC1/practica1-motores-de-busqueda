package saul.rodriguez.naranjo.practica1.motores.de.busqueda.ui.task;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 * Loading dialog intended to use when performing long running task that do not
 * need a status update.
 * 
 * @author Saúl Rodríguez Naranjo
 */
public class LoadingDialog extends JDialog 
{
    private ImageIcon loadingImage;
    private String loadingText;
    private JFrame parentFrame;
    

    public LoadingDialog(ImageIcon loadingImage, String loadingText, int width, 
            int height, JFrame parentFrame, String title)
    {
        super();
        this.loadingImage = loadingImage;
        this.loadingText = loadingText;
        this.parentFrame = parentFrame;
        
        this.setPreferredSize(new Dimension(width, height));
        this.setMinimumSize(new Dimension(width, height));
        
        this.setResizable(false);
        this.setModalityType(ModalityType.APPLICATION_MODAL);
        this.setAlwaysOnTop(true);
        this.setLocationRelativeTo(parentFrame);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setTitle(title);
        this.getContentPane().setBackground(Color.WHITE);
        
        this.add(new JLabel(loadingText, loadingImage, JLabel.CENTER));
    }
    
    
}
