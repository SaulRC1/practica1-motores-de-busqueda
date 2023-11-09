package saul.rodriguez.naranjo.practica1.motores.de.busqueda.ui.query;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import saul.rodriguez.naranjo.practica1.motores.de.busqueda.ui.task.LoadingDialog;
import saul.rodriguez.naranjo.practica1.motores.de.busqueda.ui.worker.QueryApacheSolrCoreWorker;

/**
 *
 * @author Saúl Rodríguez Naranjo
 */
public class QueryDocumentsFromFilePanel extends JPanel
{

    private final List<SolrQuery> solrQueries;
    private final JFrame parentFrame;

    private SolrQueryTable solrQueryTable;

    private JPanel queryPanel;
    private JLabel queryIdLabel;
    private JComboBox queryIdComboBox;
    private JLabel queryCoreLabel;
    private JTextField queryCoreTextField;
    private JButton queryCoreButton;

    private static final String QUERY_ID_LABEL_TEXT = "Selecciona una consulta:";
    private static final String QUERY_CORE_LABEL_TEXT = "Core:";
    private static final String QUERY_CORE_BUTTON_TEXT = "Consultar";

    private static final String CORE_NAME_INVALID_TEXT = "El nombre del core no es válido.";
    private static final String CORE_NAME_INVALID_TITLE = "Core inválido";

    public QueryDocumentsFromFilePanel(List<SolrQuery> solrQueries, JFrame parentFrame)
    {
        super();
        this.solrQueries = solrQueries;
        this.parentFrame = parentFrame;
        this.setLayout(new BorderLayout());
    }

    public void initializeComponents()
    {
        solrQueryTable = new SolrQueryTable();
        solrQueryTable.initializeComponents();
        solrQueryTable.addSolrQueries(solrQueries);

        this.add(solrQueryTable, BorderLayout.CENTER);

        initializeQueryPanel();
    }

    private void initializeQueryPanel()
    {
        this.queryPanel = new JPanel();

        this.queryIdLabel = new JLabel(QUERY_ID_LABEL_TEXT);

        this.queryPanel.add(queryIdLabel);

        this.queryIdComboBox = new JComboBox();

        for (int i = 0; i < this.solrQueries.size(); i++)
        {
            this.queryIdComboBox.addItem(i);
        }

        this.queryIdComboBox.setPreferredSize(new Dimension(200, 30));
        this.queryIdComboBox.setMinimumSize(new Dimension(200, 30));

        this.queryPanel.add(queryIdComboBox);

        this.queryCoreLabel = new JLabel(QUERY_CORE_LABEL_TEXT);
        this.queryPanel.add(queryCoreLabel);

        this.queryCoreTextField = new JTextField();
        this.queryCoreTextField.setPreferredSize(new Dimension(200, 30));
        this.queryCoreTextField.setMinimumSize(new Dimension(200, 30));

        this.queryPanel.add(queryCoreTextField);

        this.queryCoreButton = new JButton(QUERY_CORE_BUTTON_TEXT);

        this.queryCoreButton.addActionListener((ActionEvent e) ->
        {
            queryCoreButtonAction();
        });

        this.queryPanel.add(queryCoreButton);

        this.add(queryPanel, BorderLayout.SOUTH);
    }

    private void queryCoreButtonAction()
    {
        if (validateInput())
        {
            SolrQuery solrQuery = this.solrQueries.get((int) this.queryIdComboBox.getSelectedItem());
            String coreName = this.queryCoreTextField.getText();

            QueryApacheSolrCoreWorker worker = new QueryApacheSolrCoreWorker(solrQuery, coreName);

            ImageIcon loadingImage = new ImageIcon("src/main/resources/icons/loading_spinner.gif");

            Image image = loadingImage.getImage();
            Image newimg = image.getScaledInstance(50, 50, java.awt.Image.SCALE_DEFAULT);
            loadingImage = new ImageIcon(newimg);

            final LoadingDialog loadingDialog = new LoadingDialog(loadingImage,
                    "Consultando Apache Solr, por favor, espere...",
                    400, 200, parentFrame, "Consultando Apache Solr");

            SwingUtilities.invokeLater(() ->
            {
                Thread thread = new Thread(() ->
                {
                    try
                    {
                        SolrDocumentList retrievedDocuments = worker.get();
                        
                        loadingDialog.dispose();
                        
                        RetrievedDocumentsFromApacheSolrDialog retrievedDocumentsFromApacheSolrDialog
                                = new RetrievedDocumentsFromApacheSolrDialog(parentFrame,
                                        solrQuery, coreName, retrievedDocuments);
                        
                        retrievedDocumentsFromApacheSolrDialog.initializeComponents();
                        
                        retrievedDocumentsFromApacheSolrDialog.setVisible(true);
                        
                    } catch (InterruptedException | ExecutionException ex)
                    {
                        loadingDialog.dispose();
                        
                        Logger.getLogger(QueryDocumentsFromFilePanel.class.getName())
                                .log(Level.SEVERE, ex.getMessage(), ex);
                        
                        JOptionPane.showMessageDialog(parentFrame,
                                    "Error al consultar Apache Solr " + ex.getMessage(),
                                    "Error", JOptionPane.ERROR_MESSAGE);
                    }
                });
                
                worker.execute();
                
                thread.start();

                loadingDialog.setVisible(true);
            });
        }
    }

    private boolean validateInput()
    {
        boolean validationResult = true;

        String coreName = this.queryCoreTextField.getText();

        if (coreName == null || coreName.isBlank() || coreName.isEmpty())
        {
            validationResult = false;

            SwingUtilities.invokeLater(() ->
            {
                JOptionPane.showMessageDialog(parentFrame,
                        CORE_NAME_INVALID_TEXT, CORE_NAME_INVALID_TITLE,
                        JOptionPane.ERROR_MESSAGE);
            });
        }

        return validationResult;
    }

}
