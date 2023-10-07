package saul.rodriguez.naranjo.practica1.motores.de.busqueda;

import java.io.IOException;
import java.util.List;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrInputDocument;
import saul.rodriguez.naranjo.practica1.motores.de.busqueda.indexing.parse.CorpusFormatDocumentParser;
import saul.rodriguez.naranjo.practica1.motores.de.busqueda.indexing.parse.DocumentParser;
import saul.rodriguez.naranjo.practica1.motores.de.busqueda.indexing.parse.WrongDocumentExtensionException;

/**
 *
 * @author Saúl Rodríguez Naranjo
 */
public class Practica1MotoresDeBusqueda {

    public static void main(String[] args) throws SolrServerException, IOException, WrongDocumentExtensionException {
        final SolrClient client = new HttpSolrClient.Builder("http://localhost:8983/solr").build();
        
        DocumentParser corpusDocumentParser = new CorpusFormatDocumentParser();
        
        List<SolrInputDocument> solrInputDocumentList = corpusDocumentParser.parseDocument("src/main/resources/cisi/CISI.ALL");
        
        for (SolrInputDocument solrInputDocument : solrInputDocumentList)
        {
            System.out.println("Adding " + solrInputDocument.getFieldValue(CorpusFormatDocumentParser.SOLR_INPUT_DOCUMENT_IDENTIFIER_KEY));
            
            final UpdateResponse updateResponse = client.add("micoleccion", solrInputDocument);
            
            client.commit("micoleccion");
        }
    }
}
