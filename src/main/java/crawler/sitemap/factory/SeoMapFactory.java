package crawler.sitemap.factory;

import java.net.URL;
import java.util.List;
import java.util.Optional;


public interface SeoMapFactory {
	public void setUrl(URL url);
	public List<Optional<URL>> build();
}
