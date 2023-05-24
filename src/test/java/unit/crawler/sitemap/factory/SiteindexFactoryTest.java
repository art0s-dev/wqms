package unit.crawler.sitemap.factory;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.net.URL;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import crawler.sitemap.factory.SitemapFactory;
import crawler.sitemap.parser.StandardSeoMapParser;
import crawler.sitemap.validator.StandardSeoMapValidator;

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

	@Test @Disabled
	void GivenNoUrl_WhenFactoryIsCalled_ThenListIsEmpty() {
		//var factory = new SiteindexFactory();
		//List<Optional<URL>> list = factory.build().linkList;
		
		//assertTrue(list.isEmpty());
	}

}
