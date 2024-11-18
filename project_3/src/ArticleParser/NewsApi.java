package ArticleParser;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * This class represents a response from NewsAPI containing status, total results, and a list of articles.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class NewsApi {

    private String status = "unkown"; //default to unknown status
    private int totalResults = 0; //default to 0 if not provided

    @JsonProperty("articles")
    private List<Article> articles;

    public NewsApi(String status, int totalResults, List<Article> articles) {
    	this.status = status;
    	this.totalResults = totalResults;
    	this.articles = articles;
    }
    
    /**
     * Getter for the status of the JSON response.
     * @return the status as a string.
     */
    public String getStatus() {
        return status;
    }

    /**
     * Getter for the total number of results in the JSON response.
     * @return the total number of results as an integer.
     */
    public int getTotalResults() {
        return totalResults;
    }

    /**
     * Getter for the list of articles in the JSON response.
     * @return a list of Article objects.
     */
    public List<Article> getArticles() {
        return articles;
    }

    @Override
    public String toString() {
        return "Status: " + status + "\nTotal Results: " + totalResults + "\nArticles: " + articles;
    }
}
