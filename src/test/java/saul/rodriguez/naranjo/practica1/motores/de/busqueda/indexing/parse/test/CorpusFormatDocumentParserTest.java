package saul.rodriguez.naranjo.practica1.motores.de.busqueda.indexing.parse;

import java.io.FileNotFoundException;
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
