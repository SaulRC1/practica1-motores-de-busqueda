package saul.rodriguez.naranjo.practica1.motores.de.busqueda.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.HeadlessException;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import saul.rodriguez.naranjo.practica1.motores.de.busqueda.connector.CorpusConnection;

/**
 * The main frame for the Corpus app.
 * 
 * @author Saúl Rodríguez Naranjo
 */
public class MainFrame extends JFrame
{
    private static final String CORPUS_APP_TITLE = "Corpus Application";
    private static final int MINIMUM_WINDOW_HEIGHT = 600;
    private static final int MINIMUM_WINDOW_WIDTH = 800;
    
    private MainFrameMenuBar mainFrameMenuBar;
    
    private ImageIcon apacheSolrLogo;

    public MainFrame() throws HeadlessException
    {
        this.getContentPane().setLayout(new BorderLayout());
        this.setTitle(CORPUS_APP_TITLE);        
        this.setAlwaysOnTop(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setPreferredSize(new Dimension(MINIMUM_WINDOW_WIDTH, MINIMUM_WINDOW_HEIGHT));
        this.setMinimumSize(new Dimension(MINIMUM_WINDOW_WIDTH, MINIMUM_WINDOW_HEIGHT));
    }
    
    /**
     * This method will initialize all this frame subcomponents.
     */
    public void initComponents()
    {
        mainFrameMenuBar = new MainFrameMenuBar(this);
        mainFrameMenuBar.initComponents();
        this.setJMenuBar(mainFrameMenuBar);
        
        apacheSolrLogo = new ImageIcon("src/main/resources/icons/Apache_Solr_Logo.png");
        
    }
    
    public void initializeConnectedContentPane()
    {
        this.getContentPane().add(new JLabel(apacheSolrLogo, JLabel.CENTER), 
                BorderLayout.CENTER);
        
        JLabel connectionStatus = new JLabel("Conectado a Apache Solr en " +
                CorpusConnection.getHttpSecurityProtocol() + "://" + 
                CorpusConnection.getIpAddress() + ":" +
                CorpusConnection.getPort() + "/solr");
        
        this.getContentPane().add(connectionStatus, BorderLayout.SOUTH);
        
        this.getContentPane().validate();
        this.getContentPane().repaint();
    }
    
    public void initializeDisconnectedContentPane()
    {
        this.getContentPane().add(new JLabel(apacheSolrLogo, JLabel.CENTER),
                BorderLayout.CENTER);
        
        JLabel connectionStatus = new JLabel("Desconectado de Apache Solr");
        
        this.getContentPane().add(connectionStatus, BorderLayout.SOUTH);
        
        this.getContentPane().validate();
        this.getContentPane().repaint();
    }
    
}
