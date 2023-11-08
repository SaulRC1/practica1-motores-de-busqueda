package saul.rodriguez.naranjo.practica1.motores.de.busqueda.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import saul.rodriguez.naranjo.practica1.motores.de.busqueda.connector.CorpusConnection;
import saul.rodriguez.naranjo.practica1.motores.de.busqueda.ui.configuration.ServerConnectionConfigurationDialog;

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
        this.getContentPane().removeAll();
        
        this.getContentPane().add(new JLabel(apacheSolrLogo, JLabel.CENTER),
                BorderLayout.CENTER);

        JLabel connectionStatus = new JLabel("Conectado a Apache Solr en "
                + CorpusConnection.getHttpSecurityProtocol() + "://"
                + CorpusConnection.getIpAddress() + ":"
                + CorpusConnection.getPort() + "/solr", JLabel.CENTER);

        this.getContentPane().add(connectionStatus, BorderLayout.SOUTH);

        this.getContentPane().validate();
        this.getContentPane().repaint();
    }

    public void initializeDisconnectedContentPane()
    {
        this.getContentPane().removeAll();
        
        this.getContentPane().add(new JLabel(apacheSolrLogo, JLabel.CENTER),
                BorderLayout.CENTER);

        JPanel connectionStatusPanel = new JPanel();

        JLabel connectionStatus = new JLabel("Desconectado de Apache Solr");

        JButton connectionButton = new JButton("Conectar");

        connectionButton.addActionListener((ActionEvent e) ->
        {
            ServerConnectionConfigurationDialog serverConnectionConfigurationDialog
                    = new ServerConnectionConfigurationDialog(this);
            
            serverConnectionConfigurationDialog.initializeComponents();
            
            serverConnectionConfigurationDialog.setVisible(true);
        });

        connectionStatusPanel.add(connectionStatus);
        connectionStatusPanel.add(connectionButton);

        this.getContentPane().add(connectionStatusPanel, BorderLayout.SOUTH);

        this.getContentPane().validate();
        this.getContentPane().repaint();
    }

}
