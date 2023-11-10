package saul.rodriguez.naranjo.practica1.motores.de.busqueda.trec.top.file;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NotDirectoryException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Writer for trec_top_file format using the Corpus data and formatting.
 *
 * @author Saúl Rodríguez Naranjo
 */
public class CorpusTrecTopFileWriter
{

    private static final String NOT_A_DIRECTORY_FILE_PATH_EXCEPTION_MESSAGE
            = "The file path provided is not a directory";

    /**
     * Writes a trec_top_file with the data passed by parameter in the path and
     * with the file name defined by the caller.
     *
     * <p>
     * Take into account that if the path provided for storing the trec_top_file
     * does not exist, all directories listed in the path will be created and
     * then the file stored in the last directory.
     * </p>
     *
     * @param entryData The data to be added to the trec_top_file.
     *
     * @param filePath The path where whe trec_top_file will be stored. Must be
     * a directory.
     *
     * @param fileName The trec_top_file name, without any file extension.
     *
     * @throws java.io.IOException
     */
    public void writeTrecTopFile(List<CorpusTrecTopFileData> entryData,
            String filePath, String fileName) throws IOException
    {
        checkPathAvailability(filePath);
        
        Path pathToDocument = Paths.get(filePath).toAbsolutePath();

        try ( BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter
        (new File(pathToDocument + File.separator +  fileName))))
        {
            for (CorpusTrecTopFileData corpusTrecTopFileData : entryData)
            {
                bufferedWriter.write(corpusTrecTopFileData.toTrecTopFileFormat() + "\n");
            }
        }
    }
    
    /**
     * Writes a trec_top_file with the data passed by parameter in the path
     * defined by the caller.
     *
     * @param entryData The data to be added to the trec_top_file.
     *
     * @param filePath The path where whe trec_top_file will be stored.
     *
     * @throws java.io.IOException
     */
    public void writeTrecTopFile(List<CorpusTrecTopFileData> entryData,
            String filePath) throws IOException
    {   
        Path pathToDocument = Paths.get(filePath).toAbsolutePath();

        try ( BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter
        (new File(pathToDocument.toString()))))
        {
            for (CorpusTrecTopFileData corpusTrecTopFileData : entryData)
            {
                bufferedWriter.write(corpusTrecTopFileData.toTrecTopFileFormat() + "\n");
            }
        }
    }

    /**
     * This method will check if the path provided exists and in case it does 
     * not, it will create all necessary folders until the end of the path.
     * 
     * <p>
     * In case the path provided exists, but it is not a directory, it will 
     * throw {@link NotDirectoryException}.
     * </p>
     * 
     * @param filePath The path where the file should be stored.
     * 
     * @throws IOException If an I/O error occurs.
     * 
     * @throws NotDirectoryException If the filePath points to something that it
     * is not a directory.
     */
    private void checkPathAvailability(String filePath) throws IOException
    {
        Path pathToDocument = Paths.get(filePath).toAbsolutePath();

        if (!Files.exists(pathToDocument))
        {
            Files.createDirectories(pathToDocument);
        }

        if (!Files.isDirectory(pathToDocument))
        {
            throw new NotDirectoryException(NOT_A_DIRECTORY_FILE_PATH_EXCEPTION_MESSAGE);
        }
    }
}
