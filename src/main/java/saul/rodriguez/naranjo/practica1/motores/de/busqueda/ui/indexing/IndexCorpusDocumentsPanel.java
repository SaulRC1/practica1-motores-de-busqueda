package saul.rodriguez.naranjo.practica1.motores.de.busqueda.ui.indexing;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrInputDocument;
import saul.rodriguez.naranjo.practica1.motores.de.busqueda.ui.task.LoadingDialog;
import saul.rodriguez.naranjo.practica1.motores.de.busqueda.ui.worker.IndexSolrInputDocumentsWorker;

/**
 *
 * @author Saúl Rodríguez Naranjo
 */
public class IndexCorpusDocumentsPanel extends JPanel
{

    private final JFrame parentFrame;

    private CorpusDocumentTable corpusDocumentsTable;
    private List<SolrInputDocument> solrInputDocuments;

    private JButton indexButton;
    private static final String INDEX_BUTTON_TEXT = "Indexar Documentos";

    private JPanel indexPanel;

    private JLabel indexToCoreLabel;
    private JTextField indexToCoreTextField;

    private static final String INDEX_TO_CORE_LABEL_TEXT = "Indexar al core:";

    public IndexCorpusDocumentsPanel(JFrame parentFrame, List<SolrInputDocument> solrInputDocuments)
    {
        this.parentFrame = parentFrame;
        this.solrInputDocuments = solrInputDocuments;
        this.setLayout(new BorderLayout());
    }

    public void initializeComponents()
    {
        corpusDocumentsTable = new CorpusDocumentTable(400, 400);
        corpusDocumentsTable.initializeComponents();

        corpusDocumentsTable.addDocuments(solrInputDocuments);

        this.add(new JScrollPane(corpusDocumentsTable), BorderLayout.CENTER);

        this.indexPanel = new JPanel();

        this.indexToCoreLabel = new JLabel(INDEX_TO_CORE_LABEL_TEXT);
        this.indexToCoreTextField = new JTextField();
        this.indexToCoreTextField.setPreferredSize(new Dimension(200, 30));

        this.indexButton = new JButton(INDEX_BUTTON_TEXT);

        this.indexPanel.add(indexToCoreLabel);
        this.indexPanel.add(indexToCoreTextField);
        this.indexPanel.add(indexButton);

        this.add(indexPanel, BorderLayout.SOUTH);

        initializeListeners();
    }

    private void initializeListeners()
    {
        indexButton.addActionListener((ActionEvent e) ->
        {
            String coreName = this.indexToCoreTextField.getText();

            if (coreName == null || coreName.isBlank() || coreName.isEmpty())
            {
                SwingUtilities.invokeLater(() ->
                {
                    JOptionPane.showMessageDialog(parentFrame,
                            "Por favor, ingrese un nombre de core válido.",
                            "Indexación fallida",
                            JOptionPane.ERROR_MESSAGE);
                });

                return;
            }

            IndexSolrInputDocumentsWorker worker
                    = new IndexSolrInputDocumentsWorker(solrInputDocuments, coreName);

            ImageIcon loadingImage = new ImageIcon("src/main/resources/icons/loading_spinner.gif");

            Image image = loadingImage.getImage();
            Image newimg = image.getScaledInstance(50, 50, java.awt.Image.SCALE_DEFAULT);
            loadingImage = new ImageIcon(newimg);

            final LoadingDialog loadingDialog = new LoadingDialog(loadingImage,
                    "Indexando documentos, por favor, espere...",
                    400, 200, parentFrame, "Indexando documentos");

            SwingUtilities.invokeLater(() ->
            {
                worker.execute();

                Thread thread = new Thread(() ->
                {
                    try
                    {
                        UpdateResponse updateResponse = worker.get();

                        SwingUtilities.invokeLater(() ->
                        {
                            loadingDialog.dispose();

                            if (updateResponse.getStatus() != 0)
                            {
                                JOptionPane.showMessageDialog(parentFrame,
                                        "Indexación fallida, ha ocurrido un error en Apache Solr.",
                                        "Indexación fallida",
                                        JOptionPane.ERROR_MESSAGE);
                            } else
                            {
                                JOptionPane.showMessageDialog(parentFrame,
                                        "Documentos indexados correctamente.",
                                        "Indexación satisfactoria",
                                        JOptionPane.INFORMATION_MESSAGE);
                            }
                        });

                    } catch (InterruptedException | ExecutionException ex)
                    {
                        Logger.getLogger(IndexCorpusDocumentsPanel.class.getName())
                                .log(Level.SEVERE, ex.getMessage(), ex);

                        SwingUtilities.invokeLater(() ->
                        {
                            loadingDialog.dispose();

                            JOptionPane.showMessageDialog(parentFrame,
                                    "Indexación fallida " + ex.getMessage(),
                                    "Indexación fallida",
                                    JOptionPane.ERROR_MESSAGE);
                        });
                    }
                });

                thread.start();

                loadingDialog.setVisible(true);
            });
        });
    }
}
