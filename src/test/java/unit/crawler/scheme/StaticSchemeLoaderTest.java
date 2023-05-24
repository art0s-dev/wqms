package unit.crawler.scheme;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import crawler.schemes.Siteindex;
import crawler.schemes.Sitemap;
import crawler.schemes.loader.StaticSchemeLoader;

class StaticSchemeLoaderTest {

	@Test 
	void GivenRequestForSitemap_WhenLoaderIsCalled_ThenLoadSitemap() {
		var loader = new StaticSchemeLoader();
		assertTrue(loader.loadSitemap() instanceof Sitemap);
	}
	
	@Test 
	void GivenRequestForSiteindex_WhenLoaderIsCalled_ThenLoadSiteindex() {
		var loader = new StaticSchemeLoader();
		assertTrue(loader.loadSiteindex() instanceof Siteindex);
	}

}
