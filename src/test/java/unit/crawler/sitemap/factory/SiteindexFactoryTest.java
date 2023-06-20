package unit.crawler.sitemap.factory;
import static org.junit.jupiter.api.Assertions.*;

import static unit.crawler.website.TestSites.largeSiteindex;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static unit.crawler.website.TestSites.localGovernmentSitemap;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import crawler.sitemap.factory.SitemapFactory;
import crawler.sitemap.parser.StandardSeoMapParser;
import crawler.sitemap.validator.StandardSeoMapValidator;
import crawler.schemes.loader.StaticSchemeLoader;
import crawler.sitemap.factory.SiteindexFactory;

class SiteindexFactoryTest {
	
	private static SitemapFactory sitemapFactory;
	private static URL invalidUrl;
	private static URL siteindexUrl;
	
	@BeforeAll
	static void setup() throws MalformedURLException {
		prepareMocks();
		invalidUrl = new URL("https://baden-wurtemberg.de");
		siteindexUrl = new URL("http://www.oracle.com/sitemap.xml");
	}

	@Test
	void GivenNoUrl_WhenFactoryIsCalled_ThenListIsEmpty() {
		var factory = new SiteindexFactory(sitemapFactory);
		var list = factory.build().linkList();
		
		assertTrue(list.isEmpty());
	}
	
	@Test
	void GivenInvalidUrl_WhenFactoryIsCalled_ThenListIsEmpty() {	
		var factory = new SiteindexFactory(sitemapFactory);
		{
			factory.setUrl(invalidUrl);
		}
		
		var list = factory.build().linkList();
		
		assertTrue(list.isEmpty());
	}
	
	@Test @Disabled
	void GivenValidUrl_WhenFactoryIsCalled_ThenListIsNotEmpty(){
		
		var parser = new StandardSeoMapParser();
		var validator = new StandardSeoMapValidator();
		{
			var scheme = new StaticSchemeLoader().loadSiteindex();
			validator.setUrl(siteindexUrl);
			validator.setScheme(scheme);
		}
		var sitemapFactory = new SitemapFactory(validator, parser);
		{
			sitemapFactory.setUrl(siteindexUrl);
		}
		
		var factory = new SiteindexFactory(sitemapFactory);
		{
			factory.setUrl(siteindexUrl);
		}
		
		var list = factory.build().linkList();
		var listIsNotEmpty = !list.isEmpty();
		
		assertTrue(listIsNotEmpty);
	}
	
	private static void prepareMocks() {
		var validator = mock(StandardSeoMapValidator.class);
		when(validator.isValidSitemap())
			.thenReturn(false);
		
		var parser = mock(StandardSeoMapParser.class);
		when(parser.parse())
			.thenReturn(Optional.empty());
		
		sitemapFactory = new SitemapFactory(validator, parser);
	}

}
