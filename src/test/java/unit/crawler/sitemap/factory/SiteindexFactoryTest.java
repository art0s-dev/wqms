package unit.crawler.sitemap.factory;
import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import crawler.sitemap.factory.SitemapFactory;
import crawler.sitemap.parser.StandardSeoMapParser;
import crawler.sitemap.validator.StandardSeoMapValidator;
import crawler.sitemap.factory.SiteindexFactory;

class SiteindexFactoryTest {
	
	private static SitemapFactory sitemapFactory;
	
	@BeforeAll
	static void setup() {
		var validator = mock(StandardSeoMapValidator.class);
		when(validator.isValidSitemap())
			.thenReturn(false);
		
		var parser = mock(StandardSeoMapParser.class);
		when(parser.parse())
			.thenReturn(Optional.empty());
		
		sitemapFactory = new SitemapFactory(validator, parser);
	}

	@Test
	void GivenNoUrl_WhenFactoryIsCalled_ThenListIsEmpty() {
		var factory = new SiteindexFactory(sitemapFactory);
		var list = factory.build().linkList();
		
		assertTrue(list.isEmpty());
	}
	
	@Test
	void GivenInvalidUrl_WhenFactoryIsCalled_ThenListIsEmpty() throws MalformedURLException {
		var url = new URL("https://baden-wurtemberg.de");		
		var factory = new SiteindexFactory(sitemapFactory);
		{
			factory.setUrl(url);
		}
		
		var list = factory.build().linkList();
		
		assertTrue(list.isEmpty());
	}

}
