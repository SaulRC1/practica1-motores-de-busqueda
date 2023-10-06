package saul.rodriguez.naranjo.practica1.motores.de.busqueda.indexing.parse;

import java.io.FileNotFoundException;
import java.util.List;
import org.apache.solr.common.SolrInputDocument;

/**
 * Interface that all document parsers must implement to return a properly built
 * {@link SolrInputDocument} list in order to index documents to Apache Solr.
 * 
 * @author Saúl Rodríguez Naranjo
 */
public interface DocumentParser
{
    /**
     * Will parse the document passed by parameter and return a list of
     * {@link SolrInputDocument} ready to be indexed.
     * 
     * <p>
     *  There will be as many {@link SolrInputDocument} present in the list as
     *  documents found in the original recipient document.
     * </p>
     * 
     * @param documentPath The path of the document that must be parsed. Preferably
     * an absolute path.
     * 
     * @return The {@link SolrInputDocument} list ready to be indexed in Apache Solr.
     * 
     * @throws saul.rodriguez.naranjo.practica1.motores.de.busqueda.indexing.parse.WrongDocumentExtensionException
     * If the extension of the document is not expected by the document parser.
     * 
     * @throws java.io.FileNotFoundException If the document does not exist.
     */
    public List<SolrInputDocument> parseDocument(String documentPath) throws WrongDocumentExtensionException, FileNotFoundException;
}
