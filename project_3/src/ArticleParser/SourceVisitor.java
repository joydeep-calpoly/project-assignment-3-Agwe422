package ArticleParser;

/**
 * Visitor interface for handling different types of article sources.
 */
public interface SourceVisitor {

    /**
     * Visit method for handling file sources.
     *
     * @param fileSource the file source to be processed
     */
    void visit(FileSource fileSource);

    /**
     * Visit method for handling URL sources.
     *
     * @param urlSource the URL source to be processed
     */
    void visit(UrlSource urlSource);

    /**
     * Visit method for handling raw JSON article sources.
     *
     * @param rawJsonSource the raw JSON source to be processed
     */
    void visit(RawJsonSource rawJsonSource);
}
