package saul.rodriguez.naranjo.practica1.motores.de.busqueda.trec.top.file;

import java.util.ArrayList;
import java.util.List;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.params.HighlightParams;
import saul.rodriguez.naranjo.practica1.motores.de.busqueda.indexing.parse.CorpusFormatDocumentParser;

/**
 * This class represents the data that should be inserted for every query in a
 * trec_top_file.
 *
 * @author Saúl Rodríguez Naranjo
 */
public class CorpusTrecTopFileData
{

    private long queryId;
    private String phase;
    private long documentId;
    private long ranking;
    private double score;
    private String team;

    private static final String ILLEGAL_QUERY_ID_EXCEPTION_MESSAGE
            = "queryId argument must be equal or greater than 1.";

    private static final String ILLEGAL_DOCUMENT_ID_EXCEPTION_MESSAGE
            = "documentId argument must be equal or greater than 1";

    private static final String ILLEGAL_RANKING_EXCEPTION_MESSAGE
            = "ranking argument must be equal or greater than 0";

    private static final String ILLEGAL_SCORE_EXCEPTION_MESSAGE
            = "score argument must be equal or greater than 0";

    private static final String ILLEGAL_PHASE_EXCEPTION_MESSAGE
            = "phase argument must not be null nor empty";

    private static final String ILLEGAL_TEAM_EXCEPTION_MESSAGE
            = "team argument must not be null nor empty";

    /**
     * Will build a trec_top_file data for the corpus format.
     *
     * @param queryId The query id that was executed against the Apache Solr
     * server. Must be equal or greater than 1.
     * @param phase The phase of the competition. Must not be null nor empty.
     * @param documentId The document id. Must be equal or greater than 1.
     * @param ranking The ranking of the document. Must be equal or greater than
     * 0.
     * @param score The score of the document. Must be equal or greater than 0.
     * @param team The team that performed the query. Must not be null nor
     * empty.
     */
    public CorpusTrecTopFileData(long queryId, String phase, long documentId,
            long ranking, double score, String team)
    {
        if (queryId < 1)
        {
            throw new IllegalArgumentException(ILLEGAL_QUERY_ID_EXCEPTION_MESSAGE);
        }

        if (documentId < 1)
        {
            throw new IllegalArgumentException(ILLEGAL_DOCUMENT_ID_EXCEPTION_MESSAGE);
        }

        if (ranking < 0)
        {
            throw new IllegalArgumentException(ILLEGAL_RANKING_EXCEPTION_MESSAGE);
        }

        if (score < 0)
        {
            throw new IllegalArgumentException(ILLEGAL_SCORE_EXCEPTION_MESSAGE);
        }

        if (phase == null || phase.isBlank() || phase.isEmpty())
        {
            throw new IllegalArgumentException(ILLEGAL_PHASE_EXCEPTION_MESSAGE);
        }

        if (team == null || team.isBlank() || team.isEmpty())
        {
            throw new IllegalArgumentException(ILLEGAL_TEAM_EXCEPTION_MESSAGE);
        }

        this.queryId = queryId;
        this.phase = phase.trim();
        this.documentId = documentId;
        this.ranking = ranking;
        this.score = score;
        this.team = team.trim();
    }

    /**
     * This method will return the query id, for the query that performed the
     * execution against Apache Solr server.
     *
     * @return The query id.
     */
    public long getQueryId()
    {
        return queryId;
    }

    /**
     * This method will set the query id, for the query that performed the
     * execution against the Apache Solr server.
     *
     * @param queryId The query id.
     */
    public void setQueryId(long queryId)
    {
        if (queryId < 1)
        {
            throw new IllegalArgumentException(ILLEGAL_QUERY_ID_EXCEPTION_MESSAGE);
        }

        this.queryId = queryId;
    }

    /**
     * This method will get the phase of the competition.
     *
     * @return The phase of the competition.
     */
    public String getPhase()
    {
        return phase;
    }

    /**
     * This method will set the phase of the competition.
     *
     * @param phase The phase of the competition. Must not be null nor empty.
     */
    public void setPhase(String phase)
    {
        if (phase == null || phase.isBlank() || phase.isEmpty())
        {
            throw new IllegalArgumentException(ILLEGAL_PHASE_EXCEPTION_MESSAGE);
        }

        this.phase = phase.trim();
    }

    /**
     * This method will return the document id.
     *
     * @return The document id.
     */
    public long getDocumentId()
    {
        return documentId;
    }

    /**
     * This method will set the document id.
     *
     * @param documentId The document id. Must be equal or greater than 1.
     */
    public void setDocumentId(long documentId)
    {
        if (documentId < 1)
        {
            throw new IllegalArgumentException(ILLEGAL_DOCUMENT_ID_EXCEPTION_MESSAGE);
        }

        this.documentId = documentId;
    }

    /**
     * Will get the ranking of the document.
     *
     * <p>
     * Ranking is the position where the document is located among the rest
     * returned by the same {@link SolrQuery} based on its score.
     * </p>
     *
     * @return The ranking of the document.
     */
    public long getRanking()
    {
        return ranking;
    }

    /**
     * Will set the ranking of the document.
     *
     * <p>
     * Ranking is the position where the document is located among the rest
     * returned by the same {@link SolrQuery} based on its score.
     * </p>
     *
     * @param ranking The ranking of the document. Must be equal or greater than
     * 0.
     */
    public void setRanking(long ranking)
    {
        if (ranking < 0)
        {
            throw new IllegalArgumentException(ILLEGAL_RANKING_EXCEPTION_MESSAGE);
        }

        this.ranking = ranking;
    }

    /**
     * Will get the score of the document.
     *
     * @return The score of the document.
     */
    public double getScore()
    {
        return score;
    }

    /**
     * Will set the score of the document.
     *
     * @param score The score of the document. Must be equal or greater than 0.
     */
    public void setScore(double score)
    {
        if (score < 0)
        {
            throw new IllegalArgumentException(ILLEGAL_SCORE_EXCEPTION_MESSAGE);
        }

        this.score = score;
    }

    /**
     * Will return the team that performed the query.
     *
     * @return The team that performed the query.
     */
    public String getTeam()
    {
        return team;
    }

    /**
     * Will set the team that has performed the query.
     *
     * @param team The team that has performed the query. Must not be null nor
     * empty.
     */
    public void setTeam(String team)
    {
        if (team == null || team.isBlank() || team.isEmpty())
        {
            throw new IllegalArgumentException(ILLEGAL_TEAM_EXCEPTION_MESSAGE);
        }

        this.team = team.trim();
    }

    /**
     * Builds a series of {@link CorpusTrecTopFileData} from a
     * {@link SolrDocumentList}.
     *
     * @param solrDocuments The {@link SolrDocumentList} to be converted.
     *
     * @param queryId The queryId for the query that got returned the
     * {@link SolrDocumentList} passed by parameter.
     *
     * @param phase The phase of the competition.
     *
     * @param team The team that executed the query.
     *
     * @return List of {@link CorpusTrecTopFileData}
     */
    public static List<CorpusTrecTopFileData> fromSolrDocumentList(SolrDocumentList solrDocuments,
            long queryId, String phase, String team)
    {
        List<CorpusTrecTopFileData> corpusTrecTopFileDataList = new ArrayList<>();

        //The documents come ordered from the highest score to the lowest.
        long ranking = 0;

        for (SolrDocument solrDocument : solrDocuments)
        {
            double documentScore = (float) solrDocument.getFieldValue(HighlightParams.SCORE);
            long solrDocumentId = ((ArrayList<Long>) solrDocument.getFieldValue(
                    CorpusFormatDocumentParser.SOLR_INPUT_DOCUMENT_IDENTIFIER_KEY)).get(0);
            
            CorpusTrecTopFileData corpusTrecTopFileData
                    = new CorpusTrecTopFileData(queryId, phase, solrDocumentId, ranking,
                            documentScore, team);

            corpusTrecTopFileDataList.add(corpusTrecTopFileData);

            ranking++;
        }

        return corpusTrecTopFileDataList;
    }

    @Override
    public String toString()
    {
        return "CorpusTrecTopFileData{" + "queryId=" + queryId + ", phase=" + 
                phase + ", documentId=" + documentId + ", ranking=" + ranking + 
                ", score=" + score + ", team=" + team + '}';
    }

    /**
     * Will return this {@link CorpusTrecTopFileData} formatted for the 
     * trec_top_file
     * 
     * @return this {@link CorpusTrecTopFileData} formatted for the 
     * trec_top_file
     */
    public String toTrecTopFileFormat()
    {
        String trecTopFileFormat = queryId + " " + phase + " " + documentId + " "
                + ranking + " " + score + " " + team;
        
        return trecTopFileFormat;
    }
}
