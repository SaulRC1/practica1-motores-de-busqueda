package saul.rodriguez.naranjo.practica1.motores.de.busqueda.indexing.parse;

import org.apache.solr.common.SolrInputDocument;

/**
 * Interface that all document parsers must implement to return a properly built
 * {@link SolrInputDocument} in order to index documents to Apache Solr.
 * 
 * @author Saúl Rodríguez Naranjo
 */
public interface DocumentParser
{
    /**
     * Will parse the document passed by parameter and return a properly structured
     * {@link SolrInputDocument} ready to be indexed.
     * 
     * @param documentPath The absolute path of the document that must be parsed.
     * @return The {@link SolrInputDocument} ready to be indexed in Apache Solr.
     */
    public SolrInputDocument parseDocument(String documentPath);
}
