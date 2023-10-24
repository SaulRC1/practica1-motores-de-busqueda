package saul.rodriguez.naranjo.practica1.motores.de.busqueda.trec.top.file.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import saul.rodriguez.naranjo.practica1.motores.de.busqueda.indexing.parse.WrongDocumentExtensionException;
import saul.rodriguez.naranjo.practica1.motores.de.busqueda.trec.top.file.CorpusTrecTopFileData;

/**
 * Test class for {@link CorpusTrecTopFileData}
 *
 * @author Saúl Rodríguez Naranjo
 */
public class CorpusTrecTopFileDataTest
{
    private static final long CORPUS_TREC_TOP_FILE_DATA_TEST_QUERY_ID = 1;
    private static final String CORPUS_TREC_TOP_FILE_DATA_TEST_PHASE = "Q0";
    private static final long CORPUS_TREC_TOP_FILE_DATA_TEST_DOCUMENT_ID = 1;
    private static final long CORPUS_TREC_TOP_FILE_DATA_TEST_RANKING = 0;
    private static final double CORPUS_TREC_TOP_FILE_DATA_TEST_SCORE = 0.00178;
    private static final String CORPUS_TREC_TOP_FILE_DATA_TEST_TEAM = "TEST_TEAM";
    
    @Test
    public void test_illegal_argument_exception_thrown_when_lower_than_1_queryId()
    {
        Assertions.assertThrows(IllegalArgumentException.class, () ->
        {
            CorpusTrecTopFileData data = 
                    new CorpusTrecTopFileData(-1,
                            CORPUS_TREC_TOP_FILE_DATA_TEST_PHASE,
                            CORPUS_TREC_TOP_FILE_DATA_TEST_DOCUMENT_ID,
                            CORPUS_TREC_TOP_FILE_DATA_TEST_RANKING,
                            CORPUS_TREC_TOP_FILE_DATA_TEST_SCORE,
                            CORPUS_TREC_TOP_FILE_DATA_TEST_TEAM);
        });
    }
    
    @Test
    public void test_illegal_argument_exception_not_thrown_when_1_queryId()
    {
        Assertions.assertDoesNotThrow(() ->
        {
            CorpusTrecTopFileData data = 
                    new CorpusTrecTopFileData(1,
                            CORPUS_TREC_TOP_FILE_DATA_TEST_PHASE,
                            CORPUS_TREC_TOP_FILE_DATA_TEST_DOCUMENT_ID,
                            CORPUS_TREC_TOP_FILE_DATA_TEST_RANKING,
                            CORPUS_TREC_TOP_FILE_DATA_TEST_SCORE,
                            CORPUS_TREC_TOP_FILE_DATA_TEST_TEAM);
        });
    }
    
    @Test
    public void test_illegal_argument_exception_not_thrown_when_higher_than_1_queryId()
    {
        Assertions.assertDoesNotThrow(() ->
        {
            CorpusTrecTopFileData data = 
                    new CorpusTrecTopFileData(2,
                            CORPUS_TREC_TOP_FILE_DATA_TEST_PHASE,
                            CORPUS_TREC_TOP_FILE_DATA_TEST_DOCUMENT_ID,
                            CORPUS_TREC_TOP_FILE_DATA_TEST_RANKING,
                            CORPUS_TREC_TOP_FILE_DATA_TEST_SCORE,
                            CORPUS_TREC_TOP_FILE_DATA_TEST_TEAM);
        });
    }
    
    @Test
    public void test_illegal_argument_exception_thrown_when_lower_than_1_documentId()
    {
        Assertions.assertThrows(IllegalArgumentException.class, () ->
        {
            CorpusTrecTopFileData data = 
                    new CorpusTrecTopFileData(CORPUS_TREC_TOP_FILE_DATA_TEST_QUERY_ID,
                            CORPUS_TREC_TOP_FILE_DATA_TEST_PHASE,
                            0,
                            CORPUS_TREC_TOP_FILE_DATA_TEST_RANKING,
                            CORPUS_TREC_TOP_FILE_DATA_TEST_SCORE,
                            CORPUS_TREC_TOP_FILE_DATA_TEST_TEAM);
        });
    }
    
    @Test
    public void test_illegal_argument_exception_not_thrown_when_1_documentId()
    {
        Assertions.assertDoesNotThrow(() ->
        {
            CorpusTrecTopFileData data = 
                    new CorpusTrecTopFileData(CORPUS_TREC_TOP_FILE_DATA_TEST_QUERY_ID,
                            CORPUS_TREC_TOP_FILE_DATA_TEST_PHASE,
                            1,
                            CORPUS_TREC_TOP_FILE_DATA_TEST_RANKING,
                            CORPUS_TREC_TOP_FILE_DATA_TEST_SCORE,
                            CORPUS_TREC_TOP_FILE_DATA_TEST_TEAM);
        });
    }
    
    @Test
    public void test_illegal_argument_exception_not_thrown_when_higher_than_1_documentId()
    {
        Assertions.assertDoesNotThrow(() ->
        {
            CorpusTrecTopFileData data = 
                    new CorpusTrecTopFileData(CORPUS_TREC_TOP_FILE_DATA_TEST_QUERY_ID,
                            CORPUS_TREC_TOP_FILE_DATA_TEST_PHASE,
                            2,
                            CORPUS_TREC_TOP_FILE_DATA_TEST_RANKING,
                            CORPUS_TREC_TOP_FILE_DATA_TEST_SCORE,
                            CORPUS_TREC_TOP_FILE_DATA_TEST_TEAM);
        });
    }
    
    @Test
    public void test_illegal_argument_exception_thrown_when_lower_than_0_ranking()
    {
        Assertions.assertThrows(IllegalArgumentException.class, () ->
        {
            CorpusTrecTopFileData data = 
                    new CorpusTrecTopFileData(CORPUS_TREC_TOP_FILE_DATA_TEST_QUERY_ID,
                            CORPUS_TREC_TOP_FILE_DATA_TEST_PHASE,
                            CORPUS_TREC_TOP_FILE_DATA_TEST_DOCUMENT_ID,
                            -1,
                            CORPUS_TREC_TOP_FILE_DATA_TEST_SCORE,
                            CORPUS_TREC_TOP_FILE_DATA_TEST_TEAM);
        });
    }
    
    @Test
    public void test_illegal_argument_exception_not_thrown_when_0_ranking()
    {
        Assertions.assertDoesNotThrow(() ->
        {
            CorpusTrecTopFileData data = 
                    new CorpusTrecTopFileData(CORPUS_TREC_TOP_FILE_DATA_TEST_QUERY_ID,
                            CORPUS_TREC_TOP_FILE_DATA_TEST_PHASE,
                            CORPUS_TREC_TOP_FILE_DATA_TEST_DOCUMENT_ID,
                            0,
                            CORPUS_TREC_TOP_FILE_DATA_TEST_SCORE,
                            CORPUS_TREC_TOP_FILE_DATA_TEST_TEAM);
        });
    }
    
    @Test
    public void test_illegal_argument_exception_not_thrown_when_higher_than_0_ranking()
    {
        Assertions.assertDoesNotThrow(() ->
        {
            CorpusTrecTopFileData data = 
                    new CorpusTrecTopFileData(CORPUS_TREC_TOP_FILE_DATA_TEST_QUERY_ID,
                            CORPUS_TREC_TOP_FILE_DATA_TEST_PHASE,
                            CORPUS_TREC_TOP_FILE_DATA_TEST_DOCUMENT_ID,
                            1,
                            CORPUS_TREC_TOP_FILE_DATA_TEST_SCORE,
                            CORPUS_TREC_TOP_FILE_DATA_TEST_TEAM);
        });
    }
    
    @Test
    public void test_illegal_argument_exception_thrown_when_null_phase()
    {
        Assertions.assertThrows(IllegalArgumentException.class, () ->
        {
            CorpusTrecTopFileData data = 
                    new CorpusTrecTopFileData(CORPUS_TREC_TOP_FILE_DATA_TEST_QUERY_ID,
                            null,
                            CORPUS_TREC_TOP_FILE_DATA_TEST_DOCUMENT_ID,
                            CORPUS_TREC_TOP_FILE_DATA_TEST_RANKING,
                            CORPUS_TREC_TOP_FILE_DATA_TEST_SCORE,
                            CORPUS_TREC_TOP_FILE_DATA_TEST_TEAM);
        });
    }
    
    @Test
    public void test_illegal_argument_exception_thrown_when_blank_phase()
    {
        Assertions.assertThrows(IllegalArgumentException.class, () ->
        {
            CorpusTrecTopFileData data = 
                    new CorpusTrecTopFileData(CORPUS_TREC_TOP_FILE_DATA_TEST_QUERY_ID,
                            "    ",
                            CORPUS_TREC_TOP_FILE_DATA_TEST_DOCUMENT_ID,
                            CORPUS_TREC_TOP_FILE_DATA_TEST_RANKING,
                            CORPUS_TREC_TOP_FILE_DATA_TEST_SCORE,
                            CORPUS_TREC_TOP_FILE_DATA_TEST_TEAM);
        });
    }
    
    @Test
    public void test_illegal_argument_exception_thrown_when_empty_phase()
    {
        Assertions.assertThrows(IllegalArgumentException.class, () ->
        {
            CorpusTrecTopFileData data = 
                    new CorpusTrecTopFileData(CORPUS_TREC_TOP_FILE_DATA_TEST_QUERY_ID,
                            "",
                            CORPUS_TREC_TOP_FILE_DATA_TEST_DOCUMENT_ID,
                            CORPUS_TREC_TOP_FILE_DATA_TEST_RANKING,
                            CORPUS_TREC_TOP_FILE_DATA_TEST_SCORE,
                            CORPUS_TREC_TOP_FILE_DATA_TEST_TEAM);
        });
    }
    
    @Test
    public void test_illegal_argument_exception_thrown_when_null_team()
    {
        Assertions.assertThrows(IllegalArgumentException.class, () ->
        {
            CorpusTrecTopFileData data = 
                    new CorpusTrecTopFileData(CORPUS_TREC_TOP_FILE_DATA_TEST_QUERY_ID,
                            CORPUS_TREC_TOP_FILE_DATA_TEST_PHASE,
                            CORPUS_TREC_TOP_FILE_DATA_TEST_DOCUMENT_ID,
                            CORPUS_TREC_TOP_FILE_DATA_TEST_RANKING,
                            CORPUS_TREC_TOP_FILE_DATA_TEST_SCORE,
                            null);
        });
    }
    
    @Test
    public void test_illegal_argument_exception_thrown_when_blank_team()
    {
        Assertions.assertThrows(IllegalArgumentException.class, () ->
        {
            CorpusTrecTopFileData data = 
                    new CorpusTrecTopFileData(CORPUS_TREC_TOP_FILE_DATA_TEST_QUERY_ID,
                            CORPUS_TREC_TOP_FILE_DATA_TEST_PHASE,
                            CORPUS_TREC_TOP_FILE_DATA_TEST_DOCUMENT_ID,
                            CORPUS_TREC_TOP_FILE_DATA_TEST_RANKING,
                            CORPUS_TREC_TOP_FILE_DATA_TEST_SCORE,
                            "   ");
        });
    }
    
    @Test
    public void test_illegal_argument_exception_thrown_when_empty_team()
    {
        Assertions.assertThrows(IllegalArgumentException.class, () ->
        {
            CorpusTrecTopFileData data = 
                    new CorpusTrecTopFileData(CORPUS_TREC_TOP_FILE_DATA_TEST_QUERY_ID,
                            CORPUS_TREC_TOP_FILE_DATA_TEST_PHASE,
                            CORPUS_TREC_TOP_FILE_DATA_TEST_DOCUMENT_ID,
                            CORPUS_TREC_TOP_FILE_DATA_TEST_RANKING,
                            CORPUS_TREC_TOP_FILE_DATA_TEST_SCORE,
                            "");
        });
    }
    
    @Test
    public void test_illegal_argument_exception_thrown_when_lower_than_0_score()
    {
        Assertions.assertThrows(IllegalArgumentException.class, () ->
        {
            CorpusTrecTopFileData data = 
                    new CorpusTrecTopFileData(CORPUS_TREC_TOP_FILE_DATA_TEST_QUERY_ID,
                            CORPUS_TREC_TOP_FILE_DATA_TEST_PHASE,
                            CORPUS_TREC_TOP_FILE_DATA_TEST_DOCUMENT_ID,
                            CORPUS_TREC_TOP_FILE_DATA_TEST_RANKING,
                            -1,
                            CORPUS_TREC_TOP_FILE_DATA_TEST_TEAM);
        });
    }
    
    @Test
    public void test_illegal_argument_exception_not_thrown_when_0_score()
    {
        Assertions.assertDoesNotThrow(() ->
        {
            CorpusTrecTopFileData data = 
                    new CorpusTrecTopFileData(CORPUS_TREC_TOP_FILE_DATA_TEST_QUERY_ID,
                            CORPUS_TREC_TOP_FILE_DATA_TEST_PHASE,
                            CORPUS_TREC_TOP_FILE_DATA_TEST_DOCUMENT_ID,
                            CORPUS_TREC_TOP_FILE_DATA_TEST_RANKING,
                            0,
                            CORPUS_TREC_TOP_FILE_DATA_TEST_TEAM);
        });
    }
    
    @Test
    public void test_illegal_argument_exception_not_thrown_when_higher_than_0_score()
    {
        Assertions.assertDoesNotThrow(() ->
        {
            CorpusTrecTopFileData data = 
                    new CorpusTrecTopFileData(CORPUS_TREC_TOP_FILE_DATA_TEST_QUERY_ID,
                            CORPUS_TREC_TOP_FILE_DATA_TEST_PHASE,
                            CORPUS_TREC_TOP_FILE_DATA_TEST_DOCUMENT_ID,
                            CORPUS_TREC_TOP_FILE_DATA_TEST_RANKING,
                            0.0200,
                            CORPUS_TREC_TOP_FILE_DATA_TEST_TEAM);
        });
    }
    
}
