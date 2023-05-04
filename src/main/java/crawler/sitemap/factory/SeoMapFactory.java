package crawler.sitemap.factory;

import java.net.URL;
import java.util.List;
import java.util.Optional;

import crawler.schemes.Scheme;
import crawler.sitemap.SeoMap;
import crawler.sitemap.Sitemap;

public interface SeoMapFactory {
	public void setUrl(URL url);
	public Optional<SeoMap> build();
}
