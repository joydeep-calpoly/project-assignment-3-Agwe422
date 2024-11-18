package ArticleParser;

import java.util.logging.Logger;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class RawJsonArticleParser extends ArticleParser{

	private final String jsonSource;

    public RawJsonArticleParser(String jsonSource, Logger logger) {
        super(logger);
        this.jsonSource = jsonSource;
    }

    @Override
    protected String loadJsonContent() throws IOException{
    if (Files.exists(Paths.get(jsonSource))) {
        return Files.readString(Paths.get(jsonSource));
    } else {
        return jsonSource;
    }
    }
	
}
