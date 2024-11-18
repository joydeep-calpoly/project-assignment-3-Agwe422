package ArticleParser;

public abstract class ArticleSource {
    public abstract void accept(SourceVisitor visitor);
}
