package unit.crawler.sitemap.validator;

import static org.junit.jupiter.api.Assertions.*;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import crawler.schemes.Scheme;
import crawler.schemes.loader.StaticSchemeLoader;
import crawler.sitemap.validator.StandardSeoMapValidator;

import static unit.crawler.website.TestSites.localGovernmentSitemap;;

class StandardSeoMapValidatorTest {
	
	private static URL url;
	private static URL urlWithNoDocument;
	
	@BeforeAll
	static void setup() throws MalformedURLException {
		url = new URL(localGovernmentSitemap);
		urlWithNoDocument = new URL("https://www.sap.com");
	}
	
	@Test
	void GivenUrlWithNoValidXMLDocument_WhenValidating_ReturnFalse() {
		var validator =  new StandardSeoMapValidator();
		{
			var sitemapScheme = new StaticSchemeLoader().loadSitemap();
			validator.setScheme(sitemapScheme);
		}
		
		var sitemapIsNotValid = !validator.isValidSitemap(urlWithNoDocument);
		assertTrue(sitemapIsNotValid);
	}
	
	@Test
	void GivenNoScheme_WhenValidating_ThenReturnFalse()  {
		var validator =  new StandardSeoMapValidator();
		var sitemapIsNotValid = !validator.isValidSitemap(url);
		
		assertTrue(sitemapIsNotValid);
	}
	
	@Test
	void GivenSitemapAndScheme_WhenValidating_ThenReturnTrue() {
		var validator =  new StandardSeoMapValidator();
		{
			var sitemapScheme = new StaticSchemeLoader().loadSitemap();
			validator.setScheme(sitemapScheme);
		}
		
		var sitemapIsValid = validator.isValidSitemap(url);
		assertTrue(sitemapIsValid);
	}

}
