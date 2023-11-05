package saul.rodriguez.naranjo.practica1.motores.de.busqueda.ui.worker;

import javax.swing.SwingWorker;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import saul.rodriguez.naranjo.practica1.motores.de.busqueda.connector.CorpusConnection;
import saul.rodriguez.naranjo.practica1.motores.de.busqueda.connector.CorpusSolrConnector;

/**
 * Worker for connecting to the Apache Solr server.
 *
 * @author Saúl Rodríguez Naranjo
 */
public class ApacheSolrConnectionWorker extends SwingWorker<CorpusSolrConnector, Void>
{

    private String httpSecurityProtocol;
    private String ipAddress;
    private String port;

    public ApacheSolrConnectionWorker(String httpSecurityProtocol, String ipAddress, String port)
    {
        super();
        this.httpSecurityProtocol = httpSecurityProtocol;
        this.ipAddress = ipAddress;
        this.port = port;
    }

    @Override
    protected CorpusSolrConnector doInBackground() throws Exception
    {
        String baseSolrUrl = httpSecurityProtocol + "://" + ipAddress + ":"
                + port + "/solr";

        SolrClient client = new HttpSolrClient.Builder(baseSolrUrl).build();

        CorpusSolrConnector corpusSolrConnector = new CorpusSolrConnector(client);

        corpusSolrConnector.getAllCores();

        return corpusSolrConnector;
    }

}
