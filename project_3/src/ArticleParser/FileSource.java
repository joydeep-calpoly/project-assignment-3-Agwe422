package ArticleParser;

/**
 * Represents a file source for articles.
 */
public class FileSource extends ArticleSource {

    private final String filePath;

    /**
     * Constructs a FileSource with the specified file path.
     *
     * @param filePath the path to the file containing articles
     */
    public FileSource(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Returns the file path for the source.
     *
     * @return the file path as a string
     */
    public String getFilePath() {
        return filePath;
    }

    /**
     * Accepts a visitor to process the file source.
     *
     * @param visitor the visitor to accept
     */
    @Override
    public void accept(SourceVisitor visitor) {
        visitor.visit(this);
    }
}
