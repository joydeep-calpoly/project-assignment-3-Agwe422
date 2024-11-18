package ArticleParser;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;


abstract class ArticleParser {
	 protected final ObjectMapper objectMapper;
	    protected final Logger logger;

	    protected ArticleParser(Logger logger) {
	        this.logger = logger;
	        this.objectMapper = new ObjectMapper();
	        this.objectMapper.registerModule(new JavaTimeModule());
	    }

	    /**
	     * Parses the JSON and returns a NewsApi object.
	     * @return a NewsApi object containing parsed articles or an empty one if parsing fails.
	     */
	    public NewsApi parse() {
	        try {
	            String jsonContent = loadJsonContent();
	            if (jsonContent == null) {
	                return emptyNewsApi();
	            }

	            JsonNode rootNode = objectMapper.readTree(jsonContent);

	            if (rootNode.has("articles") && rootNode.get("articles").isArray()) {
	                List<Article> articles = new ArrayList<>();
	                for (JsonNode node : rootNode.get("articles")) {
	                	
	                	
	                    
	                	Article article = objectMapper.treeToValue(node, Article.class);
	                    if (article.getTitle() != null && article.getDescription() != null) {
	                        articles.add(article);
	                    } else {
	                    	System.out.println(node);
	                        logger.warning("Skipped an article with missing essential fields.");
	                    }
//	                    if (article.getTitle() != null && article.getDescription() != null) {
//	                        articles.add(article);
//	                    } else {
//	                        logger.warning("Skipped an article with missing essential fields.");
//	                    }
	                }
	                return new NewsApi("ok", articles.size(), articles);
	            } else if (rootNode.has("title") && rootNode.has("description")) {
	                Article article = objectMapper.treeToValue(rootNode, Article.class);
	                return new NewsApi("ok", 1, List.of(article));
	            } else {
	                logger.warning("Unknown JSON format: Unable to parse articles.");
	                return emptyNewsApi();
	            }
	        } catch (IOException e) {
	            logger.warning("Failed to parse JSON: " + e.getMessage());
	            return emptyNewsApi();
	        }
	    }

	    /**
	     * Abstract method to load JSON content, implemented by subclasses.
	     * @return the JSON content as a string, or null if an error occurs.
	     */
	    protected abstract String loadJsonContent() throws IOException;

	    /**
	     * Creates an empty NewsApi object.
	     * @return a NewsApi object with empty status, zero total results, and an empty article list.
	     */
	    protected NewsApi emptyNewsApi() {
	        return new NewsApi("error", 0, new ArrayList<>());
	    }

}
