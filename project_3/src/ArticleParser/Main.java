package ArticleParser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Main {

    public static void main(String[] args) {
        Logger logger = setupLogger();

        // URLs and paths for each JSON source
        ArticleSource newsApiUrlSource = new UrlSource("https://newsapi.org/v2/top-headlines?country=us&apiKey=ed4672185ae24959aaa5cb54252bd986");
        ArticleSource newsApiJsonSource = new FileSource("/Users/JoshH./IdeaProjects/project-assignment-3-Agwe422/project_3/src/inputs/newsapi.json");
        ArticleSource simpleJsonSource = new RawJsonSource(readFile("/Users/JoshH./IdeaProjects/project-assignment-3-Agwe422/project_3/src/inputs/simple.json"));

            // Parse and display articles from each source
            displayArticles("NewsAPI URL", newsApiUrlSource, SourceFormat.NEWSAPI, logger);
            displayArticles("NewsAPI JSON File", newsApiJsonSource, SourceFormat.RAWJSON, logger);
            displayArticles("Simple JSON File", simpleJsonSource, SourceFormat.SIMPLE, logger);
    }

    private static void displayArticles(String sourceName, ArticleSource source, SourceFormat format, Logger logger) {
        System.out.println("=== Articles from " + sourceName + " ===");

        // Use the visitor to determine the parser
        ParserVisitor visitor = new ParserVisitor(logger);
        source.accept(visitor);

        // Get the parser and parse articles
        ArticleParser parser = visitor.getParser();
        NewsApi newsApi = parser.parse();

        // Display parsed articles
        newsApi.getArticles().forEach(article -> {
            System.out.println("Title: " + article.getTitle());
            System.out.println("Description: " + article.getDescription());
            System.out.println("Published At: " + article.getPublishedDate());
            System.out.println("URL: " + article.getUrl());
            System.out.println();
        });
    }

    private static String readFile(String filePath) {
        try {
            return Files.readString(Paths.get(filePath));
        } catch (IOException e) {
            throw new RuntimeException("Failed to read file: " + filePath, e);
        }
    }

    /**
     * Sets up a logger for logging during the application execution.
     *
     * @return a configured Logger instance
     */
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

