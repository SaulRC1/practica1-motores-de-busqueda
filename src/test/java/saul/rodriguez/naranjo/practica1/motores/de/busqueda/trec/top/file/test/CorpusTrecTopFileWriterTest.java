package saul.rodriguez.naranjo.practica1.motores.de.busqueda.trec.top.file.test;

import java.io.IOException;
import java.nio.file.NotDirectoryException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import saul.rodriguez.naranjo.practica1.motores.de.busqueda.trec.top.file.CorpusTrecTopFileData;
import saul.rodriguez.naranjo.practica1.motores.de.busqueda.trec.top.file.CorpusTrecTopFileWriter;

/**
 * Test class for {@link CorpusTrecTopFileWriter}
 * 
 * @author Saúl Rodríguez Naranjo
 */
public class CorpusTrecTopFileWriterTest
{
    private List<CorpusTrecTopFileData> corpusTrecTopFileDataList;
    
    private static final String PHASE = "TEST";
    
    private static final String TEAM = "TEST_TEAM";
    
    @BeforeEach
    public void initializeTestData()
    {
        corpusTrecTopFileDataList = new ArrayList<>();
        
        CorpusTrecTopFileData sampleData1 = new CorpusTrecTopFileData(1, 
                PHASE, 1, 0, 1, TEAM);
        
        CorpusTrecTopFileData sampleData2 = new CorpusTrecTopFileData(1, 
                PHASE, 2, 1, 0.75, TEAM);
        
        CorpusTrecTopFileData sampleData3 = new CorpusTrecTopFileData(1, 
                PHASE, 3, 2, 0.5, TEAM);
        
        CorpusTrecTopFileData sampleData4 = new CorpusTrecTopFileData(1, 
                PHASE, 4, 3, 0.25, TEAM);
        
        corpusTrecTopFileDataList.add(sampleData1);
        corpusTrecTopFileDataList.add(sampleData2);
        corpusTrecTopFileDataList.add(sampleData3);
        corpusTrecTopFileDataList.add(sampleData4);
    }
    
    @Test
    public void test_NotDirectoryException_thrown_when_providing_path_to_file()
    {
        Assertions.assertThrows(NotDirectoryException.class, () -> 
        {
            //Points to a file and not a directory
            String testPath = "src/main/resources/cisi/CISI.ALL";
        
            CorpusTrecTopFileWriter corpusTrecTopFileWriter = new CorpusTrecTopFileWriter();
        
            corpusTrecTopFileWriter.writeTrecTopFile(corpusTrecTopFileDataList, testPath, "Test");
        }); 
    }
    
    @Test
    public void test_correct_trec_top_file_creation_when_path_directories_do_not_exist()
    {
        String path = "C:\\TestCorpus\\Test1";
        
        CorpusTrecTopFileWriter corpusTrecTopFileWriter = new CorpusTrecTopFileWriter();
        
        try
        {
            corpusTrecTopFileWriter.writeTrecTopFile(corpusTrecTopFileDataList, 
                    path, "Test");
        } catch (IOException ex)
        {
            Logger.getLogger(CorpusTrecTopFileWriterTest.class.getName())
                    .log(Level.SEVERE, ex.getMessage(), ex);
        }
    }
}
