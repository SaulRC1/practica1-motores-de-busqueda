package saul.rodriguez.naranjo.practica1.motores.de.busqueda.ui.worker;

import java.util.ArrayList;
import java.util.List;
import javax.swing.SwingWorker;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import saul.rodriguez.naranjo.practica1.motores.de.busqueda.connector.CorpusConnection;
import saul.rodriguez.naranjo.practica1.motores.de.busqueda.connector.CorpusSolrConnector;
import saul.rodriguez.naranjo.practica1.motores.de.busqueda.indexing.parse.GateCorpusFormatDocumentParser;
import saul.rodriguez.naranjo.practica1.motores.de.busqueda.trec.top.file.CorpusTrecTopFileData;
import saul.rodriguez.naranjo.practica1.motores.de.busqueda.trec.top.file.CorpusTrecTopFileWriter;

/**
 *
 * @author Saúl Rodríguez Naranjo
 */
public class BuildGateTrecTopFileWorker extends SwingWorker<Void, Void>
{
    private final List<SolrQuery> solrQueries;
    private final String trecTopFilePath;

    public BuildGateTrecTopFileWorker(List<SolrQuery> solrQueries, String trecTopFilePath)
    {
        this.solrQueries = solrQueries;
        this.trecTopFilePath = trecTopFilePath;
    }

    @Override
    protected Void doInBackground() throws Exception
    {
        if(!CorpusConnection.isValidConnection())
        {
            throw new Exception(CorpusConnection.NOT_VALID_CONNECTION_MESSAGE);
        }
        
        CorpusSolrConnector corpusSolrConnector = CorpusConnection.getCorpusSolrConnector();
        
        long queryId = 1;
        
        List<CorpusTrecTopFileData> trecTopFileData = new ArrayList<>();
        
        for (SolrQuery solrQuery : solrQueries)
        {
            solrQuery.addField(GateCorpusFormatDocumentParser.SOLR_INPUT_DOCUMENT_PERSON_KEY);
            solrQuery.addField(GateCorpusFormatDocumentParser.SOLR_INPUT_DOCUMENT_ORGANIZATION_KEY);
            solrQuery.addField(GateCorpusFormatDocumentParser.SOLR_INPUT_DOCUMENT_DATE_KEY);
            
            SolrDocumentList documentList = corpusSolrConnector
                    .queryCore("micolecciongate", solrQuery);

            List<CorpusTrecTopFileData> corpusTrecTopFileDataList
                    = CorpusTrecTopFileData.fromSolrDocumentList(documentList, queryId, "Q0", "ETSI");

            trecTopFileData.addAll(corpusTrecTopFileDataList);
            
            queryId++;
        }
        
        CorpusTrecTopFileWriter corpusTrecTopFileWriter = new CorpusTrecTopFileWriter();
        
        corpusTrecTopFileWriter.writeTrecTopFile(trecTopFileData, trecTopFilePath);
        
        return null;
    }

}
