package ArticleParser;

import org.junit.jupiter.api.Test;

import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;

public class SourceVisitorTest {
    private static final Logger logger = Logger.getLogger("TestLogger");

    /**
     * Test for creating a parser from a URL source using the NEWSAPI format.
     */
    @Test
    public void testVisitUrlSource() {
        ArticleSource urlSource = new UrlSource("https://example.com/newsapi");
        ParserVisitor visitor = new ParserVisitor(logger);

        urlSource.accept(visitor);
        ArticleParser parser = visitor.getParser();

        assertTrue(parser instanceof UrlArticleParser, "Expected UrlArticleParser for URL source with NEWSAPI format.");
    }

    /**
     * Test for creating a parser from a file source using the NEWSAPI format.
     */
    @Test
    public void testVisitFileSourceWithNewsApiFormat() {
        ArticleSource fileSource = new FileSource("/path/to/newsapi.json");
        ParserVisitor visitor = new ParserVisitor(logger);

        fileSource.accept(visitor);
        ArticleParser parser = visitor.getParser();

        assertTrue(parser instanceof FileArticleParser, "Expected FileArticleParser for file source with NEWSAPI format.");
    }

    /**
     * Test for creating a parser from a file source using the SIMPLE format.
     */
    @Test
    public void testVisitFileSourceWithSimpleFormat() {
        ArticleSource fileSource = new FileSource("/path/to/simple.json");
        ParserVisitor visitor = new ParserVisitor(logger);

        fileSource.accept(visitor);
        ArticleParser parser = visitor.getParser();

        assertTrue(parser instanceof FileArticleParser, "Expected FileArticleParser for file source with SIMPLE format.");
    }

    /**
     * Test for creating a parser from a raw JSON source.
     */
    @Test
    public void testVisitRawJsonSource() {
        String rawJson = "{ \"articles\": [{ \"title\": \"Sample Title\", \"description\": \"Sample Description\" }] }";
        ArticleSource rawJsonSource = new RawJsonSource(rawJson);
        ParserVisitor visitor = new ParserVisitor(logger);

        rawJsonSource.accept(visitor);
        ArticleParser parser = visitor.getParser();

        assertTrue(parser instanceof RawJsonArticleParser, "Expected RawJsonArticleParser for raw JSON source.");
    }

//    /**
//     * Test for unsupported format with a URL source.
//     */
//    @Test
//    public void testUnsupportedFormatForUrlSource() {
//        ArticleSource urlSource = new UrlSource("https://example.com/newsapi");
//        ParserVisitor visitor = new ParserVisitor(logger);
//
//        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
//            urlSource.accept(visitor);
//        });
//
//        assertEquals("Simple format not supported for URL source", exception.getMessage());
//    }
}
