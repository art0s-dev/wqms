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

import static unit.crawler.website.TestSites.localGovernmentSitemap;

class SitemapFactoryTest {
	
	private static StandardSeoMapValidator validator;
	private static StandardSeoMapParser parser;
	
	@BeforeAll
	static void setup() {
		validator = mock(StandardSeoMapValidator.class);
		when(validator.isValidSitemap())
			.thenReturn(false);
		
		parser = mock(StandardSeoMapParser.class);
		when(parser.parse())
			.thenReturn(Optional.empty());
		
	}
	
	@Test
	void GivenNoUrl_WhenFactoryIsCalled_ThenListIsEmpty() {
		var factory = new SitemapFactory(validator, parser);
		var list = factory.build().linkList();
		
		assertTrue(list.isEmpty());
	}
	
	@Test
	void GivenInvalidUrl_WhenFactoryIsCalled_ThenListIsEmpty() throws MalformedURLException {
		var url = new URL("https://baden-wurtemberg.de");		
		var factory = new SitemapFactory(validator, parser);
		{
			factory.setUrl(url);
		}
		
		var list = factory.build().linkList();
		
		assertTrue(list.isEmpty());
	}
	
	@Test
	void GivenAUrlAndFailingParser_WhenFactoryIsCalled_ThenListIsEmpty() throws MalformedURLException {
		var url = new URL(localGovernmentSitemap);
		var validator = mock(StandardSeoMapValidator.class);
		when(validator.isValidSitemap())
			.thenReturn(true);
		
		var factory = new SitemapFactory(validator, parser);
		{
			factory.setUrl(url);
		}
		var list = factory.build().linkList();
		
		assertTrue(list.isEmpty());
	}
	
	@Test
	void _GivenAUrlToASitemap_WhenFactoryIsCalled_ThenSitemapIsConstructed() throws MalformedURLException {
		var url = new URL(localGovernmentSitemap);
		var validator = mock(StandardSeoMapValidator.class);
		when(validator.isValidSitemap())
			.thenReturn(true);
		
		var parser = new StandardSeoMapParser();
		var factory = new SitemapFactory(validator, parser);
		{
			factory.setUrl(url);
		}
		
		var list = factory.build().linkList();
		var listIsNotEmpty = !list.isEmpty();
		
		assertTrue(listIsNotEmpty);
	}

}



