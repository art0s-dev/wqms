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
		var urlWasntSetOrSitemapIsNotValid = checkUrlAndSitemap();
		if(urlWasntSetOrSitemapIsNotValid) {
			return List.of();
		}
		
		
		return List.of();
	}
	
	private boolean checkUrlAndSitemap() {
		var validator = this.validator;
		var sitemapIsNotValid = !validator.isValidSitemap(url);
		var urlWasNotSet = url == null;
		return sitemapIsNotValid | urlWasNotSet;
	}

}
