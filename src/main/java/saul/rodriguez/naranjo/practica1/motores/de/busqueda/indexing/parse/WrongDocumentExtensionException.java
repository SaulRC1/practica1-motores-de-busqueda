package saul.rodriguez.naranjo.practica1.motores.de.busqueda.indexing.parse;

/**
 * This is an exception used when the extension of a document that must be parsed
 * is not expected/compatible with the currently used {@link DocumentParser}
 * 
 * @author Saúl Rodríguez Naranjo
 */
public class WrongDocumentExtensionException extends Exception
{

    public WrongDocumentExtensionException()
    {
        super();
    }

    public WrongDocumentExtensionException(String message)
    {
        super(message);
    }

    public WrongDocumentExtensionException(String message, Throwable cause)
    {
        super(message, cause);
    }
    
}
