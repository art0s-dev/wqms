package crawler.sitemap;

import java.net.URL;
import java.util.Optional;

public interface SitemapFactory {
	public void setUrl(URL url);
	public Optional<Sitemap> build();
}
