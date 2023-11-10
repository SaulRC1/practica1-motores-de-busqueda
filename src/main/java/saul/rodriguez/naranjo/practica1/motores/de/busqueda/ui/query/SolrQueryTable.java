package saul.rodriguez.naranjo.practica1.motores.de.busqueda.ui.query;

import java.util.List;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import org.apache.solr.client.solrj.SolrQuery;

/**
 *
 * @author Saúl Rodríguez Naranjo
 */
public class SolrQueryTable extends JScrollPane
{
    private JTable solrQueryTable;
    
    private static final String ID_COLUMN_NAME = "ID";
    private static final String QUERY_COLUMN_NAME = "Consulta";
    
    public void initializeComponents()
    {
        this.solrQueryTable = new JTable();
        
        DefaultTableModel tableModel = (DefaultTableModel) this.solrQueryTable.getModel();
        
        tableModel.addColumn(ID_COLUMN_NAME);
        tableModel.addColumn(QUERY_COLUMN_NAME);
        
        TableColumnModel columnModel = this.solrQueryTable.getColumnModel();
        
        columnModel.getColumn(0).setPreferredWidth(100);
        columnModel.getColumn(0).setMinWidth(100);
        columnModel.getColumn(0).setMaxWidth(100);
        
        this.setViewportView(solrQueryTable);
    }
    
    public void addSolrQueries(List<SolrQuery> solrQueries)
    {
        DefaultTableModel tableModel = (DefaultTableModel) this.solrQueryTable.getModel();
        
        tableModel.setRowCount(0);
        
        long queryId = 0;
        
        for (SolrQuery solrQuery : solrQueries)
        {
            String queryText = solrQuery.getQuery().replaceFirst("text:", "");
            
            tableModel.addRow(new Object[] {queryId, queryText});
            
            queryId++;
        }
    }
}
