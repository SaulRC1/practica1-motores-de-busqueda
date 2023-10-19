package saul.rodriguez.naranjo.practica1.motores.de.busqueda.connector;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.request.CoreAdminRequest;
import org.apache.solr.client.solrj.response.CoreAdminResponse;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.NonExistentCoreException;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.params.CoreAdminParams.CoreAdminAction;

/**
 * Utility class that acts as a connector between the Apache Solr server
 * instance and a {@link SolrClient}.
 *
 * <p>
 * This connector is capable of executing miscellanous tasks that normally a
 * client that connects to an Apache Solr server instance would do, but
 * providing a wrapper that makes communications less verbose and more
 * straightforward.
 * </p>
 *
 * @author Saúl Rodríguez Naranjo
 */
public class CorpusSolrConnector
{

    private final SolrClient solrClient;

    /**
     * Will build a CorpusSolrConnector for the {@link SolrClient} passed by
     * parameter.
     *
     * @param solrClient The {@link SolrClient} that will communicate with the
     * Apache Solr server instance.
     */
    public CorpusSolrConnector(SolrClient solrClient)
    {
        this.solrClient = solrClient;
    }

    /**
     * This method will execute a query against a core inside the Apache Solr
     * server instance.
     *
     * @param coreName The name of the core where the query will be executed.
     *
     * @param query The query to be executed against the core.
     *
     * @return The resulting list of documents that comply with the query.
     *
     * @throws SolrServerException if there is an error on the server.
     *
     * @throws IOException if there is a communication error with the server.
     */
    public SolrDocumentList queryCore(String coreName, SolrQuery query)
            throws SolrServerException, IOException
    {
        if (!coreExists(coreName))
        {
            throw new NonExistentCoreException("The core " + coreName + " does not exist.");
        }

        QueryResponse rsp = solrClient.query(coreName, query);

        SolrDocumentList docs = rsp.getResults();

        return docs;
    }

    /**
     * This method will index a document in the core passed by parameter.
     *
     * @param coreName The core name.
     * @param inputDocument The document to be indexed.
     */
    public void indexDocument(String coreName, SolrInputDocument inputDocument)
    {
        try
        {
            if (!coreExists(coreName))
            {
                throw new NonExistentCoreException("The core " + coreName + " does not exist.");
            }

            solrClient.add(coreName, inputDocument);

            solrClient.commit(coreName);

        } catch (SolrServerException | IOException ex)
        {
            try
            {
                solrClient.rollback();

            } catch (SolrServerException | IOException ex1)
            {
                Logger.getLogger(CorpusSolrConnector.class.getName()).log(Level.SEVERE, ex1.getMessage(), ex1);
            }

            Logger.getLogger(CorpusSolrConnector.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
    }

    /**
     * This method will index a list of documents in the core passed by
     * parameter.
     *
     * @param coreName The core name.
     * @param inputDocuments The list of input documents.
     */
    public void indexDocuments(String coreName, List<SolrInputDocument> inputDocuments)
    {
        try
        {
            if (!coreExists(coreName))
            {
                throw new NonExistentCoreException("The core " + coreName + " does not exist.");
            }

            for (SolrInputDocument inputDocument : inputDocuments)
            {
                solrClient.add(coreName, inputDocument);
            }

            solrClient.commit(coreName);

        } catch (SolrServerException | IOException ex)
        {
            try
            {
                solrClient.rollback();

            } catch (SolrServerException | IOException ex1)
            {
                Logger.getLogger(CorpusSolrConnector.class.getName()).log(Level.SEVERE, ex1.getMessage(), ex1);
            }

            Logger.getLogger(CorpusSolrConnector.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
    }

    public void createCore(String coreName)
    {
        //Implementation pending
    }

    /**
     * Checks if the core passed by parameter exists.
     *
     * @param coreName The name of the core to be checked.
     *
     * @return If the core exists.
     *
     * @throws SolrServerException if there is an error on the server.
     *
     * @throws IOException if there is a communication error with the server.
     */
    public boolean coreExists(String coreName) throws SolrServerException, IOException
    {
        Set<String> allCores = this.getAllCores();

        return allCores.contains(coreName);
    }

    /**
     * Gets all cores inside the Apache Solr server instance.
     *
     * @return All cores inside the Apache Solr server instance.
     *
     * @throws SolrServerException if there is an error on the server.
     *
     * @throws IOException if there is a communication error with the server.
     */
    public Set<String> getAllCores() throws SolrServerException, IOException
    {
        CoreAdminRequest request = new CoreAdminRequest();
        request.setAction(CoreAdminAction.STATUS);
        CoreAdminResponse cores;

        Set<String> coreList = new HashSet<>();

        cores = request.process(solrClient);

        for (int i = 0; i < cores.getCoreStatus().size(); i++)
        {
            coreList.add(cores.getCoreStatus().getName(i));
        }

        return coreList;
    }
}
