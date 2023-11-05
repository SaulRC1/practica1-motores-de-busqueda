package saul.rodriguez.naranjo.practica1.motores.de.busqueda.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.SwingUtilities;
import saul.rodriguez.naranjo.practica1.motores.de.busqueda.ui.configuration.ServerConnectionConfigurationDialog;

/**
 *
 * @author Saúl Rodríguez Naranjo
 */
public class MainFrameMenuBar extends JMenuBar
{

    private static final String CORPUS_MENU_TITLE = "Utilidades corpus";
    private static final String CONFIGURATION_MENU_TITLE = "Configuración";
    private static final String INDEX_DOCUMENTS_SUB_MENU_TITLE = "Indexar documentos";
    private static final String INDEX_SINGLE_DOCUMENTS_MENU_ITEM_TITLE
            = "Indexar un único documento";
    private static final String INDEX_CORPUS_DOCUMENTS_MENU_ITEM_TITLE
            = "Indexar documentos mediante archivo .ALL";
    private static final String QUERY_APACHE_SOLR_SUB_MENU_TITLE
            = "Consultar Apache Solr";
    private static final String CUSTOM_QUERY_APACHE_SOLR_MENU_ITEM_TITLE
            = "Consulta personalizada";
    private static final String QUERY_APACHE_SOLR_BY_QUERY_FILE_MENU_ITEM_TITLE
            = "Consulta mediante archivo .QRY";
    private static final String SERVER_CONNECTION_PARAMETERS_MENU_ITEM_TITLE
            = "Conexión a Apache Solr";

    private final JFrame parentFrame;

    private JMenu configurationMenu;

    private JMenuItem serverConnectionParametersMenuItem;

    private JMenu corpusMenu;

    private JMenu indexDocumentsSubMenu;

    private JMenuItem indexSingleDocumentMenuItem;
    private JMenuItem indexCorpusDocumentsMenuItem;

    private JMenu queryApacheSolrSubMenu;

    private JMenuItem customQueryApacheSolrMenuItem;
    private JMenuItem queryApacheSolrByQueryFileMenuItem;

    public MainFrameMenuBar(JFrame parentFrame)
    {
        super();
        this.parentFrame = parentFrame;
    }

    public void initComponents()
    {
        indexDocumentsSubMenu = new JMenu(INDEX_DOCUMENTS_SUB_MENU_TITLE);

        indexSingleDocumentMenuItem = new JMenuItem(INDEX_SINGLE_DOCUMENTS_MENU_ITEM_TITLE);
        indexCorpusDocumentsMenuItem = new JMenuItem(INDEX_CORPUS_DOCUMENTS_MENU_ITEM_TITLE);

        indexDocumentsSubMenu.add(indexSingleDocumentMenuItem);
        indexDocumentsSubMenu.add(indexCorpusDocumentsMenuItem);

        queryApacheSolrSubMenu = new JMenu(QUERY_APACHE_SOLR_SUB_MENU_TITLE);

        customQueryApacheSolrMenuItem = new JMenuItem(CUSTOM_QUERY_APACHE_SOLR_MENU_ITEM_TITLE);
        queryApacheSolrByQueryFileMenuItem = new JMenuItem(QUERY_APACHE_SOLR_BY_QUERY_FILE_MENU_ITEM_TITLE);

        queryApacheSolrSubMenu.add(customQueryApacheSolrMenuItem);
        queryApacheSolrSubMenu.add(queryApacheSolrByQueryFileMenuItem);

        corpusMenu = new JMenu(CORPUS_MENU_TITLE);

        corpusMenu.add(indexDocumentsSubMenu);
        corpusMenu.add(queryApacheSolrSubMenu);

        this.add(corpusMenu);

        serverConnectionParametersMenuItem = new JMenuItem(SERVER_CONNECTION_PARAMETERS_MENU_ITEM_TITLE);

        configurationMenu = new JMenu(CONFIGURATION_MENU_TITLE);
        configurationMenu.add(serverConnectionParametersMenuItem);
        this.add(configurationMenu);

        this.initializeListeners();
    }

    private void initializeListeners()
    {
        serverConnectionParametersMenuItem.addActionListener((ActionEvent e) ->
        {
            SwingUtilities.invokeLater(() ->
            {
                ServerConnectionConfigurationDialog serverConnectionConfigurationDialog
                        = new ServerConnectionConfigurationDialog(parentFrame);

                serverConnectionConfigurationDialog.initializeComponents();

                serverConnectionConfigurationDialog.setVisible(true);
            });
        });
    }
}
