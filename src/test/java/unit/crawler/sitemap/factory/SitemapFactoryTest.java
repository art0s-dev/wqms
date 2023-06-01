package unit.crawler.sitemap.factory;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import crawler.schemes.loader.StaticSchemeLoader;
import crawler.sitemap.factory.SitemapFactory;
import crawler.sitemap.parser.StandardSeoMapParser;
import crawler.sitemap.validator.StandardSeoMapValidator;

import static unit.crawler.website.TestSites.localGovernmentSitemap;

class SitemapFactoryTest {
	
	private static StandardSeoMapValidator validator;
	private static StandardSeoMapParser parser;
	private static URL localGovernmentUrl;
	private static URL invalidUrl;
	private static URL oracleUrl;
	private static URL testUrl;
	
	@BeforeAll
	static void setup() throws MalformedURLException {
		validator = mock(StandardSeoMapValidator.class);
		when(validator.isValidSitemap())
			.thenReturn(false);
		
		parser = mock(StandardSeoMapParser.class);
		when(parser.parse())
			.thenReturn(Optional.empty());
		
		localGovernmentUrl = new URL(localGovernmentSitemap);
		invalidUrl = new URL("https://baden-wurtemberg.de");	
		oracleUrl = new URL("https://www.oracle.com/oci.xml");
		testUrl = new URL("https://www.baden-wuerttemberg.de/sitemap.xml");
	}
	
	@Test
	void GivenNoUrl_WhenFactoryIsCalled_ThenListIsEmpty() {
		var factory = new SitemapFactory(validator, parser);
		var list = factory.build().linkList();
		
		assertTrue(list.isEmpty());
	}
	
	@Test
	void GivenInvalidUrl_WhenFactoryIsCalled_ThenListIsEmpty() {
		var factory = new SitemapFactory(validator, parser);
		{
			factory.setUrl(invalidUrl);
		}
		
		var list = factory.build().linkList();
		
		assertTrue(list.isEmpty());
	}
	
	@Test
	void GivenAUrlAndFailingParser_WhenFactoryIsCalled_ThenListIsEmpty() {
		var validator = mock(StandardSeoMapValidator.class);
		when(validator.isValidSitemap())
			.thenReturn(true);
		
		var factory = new SitemapFactory(validator, parser);
		{
			factory.setUrl(localGovernmentUrl);
		}
		var list = factory.build().linkList();
		
		assertTrue(list.isEmpty());
	}
	
	@Test
	void GivenAUrlToASitemap_WhenFactoryIsCalled_ThenSitemapIsConstructed() {
		var validator = mock(StandardSeoMapValidator.class);
		when(validator.isValidSitemap())
			.thenReturn(true);
		
		var parser = new StandardSeoMapParser();
		var factory = new SitemapFactory(validator, parser);
		{
			factory.setUrl(localGovernmentUrl);
		}
		
		var list = factory.build().linkList();
		var listIsNotEmpty = !list.isEmpty();
		
		assertTrue(listIsNotEmpty);
	}
	
	@Test
	void GivenUrl_WhenFactoryIsCalled_ThenReturnSitemap() {
		
		var validator = new StandardSeoMapValidator();
		{
			var scheme = new StaticSchemeLoader().loadSitemap();
			validator.setScheme(scheme);
			validator.setUrl(testUrl);
		}
		
		var parser = new StandardSeoMapParser();
		{
			parser.setUrl(testUrl);
		}
		
		var factory = new SitemapFactory(validator, parser);
		{
			factory.setUrl(testUrl);
		}
		
		var list = factory.build().linkList();
		var listIsNotEmpty = !list.isEmpty();
		assertTrue(listIsNotEmpty);
	}

}



