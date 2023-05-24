package crawler.sitemap.factory;

import java.net.URL;

public interface SeoMapFactory {
	public void setUrl(URL url);
	public Sitemap build();
}
