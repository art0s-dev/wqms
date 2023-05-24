package crawler.sitemap.factory;

import java.net.URL;

import crawler.sitemap.parser.SeoMapParser;
import crawler.sitemap.validator.SeoMapValidator;

public class SiteindexFactory implements SeoMapFactory {

	private URL url;
	private SeoMapValidator validator;
	private SeoMapParser parser;
	private SitemapFactory sitemapFactory;
	
	public SiteindexFactory(
			SeoMapValidator validator, 
			SeoMapParser parser, 
			SitemapFactory sitemapFactory
	) {
		this.validator = validator;
		this.parser = parser;
		this.sitemapFactory = sitemapFactory;
	}
	
	public void setUrl(URL url) {
		this.url = url;
	}

	public Sitemap build() {
		return null;
	}

}
