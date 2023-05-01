package crawler.sitemap;

import java.net.URL;
import java.util.List;
import java.util.Optional;

public final class StandardSitemapFactory implements SitemapFactory{
	
	private static List<Scheme> sitemapSchemes;
	
	private URL url;
	
	public Optional<Sitemap> build(){
		return Optional.empty();
	}
	
	public void setUrl(URL url) { this.url = url; }
}
