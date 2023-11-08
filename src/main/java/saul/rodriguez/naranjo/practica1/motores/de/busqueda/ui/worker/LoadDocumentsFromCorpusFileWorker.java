package saul.rodriguez.naranjo.practica1.motores.de.busqueda.ui.worker;

import java.util.List;
import javax.swing.SwingWorker;
import org.apache.solr.common.SolrInputDocument;
import saul.rodriguez.naranjo.practica1.motores.de.busqueda.indexing.parse.CorpusFormatDocumentParser;
import saul.rodriguez.naranjo.practica1.motores.de.busqueda.indexing.parse.DocumentParser;

/**
 *
 * @author Saúl Rodríguez Naranjo
 */
public class LoadDocumentsFromCorpusFileWorker extends SwingWorker<List<SolrInputDocument>, Void>
{
    private final String filePath;

    public LoadDocumentsFromCorpusFileWorker(String filePath)
    {
        this.filePath = filePath;
    }

    @Override
    protected List<SolrInputDocument> doInBackground() throws Exception
    {
        DocumentParser documentParser = new CorpusFormatDocumentParser();
        
        return documentParser.parseDocument(filePath);
    }

}
