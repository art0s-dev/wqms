package unit;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.net.MalformedURLException;
import java.net.URL;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;

import main.Sitemap;

class SitemapTest {
	
	@Test
	void GivenUrlWithNoSitemap_WhenLinkListIsCalled_ThenThrow() 
	throws MalformedURLException {
		URL url = new URL("https://www.microsoft.com");
		var e = assertThrows(Exception.class, () -> Sitemap.create(url) );
		assertTrue(e.getClass() == Exception.class);
	}
	
	@Test @Disabled
	void GivenUrlWithOnlyOneSitemapAndItsDamaged_WhenLinkListIsCalled_ThenThrow() {}
	
	@Test @Disabled
	void GivenUrlWithMoreThanOneSitemapsAndOneIsDamaged_ThenLinkListIsCalled_ThenOmit() {}
	
	@Test @Disabled
	void GivenUrlToLocalSiteindex_WhenLinkListIsCalled_ThenParseToLinkList() {}
	
	@Test @Disabled
	void GivenUrlToLocalSitemap_WhenLinkListIsCalled_ThenParseToLinkList() {}
}
