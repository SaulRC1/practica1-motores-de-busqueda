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
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.solr.common.SolrInputDocument;
import static saul.rodriguez.naranjo.practica1.motores.de.busqueda.indexing.parse.CorpusFormatDocumentParser.CORPUS_DOCUMENT_AUTHOR;
import static saul.rodriguez.naranjo.practica1.motores.de.busqueda.indexing.parse.CorpusFormatDocumentParser.CORPUS_DOCUMENT_IDENTIFIER;
import static saul.rodriguez.naranjo.practica1.motores.de.busqueda.indexing.parse.CorpusFormatDocumentParser.CORPUS_DOCUMENT_TEXT;
import static saul.rodriguez.naranjo.practica1.motores.de.busqueda.indexing.parse.CorpusFormatDocumentParser.CORPUS_DOCUMENT_TITLE;
import static saul.rodriguez.naranjo.practica1.motores.de.busqueda.indexing.parse.CorpusFormatDocumentParser.CORPUS_DOCUMENT_UNKNOWN;
import static saul.rodriguez.naranjo.practica1.motores.de.busqueda.indexing.parse.CorpusFormatDocumentParser.SOLR_INPUT_DOCUMENT_AUTHOR_KEY;
import static saul.rodriguez.naranjo.practica1.motores.de.busqueda.indexing.parse.CorpusFormatDocumentParser.SOLR_INPUT_DOCUMENT_IDENTIFIER_KEY;
import static saul.rodriguez.naranjo.practica1.motores.de.busqueda.indexing.parse.CorpusFormatDocumentParser.SOLR_INPUT_DOCUMENT_TEXT_KEY;
import static saul.rodriguez.naranjo.practica1.motores.de.busqueda.indexing.parse.CorpusFormatDocumentParser.SOLR_INPUT_DOCUMENT_TITLE_KEY;

/**
 *
 * @author Saúl Rodríguez Naranjo
 */
public class GateCorpusFormatDocumentParser implements DocumentParser
{

    public static final String SOLR_INPUT_DOCUMENT_PERSON_KEY = "person";
    public static final String SOLR_INPUT_DOCUMENT_ORGANIZATION_KEY = "organization";
    public static final String SOLR_INPUT_DOCUMENT_DATE_KEY = "date";
    
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

        if (!pathToDocument.toAbsolutePath().toString().toLowerCase().endsWith(".xml"))
        {
            throw new WrongDocumentExtensionException("The extension of the document must be .xml");
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
                if (line.trim().split(" ")[0].equals(CORPUS_DOCUMENT_IDENTIFIER))
                {
                    solrInputDocument = new SolrInputDocument();

                    String documentIdentifier = line.split(" ")[1];

                    solrInputDocument.addField(SOLR_INPUT_DOCUMENT_IDENTIFIER_KEY, documentIdentifier);

                    documentTitle = new StringBuilder();
                    documentAuthor = new StringBuilder();
                    documentText = new StringBuilder();

                    //Immediately start the next iteration
                    continue;
                } else if (line.trim().equals(CORPUS_DOCUMENT_TITLE))
                {
                    parsingTitle = true;
                    parsingAuthor = false;
                    parsingText = false;

                    //Immediately start the next iteration
                    continue;
                } else if (line.trim().equals(CORPUS_DOCUMENT_AUTHOR))
                {
                    parsingTitle = false;
                    parsingAuthor = true;
                    parsingText = false;

                    //Immediately start the next iteration
                    continue;
                } else if (line.trim().equals(CORPUS_DOCUMENT_TEXT))
                {
                    parsingTitle = false;
                    parsingAuthor = false;
                    parsingText = true;

                    //Immediately start the next iteration
                    continue;
                } else if (line.trim().equals(CORPUS_DOCUMENT_UNKNOWN))
                {
                    parsingTitle = false;
                    parsingAuthor = false;
                    parsingText = false;

                    if (solrInputDocument != null)
                    {
                        String allPersons = "";
                        String allDates = "";
                        String allOrganizations = "";
                        
                        if (documentTitle != null)
                        {
                            solrInputDocument.addField(SOLR_INPUT_DOCUMENT_TITLE_KEY, escapeAllTagsFromText(documentTitle.toString()));
                            allPersons += selectAllPersonsFromText(documentTitle.toString()) + " ";
                            allDates += selectAllDatesFromText(documentTitle.toString()) + " ";
                            allOrganizations += selectAllOrganizationsFromText(documentTitle.toString()) + " ";
                        }

                        if (documentAuthor != null)
                        {
                            solrInputDocument.addField(SOLR_INPUT_DOCUMENT_AUTHOR_KEY, escapeAllTagsFromText(documentAuthor.toString()));
                            allPersons += selectAllPersonsFromText(documentAuthor.toString()) + " ";
                            allDates += selectAllDatesFromText(documentAuthor.toString()) + " ";
                            allOrganizations += selectAllOrganizationsFromText(documentAuthor.toString()) + " ";
                        }

                        if (documentText != null)
                        {
                            solrInputDocument.addField(SOLR_INPUT_DOCUMENT_TEXT_KEY, escapeAllTagsFromText(documentText.toString()));
                            allPersons += selectAllPersonsFromText(documentText.toString()) + " ";
                            allDates += selectAllDatesFromText(documentText.toString()) + " ";
                            allOrganizations += selectAllOrganizationsFromText(documentText.toString()) + " ";
                        }
                        
                        solrInputDocument.addField(SOLR_INPUT_DOCUMENT_PERSON_KEY, allPersons);
                        solrInputDocument.addField(SOLR_INPUT_DOCUMENT_ORGANIZATION_KEY, allOrganizations);
                        solrInputDocument.addField(SOLR_INPUT_DOCUMENT_DATE_KEY, allDates);

                        solrInputDocumentList.add(solrInputDocument);
                    }

                    //Immediately start the next iteration
                    continue;
                }

                if (parsingTitle)
                {
                    if (documentTitle != null)
                    {
                        documentTitle.append(line);
                    }
                }

                if (parsingAuthor)
                {
                    if (documentAuthor != null)
                    {
                        documentAuthor.append(line);
                    }
                }

                if (parsingText)
                {
                    if (documentText != null)
                    {
                        documentText.append(line);
                    }
                }
            }

        } catch (IOException ex)
        {
            Logger.getLogger(GateCorpusFormatDocumentParser.class.getName())
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
                Logger.getLogger(GateCorpusFormatDocumentParser.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }
        }

        return solrInputDocumentList;
    }

    private String selectAllPersonsFromText(String text)
    {
        String personPattern = "<Person[^>]*>(.*?)</Person>";
        Pattern pattern = Pattern.compile(personPattern);
        Matcher matcher = pattern.matcher(text);

        String allPersons = "";

        while (matcher.find())
        {
            String person = matcher.group(1);
            System.out.println("Contenido encontrado: " + person);
            allPersons += person + " ";
        }

        return allPersons;
    }

    private String selectAllOrganizationsFromText(String text)
    {
        String organizationPattern = "<Organization[^>]*>(.*?)</Organization>";
        Pattern pattern = Pattern.compile(organizationPattern);
        Matcher matcher = pattern.matcher(text);

        String allOrganizations = "";

        while (matcher.find())
        {
            String organization = matcher.group(1);
            System.out.println("Contenido encontrado: " + organization);
            allOrganizations += organization + " ";
        }

        return allOrganizations;
    }

    private String selectAllDatesFromText(String text)
    {
        String datesPattern = "<Date[^>]*>(.*?)</Date>";
        Pattern pattern = Pattern.compile(datesPattern);
        Matcher matcher = pattern.matcher(text);

        String allDates = "";

        while (matcher.find())
        {
            String date = matcher.group(1);
            System.out.println("Contenido encontrado: " + date);

            allDates += date + " ";
        }

        return allDates;
    }
    
    private String escapeAllTagsFromText(String text)
    {
        String escapedText;
        
        escapedText = text.replace("</Person>", "");
        escapedText = escapedText.replace("</Organization>", "");
        escapedText = escapedText.replace("</Date>", "");
        
        escapedText = escapedText.replaceAll("<Person[^>]*>", "");
        escapedText = escapedText.replaceAll("<Organization[^>]*>", "");
        escapedText = escapedText.replaceAll("<Date[^>]*>", "");
        
        return escapedText;
    }
}
