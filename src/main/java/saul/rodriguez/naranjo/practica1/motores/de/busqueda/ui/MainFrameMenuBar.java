package saul.rodriguez.naranjo.practica1.motores.de.busqueda.ui;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileFilter;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.common.SolrInputDocument;
import saul.rodriguez.naranjo.practica1.motores.de.busqueda.ui.configuration.ServerConnectionConfigurationDialog;
import saul.rodriguez.naranjo.practica1.motores.de.busqueda.ui.file.FileTypeFilter;
import saul.rodriguez.naranjo.practica1.motores.de.busqueda.ui.indexing.IndexCorpusDocumentsPanel;
import saul.rodriguez.naranjo.practica1.motores.de.busqueda.ui.query.QueryDocumentsFromFilePanel;
import saul.rodriguez.naranjo.practica1.motores.de.busqueda.ui.task.LoadingDialog;
import saul.rodriguez.naranjo.practica1.motores.de.busqueda.ui.trec.BuildTrecTopFilePanel;
import saul.rodriguez.naranjo.practica1.motores.de.busqueda.ui.worker.LoadDocumentsFromCorpusFileWorker;
import saul.rodriguez.naranjo.practica1.motores.de.busqueda.ui.worker.LoadQueriesFromQueryFileWorker;

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
    private static final String BUILD_TREC_TOP_FILE_MENU_ITEM_TITLE
            = "Construir archivo trec_top_file";

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

    private JMenuItem buildTrecTopFileMenuItem;

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

        this.buildTrecTopFileMenuItem = new JMenuItem(BUILD_TREC_TOP_FILE_MENU_ITEM_TITLE);

        corpusMenu = new JMenu(CORPUS_MENU_TITLE);

        corpusMenu.add(indexDocumentsSubMenu);
        corpusMenu.add(queryApacheSolrSubMenu);
        corpusMenu.add(buildTrecTopFileMenuItem);

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

        indexCorpusDocumentsMenuItem.addActionListener((ActionEvent e) ->
        {
            ImageIcon loadingImage = new ImageIcon("src/main/resources/icons/loading_spinner.gif");

            Image image = loadingImage.getImage(); // transform it
            Image newimg = image.getScaledInstance(50, 50, java.awt.Image.SCALE_DEFAULT); // scale it the smooth way
            loadingImage = new ImageIcon(newimg);  // transform it back

            final LoadingDialog loadingDialog = new LoadingDialog(loadingImage,
                    "Cargando documentos del Corpus, por favor, espere...",
                    400, 200, parentFrame, "Cargando documento del Corpus");

            SwingUtilities.invokeLater(() ->
            {
                String filePath;

                JFileChooser fileChooser = new JFileChooser();

                fileChooser.setMultiSelectionEnabled(false);

                fileChooser.setFileFilter(new FileTypeFilter(".ALL", "Documentos del Corpus (.ALL)"));

                fileChooser.showOpenDialog(parentFrame);

                if (fileChooser.getSelectedFile() != null)
                {
                    filePath = fileChooser.getSelectedFile().getAbsolutePath();

                    LoadDocumentsFromCorpusFileWorker loadDocumentsFromCorpusFileWorker
                            = new LoadDocumentsFromCorpusFileWorker(filePath);

                    loadDocumentsFromCorpusFileWorker.execute();

                    Thread thread = new Thread(() ->
                    {
                        try
                        {
                            List<SolrInputDocument> solrInputDocuments
                                    = loadDocumentsFromCorpusFileWorker.get();

                            for (SolrInputDocument solrInputDocument : solrInputDocuments)
                            {
                                System.out.println(solrInputDocument);
                            }

                            SwingUtilities.invokeLater(() ->
                            {
                                loadingDialog.dispose();

                                JOptionPane.showMessageDialog(parentFrame,
                                        "Documentos cargados correctamente",
                                        "Documentos cargados", JOptionPane.INFORMATION_MESSAGE);

                                IndexCorpusDocumentsPanel indexCorpusDocumentsPanel
                                        = new IndexCorpusDocumentsPanel(parentFrame, solrInputDocuments);

                                parentFrame.setContentPane(indexCorpusDocumentsPanel);

                                indexCorpusDocumentsPanel.initializeComponents();

                                parentFrame.validate();
                                parentFrame.repaint();

                                indexCorpusDocumentsPanel.setVisible(true);
                            });

                        } catch (InterruptedException | ExecutionException ex)
                        {
                            Logger.getLogger(MainFrameMenuBar.class.getName()).log(Level.SEVERE, null, ex);

                            SwingUtilities.invokeLater(() ->
                            {
                                loadingDialog.dispose();

                                JOptionPane.showMessageDialog(parentFrame,
                                        "Error al cargar documentos del archivo",
                                        "Error", JOptionPane.ERROR_MESSAGE);
                            });
                        }
                    });

                    thread.start();

                    loadingDialog.setVisible(true);
                }
            });
        });

        this.queryApacheSolrByQueryFileMenuItem.addActionListener((ActionEvent e) ->
        {
            queryApacheSolrByQueryFileAction();
        });

        this.buildTrecTopFileMenuItem.addActionListener((ActionEvent e) ->
        {
            buildTrecTopFileAction();
        });
    }

    private void queryApacheSolrByQueryFileAction()
    {
        ImageIcon loadingImage = new ImageIcon("src/main/resources/icons/loading_spinner.gif");

        Image image = loadingImage.getImage(); // transform it
        Image newimg = image.getScaledInstance(50, 50, java.awt.Image.SCALE_DEFAULT); // scale it the smooth way
        loadingImage = new ImageIcon(newimg);  // transform it back

        final LoadingDialog loadingDialog = new LoadingDialog(loadingImage,
                "Cargando documentos de consultas, por favor, espere...",
                400, 200, parentFrame, "Cargando documento de consultas");

        SwingUtilities.invokeLater(() ->
        {
            String filePath;

            JFileChooser fileChooser = new JFileChooser();

            fileChooser.setMultiSelectionEnabled(false);

            fileChooser.setFileFilter(new FileTypeFilter(".QRY",
                    "Documentos de consultas del Corpus (.QRY)"));

            fileChooser.showOpenDialog(parentFrame);

            if (fileChooser.getSelectedFile() != null)
            {
                filePath = fileChooser.getSelectedFile().getAbsolutePath();

                LoadQueriesFromQueryFileWorker loadQueriesFromQueryFileWorker
                        = new LoadQueriesFromQueryFileWorker(filePath);

                loadQueriesFromQueryFileWorker.execute();

                Thread thread = new Thread(() ->
                {
                    try
                    {
                        List<SolrQuery> solrQueries
                                = loadQueriesFromQueryFileWorker.get();

                        for (SolrQuery solrQuery : solrQueries)
                        {
                            System.out.println("Query: " + solrQuery);
                        }

                        SwingUtilities.invokeLater(() ->
                        {
                            loadingDialog.dispose();

                            JOptionPane.showMessageDialog(parentFrame,
                                    "Consultas cargadas correctamente",
                                    "Consultas cargadas", JOptionPane.INFORMATION_MESSAGE);

                            QueryDocumentsFromFilePanel queryDocumentsFromFilePanel
                                    = new QueryDocumentsFromFilePanel(solrQueries, parentFrame);

                            parentFrame.setContentPane(queryDocumentsFromFilePanel);

                            queryDocumentsFromFilePanel.initializeComponents();

                            parentFrame.validate();
                            parentFrame.repaint();

                            queryDocumentsFromFilePanel.setVisible(true);
                        });

                    } catch (InterruptedException | ExecutionException ex)
                    {
                        Logger.getLogger(MainFrameMenuBar.class.getName()).log(Level.SEVERE, null, ex);

                        SwingUtilities.invokeLater(() ->
                        {
                            loadingDialog.dispose();

                            JOptionPane.showMessageDialog(parentFrame,
                                    "Error al cargar consultas del archivo",
                                    "Error", JOptionPane.ERROR_MESSAGE);
                        });
                    }
                });

                thread.start();

                loadingDialog.setVisible(true);
            }
        });
    }

    private void buildTrecTopFileAction()
    {
        SwingUtilities.invokeLater(() ->
        {
            BuildTrecTopFilePanel buildTrecTopFilePanel = new BuildTrecTopFilePanel(parentFrame);

            parentFrame.setContentPane(buildTrecTopFilePanel);

            buildTrecTopFilePanel.initializeComponents();

            parentFrame.validate();
            parentFrame.repaint();

            buildTrecTopFilePanel.setVisible(true);
        });
    }
}
