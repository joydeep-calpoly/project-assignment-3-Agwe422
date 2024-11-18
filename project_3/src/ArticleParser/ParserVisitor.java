package ArticleParser;

import java.util.logging.Logger;

public class ParserVisitor implements SourceVisitor {

    private final Logger logger;
    private ArticleParser parser;

    /**
     * Constructs a ParserVisitor with the specified logger.
     *
     * @param logger the logger to log information during parsing
     */
    public ParserVisitor(Logger logger) {
        this.logger = logger;
    }

    /**
     * Returns the constructed parser after visiting a source.
     *
     * @return the constructed ArticleParser
     */
    public ArticleParser getParser() {
        return parser;
    }

    @Override
    public void visit(UrlSource urlSource) {
        parser = new UrlArticleParser(urlSource.getUrl(), logger);
    }

    @Override
    public void visit(FileSource fileSource) {
        parser = new FileArticleParser(fileSource.getFilePath(), logger);
    }

    @Override
    public void visit(RawJsonSource rawJsonSource) {
        parser = new RawJsonArticleParser(rawJsonSource.getRawJson(), logger);
    }
}
