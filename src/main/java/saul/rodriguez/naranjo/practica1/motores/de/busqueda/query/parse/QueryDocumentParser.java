package saul.rodriguez.naranjo.practica1.motores.de.busqueda.query.parse;

import java.io.FileNotFoundException;
import java.util.List;
import org.apache.solr.client.solrj.SolrQuery;
import saul.rodriguez.naranjo.practica1.motores.de.busqueda.indexing.parse.WrongDocumentExtensionException;

/**
 * A parser that is used specifically for retrieving queries from an specific
 * document in order to be executed against an Apache Solr server instance.
 *
 * @author Saúl Rodríguez Naranjo
 */
public interface QueryDocumentParser
{

    /**
     * This method will parse the query document passed by parameter and return
     * a list of {@link String} that represent each a query that must be
     * executed against an Apache Solr server instance.
     *
     * @param documentPath The path of the document that must be parsed.
     * Preferably an absolute path.
     *
     * @return A list of queries retrieved from the query document.
     *
     * @throws FileNotFoundException If the document does not exist.
     *
     * @throws WrongDocumentExtensionException If the extension of the document
     * is not expected by the document parser.
     */
    public List<String> parseQueryDocument(String documentPath) throws FileNotFoundException,
            WrongDocumentExtensionException;

    /**
     * Will build a series of {@link SolrQuery} objects already prepared with
     * the format required by the collection/collections of documents to be
     * queried for an optimized search.
     *
     * @param queryStrings The queries to be formated.
     *
     * @return A list of {@link SolrQuery} objects already prepared with the
     * format required by the document for an optimized search.
     *
     * @see #buildStandardDocumentQuery(java.lang.String)
     */
    public List<SolrQuery> buildStandardDocumentQueries(List<String> queryStrings);

    /**
     * This method will build a {@link SolrQuery} already prepared with the
     * standard format that the collection/collections of documents to be
     * queried requires for an optimized search, based on the query string
     * passed by parameter.
     *
     * <p>
     * In case the string passed by parameter is null, empty or blank, the query
     * string will be assumed to be "*", meaning that it will search for any
     * value.
     * </p>
     *
     * @param queryString The query string to be formated.
     *
     * @return A {@link SolrQuery} already prepared with the format required.
     */
    public SolrQuery buildStandardDocumentQuery(String queryString);
}
