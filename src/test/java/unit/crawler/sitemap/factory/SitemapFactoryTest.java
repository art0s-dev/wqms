package unit.crawler.sitemap.factory;

import static org.junit.jupiter.api.Assertions.*;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import crawler.sitemap.factory.SitemapFactory;
import crawler.sitemap.validator.StandardSeoMapValidator;

class SitemapFactoryTest {

	/**
	 * The idea is here to load u arl and to return a sitemap
	 *  ( a data object which incorporates a list of urls )
	 * Later the siteindex factory will incorporate a sitemap factory??? Work in progress
	 * @throws MalformedURLException 
	 */
	
	@Test @Disabled
	void GivenNoUrl_WhenFactoryIsCalled_ThenListIsEmpty() {
		var validator = new StandardSeoMapValidator();
		var factory = new SitemapFactory(validator);
		var list = factory.build();
		
		
		assertTrue(list.isEmpty());
	}
	
	@Test @Disabled
	void GivenInvalidUrl_WhenFactoryIsCalled_ThenListIsEmpty() throws MalformedURLException {
		var url = new URL("https://baden-wurtemberg.de");
		var validator = new StandardSeoMapValidator();
		var factory = new SitemapFactory(validator);
		{
			factory.setUrl(url);
		}
		var list = factory.build();
		
		assertTrue(list.isEmpty());
	}
	
	
	
	@Test @Disabled
	void GivenAUrlToASitemap_WhenFacotryIsCalled_ThenSitemapIsConstructed() throws MalformedURLException {
		var url = new URL("https://www.baden-wuerttemberg.de/sitemap.xml");
		var validator = new StandardSeoMapValidator();
		var factory = new SitemapFactory(validator);
		{
			factory.setUrl(url);
		}
		
		var list = factory.build();
		var listIsNotEmpty = !list.isEmpty();
		
		assertTrue(listIsNotEmpty);
	}

}
