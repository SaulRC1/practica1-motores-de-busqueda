package saul.rodriguez.naranjo.practica1.motores.de.busqueda.indexing.parse.test;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.solr.common.SolrInputDocument;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import saul.rodriguez.naranjo.practica1.motores.de.busqueda.indexing.parse.CorpusFormatDocumentParser;
import saul.rodriguez.naranjo.practica1.motores.de.busqueda.indexing.parse.DocumentParser;
import saul.rodriguez.naranjo.practica1.motores.de.busqueda.indexing.parse.WrongDocumentExtensionException;

/**
 *
 * @author Saúl Rodríguez Naranjo
 */
public class CorpusFormatDocumentParserTest
{

    @Test
    public void testDocumentParsing()
    {
        try
        {
            DocumentParser documentParser = new CorpusFormatDocumentParser();

            List<SolrInputDocument> solrInputDocumentList
                    = documentParser.parseDocument("src/main/resources/cisi/CISI.ALL");

            Assertions.assertAll("Verify list of SolrInputDocument",
                    () -> Assertions.assertEquals(1460,
                            solrInputDocumentList.size()),
                    
                    () -> Assertions.assertEquals("1", 
                            solrInputDocumentList.get(0).getFieldValue(CorpusFormatDocumentParser.SOLR_INPUT_DOCUMENT_IDENTIFIER_KEY)),
                    
                    () -> Assertions.assertEquals("18 Editions of the Dewey Decimal Classifications", 
                            solrInputDocumentList.get(0).getFieldValue(CorpusFormatDocumentParser.SOLR_INPUT_DOCUMENT_TITLE_KEY)),
                    
                    () -> Assertions.assertEquals("Comaromi, J.P.",
                             solrInputDocumentList.get(0).getFieldValue(CorpusFormatDocumentParser.SOLR_INPUT_DOCUMENT_AUTHOR_KEY))
            );
            
        } catch (WrongDocumentExtensionException | FileNotFoundException ex)
        {
            Logger.getLogger(CorpusFormatDocumentParserTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Test
    public void test_WrongDocumentExtensionException_thrown_when_incorrect_document_extension()
    {
        Assertions.assertThrows(WrongDocumentExtensionException.class, () ->
        {
            DocumentParser documentParser = new CorpusFormatDocumentParser();

            documentParser.parseDocument("src/main/resources/cisi/CISI.REL");
        });
    }

    @Test
    public void test_FileNotFoundException_thrown_when_file_does_not_exists()
    {
        Assertions.assertThrows(FileNotFoundException.class, () ->
        {
            DocumentParser documentParser = new CorpusFormatDocumentParser();

            documentParser.parseDocument("src/main/resources/cisi/CISI.ALS");
        });
    }
}
