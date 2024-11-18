package ArticleParser;

import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.logging.Logger;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the GenericArticleParser class.
 */
public class ArticleParserTest {
    private static final Logger logger = Logger.getLogger("TestLogger");

    /**
     * Tests parsing of valid JSON input with an array of articles.
     */
    @Test
    public void testParseWithValidJsonArray() {
        String validJson = "{ \"articles\": [{ \"title\": \"Sample Title\", \"description\": \"Sample Description\", \"url\": \"https://example.com\", \"publishedAt\": \"2021-03-24T22:32:00Z\" }] }";
        RawJsonArticleParser parser = new RawJsonArticleParser(validJson, logger);

        NewsApi newsApi = parser.parse();
        assertEquals(1, newsApi.getArticles().size());
        assertEquals("Sample Title", newsApi.getArticles().get(0).getTitle());
        assertEquals("Sample Description", newsApi.getArticles().get(0).getDescription());
    }

    /**
     * Tests parsing of valid JSON input with a single article format.
     */
    @Test
    public void testParseWithValidSingleArticleJson() {
        String singleArticleJson = "{ \"title\": \"Single Article\", \"description\": \"Single Description\", \"url\": \"https://example.com\", \"publishedAt\": \"2021-04-16 09:53:23.709229\" }";
        RawJsonArticleParser parser = new RawJsonArticleParser(singleArticleJson, logger);

        NewsApi newsApi = parser.parse();
        assertEquals(1, newsApi.getArticles().size());
        assertEquals("Single Article", newsApi.getArticles().get(0).getTitle());
        assertEquals("Single Description", newsApi.getArticles().get(0).getDescription());
    }

    /**
     * Tests parsing with invalid JSON format using RawJsonArticleParser.
     */
    @Test
    public void testParseWithInvalidJson() {
        String invalidJson = "{ \"invalid\": [] }";
        RawJsonArticleParser parser = new RawJsonArticleParser(invalidJson, logger);

        NewsApi newsApi = parser.parse();
        assertTrue(newsApi.getArticles().isEmpty(), "Expected no articles to be parsed from invalid JSON");
    }

    /**
     * Tests parsing of JSON with missing title using RawJsonArticleParser.
     */
    @Test
    public void testParseWithMissingTitle() {
        String jsonWithMissingTitle = "{ \"articles\": [{ \"description\": \"Sample Description\", \"url\": \"https://example.com\", \"publishedAt\": \"2021-03-24T22:32:00Z\" }] }";
        RawJsonArticleParser parser = new RawJsonArticleParser(jsonWithMissingTitle, logger);

        NewsApi newsApi = parser.parse();
        assertTrue(newsApi.getArticles().isEmpty(), "Articles without titles should be excluded.");
    }

    /**
     * Tests parsing of JSON with missing description using RawJsonArticleParser.
     */
    @Test
    public void testParseWithMissingDescription() {
        String jsonWithMissingDescription = "{ \"articles\": [{ \"title\": \"Sample Title\", \"url\": \"https://example.com\", \"publishedAt\": \"2021-03-24T22:32:00Z\" }] }";
        RawJsonArticleParser parser = new RawJsonArticleParser(jsonWithMissingDescription, logger);

        NewsApi newsApi = parser.parse();
        assertTrue(newsApi.getArticles().isEmpty(), "Articles without descriptions should be excluded.");
    }

    /**
     * Tests parsing JSON with a mix of valid and invalid articles using RawJsonArticleParser.
     */
    @Test
    public void testParseWithMixedValidAndInvalidArticles() {
        String mixedJson = "{ \"articles\": [{ \"title\": \"Valid Article\", \"description\": \"Valid Description\", \"url\": \"https://example.com\", \"publishedAt\": \"2021-03-24T22:32:00Z\" }, { \"url\": \"https://example.com\" }] }";
        RawJsonArticleParser parser = new RawJsonArticleParser(mixedJson, logger);

        NewsApi newsApi = parser.parse();
        assertEquals(1, newsApi.getArticles().size(), "Only one valid article should be returned.");
        assertEquals("Valid Article", newsApi.getArticles().get(0).getTitle());
    }

    /**
     * Tests parsing from a file source with valid JSON content using FileArticleParser.
     */
    @Test
    public void testParseWithFileSource() throws IOException {
        String fileJsonContent = "{ \"articles\": [{ \"title\": \"File Title\", \"description\": \"File Description\", \"url\": \"https://example.com\", \"publishedAt\": \"2021-03-24T22:32:00Z\" }] }";
        Path tempFile = Files.createTempFile("testJson", ".json");
        Files.writeString(tempFile, fileJsonContent);

        FileArticleParser parser = new FileArticleParser(tempFile.toString(), logger);
        NewsApi newsApi = parser.parse();

        assertEquals(1, newsApi.getArticles().size());
        assertEquals("File Title", newsApi.getArticles().get(0).getTitle());
        assertEquals("File Description", newsApi.getArticles().get(0).getDescription());

        // Clean up the temporary file
        Files.deleteIfExists(tempFile);
    }

    /**
     * Test for when `jsonContent` is null due to file loading error.
     */
    @Test
    public void testFileNotFound() {
        FileArticleParser parser = new FileArticleParser("nonexistent_file.json", logger);
        NewsApi newsApi = parser.parse();

        assertTrue(newsApi.getArticles().isEmpty(), "Expected an empty list when file is not found.");
    }

    /**
     * Test for malformed JSON input using RawJsonArticleParser.
     */
    @Test
    public void testMalformedJson() {
        String malformedJson = "{ \"title\": \"Missing end brace\"";  // Malformed JSON
        RawJsonArticleParser parser = new RawJsonArticleParser(malformedJson, logger);

        NewsApi newsApi = parser.parse();
        assertTrue(newsApi.getArticles().isEmpty(), "Expected an empty list for malformed JSON input.");
    }

    /**
     * Test for network failure in `loadJsonFromUrl()` by using an invalid URL in UrlArticleParser.
     */
    @Test
    public void testLoadJsonFromInvalidUrl() {
        UrlArticleParser parser = new UrlArticleParser("http://invalid.url", logger);
        NewsApi newsApi = parser.parse();

        assertTrue(newsApi.getArticles().isEmpty(), "Expected an empty list when URL is invalid or unreachable.");
    }

    /**
     * Tests parsing with a valid file to ensure the parsing logic works.
     */
    @Test
    public void testParseWithValidFile() throws IOException {
        String validJson = "{ \"articles\": [{ \"title\": \"File Article\", \"description\": \"Valid Description\", \"url\": \"https://example.com\", \"publishedAt\": \"2021-03-24T22:32:00Z\" }] }";
        Path tempFile = Files.createTempFile("testJson", ".json");
        Files.writeString(tempFile, validJson);

        FileArticleParser parser = new FileArticleParser(tempFile.toString(), logger);
        NewsApi newsApi = parser.parse();

        assertEquals(1, newsApi.getArticles().size(), "Expected one article from valid JSON file.");
        assertEquals("File Article", newsApi.getArticles().get(0).getTitle());
        
        Files.deleteIfExists(tempFile);
    }
}
