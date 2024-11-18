package ArticleParser;


import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Logger;
import java.net.URI;

public class UrlArticleParser extends ArticleParser {

	private final URI uri;

    public UrlArticleParser(String url, Logger logger) {
        super(logger);
        this.uri = URI.create(url);
    }

    @Override
    protected String loadJsonContent() throws IOException {
        HttpURLConnection connection = (HttpURLConnection) uri.toURL().openConnection();
        connection.setRequestMethod("GET");
        try (InputStream inputStream = connection.getInputStream()) {
            return new String(inputStream.readAllBytes());
        }
    }

}
