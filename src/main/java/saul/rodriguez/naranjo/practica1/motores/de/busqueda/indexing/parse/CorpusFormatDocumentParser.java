package saul.rodriguez.naranjo.practica1.motores.de.busqueda.indexing.parse;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.solr.common.SolrInputDocument;

/**
 * This class is for parsing documents with the format of the corpus file, which
 * must end with the .ALL extension.
 *
 * @author Saúl Rodríguez Naranjo
 */
public class CorpusFormatDocumentParser implements DocumentParser
{

    @Override
    public SolrInputDocument parseDocument(String documentPath) throws WrongDocumentExtensionException, FileNotFoundException
    {
        BufferedReader reader = null;

        Path pathToDocument = Paths.get(documentPath);

        if (Files.notExists(pathToDocument.toAbsolutePath()))
        {
            throw new FileNotFoundException("The document provided does not exist.");
        }

        if (!pathToDocument.toAbsolutePath().toString().toLowerCase().endsWith(".all"))
        {
            throw new WrongDocumentExtensionException("The extension of the document must be .all/.ALL");
        }

        try
        {
            reader = Files.newBufferedReader(pathToDocument.toAbsolutePath());

            String line;

            while ((line = reader.readLine()) != null)
            {

            }

        } catch (IOException ex)
        {
            Logger.getLogger(CorpusFormatDocumentParser.class.getName())
                    .log(Level.SEVERE, "An error occurred when reading the file", ex);
        } finally
        {
            try
            {
                if (reader != null)
                {
                    reader.close();
                }
            } catch (IOException ex)
            {
                Logger.getLogger(CorpusFormatDocumentParser.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return null;
    }

}
