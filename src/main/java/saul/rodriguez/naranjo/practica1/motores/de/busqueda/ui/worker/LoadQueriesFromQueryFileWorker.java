package saul.rodriguez.naranjo.practica1.motores.de.busqueda.ui.worker;

import java.util.List;
import javax.swing.SwingWorker;
import org.apache.solr.client.solrj.SolrQuery;
import saul.rodriguez.naranjo.practica1.motores.de.busqueda.query.parse.CorpusFormatQueryDocumentParser;
import saul.rodriguez.naranjo.practica1.motores.de.busqueda.query.parse.QueryDocumentParser;

/**
 *
 * @author Saúl Rodríguez Naranjo
 */
public class LoadQueriesFromQueryFileWorker extends SwingWorker<List<SolrQuery>, Void>
{
    private final String filePath;

    public LoadQueriesFromQueryFileWorker(String filePath)
    {
        this.filePath = filePath;
    }

    @Override
    protected List<SolrQuery> doInBackground() throws Exception
    {
        QueryDocumentParser queryDocumentParser = new CorpusFormatQueryDocumentParser();
        
        List<String> queryStrings = queryDocumentParser.parseQueryDocument(filePath);
        
        return queryDocumentParser.buildStandardDocumentQueries(queryStrings);
    }
}
