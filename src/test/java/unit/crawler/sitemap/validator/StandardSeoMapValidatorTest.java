package unit.crawler.sitemap.validator;

import static org.junit.jupiter.api.Assertions.*;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import crawler.schemes.loader.StaticSchemeLoader;
import crawler.sitemap.validator.StandardSeoMapValidator;

import static unit.crawler.website.TestSites.localGovernmentSitemap;;

class StandardSeoMapValidatorTest {
	
	private static URL url;
	private static URL urlWithNoDocument;
	private static URL oracleUrl;
	private static URL wordpressUrl;
	
	private static List<URL> linkList;
	
	@BeforeAll
	static void setup() throws MalformedURLException {
		
		url = new URL(localGovernmentSitemap);
		urlWithNoDocument = new URL("https://www.sap.com");
		oracleUrl = new URL("https://www.oracle.com/oci.xml");
		wordpressUrl = new URL("https://wordpress.com/sitemap-1.xml");
	
		linkList = List.of(url, wordpressUrl);
	}
	
	@ParameterizedTest
	@MethodSource("getLinkList")
	void GivenLinkList_WhenValidation_ReturnTrue(URL link) {
		var validator =  new StandardSeoMapValidator();
		{
			var sitemapScheme = new StaticSchemeLoader().loadSitemap();
			validator.setScheme(sitemapScheme);
			validator.setUrl(url);
		}
		
		var sitemapIsValid = validator.isValidSitemap();
		assertTrue(sitemapIsValid);
	}
	
	
	@Test
	void GivenUrlWithNoValidXMLDocument_WhenValidating_ReturnFalse() {
		var validator =  new StandardSeoMapValidator();
		{
			var sitemapScheme = new StaticSchemeLoader().loadSitemap();
			validator.setScheme(sitemapScheme);
			validator.setUrl(urlWithNoDocument);
		}
		
		var sitemapIsNotValid = !validator.isValidSitemap();
		assertTrue(sitemapIsNotValid);
	}
	
	@Test
	void GivenNoScheme_WhenValidating_ThenReturnFalse()  {
		var validator =  new StandardSeoMapValidator();
		{
			validator.setUrl(url);
		}
		var sitemapIsNotValid = !validator.isValidSitemap();
		assertTrue(sitemapIsNotValid);
	}
	
	
	@Test
	void GivenUrlWithInvalidDocument_WhenValidating_ThenSitemapIsNotValid() {
		var validator =  new StandardSeoMapValidator();
		{
			var sitemapScheme = new StaticSchemeLoader().loadSitemap();
			validator.setScheme(sitemapScheme);
			validator.setUrl(oracleUrl);
		}
		
		var sitemapIsNotValid = !validator.isValidSitemap();
		assertTrue(sitemapIsNotValid);
	}
	

	public static List<URL> getLinkList(){return linkList;}

}
