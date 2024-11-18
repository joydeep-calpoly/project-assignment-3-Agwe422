package ArticleParser;

public class RawJsonSource extends ArticleSource {

    private final String rawJson;

    /**
     * Constructs a RawJsonSource with the specified raw JSON content.
     *
     * @param rawJson the raw JSON content as a string
     */
    public RawJsonSource(String rawJson) {
        this.rawJson = rawJson;
    }

    /**
     * Returns the raw JSON content for the source.
     *
     * @return the raw JSON content as a string
     */
    public String getRawJson() {
        return rawJson;
    }

    /**
     * Accepts a visitor to process the raw JSON source.
     *
     * @param visitor the visitor to accept
     */
    @Override
    public void accept(SourceVisitor visitor) {
        visitor.visit(this);
    }
}
