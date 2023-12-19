package saul.rodriguez.naranjo.practica1.motores.de.busqueda.ui.gate;

import saul.rodriguez.naranjo.practica1.motores.de.busqueda.ui.indexing.*;
import java.awt.Dimension;
import java.util.List;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import saul.rodriguez.naranjo.practica1.motores.de.busqueda.indexing.parse.CorpusFormatDocumentParser;
import saul.rodriguez.naranjo.practica1.motores.de.busqueda.indexing.parse.GateCorpusFormatDocumentParser;

/**
 * Table for displaying information about Corpus index documents.
 *
 * @author Saúl Rodríguez Naranjo
 */
public class GateCorpusDocumentTable extends JTable
{   
    private static final String ID_COLUMN_NAME = "ID";
    private static final String TITLE_COLUMN_NAME = "Título";
    private static final String AUTHOR_COLUMN_NAME = "Autor";
    private static final String PERSON_COLUMN_NAME = "Persona";
    private static final String ORGANIZATION_COLUMN_NAME = "Organización";
    private static final String DATE_COLUMN_NAME = "Fecha";
    
    private int width;
    private int height;
    private Dimension dimension;
    
    public GateCorpusDocumentTable(int width, int height)
    {
        this.width = width;
        this.height = height;
        this.dimension = new Dimension(width, height);
    }
    
    public void initializeComponents()
    {           
        DefaultTableModel tableModel = (DefaultTableModel) this.getModel();
        
        tableModel.addColumn(ID_COLUMN_NAME);
        tableModel.addColumn(TITLE_COLUMN_NAME);
        tableModel.addColumn(AUTHOR_COLUMN_NAME);
        tableModel.addColumn(PERSON_COLUMN_NAME);
        tableModel.addColumn(DATE_COLUMN_NAME);
        tableModel.addColumn(ORGANIZATION_COLUMN_NAME);
    }
    
    public void addDocument(SolrInputDocument solrInputDocument)
    {
        DefaultTableModel tableModel = (DefaultTableModel) this.getModel();
        
        String documentId = (String) solrInputDocument.getField(
                CorpusFormatDocumentParser.SOLR_INPUT_DOCUMENT_IDENTIFIER_KEY).getValue();
        
        String documentTitle = (String) solrInputDocument.getField(
                CorpusFormatDocumentParser.SOLR_INPUT_DOCUMENT_TITLE_KEY).getValue();
        
        String documentAuthor = (String) solrInputDocument.getField(
                CorpusFormatDocumentParser.SOLR_INPUT_DOCUMENT_AUTHOR_KEY).getValue();
        
        String documentPerson = (String) solrInputDocument.getField(
                GateCorpusFormatDocumentParser.SOLR_INPUT_DOCUMENT_PERSON_KEY).getValue();
        
        String documentDate = (String) solrInputDocument.getField(
                GateCorpusFormatDocumentParser.SOLR_INPUT_DOCUMENT_DATE_KEY).getValue();
        
        String documentOrganization = (String) solrInputDocument.getField(
                GateCorpusFormatDocumentParser.SOLR_INPUT_DOCUMENT_ORGANIZATION_KEY).getValue();
        
        tableModel.addRow(new Object[]{documentId, documentTitle, documentAuthor, documentPerson, documentDate, documentOrganization});
    }
    
    public void addDocuments(List<SolrInputDocument> solrInputDocuments)
    {
        DefaultTableModel tableModel = (DefaultTableModel) this.getModel();
        
        tableModel.setRowCount(0);
        
        for (SolrInputDocument solrInputDocument : solrInputDocuments)
        {
            this.addDocument(solrInputDocument);
        }
    }
    
    public void addDocument(SolrDocument solrDocument)
    {
        DefaultTableModel tableModel = (DefaultTableModel) this.getModel();
        
        long documentId = (long) solrDocument.getFieldValue(
                CorpusFormatDocumentParser.SOLR_INPUT_DOCUMENT_IDENTIFIER_KEY);
        
        String documentTitle = (String) solrDocument.getFieldValue(
                CorpusFormatDocumentParser.SOLR_INPUT_DOCUMENT_TITLE_KEY);
        
        String documentAuthor = (String) solrDocument.getFieldValue(
                CorpusFormatDocumentParser.SOLR_INPUT_DOCUMENT_AUTHOR_KEY);
        
        tableModel.addRow(new Object[]{documentId, documentTitle, documentAuthor});
    }
    
    public void addDocuments(SolrDocumentList documentList)
    {
        DefaultTableModel tableModel = (DefaultTableModel) this.getModel();
        
        tableModel.setRowCount(0);
        
        for (SolrDocument solrDocument : documentList)
        {
            addDocument(solrDocument);
        }
    }
}
