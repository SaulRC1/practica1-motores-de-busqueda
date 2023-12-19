package saul.rodriguez.naranjo.practica1.motores.de.busqueda.ui.worker;

import java.util.List;
import javax.swing.SwingWorker;
import org.apache.solr.common.SolrInputDocument;
import saul.rodriguez.naranjo.practica1.motores.de.busqueda.indexing.parse.DocumentParser;
import saul.rodriguez.naranjo.practica1.motores.de.busqueda.indexing.parse.GateCorpusFormatDocumentParser;

/**
 *
 * @author Saúl Rodríguez Naranjo
 */
public class LoadDocumentsFromGateCorpusFileWorker extends SwingWorker<List<SolrInputDocument>, Void>
{

    private final String filePath;

    public LoadDocumentsFromGateCorpusFileWorker(String filePath)
    {
        this.filePath = filePath;
    }

    @Override
    protected List<SolrInputDocument> doInBackground() throws Exception
    {
        DocumentParser documentParser = new GateCorpusFormatDocumentParser();
        
        return documentParser.parseDocument(filePath);
    }

}
