package ArticleParser;

import java.util.logging.Logger;

public class RawJsonArticleParser extends ArticleParser{

	private final String rawJson;

    public RawJsonArticleParser(String rawJson, Logger logger) {
        super(logger);
        this.rawJson = rawJson;
    }

    @Override
    protected String loadJsonContent() {
        return rawJson;
    }
	
}
