package saul.rodriguez.naranjo.practica1.motores.de.busqueda.ui.document;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import org.apache.solr.common.SolrDocument;
import saul.rodriguez.naranjo.practica1.motores.de.busqueda.indexing.parse.CorpusFormatDocumentParser;

/**
 *
 * @author Saúl Rodríguez Naranjo
 */
public class ShowDocumentPanel extends JPanel
{
    private JPanel documentTitlePanel;
    
    private JLabel documentTitleLabel;
    private JTextField documentTitleTextField;
    
    private JPanel idAndAuthorPanel;
    
    private JLabel documentIdLabel;
    private JTextField documentIdTextField;
    
    private JLabel documentAuthorLabel;
    private JTextField documentAuthorTextField;
    
    private JTextArea documentTextTextArea;
    
    private final JFrame parentFrame;
    private final SolrDocument document;
    
    private static final String DOCUMENT_TITLE_LABEL_TEXT = "Título:";
    private static final String DOCUMENT_ID_LABEL_TEXT = "ID:";
    private static final String DOCUMENT_AUTHOR_LABEL_TEXT = "Autor:";
    
    public ShowDocumentPanel(JFrame parentFrame, SolrDocument document)
    {
        this.parentFrame = parentFrame;
        this.document = document;
        this.setLayout(new BorderLayout());
    }
    
    public void initializeComponents()
    {
        initializeDocumentTitlePanel();
        
        this.documentTextTextArea = new JTextArea();
        this.documentTextTextArea.setWrapStyleWord(true);
        this.documentTextTextArea.setLineWrap(true);
        this.documentTextTextArea.setEditable(false);
        
        String documentText = (String) document.getFieldValue(CorpusFormatDocumentParser.SOLR_INPUT_DOCUMENT_TEXT_KEY);
        
        this.documentTextTextArea.setText(documentText);
        
        this.add(new JScrollPane(documentTextTextArea), BorderLayout.CENTER);
        
        initializeIdAndAuthorPanel();
    }
    
    private void initializeDocumentTitlePanel()
    {
        this.documentTitlePanel = new JPanel();
        
        documentTitleLabel = new JLabel(DOCUMENT_TITLE_LABEL_TEXT);
        
        this.documentTitlePanel.add(documentTitleLabel);
        
        this.documentTitleTextField = new JTextField();
        this.documentTitleTextField.setEditable(false);
        this.documentTitleTextField.setPreferredSize(new Dimension(700, 30));
        this.documentTitleTextField.setMinimumSize(new Dimension(700, 30));
        
        String documentTitle = (String) document.getFieldValue(CorpusFormatDocumentParser.SOLR_INPUT_DOCUMENT_TITLE_KEY);
        
        this.documentTitleTextField.setText(documentTitle);
        
        this.documentTitlePanel.add(documentTitleTextField);
        
        this.add(documentTitlePanel, BorderLayout.NORTH);
    }

    private void initializeIdAndAuthorPanel()
    {
        this.idAndAuthorPanel = new JPanel();
        
        this.documentIdLabel = new JLabel(DOCUMENT_ID_LABEL_TEXT);
        
        this.idAndAuthorPanel.add(documentIdLabel);
        
        this.documentIdTextField = new JTextField();
        this.documentIdTextField.setPreferredSize(new Dimension(150, 30));
        this.documentIdTextField.setMinimumSize(new Dimension(150, 30));
        
        Long documentId = (Long) document.getFieldValue(CorpusFormatDocumentParser.SOLR_INPUT_DOCUMENT_IDENTIFIER_KEY);
        this.documentIdTextField.setText(documentId.toString());
        
        this.idAndAuthorPanel.add(documentIdTextField);
        
        this.documentAuthorLabel = new JLabel(DOCUMENT_AUTHOR_LABEL_TEXT);
        
        this.idAndAuthorPanel.add(documentAuthorLabel);
        
        this.documentAuthorTextField = new JTextField();
        this.documentAuthorTextField.setPreferredSize(new Dimension(200, 30));
        this.documentAuthorTextField.setMinimumSize(new Dimension(200, 30));
        
        String documentAuthor = (String) document.getFieldValue(CorpusFormatDocumentParser.SOLR_INPUT_DOCUMENT_AUTHOR_KEY);
        this.documentAuthorTextField.setText(documentAuthor);
        
        this.idAndAuthorPanel.add(documentAuthorTextField);
        
        this.add(idAndAuthorPanel, BorderLayout.SOUTH);
    }
}
