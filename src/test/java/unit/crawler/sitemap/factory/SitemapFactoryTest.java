package unit.crawler.sitemap.factory;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.net.MalformedURLException;
import java.net.URL;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import crawler.sitemap.factory.SitemapFactory;
import crawler.sitemap.validator.StandardSeoMapValidator;

import static unit.crawler.website.TestSites.localGovernmentSitemap;

class SitemapFactoryTest {
	
	private static StandardSeoMapValidator validator;
	
	@BeforeAll
	static void setup() {
		validator = mock(StandardSeoMapValidator.class);
		when(validator.isValidSitemap(null))
			.thenReturn(false);
	}
	
	@Test
	void GivenNoUrl_WhenFactoryIsCalled_ThenListIsEmpty() {
		var factory = new SitemapFactory(validator);
		var list = factory.build();
		
		assertTrue(list.isEmpty());
	}
	
	@Test
	void GivenInvalidUrl_WhenFactoryIsCalled_ThenListIsEmpty() throws MalformedURLException {
		var url = new URL("https://baden-wurtemberg.de");		
		var factory = new SitemapFactory(validator);
		{
			factory.setUrl(url);
		}
		
		var list = factory.build();
		
		assertTrue(list.isEmpty());
	}
	
	
	
	@Test @Disabled
	void GivenAUrlToASitemap_WhenFacotryIsCalled_ThenSitemapIsConstructed() throws MalformedURLException {
		var url = new URL(localGovernmentSitemap);
		var validator = mock(StandardSeoMapValidator.class);
		
		when(validator.isValidSitemap(null))
			.thenReturn(false);
		
		var factory = new SitemapFactory(validator);
		{
			factory.setUrl(url);
		}
		
		var list = factory.build();
		var listIsNotEmpty = !list.isEmpty();
		
		assertTrue(listIsNotEmpty);
	}

}



