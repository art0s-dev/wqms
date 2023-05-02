package crawler.sitemap;

import java.net.URL;
import java.util.List;
import java.util.Optional;

import crawler.schemes.Scheme;

public interface SitemapFactory {
	public void setUrl(URL url);
	public void setSchemes(List<Scheme> schemes);
	public Optional<Sitemap> build();
}
