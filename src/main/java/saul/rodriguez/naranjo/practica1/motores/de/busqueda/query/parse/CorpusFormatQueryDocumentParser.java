package saul.rodriguez.naranjo.practica1.motores.de.busqueda.query.parse;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.common.params.HighlightParams;
import saul.rodriguez.naranjo.practica1.motores.de.busqueda.indexing.parse.CorpusFormatDocumentParser;
import saul.rodriguez.naranjo.practica1.motores.de.busqueda.indexing.parse.WrongDocumentExtensionException;

/**
 * This class is for parsing documents that have the corpus format. Corpus query
 * documents will have the .QRY extension.
 *
 * @author Saúl Rodríguez Naranjo
 */
public class CorpusFormatQueryDocumentParser implements QueryDocumentParser
{

    public static final String CORPUS_QUERY_DOCUMENT_IDENTIFIER = ".I";
    public static final String CORPUS_QUERY_DOCUMENT_TEXT = ".W";

    @Override
    public List<String> parseQueryDocument(String documentPath) throws FileNotFoundException,
            WrongDocumentExtensionException
    {
        List<String> queries = new ArrayList<>();

        BufferedReader reader = null;

        checkDocumentAvailability(documentPath);

        Path pathToDocument = Paths.get(documentPath);

        try
        {
            reader = Files.newBufferedReader(pathToDocument.toAbsolutePath());

            String line;

            boolean parsingText = false;

            StringBuilder queryString = null;

            while ((line = reader.readLine()) != null)
            {
                if (line.trim().split(" ")[0].equals(CORPUS_QUERY_DOCUMENT_IDENTIFIER))
                {
                    if (queryString != null)
                    {
                        queries.add(queryString.toString());
                    }

                    queryString = new StringBuilder();

                    parsingText = false;

                    continue;

                } else if (line.trim().equals(CORPUS_QUERY_DOCUMENT_TEXT))
                {
                    parsingText = true;

                    continue;
                }

                if (parsingText)
                {
                    if (queryString != null)
                    {
                        queryString.append(line.trim());
                    }
                }
            }

        } catch (IOException e)
        {
            Logger.getLogger(CorpusFormatQueryDocumentParser.class.getName())
                    .log(Level.SEVERE, "An error occurred when reading the file", e);
        } finally
        {
            try
            {
                if (reader != null)
                {
                    reader.close();
                }

            } catch (IOException e)
            {
                Logger.getLogger(CorpusFormatQueryDocumentParser.class.getName()).log(Level.SEVERE, e.getMessage(), e);
            }
        }

        return queries;
    }

    /**
     * This method will check if the document path provided signals to a
     * document and if it exists, as well as if the extension is correct.
     *
     * @param documentPath The path to the document to be checked.
     */
    private void checkDocumentAvailability(String documentPath) throws FileNotFoundException,
            WrongDocumentExtensionException
    {
        Path pathToDocument = Paths.get(documentPath);

        if (Files.notExists(pathToDocument.toAbsolutePath()))
        {
            throw new FileNotFoundException("The document provided does not exist.");
        }

        if (!pathToDocument.toAbsolutePath().toString().toLowerCase().endsWith(".qry"))
        {
            throw new WrongDocumentExtensionException("The extension of the document must be .qry/.QRY");
        }
    }

    @Override
    public List<SolrQuery> buildStandardDocumentQueries(List<String> queryStrings)
    {
        List<SolrQuery> corpusSolrQueries = new ArrayList<>();
        
        for (String queryString : queryStrings)
        {
            corpusSolrQueries.add(buildStandardDocumentQuery(queryString));
        }
        
        return corpusSolrQueries;
    }

    @Override
    public SolrQuery buildStandardDocumentQuery(String queryString)
    {
        SolrQuery corpusSolrQuery = new SolrQuery();
        
        if(queryString == null || queryString.isEmpty() || queryString.isBlank())
        {
            queryString = "*";
        }

        corpusSolrQuery.setQuery(CorpusFormatDocumentParser.SOLR_INPUT_DOCUMENT_TEXT_KEY
                + ":" + queryString);

        corpusSolrQuery.setFields(CorpusFormatDocumentParser.SOLR_INPUT_DOCUMENT_IDENTIFIER_KEY,
                CorpusFormatDocumentParser.SOLR_INPUT_DOCUMENT_TITLE_KEY,
                CorpusFormatDocumentParser.SOLR_INPUT_DOCUMENT_AUTHOR_KEY,
                CorpusFormatDocumentParser.SOLR_INPUT_DOCUMENT_TEXT_KEY,
                HighlightParams.SCORE);

        return corpusSolrQuery;
    }
}
