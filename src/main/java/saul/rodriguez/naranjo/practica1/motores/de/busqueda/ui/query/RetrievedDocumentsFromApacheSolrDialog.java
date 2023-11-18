package saul.rodriguez.naranjo.practica1.motores.de.busqueda.ui.query;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import saul.rodriguez.naranjo.practica1.motores.de.busqueda.indexing.parse.CorpusFormatDocumentParser;
import saul.rodriguez.naranjo.practica1.motores.de.busqueda.ui.document.ShowDocumentPanel;
import saul.rodriguez.naranjo.practica1.motores.de.busqueda.ui.indexing.CorpusDocumentTable;

/**
 *
 * @author Saúl Rodríguez Naranjo
 */
public class RetrievedDocumentsFromApacheSolrDialog extends JDialog
{

    private final SolrQuery executedQuery;
    private final String queriedCore;
    private final SolrDocumentList retrievedDocuments;

    private JPanel queryInformationPanel;

    private JLabel executedQueryLabel;
    private JTextArea executedQueryTextArea;
    private JLabel queriedCoreLabel;

    private CorpusDocumentTable corpusDocumentTable;

    private JPanel showMoreInformationPanel;

    private JLabel selectRetrievedDocumentLabel;
    private JComboBox retrievedDocumentsIdComboBox;
    private JButton showMoreInformationButton;

    private static final String EXECUTED_QUERY_LABEL_TEXT = "Consulta ejecutada:";
    private static final String QUERIED_CORE_LABEL_TEXT = "Core consultado:";
    private static final String SELECT_RETRIEVED_DOCUMENT_LABEL_TEXT = "Seleccionar Documento:";
    private static final String SHOW_MORE_INFORMATION_BUTTON_TEXT = "Mostrar Documento";

    public RetrievedDocumentsFromApacheSolrDialog(Frame owner, SolrQuery executedQuery,
            String queriedCore, SolrDocumentList retrievedDocuments)
    {
        super(owner);
        this.setLayout(new BorderLayout());
        this.setPreferredSize(new Dimension(800, 600));
        this.setMinimumSize(new Dimension(800, 600));
        this.setTitle("Consulta a Apache Solr");

        this.executedQuery = executedQuery;
        this.queriedCore = queriedCore;
        this.retrievedDocuments = retrievedDocuments;
    }

    public void initializeComponents()
    {
        initializeQueryInformationPanel();

        initializeCorpusDocumentTable();

        initializeShowMoreInformationPanel();

        this.validate();
        this.pack();
        this.setLocationRelativeTo(this.getOwner());
    }

    private void initializeQueryInformationPanel()
    {
        this.queryInformationPanel = new JPanel();

        String queryText = executedQuery.getQuery().replaceFirst("text:", "");

        this.executedQueryLabel = new JLabel(EXECUTED_QUERY_LABEL_TEXT);

        this.queryInformationPanel.add(executedQueryLabel);

        this.executedQueryTextArea = new JTextArea(queryText);
        this.executedQueryTextArea.setWrapStyleWord(true);
        this.executedQueryTextArea.setLineWrap(true);
        this.executedQueryTextArea.setEditable(false);

        JScrollPane executedQueryTextAreScrollPane = new JScrollPane(executedQueryTextArea);
        executedQueryTextAreScrollPane.setPreferredSize(new Dimension(200, 50));
        executedQueryTextAreScrollPane.setMinimumSize(new Dimension(200, 50));

        this.queryInformationPanel.add(executedQueryTextAreScrollPane);

        this.queriedCoreLabel = new JLabel(QUERIED_CORE_LABEL_TEXT + " " + queriedCore);

        this.queryInformationPanel.add(queriedCoreLabel);

        this.add(queryInformationPanel, BorderLayout.NORTH);
    }

    private void initializeCorpusDocumentTable()
    {
        this.corpusDocumentTable = new CorpusDocumentTable(400, 400);

        this.corpusDocumentTable.initializeComponents();
        this.corpusDocumentTable.addDocuments(retrievedDocuments);

        this.add(new JScrollPane(corpusDocumentTable), BorderLayout.CENTER);
    }

    private void initializeShowMoreInformationPanel()
    {
        this.showMoreInformationPanel = new JPanel();

        this.selectRetrievedDocumentLabel = new JLabel(SELECT_RETRIEVED_DOCUMENT_LABEL_TEXT);

        this.showMoreInformationPanel.add(selectRetrievedDocumentLabel);

        this.retrievedDocumentsIdComboBox = new JComboBox();
        this.retrievedDocumentsIdComboBox.setPreferredSize(new Dimension(200, 30));
        this.retrievedDocumentsIdComboBox.setMinimumSize(new Dimension(200, 30));

        for (SolrDocument retrievedDocument : retrievedDocuments)
        {
            Long documentId = (Long) retrievedDocument.getFieldValue(CorpusFormatDocumentParser.SOLR_INPUT_DOCUMENT_IDENTIFIER_KEY);

            retrievedDocumentsIdComboBox.addItem(documentId);
        }

        this.showMoreInformationPanel.add(retrievedDocumentsIdComboBox);

        this.showMoreInformationButton = new JButton(SHOW_MORE_INFORMATION_BUTTON_TEXT);

        this.showMoreInformationButton.addActionListener((ActionEvent e) ->
        {
            showMoreInformationButtonAction();
        });

        this.showMoreInformationPanel.add(showMoreInformationButton);

        this.add(showMoreInformationPanel, BorderLayout.SOUTH);
    }

    private void showMoreInformationButtonAction()
    {
        int documentTableIndex = this.retrievedDocumentsIdComboBox.getSelectedIndex();
        SolrDocument document = this.retrievedDocuments.get(documentTableIndex);

        SwingUtilities.invokeLater(() ->
        {
            JDialog showMoreInformationDialog = new JDialog();
            showMoreInformationDialog.setTitle("Mostrando documento");
            showMoreInformationDialog.setPreferredSize(new Dimension(800, 600));
            showMoreInformationDialog.setMinimumSize(new Dimension(800, 600));

            ShowDocumentPanel showDocumentPanel = new ShowDocumentPanel(null, document);

            showDocumentPanel.initializeComponents();

            showMoreInformationDialog.setContentPane(showDocumentPanel);

            showMoreInformationDialog.pack();
            showMoreInformationDialog.validate();

            showMoreInformationDialog.setLocationRelativeTo(this);
            showMoreInformationDialog.setVisible(true);
        });
    }
}
