package saul.rodriguez.naranjo.practica1.motores.de.busqueda.indexing.parse.test;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.solr.common.SolrInputDocument;
import org.junit.jupiter.api.Test;
import saul.rodriguez.naranjo.practica1.motores.de.busqueda.indexing.parse.DocumentParser;
import saul.rodriguez.naranjo.practica1.motores.de.busqueda.indexing.parse.GateCorpusFormatDocumentParser;
import saul.rodriguez.naranjo.practica1.motores.de.busqueda.indexing.parse.WrongDocumentExtensionException;

/**
 *
 * @author Saúl Rodríguez Naranjo
 */
public class GateCorpusFormatDocumentParserTest
{
    @Test
    public void testDocumentParsing()
    {
        try
        {
            DocumentParser documentParser = new GateCorpusFormatDocumentParser();

            List<SolrInputDocument> solrInputDocumentList
                    = documentParser.parseDocument("src/main/resources/cisi/CISI.xml");
            
            for (SolrInputDocument solrInputDocument : solrInputDocumentList)
            {
                System.out.println("Document: " + solrInputDocument);
            }
            
        } catch (WrongDocumentExtensionException | FileNotFoundException ex)
        {
            Logger.getLogger(CorpusFormatDocumentParserTest.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
    }
}
