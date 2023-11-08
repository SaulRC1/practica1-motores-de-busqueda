package saul.rodriguez.naranjo.practica1.motores.de.busqueda.ui.worker;

import java.util.List;
import javax.swing.SwingWorker;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrInputDocument;
import saul.rodriguez.naranjo.practica1.motores.de.busqueda.connector.CorpusConnection;
import saul.rodriguez.naranjo.practica1.motores.de.busqueda.connector.CorpusSolrConnector;

/**
 *
 * @author Saúl Rodríguez Naranjo
 */
public class IndexSolrInputDocumentsWorker extends SwingWorker<UpdateResponse, Void>
{
    private List<SolrInputDocument> solrInputDocuments;
    private String coreName;

    public IndexSolrInputDocumentsWorker(List<SolrInputDocument> solrInputDocuments, String coreName)
    {
        this.solrInputDocuments = solrInputDocuments;
        this.coreName = coreName;
    }
    
    @Override
    protected UpdateResponse doInBackground() throws Exception
    {
        if(!CorpusConnection.isValidConnection())
        {
            throw new Exception(CorpusConnection.NOT_VALID_CONNECTION_MESSAGE);
        }
        
        CorpusSolrConnector connector = CorpusConnection.getCorpusSolrConnector();
        
        UpdateResponse updateResponse = connector.indexDocuments(coreName, solrInputDocuments);
        
        return updateResponse;
    }

}
