package ArticleParser;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Main {

    public static void main(String[] args) {
        Logger logger = setupLogger();

        // URLs and paths for each JSON source
        String newsApiUrl = "https://newsapi.org/v2/top-headlines?country=us&apiKey=ed4672185ae24959aaa5cb54252bd986";
        String newsApiJsonPath = "/Users/JoshH./IdeaProjects/project-assignment-3-Agwe422/project_3/src/inputs/newsapi.json";
        String simpleJsonPath = "/Users/JoshH./IdeaProjects/project-assignment-3-Agwe422/project_3/src/inputs/simple.json";

        try {
            // Parse and display articles from each source
            displayArticles("NewsAPI URL", new UrlArticleParser(newsApiUrl, logger));
            String rawJsonContent = java.nio.file.Files.readString(java.nio.file.Paths.get(newsApiJsonPath));
            displayArticles("NewsAPI JSON File", new RawJsonArticleParser(rawJsonContent, logger));
            displayArticles("Simple JSON File", new FileArticleParser(simpleJsonPath, logger));
        }catch (IOException e){
                logger.warning("Failed to read JSON file: " + e.getMessage());
            }
        }

    private static void displayArticles(String source, ArticleParser parser) {
        System.out.println("=== Articles from " + source + " ===");
        NewsApi newsApi = parser.parse();
//        if (newsApi.getArticles().isEmpty()) {
//        	System.out.println("No articles found.");
//        	return;
//        }
        
        newsApi.getArticles().forEach(article -> {
            System.out.println("Title: " + article.getTitle());
            System.out.println("Description: " + article.getDescription());
            System.out.println("Published At: " + article.getPublishedDate());
            System.out.println("URL: " + article.getUrl());
            System.out.println();
        });
    }

    private static Logger setupLogger() {
        Logger logger = Logger.getLogger("NewsApiLogger");
        try {
            FileHandler handler = new FileHandler("newsapi.log", true);
            handler.setFormatter(new SimpleFormatter());
            logger.addHandler(handler);
        } catch (IOException e) {
            System.err.println("Failed to set up logger: " + e.getMessage());
        }
        return logger;
    }
}
