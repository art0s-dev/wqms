package crawler.sitemap;

import java.net.URL;
import java.util.List;

public final class Sitemap extends SeoMap{
	public List<URL> linkList;
	
	public Sitemap(List<URL> linkList) {
		this.linkList = linkList;
	}
}
