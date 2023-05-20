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

class StandardSeoMapValidatorTest {
	
	static List<Scheme> schemes;
	static List<URL> validSitemaps;
	
	

	@Test
	void GivenSitemapAndScheme_WhenValidating_ThenReturnTrue() throws MalformedURLException {
		URL url = new URL("https://www.baden-wuerttemberg.de/sitemap.xml");
		
		var validator =  new StandardSeoMapValidator();
		{
			var sitemapScheme = new StaticSchemeLoader().loadSitemap();
			validator.setScheme(sitemapScheme);
		}
		
		var sitemapIsValid = validator.isValidSitemap(url);
		
		assertTrue(sitemapIsValid);
	}

	
	
	//TODO: GivenSitemapAndWrongScheme -> error
	//TODO: GivenScheme and Invalid Sitemap -> error

}
