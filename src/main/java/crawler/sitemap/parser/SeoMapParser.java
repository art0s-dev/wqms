package crawler.sitemap.parser;

import java.net.URL;
import java.util.Optional;
import org.w3c.dom.Document;

public interface SeoMapParser {
	public void setUrl(URL url);
	public Optional<Document> parse();
}
