package unit.crawler.scheme;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import crawler.schemes.Scheme;
import crawler.schemes.Siteindex;
import crawler.schemes.Sitemap;
import crawler.schemes.loader.StaticSchemeLoader;

class StandardSchemeLoaderTest {

	@Test
	void GivenSchemeLoader_WhenLoaderIsCalled_ThenSitemapAndIndexAreLoaded() {
		var loader = new StaticSchemeLoader();
		List<Scheme> list = loader.load();
		
		var schemesAreLoaded = checkSchemes(list);
		assertTrue(schemesAreLoaded);
	}


	private boolean checkSchemes(List<Scheme> list) {
		return isSchemeChildOFSitemap(list.get(0)) 
				&& isSchemeChildOFSitemap(list.get(1));
	}
	
	private boolean isSchemeChildOFSitemap(Scheme scheme) {
		return scheme instanceof Siteindex || 
				scheme instanceof Sitemap;
	}

}
