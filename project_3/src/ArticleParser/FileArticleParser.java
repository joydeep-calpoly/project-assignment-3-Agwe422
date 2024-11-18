package ArticleParser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Logger;

public class FileArticleParser extends ArticleParser{

	private final String filePath;

    public FileArticleParser(String filePath, Logger logger) {
        super(logger);
        this.filePath = filePath;
    }

    @Override
    protected String loadJsonContent() throws IOException {
        return Files.readString(Paths.get(filePath));
    }
}
