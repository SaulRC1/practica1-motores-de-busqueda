package saul.rodriguez.naranjo.practica1.motores.de.busqueda.indexing.parse;

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
import org.apache.solr.common.SolrInputDocument;

/**
 * This class is for parsing documents with the format of the corpus file, which
 * must end with the .ALL extension.
 *
 * @author Saúl Rodríguez Naranjo
 */
public class CorpusFormatDocumentParser implements DocumentParser
{   

    public static final String CORPUS_DOCUMENT_IDENTIFIER = ".I";
    public static final String CORPUS_DOCUMENT_TITLE = ".T";
    public static final String CORPUS_DOCUMENT_AUTHOR = ".A";
    public static final String CORPUS_DOCUMENT_TEXT = ".W";
    public static final String CORPUS_DOCUMENT_UNKNOWN = ".X";
    
    public static final String SOLR_INPUT_DOCUMENT_IDENTIFIER_KEY = "id";
    public static final String SOLR_INPUT_DOCUMENT_TITLE_KEY = "title";
    public static final String SOLR_INPUT_DOCUMENT_AUTHOR_KEY = "author";
    public static final String SOLR_INPUT_DOCUMENT_TEXT_KEY = "text";
    
    @Override
    public List<SolrInputDocument> parseDocument(String documentPath) throws WrongDocumentExtensionException, FileNotFoundException
    {
        BufferedReader reader = null;
        
        List<SolrInputDocument> solrInputDocumentList = null;

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
            
            solrInputDocumentList = new ArrayList<>();

            String line;
            
            SolrInputDocument solrInputDocument = null;
            
            boolean parsingTitle = false;
            
            boolean parsingAuthor = false;
            
            boolean parsingText = false;
            
            StringBuilder documentTitle = null;
            StringBuilder documentAuthor = null;
            StringBuilder documentText = null;

            while ((line = reader.readLine()) != null)
            {
                if(line.trim().split(" ")[0].equals(CORPUS_DOCUMENT_IDENTIFIER))
                {
                    solrInputDocument = new SolrInputDocument();
                    
                    String documentIdentifier = line.split(" ")[1];
                    
                    solrInputDocument.addField(SOLR_INPUT_DOCUMENT_IDENTIFIER_KEY, documentIdentifier);
                    
                    documentTitle = new StringBuilder();
                    documentAuthor = new StringBuilder();
                    documentText = new StringBuilder();
                    
                    //Immediately start the next iteration
                    continue;
                }
                else if(line.trim().equals(CORPUS_DOCUMENT_TITLE))
                {
                    parsingTitle = true;
                    parsingAuthor = false;
                    parsingText = false;
                    
                    //Immediately start the next iteration
                    continue;
                }
                else if(line.trim().equals(CORPUS_DOCUMENT_AUTHOR))
                {
                    parsingTitle = false;
                    parsingAuthor = true;
                    parsingText = false;
                    
                    //Immediately start the next iteration
                    continue;
                }
                else if(line.trim().equals(CORPUS_DOCUMENT_TEXT))
                {
                    parsingTitle = false;
                    parsingAuthor = false;
                    parsingText = true;
                    
                    //Immediately start the next iteration
                    continue;
                }
                else if(line.trim().equals(CORPUS_DOCUMENT_UNKNOWN))
                {
                    parsingTitle = false;
                    parsingAuthor = false;
                    parsingText = false;
                    
                    if(solrInputDocument != null)
                    {
                        if(documentTitle != null)
                        {
                            solrInputDocument.addField(SOLR_INPUT_DOCUMENT_TITLE_KEY, documentTitle.toString());
                        }
                        
                        if(documentAuthor != null)
                        {
                            solrInputDocument.addField(SOLR_INPUT_DOCUMENT_AUTHOR_KEY, documentAuthor.toString());
                        }
                        
                        if(documentText != null)
                        {
                            solrInputDocument.addField(SOLR_INPUT_DOCUMENT_TEXT_KEY, documentText.toString());
                        }

                        solrInputDocumentList.add(solrInputDocument);
                    }
                    
                    //Immediately start the next iteration
                    continue;
                }
                
                if(parsingTitle)
                {
                    if(documentTitle != null)
                    {
                        documentTitle.append(line);
                    }
                }
                
                if(parsingAuthor)
                {
                    if(documentAuthor != null)
                    {
                        documentAuthor.append(line);
                    }
                }
                
                if(parsingText)
                {
                    if(documentText != null)
                    {
                        documentText.append(line);
                    } 
                }
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
                Logger.getLogger(CorpusFormatDocumentParser.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }
        }

        return solrInputDocumentList;
    }

}
