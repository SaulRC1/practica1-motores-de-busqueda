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
