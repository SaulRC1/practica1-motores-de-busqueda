package saul.rodriguez.naranjo.practica1.motores.de.busqueda;

import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import saul.rodriguez.naranjo.practica1.motores.de.busqueda.connector.CorpusConnection;
import saul.rodriguez.naranjo.practica1.motores.de.busqueda.connector.CorpusSolrConnector;
import saul.rodriguez.naranjo.practica1.motores.de.busqueda.indexing.parse.CorpusFormatDocumentParser;
import saul.rodriguez.naranjo.practica1.motores.de.busqueda.indexing.parse.DocumentParser;
import saul.rodriguez.naranjo.practica1.motores.de.busqueda.indexing.parse.WrongDocumentExtensionException;
import saul.rodriguez.naranjo.practica1.motores.de.busqueda.query.parse.CorpusFormatQueryDocumentParser;
import saul.rodriguez.naranjo.practica1.motores.de.busqueda.query.parse.QueryDocumentParser;
import saul.rodriguez.naranjo.practica1.motores.de.busqueda.trec.top.file.CorpusTrecTopFileData;
import saul.rodriguez.naranjo.practica1.motores.de.busqueda.trec.top.file.CorpusTrecTopFileWriter;
import saul.rodriguez.naranjo.practica1.motores.de.busqueda.ui.worker.ApacheSolrConnectionWorker;
import saul.rodriguez.naranjo.practica1.motores.de.busqueda.ui.MainFrame;
import saul.rodriguez.naranjo.practica1.motores.de.busqueda.ui.task.LoadingDialog;

/**
 *
 * @author Saúl Rodríguez Naranjo
 */
public class Practica1MotoresDeBusqueda
{

    public static void main(String[] args) throws SolrServerException, IOException, WrongDocumentExtensionException
    {
        final SolrClient client = new HttpSolrClient.Builder("http://localhost:8983/solr").build();
        /*
        DocumentParser corpusDocumentParser = new CorpusFormatDocumentParser();
        
        List<SolrInputDocument> solrInputDocumentList = corpusDocumentParser.parseDocument("src/main/resources/cisi/CISI.ALL");
        
        for (SolrInputDocument solrInputDocument : solrInputDocumentList)
        {
            System.out.println("Adding " + solrInputDocument.getFieldValue(CorpusFormatDocumentParser.SOLR_INPUT_DOCUMENT_IDENTIFIER_KEY));
            
            final UpdateResponse updateResponse = client.add("micoleccion", solrInputDocument);
        }
        
        client.commit("micoleccion");*/

 /*QueryDocumentParser corpusQueryDocumentParser = new CorpusFormatQueryDocumentParser();
        
        List<String> queries = corpusQueryDocumentParser.parseQueryDocument("src/main/resources/cisi/CISI.QRY");
        
        for (String query : queries)
        {
            System.out.println("Query: " + query);
        }*/
        //CorpusSolrConnector corpusSolrConnector = new CorpusSolrConnector(client);

        /*Set<String> allCores = corpusSolrConnector.getAllCores();
        
        for (String core : allCores)
        {
            System.out.println("Core: " + core);
        }*/
 /*boolean coreExists = corpusSolrConnector.coreExists("micoleccion");
        
        if(coreExists)
        {
            System.out.println("Core exists");
        }*/
 /*QueryDocumentParser corpusQueryDocumentParser = new CorpusFormatQueryDocumentParser();

        List<String> queryStrings = corpusQueryDocumentParser.parseQueryDocument("src/main/resources/cisi/CISI.QRY");

        List<CorpusTrecTopFileData> trecTopFileData = new ArrayList<>();

        CorpusTrecTopFileWriter corpusTrecTopFileWriter = new CorpusTrecTopFileWriter();

        long queryId = 1;

        for (String queryString : queryStrings)
        {
            //Only the first 5 words
            String[] splittedQueryString = queryString.split(" ");

            queryString = "";

            for (int i = 0; i < splittedQueryString.length; i++)
            {
                if (i == 5)
                {
                    break;
                }

                queryString += splittedQueryString[i] + " ";
            }

            System.out.println("Query String: " + queryString);

            SolrQuery solrQuery = corpusQueryDocumentParser.buildStandardDocumentQuery(queryString);

            SolrDocumentList documentList = corpusSolrConnector
                    .queryCore("micoleccion", solrQuery);

            for (SolrDocument solrDocument : documentList)
            {
                System.out.println("Solr Doc: " + solrDocument);
            }

            List<CorpusTrecTopFileData> corpusTrecTopFileDataList
                    = CorpusTrecTopFileData.fromSolrDocumentList(documentList, queryId, "Q0", "ETSI");

            trecTopFileData.addAll(corpusTrecTopFileDataList);

            for (CorpusTrecTopFileData corpusTrecTopFileData : corpusTrecTopFileDataList)
            {
                System.out.println(corpusTrecTopFileData);
            }

            queryId++;
        }

        corpusTrecTopFileWriter.writeTrecTopFile(trecTopFileData,
                "C:\\TestCorpus", "trec_top_file");*/
        SwingWorker worker = new ApacheSolrConnectionWorker(CorpusConnection.DEFAULT_HTTP_SECURITY_PROTOCOL,
        CorpusConnection.DEFAULT_IP_ADDRESS, CorpusConnection.DEFAULT_PORT);

        // Create the GUI on the event-dispatching thread
        SwingUtilities.invokeLater(() ->
        {
            try
            {
                UIManager.setLookAndFeel(NimbusLookAndFeel.class.getName());

            } catch (ClassNotFoundException | IllegalAccessException
                    | InstantiationException | UnsupportedLookAndFeelException e)
            {
                Logger.getLogger(Practica1MotoresDeBusqueda.class.getName())
                        .log(Level.SEVERE, e.getMessage(), e);
            }

            MainFrame mainFrame = new MainFrame();

            mainFrame.initComponents();

            mainFrame.pack();

            //Set the frame location in the center of the screen
            mainFrame.setLocationRelativeTo(null);

            mainFrame.setVisible(true);

            ImageIcon loadingImage = new ImageIcon("src/main/resources/icons/loading_spinner.gif");

            Image image = loadingImage.getImage(); // transform it
            Image newimg = image.getScaledInstance(50, 50, java.awt.Image.SCALE_DEFAULT); // scale it the smooth way
            loadingImage = new ImageIcon(newimg);  // transform it back

            LoadingDialog loadingDialog = new LoadingDialog(loadingImage,
                    "Conectándose a Apache Solr, por favor espere...", 400, 200, mainFrame,
                    "Conectándose a Apache Solr");
            
            worker.execute();

            Thread thread = new Thread(() ->
            {
                if (!SwingUtilities.isEventDispatchThread())
                {
                    try
                    {
                        Thread.sleep(2000);
                        
                        CorpusSolrConnector corpusSolrConnector = (CorpusSolrConnector) worker.get();

                        CorpusConnection.setCorpusSolrConnector(corpusSolrConnector);
                        CorpusConnection.setValidConnection(true);

                        SwingUtilities.invokeLater(() ->
                        {   
                            loadingDialog.dispose();
                            
                            mainFrame.initializeConnectedContentPane();

                            JOptionPane.showMessageDialog(mainFrame,
                                    "Conectado a Apache Solr correctamente", "Conectado", JOptionPane.INFORMATION_MESSAGE);
                        });

                    } catch (InterruptedException | ExecutionException ex)
                    {
                        Logger.getLogger(Practica1MotoresDeBusqueda.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);

                        CorpusConnection.setValidConnection(false);
                        
                        SwingUtilities.invokeLater(() ->
                        {   
                            loadingDialog.dispose();
                            
                            mainFrame.initializeDisconnectedContentPane();

                            JOptionPane.showMessageDialog(mainFrame, "No se ha podido conectar a Apache Solr.",
                                    "Error en la conexión", JOptionPane.ERROR_MESSAGE);
                        });

                    } finally
                    {
                        //loadingDialog.dispose();
                    }
                }
            });

            thread.start();

            loadingDialog.setVisible(true);
        });

    }
}
