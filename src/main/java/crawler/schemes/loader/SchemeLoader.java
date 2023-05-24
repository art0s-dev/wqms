package crawler.schemes.loader;

import crawler.schemes.Siteindex;
import crawler.schemes.Sitemap;

public interface SchemeLoader {
	public Sitemap loadSitemap();
	public Siteindex loadSiteindex();
}
