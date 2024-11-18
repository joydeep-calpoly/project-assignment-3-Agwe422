package ArticleParser;

/**
 * Represents a URL source for articles.
 */
public class UrlSource extends ArticleSource {

    private final String url;

    /**
     * Constructs a UrlSource with the specified URL.
     *
     * @param url the URL to the article source
     */
    public UrlSource(String url) {
        this.url = url;
    }

    /**
     * Returns the URL for the source.
     *
     * @return the URL as a string
     */
    public String getUrl() {
        return url;
    }

    /**
     * Accepts a visitor to process the URL source.
     *
     * @param visitor the visitor to accept
     */
    @Override
    public void accept(SourceVisitor visitor) {
        visitor.visit(this);
    }
}
