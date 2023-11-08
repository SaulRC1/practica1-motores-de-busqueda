package saul.rodriguez.naranjo.practica1.motores.de.busqueda.connector;

/**
 * Corpus connection to the Apache Solr server for safe access from the app.
 *
 * @author Saúl Rodríguez Naranjo
 */
public class CorpusConnection
{

    private static CorpusSolrConnector corpusSolrConnector;

    private static String httpSecurityProtocol;
    private static String ipAddress;
    private static String port;
    private static boolean validConnection;

    public static final String DEFAULT_HTTP_SECURITY_PROTOCOL = "http";
    public static final String DEFAULT_IP_ADDRESS = "localhost";
    public static final String DEFAULT_PORT = "8983";

    private static CorpusConnection corpusConnection = new CorpusConnection();
    
    public static final String NOT_VALID_CONNECTION_MESSAGE = "No está conectado a Apache Solr.";

    private CorpusConnection()
    {
        CorpusConnection.setHttpSecurityProtocol(CorpusConnection.DEFAULT_HTTP_SECURITY_PROTOCOL);
        CorpusConnection.setIpAddress(CorpusConnection.DEFAULT_IP_ADDRESS);
        CorpusConnection.setPort(CorpusConnection.DEFAULT_PORT);
        validConnection = false;
    }

    public static CorpusSolrConnector getCorpusSolrConnector()
    {
        return corpusSolrConnector;
    }

    public static synchronized void setCorpusSolrConnector(CorpusSolrConnector corpusSolrConnector)
    {
        CorpusConnection.corpusSolrConnector = corpusSolrConnector;
    }

    public static String getHttpSecurityProtocol()
    {
        return httpSecurityProtocol;
    }

    public static synchronized void setHttpSecurityProtocol(String httpSecurityProtocol)
    {
        CorpusConnection.httpSecurityProtocol = httpSecurityProtocol;
    }

    public static String getIpAddress()
    {
        return ipAddress;
    }

    public static synchronized void setIpAddress(String ipAddress)
    {
        CorpusConnection.ipAddress = ipAddress;
    }

    public static String getPort()
    {
        return port;
    }

    public static synchronized void setPort(String port)
    {
        CorpusConnection.port = port;
    }

    public static CorpusConnection getCorpusConnectionInstance()
    {
        return corpusConnection;
    }

    public static boolean isValidConnection()
    {
        return validConnection;
    }

    public static synchronized void setValidConnection(boolean validConnection)
    {
        CorpusConnection.validConnection = validConnection;
    }

}
