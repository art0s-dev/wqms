package crawler.sitemap.factory;

import java.net.URL;
import java.util.List;
import java.util.Optional;

import crawler.sitemap.validator.SeoMapValidator;


public final class SitemapFactory implements SeoMapFactory {

	private URL url;
	private SeoMapValidator validator;
	
	public SitemapFactory(SeoMapValidator validator) {
		this.validator = validator;
	}
	
	public void setUrl(URL url) {
		this.url = url;
	}

	public List<Optional<URL>> build() {
		var sitemapIsNotValid = !validator.isValidSitemap(url);
		if(sitemapIsNotValid) {
			return List.of();
		}		
		
		return List.of();
	}


}
