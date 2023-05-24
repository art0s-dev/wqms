package crawler.sitemap.factory;

import java.net.URL;
import java.util.List;
import java.util.Optional;

import org.w3c.dom.Document;

public class SiteindexFactory implements SeoMapFactory {

	private URL url;
	private SitemapFactory sitemapFactory;
	
	public SiteindexFactory(SitemapFactory sitemapFactory) {
		this.sitemapFactory = sitemapFactory;
	}
	
	public void setUrl(URL url) {
		this.url = url;
	}

	public Sitemap build() {
			return new Sitemap(List.of());
	}
	
	/*public Sitemap build() {
		var documentWrapper = sitemapFactory.getParser().parse();
		var urlHasNotBeenSet = checkValidationRules(documentWrapper);
		if(urlHasNotBeenSet) {
			return new Sitemap(List.of());
		}
		
		return null;
	}*/
	
	private boolean checkValidationRules(Optional<Document> documentWrapper) {
		return documentWrapper.isEmpty() 
				|| !sitemapFactory.getValidator().isValidSitemap() 
				|| url == null;
	}

}
