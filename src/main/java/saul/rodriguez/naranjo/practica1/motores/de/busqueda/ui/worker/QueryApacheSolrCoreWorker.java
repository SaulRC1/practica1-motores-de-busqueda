package saul.rodriguez.naranjo.practica1.motores.de.busqueda.ui.worker;

import javax.swing.SwingWorker;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.common.SolrDocumentList;
import saul.rodriguez.naranjo.practica1.motores.de.busqueda.connector.CorpusConnection;
import saul.rodriguez.naranjo.practica1.motores.de.busqueda.connector.CorpusSolrConnector;

/**
 *
 * @author Saúl Rodríguez Naranjo
 */
public class QueryApacheSolrCoreWorker extends SwingWorker<SolrDocumentList, Void>
{
    private final SolrQuery solrQuery;
    private final String coreName;

    public QueryApacheSolrCoreWorker(SolrQuery solrQuery, String coreName)
    {
        this.solrQuery = solrQuery;
        this.coreName = coreName;
    }

    @Override
    protected SolrDocumentList doInBackground() throws Exception
    {
        if(!CorpusConnection.isValidConnection())
        {
            throw new Exception(CorpusConnection.NOT_VALID_CONNECTION_MESSAGE);
        }
        
        CorpusSolrConnector connector = CorpusConnection.getCorpusSolrConnector();
        
        return connector.queryCore(coreName, solrQuery);
    }
    
    
}
