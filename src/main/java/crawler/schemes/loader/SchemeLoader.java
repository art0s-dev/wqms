package crawler.schemes.loader;

import crawler.schemes.Scheme;

public interface SchemeLoader {
	public Scheme loadSitemap();
	public Scheme loadSiteindex();
}
