package saul.rodriguez.naranjo.practica1.motores.de.busqueda.ui.configuration;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import saul.rodriguez.naranjo.practica1.motores.de.busqueda.Practica1MotoresDeBusqueda;
import saul.rodriguez.naranjo.practica1.motores.de.busqueda.connector.CorpusConnection;
import saul.rodriguez.naranjo.practica1.motores.de.busqueda.connector.CorpusSolrConnector;
import saul.rodriguez.naranjo.practica1.motores.de.busqueda.ui.worker.ApacheSolrConnectionWorker;
import saul.rodriguez.naranjo.practica1.motores.de.busqueda.ui.task.LoadingDialog;

/**
 *
 * @author Saúl Rodríguez Naranjo
 */
public class ServerConnectionConfigurationDialog extends JDialog
{

    private static final String SERVER_CONNECTION_CONFIGURATION_DIALOG_TITLE
            = "Configurar conexión a Apache Solr";

    private static final int MARGIN = 20;
    private static final int INTER_COMPONENT_MARGIN = 10;

    private static final String HTTP_SECURITY_PROTOCOL_LABEL_TEXT = "Protocolo HTTP";
    private static final String HTTP_SECURITY_PROTOCOL_HTTP_TEXT = "http";
    private static final String HTTP_SECURITY_PROTOCOL_HTTPS_TEXT = "https";

    private static final String IP_ADDRESS_LABEL_TEXT = "Dirección IP";

    private static final String PORT_LABEL_TEXT = "Puerto";

    private static final int MIN_WIDTH = 300;
    private static final int MIN_HEIGHT = 350;

    private JLabel httpSecurityProtocolLabel;
    private JComboBox httpSecurityProtocolComboBox;

    private JLabel ipAddressLabel;
    private JTextField ipAddressTextField;

    private JLabel portLabel;
    private JTextField portTextField;

    private JButton acceptButton;
    private JButton cancelButton;

    public ServerConnectionConfigurationDialog(Frame owner)
    {
        super(owner);
        this.setTitle(SERVER_CONNECTION_CONFIGURATION_DIALOG_TITLE);
        this.setPreferredSize(new Dimension(MIN_WIDTH, MIN_HEIGHT));
        this.setMinimumSize(new Dimension(MIN_WIDTH, MIN_HEIGHT));
        this.setResizable(false);
        this.setLocationRelativeTo(owner);
        this.setModalityType(ModalityType.APPLICATION_MODAL);
        this.setLayout(null);
    }

    public void initializeComponents()
    {
        httpSecurityProtocolLabel = new JLabel(HTTP_SECURITY_PROTOCOL_LABEL_TEXT);

        httpSecurityProtocolLabel.setBounds(MARGIN, MARGIN, 200, 20);

        this.add(httpSecurityProtocolLabel);

        httpSecurityProtocolComboBox = new JComboBox(new Object[]
        {
            HTTP_SECURITY_PROTOCOL_HTTP_TEXT, HTTP_SECURITY_PROTOCOL_HTTPS_TEXT
        });

        httpSecurityProtocolComboBox.setBounds(MARGIN,
                (INTER_COMPONENT_MARGIN + httpSecurityProtocolLabel.getY()
                + httpSecurityProtocolLabel.getHeight()), 200, 30);

        httpSecurityProtocolComboBox.setSelectedItem(CorpusConnection.getHttpSecurityProtocol());

        this.add(httpSecurityProtocolComboBox);

        ipAddressLabel = new JLabel(IP_ADDRESS_LABEL_TEXT);
        ipAddressLabel.setBounds(MARGIN,
                (INTER_COMPONENT_MARGIN + httpSecurityProtocolComboBox.getY()
                + httpSecurityProtocolComboBox.getHeight()), 200, 20);

        this.add(ipAddressLabel);

        ipAddressTextField = new JTextField(CorpusConnection.getIpAddress());
        ipAddressTextField.setBounds(MARGIN,
                (INTER_COMPONENT_MARGIN + ipAddressLabel.getY()
                + ipAddressLabel.getHeight()), 200, 30);

        this.add(ipAddressTextField);

        portLabel = new JLabel(PORT_LABEL_TEXT);
        portLabel.setBounds(MARGIN,
                (INTER_COMPONENT_MARGIN + ipAddressTextField.getY()
                + ipAddressTextField.getHeight()), 200, 20);

        this.add(portLabel);

        portTextField = new JTextField(CorpusConnection.getPort());
        portTextField.setBounds(MARGIN,
                (INTER_COMPONENT_MARGIN + portLabel.getY()
                + portLabel.getHeight()), 200, 30);

        this.add(portTextField);

        acceptButton = new JButton("Aceptar");
        acceptButton.setBounds(MARGIN, (INTER_COMPONENT_MARGIN + portTextField.getY()
                + portTextField.getHeight()), 100, 30);

        this.add(acceptButton);

        cancelButton = new JButton("Cancelar");
        cancelButton.setBounds((acceptButton.getX() + acceptButton.getWidth() + INTER_COMPONENT_MARGIN),
                acceptButton.getY(), 100, 30);

        this.add(cancelButton);

        initializeListeners();
    }

    private void initializeListeners()
    {
        acceptButton.addActionListener((ActionEvent e) ->
        {
            SwingUtilities.invokeLater(() ->
            {
                this.validateData();
                
                CorpusConnection.setHttpSecurityProtocol((String) this.httpSecurityProtocolComboBox.getSelectedItem());
                CorpusConnection.setIpAddress(this.ipAddressTextField.getText());
                CorpusConnection.setPort(this.portTextField.getText());
                
                SwingWorker worker = new ApacheSolrConnectionWorker(CorpusConnection.getHttpSecurityProtocol(),
                CorpusConnection.getIpAddress(), CorpusConnection.getPort());

                ImageIcon loadingImage = new ImageIcon("src/main/resources/icons/loading_spinner.gif");

                Image image = loadingImage.getImage(); // transform it
                Image newimg = image.getScaledInstance(50, 50, java.awt.Image.SCALE_DEFAULT); // scale it the smooth way
                loadingImage = new ImageIcon(newimg);  // transform it back

                LoadingDialog loadingDialog = new LoadingDialog(loadingImage,
                        "Conectándose a Apache Solr, por favor espere...", 400, 200, (JFrame) this.getOwner(),
                        "Conectándose a Apache Solr");

                worker.execute();

                Thread thread = new Thread(() ->
                {
                    if (!SwingUtilities.isEventDispatchThread())
                    {
                        try
                        {
                            Thread.sleep(2000);

                            CorpusSolrConnector corpusSolrConnector = (CorpusSolrConnector) worker.get();

                            CorpusConnection.setCorpusSolrConnector(corpusSolrConnector);
                            CorpusConnection.setValidConnection(true);

                            SwingUtilities.invokeLater(() ->
                            {
                                loadingDialog.dispose();

                                JOptionPane.showMessageDialog((JFrame) this.getOwner(),
                                        "Conectado a Apache Solr correctamente", "Conectado", JOptionPane.INFORMATION_MESSAGE);
                            });

                        } catch (InterruptedException | ExecutionException ex)
                        {
                            Logger.getLogger(ServerConnectionConfigurationDialog.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);

                            CorpusConnection.setValidConnection(false);
                            
                            SwingUtilities.invokeLater(() ->
                            {
                                loadingDialog.dispose();

                                JOptionPane.showMessageDialog((JFrame) this.getOwner(), "No se ha podido conectar a Apache Solr.",
                                        "Error en la conexión", JOptionPane.ERROR_MESSAGE);
                            });
                        }
                    }
                });

                thread.start();
                
                loadingDialog.setVisible(true);
            });
        });

        cancelButton.addActionListener((ActionEvent e) ->
        {
            SwingUtilities.invokeLater(() ->
            {
                this.dispose();
            });
        });
    }

    private void validateData()
    {

    }

}
