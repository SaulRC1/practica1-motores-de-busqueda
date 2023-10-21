package saul.rodriguez.naranjo.practica1.motores.de.busqueda;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import saul.rodriguez.naranjo.practica1.motores.de.busqueda.connector.CorpusSolrConnector;
import saul.rodriguez.naranjo.practica1.motores.de.busqueda.indexing.parse.CorpusFormatDocumentParser;
import saul.rodriguez.naranjo.practica1.motores.de.busqueda.indexing.parse.DocumentParser;
import saul.rodriguez.naranjo.practica1.motores.de.busqueda.indexing.parse.WrongDocumentExtensionException;
import saul.rodriguez.naranjo.practica1.motores.de.busqueda.query.parse.CorpusFormatQueryDocumentParser;
import saul.rodriguez.naranjo.practica1.motores.de.busqueda.query.parse.QueryDocumentParser;

/**
 *
 * @author Saúl Rodríguez Naranjo
 */
public class Practica1MotoresDeBusqueda {

    public static void main(String[] args) throws SolrServerException, IOException, WrongDocumentExtensionException {
        final SolrClient client = new HttpSolrClient.Builder("http://localhost:8983/solr").build();
        /*
        DocumentParser corpusDocumentParser = new CorpusFormatDocumentParser();
        
        List<SolrInputDocument> solrInputDocumentList = corpusDocumentParser.parseDocument("src/main/resources/cisi/CISI.ALL");
        
        for (SolrInputDocument solrInputDocument : solrInputDocumentList)
        {
            System.out.println("Adding " + solrInputDocument.getFieldValue(CorpusFormatDocumentParser.SOLR_INPUT_DOCUMENT_IDENTIFIER_KEY));
            
            final UpdateResponse updateResponse = client.add("micoleccion", solrInputDocument);
        }
        
        client.commit("micoleccion");*/
        
        /*QueryDocumentParser corpusQueryDocumentParser = new CorpusFormatQueryDocumentParser();
        
        List<String> queries = corpusQueryDocumentParser.parseQueryDocument("src/main/resources/cisi/CISI.QRY");
        
        for (String query : queries)
        {
            System.out.println("Query: " + query);
        }*/
        
        CorpusSolrConnector corpusSolrConnector = new CorpusSolrConnector(client);
        
        /*Set<String> allCores = corpusSolrConnector.getAllCores();
        
        for (String core : allCores)
        {
            System.out.println("Core: " + core);
        }*/
        
        /*boolean coreExists = corpusSolrConnector.coreExists("micoleccion");
        
        if(coreExists)
        {
            System.out.println("Core exists");
        }*/
        
        QueryDocumentParser corpusQueryDocumentParser = new CorpusFormatQueryDocumentParser();
        
        List<String> queryStrings = corpusQueryDocumentParser.parseQueryDocument("src/main/resources/cisi/CISI.QRY");
        
        for (String queryString : queryStrings)
        {
            //Only the first 5 words
            String[] splittedQueryString = queryString.split(" ");
            
            queryString = "";
            
            for (int i = 0; i < splittedQueryString.length; i++)
            {
                if(i == 5)
                {
                    break;
                }
                
                queryString += splittedQueryString[i] + " ";
            }
            
            System.out.println("Query String: " + queryString);
            
            SolrQuery solrQuery = corpusQueryDocumentParser.buildStandardDocumentQuery(queryString);
            
            SolrDocumentList documentList = corpusSolrConnector
                .queryCore("micoleccion", solrQuery);
        
            System.out.println("Document List: " + documentList);
        }
    }
}
