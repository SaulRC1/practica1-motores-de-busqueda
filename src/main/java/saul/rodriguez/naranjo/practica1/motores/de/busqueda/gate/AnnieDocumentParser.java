package saul.rodriguez.naranjo.practica1.motores.de.busqueda.gate;

import gate.CorpusController;
import gate.Gate;
import java.io.File;

/**
 * This class will parse documents with GATE's ANNIE plugin.
 * 
 * @author Saúl Rodríguez Naranjo
 */
public class AnnieDocumentParser 
{
    private CorpusController annieController;
    
    private static final String ANNIE_PLUGIN_NAME = "ANNIE";
    private static final String ANNIE_GAPP_FILE_NAME = "ANNIE";
    
    public void initAnnie()
    {
        File pluginsHome = Gate.getPluginsHome();
        File anniePlugin = new File(pluginsHome, ANNIE_PLUGIN_NAME);
        File annieGapp = new File(pluginsHome, ANNIE_GAPP_FILE_NAME);
    }
}
