package saul.rodriguez.naranjo.practica1.motores.de.busqueda.ui.query;

import java.awt.BorderLayout;
import java.awt.Dimension;
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
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.params.HighlightParams;
import saul.rodriguez.naranjo.practica1.motores.de.busqueda.indexing.parse.CorpusFormatDocumentParser;
import saul.rodriguez.naranjo.practica1.motores.de.busqueda.ui.document.ShowDocumentPanel;
import saul.rodriguez.naranjo.practica1.motores.de.busqueda.ui.indexing.CorpusDocumentTable;
import saul.rodriguez.naranjo.practica1.motores.de.busqueda.ui.task.LoadingDialog;
import saul.rodriguez.naranjo.practica1.motores.de.busqueda.ui.worker.QueryApacheSolrCoreWorker;

/**
 *
 * @author Saúl Rodríguez Naranjo
 */
public class QueryDocumentsFromCustomQueryPanel extends JPanel
{

    private JPanel queryPanel;

    private JLabel queryLabel;
    private JTextField queryFieldTextField;
    private JLabel querySeparatorLabel;
    private JTextField queryValueTextField;

    private JLabel queryCoreLabel;
    private JTextField queryCoreTextField;

    private JButton executeCustomQueryButton;

    private CorpusDocumentTable corpusDocumentTable;

    private JPanel showMoreInformationPanel;

    private JLabel retrievedDocumentsLabel;
    private JComboBox retrievedDocumentsIdComboBox;
    private JButton showMoreInformationButton;

    private final JFrame parentFrame;

    private SolrDocumentList retrievedDocuments;

    private static final String QUERY_LABEL_TEXT = "Consulta:";
    private static final String QUERY_CORE_LABEL_TEXT = "Core:";
    private static final String QUERY_SEPARATOR_LABEL_TEXT = ":";
    private static final String EXECUTE_QUERY_BUTTON_TEXT = "Ejecutar";
    private static final String RETRIEVED_DOCUMENTS_LABEL_TEXT = "Seleccionar Documento:";
    private static final String SHOW_MORE_INFORMATION_BUTTON_TEXT = "Mostrar Documento";

    public QueryDocumentsFromCustomQueryPanel(JFrame parentFrame)
    {
        this.parentFrame = parentFrame;

        this.setLayout(new BorderLayout());
    }

    public void initializeComponents()
    {
        initializeQueryPanel();

        this.corpusDocumentTable = new CorpusDocumentTable(0, 0);
        this.corpusDocumentTable.initializeComponents();

        this.add(new JScrollPane(corpusDocumentTable), BorderLayout.CENTER);

        initializeShowMoreInformationPanel();
    }

    private void initializeQueryPanel()
    {
        this.queryPanel = new JPanel();

        this.queryLabel = new JLabel(QUERY_LABEL_TEXT);

        this.queryPanel.add(queryLabel);

        this.queryFieldTextField = new JTextField();
        this.queryFieldTextField.setPreferredSize(new Dimension(150, 30));
        this.queryFieldTextField.setMinimumSize(new Dimension(150, 30));

        this.queryPanel.add(queryFieldTextField);

        this.querySeparatorLabel = new JLabel(QUERY_SEPARATOR_LABEL_TEXT);

        this.queryPanel.add(querySeparatorLabel);

        this.queryValueTextField = new JTextField();
        this.queryValueTextField.setPreferredSize(new Dimension(150, 30));
        this.queryValueTextField.setMinimumSize(new Dimension(150, 30));

        this.queryPanel.add(queryValueTextField);

        this.queryCoreLabel = new JLabel(QUERY_CORE_LABEL_TEXT);

        this.queryPanel.add(queryCoreLabel);

        this.queryCoreTextField = new JTextField();
        this.queryCoreTextField.setPreferredSize(new Dimension(200, 30));
        this.queryCoreTextField.setMinimumSize(new Dimension(200, 30));

        this.queryPanel.add(queryCoreTextField);

        this.executeCustomQueryButton = new JButton(EXECUTE_QUERY_BUTTON_TEXT);

        this.executeCustomQueryButton.addActionListener((ActionEvent e) ->
        {
            executeCustomQueryButtonAction();
        });

        this.queryPanel.add(executeCustomQueryButton);

        this.add(queryPanel, BorderLayout.NORTH);
    }

    private void initializeShowMoreInformationPanel()
    {
        this.showMoreInformationPanel = new JPanel();

        this.retrievedDocumentsLabel = new JLabel(RETRIEVED_DOCUMENTS_LABEL_TEXT);

        this.showMoreInformationPanel.add(retrievedDocumentsLabel);

        this.retrievedDocumentsIdComboBox = new JComboBox();
        this.retrievedDocumentsIdComboBox.setPreferredSize(new Dimension(150, 30));
        this.retrievedDocumentsIdComboBox.setMinimumSize(new Dimension(150, 30));

        this.showMoreInformationPanel.add(retrievedDocumentsIdComboBox);

        this.showMoreInformationButton = new JButton(SHOW_MORE_INFORMATION_BUTTON_TEXT);

        this.showMoreInformationButton.addActionListener((ActionEvent e) ->
        {
            showMoreInformationButtonAction();
        });

        this.showMoreInformationPanel.add(showMoreInformationButton);

        this.add(showMoreInformationPanel, BorderLayout.SOUTH);
    }

    private void executeCustomQueryButtonAction()
    {
        if (validateQueryTextFields())
        {
            String fieldQueryText = this.queryFieldTextField.getText();
            String valueQueryText = this.queryValueTextField.getText();

            SolrQuery query = new SolrQuery(fieldQueryText + ":" + valueQueryText);

            System.out.println("Query: " + fieldQueryText + ":" + valueQueryText);

            query.setFields(CorpusFormatDocumentParser.SOLR_INPUT_DOCUMENT_IDENTIFIER_KEY,
                    CorpusFormatDocumentParser.SOLR_INPUT_DOCUMENT_TITLE_KEY,
                    CorpusFormatDocumentParser.SOLR_INPUT_DOCUMENT_AUTHOR_KEY,
                    CorpusFormatDocumentParser.SOLR_INPUT_DOCUMENT_TEXT_KEY,
                    HighlightParams.SCORE);

            String coreName = this.queryCoreTextField.getText();

            QueryApacheSolrCoreWorker worker = new QueryApacheSolrCoreWorker(query, coreName);

            ImageIcon loadingImage = new ImageIcon("src/main/resources/icons/loading_spinner.gif");

            Image image = loadingImage.getImage();
            Image newimg = image.getScaledInstance(50, 50, java.awt.Image.SCALE_DEFAULT);
            loadingImage = new ImageIcon(newimg);

            final LoadingDialog loadingDialog = new LoadingDialog(loadingImage,
                    "Consultando Apache Solr, por favor, espere...",
                    400, 200, parentFrame, "Consultando Apache Solr");

            SwingUtilities.invokeLater(() ->
            {
                Thread thread = new Thread(() ->
                {
                    try
                    {
                        retrievedDocuments = worker.get();

                        loadingDialog.dispose();

                        this.corpusDocumentTable.addDocuments(retrievedDocuments);

                        this.retrievedDocumentsIdComboBox.removeAllItems();

                        for (SolrDocument retrievedDocument : retrievedDocuments)
                        {
                            long documentId = (long) retrievedDocument.getFieldValue(
                                    CorpusFormatDocumentParser.SOLR_INPUT_DOCUMENT_IDENTIFIER_KEY);

                            this.retrievedDocumentsIdComboBox.addItem(documentId);
                        }

                    } catch (InterruptedException | ExecutionException ex)
                    {
                        loadingDialog.dispose();

                        Logger.getLogger(QueryDocumentsFromFilePanel.class.getName())
                                .log(Level.SEVERE, ex.getMessage(), ex);

                        JOptionPane.showMessageDialog(parentFrame,
                                "Error al consultar Apache Solr " + ex.getMessage(),
                                "Error", JOptionPane.ERROR_MESSAGE);
                    }
                });

                worker.execute();

                thread.start();

                loadingDialog.setVisible(true);
            });
        }
    }

    private boolean validateQueryTextFields()
    {
        String fieldQueryText = this.queryFieldTextField.getText();

        if (fieldQueryText == null || fieldQueryText.isBlank() || fieldQueryText.isEmpty())
        {
            SwingUtilities.invokeLater(() ->
            {
                JOptionPane.showMessageDialog(parentFrame,
                        "Por favor, introduzca un valor correcto en la consulta",
                        "Error", JOptionPane.ERROR_MESSAGE);
            });

            return false;
        }

        String valueQueryText = this.queryValueTextField.getText();

        if (valueQueryText == null || valueQueryText.isBlank() || valueQueryText.isEmpty())
        {
            SwingUtilities.invokeLater(() ->
            {
                JOptionPane.showMessageDialog(parentFrame,
                        "Por favor, introduzca un valor correcto en la consulta",
                        "Error", JOptionPane.ERROR_MESSAGE);
            });

            return false;
        }

        String queryCoreText = this.queryCoreTextField.getText();

        if (queryCoreText == null || queryCoreText.isBlank() || queryCoreText.isEmpty())
        {
            SwingUtilities.invokeLater(() ->
            {
                JOptionPane.showMessageDialog(parentFrame,
                        "Introduzca un core sobre el que consultar",
                        "Error", JOptionPane.ERROR_MESSAGE);
            });

            return false;
        }

        return true;
    }

    private void showMoreInformationButtonAction()
    {
        if (validateShowMoreInformationInput())
        {
            int documentTableIndex = this.retrievedDocumentsIdComboBox.getSelectedIndex();
            SolrDocument document = this.retrievedDocuments.get(documentTableIndex);
            
            SwingUtilities.invokeLater(() ->
            {
                JDialog showMoreInformationDialog = new JDialog();
                showMoreInformationDialog.setTitle("Mostrando documento");
                showMoreInformationDialog.setPreferredSize(new Dimension(800, 600));
                showMoreInformationDialog.setMinimumSize(new Dimension(800, 600));

                ShowDocumentPanel showDocumentPanel = new ShowDocumentPanel(parentFrame, document);

                showDocumentPanel.initializeComponents();

                showMoreInformationDialog.setContentPane(showDocumentPanel);

                showMoreInformationDialog.pack();
                showMoreInformationDialog.validate();

                showMoreInformationDialog.setLocationRelativeTo(parentFrame);
                showMoreInformationDialog.setVisible(true);
            });
        }
    }

    private boolean validateShowMoreInformationInput()
    {
        Object selectedItem = this.retrievedDocumentsIdComboBox.getSelectedItem();
        
        if(selectedItem == null)
        {
            SwingUtilities.invokeLater(() -> 
            {
                JOptionPane.showMessageDialog(parentFrame, 
                        "Por favor, seleccione un documento", 
                        "Error", JOptionPane.ERROR_MESSAGE);
            });
            
            return false;
        }
        
        return true;
    }
}
