package ArticleParser;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * this class represents an article with information such as title, description, URL, and published date.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
class Article {
	private String title;
    private String description;
    private String url;

    @JsonProperty("publishedAt")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime publishedDate;

    private String author;
    private String urlToImage;
    private String content;

    @JsonProperty("source")
    private Source source;

    /**
     * Nested Source class to represent the source of the article, including the ID and name.
     */
    @JsonIgnoreProperties(ignoreUnknown = true)
    static class Source {
        private String id;
        private String name;

        
        /**
         * Getter for the source ID.
         * @return the source ID as a string.
         */
        public String getId() {
            return id;
        }

        /**
         * Getter for the source name.
         * @return the source name as a string.
         */
        public String getName() {
            return name;
        }
    }

    // Getters for new fields

    /**
     * Getter for the title of the article.
     * @return the title of the article.
     */
    public String getTitle() {
        return title;
    }
    
    



    /**
     * Getter for the description of the article.
     * @return the description of the article.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Getter for the URL of the article.
     * @return the article's URL as a string.
     */
    public String getUrl() {
        return url;
    }

    /**
     * Getter for the date and time when the article was published.
     * @return the published date as a LocalDateTime object.
     */
    public LocalDateTime getPublishedDate() {
        return publishedDate;
    }

    /**
     * Getter for the author of the article.
     * @return the author as a string.
     */
    public String getAuthor() {
        return author;
    }

    /**
     * Getter for the URL to the image associated with the article.
     * @return the image URL as a string.
     */
    public String getUrlToImage() {
        return urlToImage;
    }

    /**
     * Getter for the content of the article.
     * @return the content as a string.
     */
    public String getContent() {
        return content;
    }

    /**
     * Getter for the source of the article.
     * @return the source as a Source object.
     */
    public Source getSource() {
        return source;
    }

   
    
    /**
     * Overrides the equals method to compare Article objects by title, description, publishedDate, and url.
     * @param o the object to compare with this article
     * @return true if the articles are considered equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Article article = (Article) o;
        return Objects.equals(title, article.title) &&
               Objects.equals(description, article.description) &&
               Objects.equals(url, article.url) &&
               Objects.equals(publishedDate, article.publishedDate);
    }

    /**
     * Generates a hash code based on title, description, url, and publishedDate.
     * @return an integer hash code
     */
    @Override
    public int hashCode() {
        return Objects.hash(title, description, url, publishedDate);
    }

    /**
     * string representation of the article:
     * 		including its title, description, URL, and published date.
     * @return a formatted string representing the article.
     */
    @Override
    public String toString() {
        return "Title: " + title + "\nDescription: " + description +
               "\nURL: " + url + "\nPublished At: " + publishedDate;
    }
   
    /**
     * Custom deserializer for LocalDateTime that supports multiple date formats.
     */
    private static class LocalDateTimeDeserializer extends com.fasterxml.jackson.databind.JsonDeserializer<LocalDateTime> {
        private static final List<DateTimeFormatter> DATE_FORMATS = Arrays.asList(
            DateTimeFormatter.ISO_DATE_TIME, // Standard ISO format
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS"), // Custom format with space
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss") // Fallback without milliseconds
        );

        @Override
        public LocalDateTime deserialize(com.fasterxml.jackson.core.JsonParser p, com.fasterxml.jackson.databind.DeserializationContext ctxt) throws IOException {
            String date = p.getText();
            for (DateTimeFormatter formatter : DATE_FORMATS) {
                try {
                    return LocalDateTime.parse(date, formatter);
                } catch (DateTimeParseException e) {
                    // Try next format
                }
            }
            throw new IOException("Unable to parse LocalDateTime: " + date);
        }
    }
}
