package saul.rodriguez.naranjo.practica1.motores.de.busqueda.query.parse.test;

import java.io.FileNotFoundException;
import org.apache.solr.client.solrj.SolrQuery;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import saul.rodriguez.naranjo.practica1.motores.de.busqueda.indexing.parse.WrongDocumentExtensionException;
import saul.rodriguez.naranjo.practica1.motores.de.busqueda.query.parse.CorpusFormatQueryDocumentParser;
import saul.rodriguez.naranjo.practica1.motores.de.busqueda.query.parse.QueryDocumentParser;

/**
 *
 * @author Saúl Rodríguez Naranjo
 */
public class CorpusFormatQueryDocumentParserTest
{
    @Test
    public void test_WrongDocumentExtensionException_thrown_when_incorrect_document_extension()
    {
        Assertions.assertThrows(WrongDocumentExtensionException.class, () ->
        {
            QueryDocumentParser queryDocumentParser = new CorpusFormatQueryDocumentParser();

            queryDocumentParser.parseQueryDocument("src/main/resources/cisi/CISI.REL");
        });
    }

    @Test
    public void test_FileNotFoundException_thrown_when_file_does_not_exists()
    {
        Assertions.assertThrows(FileNotFoundException.class, () ->
        {
            QueryDocumentParser queryDocumentParser = new CorpusFormatQueryDocumentParser();

            queryDocumentParser.parseQueryDocument("src/main/resources/cisi/CISI.ALS");
        });
    }
    
    @Test
    public void test_any_value_solr_query_when_null_string()
    {
        QueryDocumentParser queryDocumentParser = new CorpusFormatQueryDocumentParser();
        
        SolrQuery corpusSolrQuery = queryDocumentParser.buildStandardDocumentQuery(null);
        
        String valueQuery = corpusSolrQuery.getQuery().split(":")[1];
        
        Assertions.assertEquals("*", valueQuery);
    }
    
    @Test
    public void test_any_value_solr_query_when_empty_string()
    {
        QueryDocumentParser queryDocumentParser = new CorpusFormatQueryDocumentParser();
        
        SolrQuery corpusSolrQuery = queryDocumentParser.buildStandardDocumentQuery("");
        
        String valueQuery = corpusSolrQuery.getQuery().split(":")[1];
        
        Assertions.assertEquals("*", valueQuery);
    }
    
    @Test
    public void test_any_value_solr_query_when_blank_string()
    {
        QueryDocumentParser queryDocumentParser = new CorpusFormatQueryDocumentParser();
        
        SolrQuery corpusSolrQuery = queryDocumentParser.buildStandardDocumentQuery(" ");
        
        String valueQuery = corpusSolrQuery.getQuery().split(":")[1];
        
        Assertions.assertEquals("*", valueQuery);
    }
}
