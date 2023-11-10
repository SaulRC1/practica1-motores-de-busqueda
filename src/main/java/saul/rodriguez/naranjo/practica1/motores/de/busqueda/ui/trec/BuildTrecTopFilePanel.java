package saul.rodriguez.naranjo.practica1.motores.de.busqueda.ui.trec;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import org.apache.solr.client.solrj.SolrQuery;
import saul.rodriguez.naranjo.practica1.motores.de.busqueda.ui.MainFrameMenuBar;
import saul.rodriguez.naranjo.practica1.motores.de.busqueda.ui.file.FileTypeFilter;
import saul.rodriguez.naranjo.practica1.motores.de.busqueda.ui.query.QueryDocumentsFromFilePanel;
import saul.rodriguez.naranjo.practica1.motores.de.busqueda.ui.query.SolrQueryTable;
import saul.rodriguez.naranjo.practica1.motores.de.busqueda.ui.task.LoadingDialog;
import saul.rodriguez.naranjo.practica1.motores.de.busqueda.ui.worker.BuildTrecTopFileWorker;
import saul.rodriguez.naranjo.practica1.motores.de.busqueda.ui.worker.LoadQueriesFromQueryFileWorker;

/**
 *
 * @author Saúl Rodríguez Naranjo
 */
public class BuildTrecTopFilePanel extends JPanel
{

    private final JFrame parentFrame;

    private JPanel queryDocumentPanel;

    private JLabel selectQueryDocumentLabel;
    private JTextField selectQueryDocumentPath;
    private JButton selectQueryDocumentButton;

    private static final String SELECT_QUERY_DOCUMENT_LABEL_TEXT = "Documento de "
            + "consultas:";
    private static final String SELECT_QUERY_DOCUMENT_BUTTON_TEXT = "Examinar";

    private JPanel trecTopFilePanel;

    private JLabel trecTopFileDocumentLabel;
    private JTextField trecTopFileDocumentPath;
    private JButton trecTopFileDocumentButton;

    private JButton buildTrecTopFileButton;

    private static final String TREC_TOP_FILE_DOCUMENT_LABEL_TEXT = "Documento "
            + "trec_top_file:";
    private static final String TREC_TOP_FILE_DOCUMENT_BUTTON_TEXT = "Examinar";
    private static final String BUILD_TREC_TOP_FILE_BUTTON_TEXT = "Construir "
            + "documento trec_top_file";

    private SolrQueryTable solrQueryTable;
    private List<SolrQuery> solrQueries;

    public BuildTrecTopFilePanel(JFrame parentFrame)
    {
        super();
        this.parentFrame = parentFrame;

        this.setLayout(new BorderLayout());
    }

    public void initializeComponents()
    {
        initializeQueryDocumentPanel();

        initializeTrecTopFilePanel();

        initializeSolrQueryTable();
    }

    private void initializeQueryDocumentPanel()
    {
        this.queryDocumentPanel = new JPanel();

        this.selectQueryDocumentLabel = new JLabel(SELECT_QUERY_DOCUMENT_LABEL_TEXT);

        this.queryDocumentPanel.add(selectQueryDocumentLabel);

        this.selectQueryDocumentPath = new JTextField();
        this.selectQueryDocumentPath.setPreferredSize(new Dimension(400, 30));
        this.selectQueryDocumentPath.setMinimumSize(new Dimension(400, 30));

        this.queryDocumentPanel.add(selectQueryDocumentPath);

        this.selectQueryDocumentButton = new JButton(SELECT_QUERY_DOCUMENT_BUTTON_TEXT);
        this.selectQueryDocumentButton.addActionListener((ActionEvent e) ->
        {
            selectQueryDocumentButtonAction();
        });

        this.queryDocumentPanel.add(selectQueryDocumentButton);

        this.add(queryDocumentPanel, BorderLayout.NORTH);
    }

    private void selectQueryDocumentButtonAction()
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
                        List<SolrQuery> fileSolrQueries
                                = loadQueriesFromQueryFileWorker.get();

                        this.solrQueries = fileSolrQueries;
                        this.solrQueryTable.addSolrQueries(fileSolrQueries);

                        SwingUtilities.invokeLater(() ->
                        {
                            loadingDialog.dispose();

                            this.selectQueryDocumentPath.setText(filePath);

                            JOptionPane.showMessageDialog(parentFrame,
                                    "Consultas cargadas correctamente",
                                    "Consultas cargadas", JOptionPane.INFORMATION_MESSAGE);
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

    private void initializeTrecTopFilePanel()
    {
        this.trecTopFilePanel = new JPanel();

        this.buildTrecTopFileButton = new JButton(BUILD_TREC_TOP_FILE_BUTTON_TEXT);

        this.buildTrecTopFileButton.addActionListener((ActionEvent e) ->
        {
            buildTrecTopFileAction();
        });

        this.trecTopFilePanel.add(buildTrecTopFileButton);

        this.add(trecTopFilePanel, BorderLayout.SOUTH);
    }

    private void initializeSolrQueryTable()
    {
        this.solrQueryTable = new SolrQueryTable();

        this.solrQueryTable.initializeComponents();

        this.add(solrQueryTable, BorderLayout.CENTER);
    }

    private void buildTrecTopFileAction()
    {
        if (validateInput())
        {
            ImageIcon loadingImage = new ImageIcon("src/main/resources/icons/loading_spinner.gif");

            Image image = loadingImage.getImage(); // transform it
            Image newimg = image.getScaledInstance(50, 50, java.awt.Image.SCALE_DEFAULT); // scale it the smooth way
            loadingImage = new ImageIcon(newimg);  // transform it back

            final LoadingDialog loadingDialog = new LoadingDialog(loadingImage,
                    "Construyendo archivo trec_top_file, por favor, espere...",
                    400, 200, parentFrame, "Construyendo trec_top_file");

            SwingUtilities.invokeLater(() ->
            {
                String filePath;

                JFileChooser fileChooser = new JFileChooser();

                fileChooser.setMultiSelectionEnabled(false);

                if (fileChooser.showSaveDialog(parentFrame) == JFileChooser.APPROVE_OPTION)
                {
                    if (fileChooser.getSelectedFile() != null)
                    {
                        filePath = fileChooser.getSelectedFile().getAbsolutePath();

                        BuildTrecTopFileWorker worker = new BuildTrecTopFileWorker(solrQueries, filePath);

                        SwingUtilities.invokeLater(() ->
                        {
                            Thread thread = new Thread(() ->
                            {
                                try
                                {
                                    worker.get();

                                    SwingUtilities.invokeLater(() ->
                                    {
                                        loadingDialog.dispose();

                                        JOptionPane.showMessageDialog(parentFrame,
                                                "Archivo trec_top_file construido correctamente en " 
                                                        + filePath,
                                                "Construcción exitosa", JOptionPane.INFORMATION_MESSAGE);
                                    });
                                    
                                } catch (InterruptedException | ExecutionException ex)
                                {
                                    Logger.getLogger(BuildTrecTopFilePanel.class.getName())
                                            .log(Level.SEVERE, ex.getMessage(), ex);

                                    SwingUtilities.invokeLater(() ->
                                    {
                                        loadingDialog.dispose();

                                        JOptionPane.showMessageDialog(parentFrame,
                                                "Error al construir el archivo trec_top_file "
                                                + ex.getMessage(),
                                                "Error", JOptionPane.ERROR_MESSAGE);
                                    });
                                }
                            });

                            worker.execute();

                            thread.start();

                            loadingDialog.setVisible(true);
                        });
                    }
                }
            });
        }
    }

    private boolean validateInput()
    {
        boolean validationResult = true;

        if (this.solrQueries == null)
        {
            SwingUtilities.invokeLater(() ->
            {
                JOptionPane.showMessageDialog(parentFrame,
                        "Por favor, cargue un documento de consultas",
                        "Error", JOptionPane.ERROR_MESSAGE);
            });

            validationResult = false;
        }

        return validationResult;
    }

}
