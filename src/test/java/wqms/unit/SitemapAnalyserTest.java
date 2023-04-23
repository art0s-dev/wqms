package wqms.unit;

import static org.junit.jupiter.api.Assertions.*;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import static wqms.unit.TestSites.*;
import crawler.SitemapAnalyser;

class SitemapAnalyserTest {

	@Test
	void GivenMalformedUrl_WhenAnalyserIsCalled_ThenSitemapIsEmpty() {
		SitemapAnalyser analyzer = new SitemapAnalyser(errorUrl);
		assertTrue(analyzer.getSitemap().isEmpty());
	}
	
	@Test 
	void GivenPageWithInvalidSitemap_WhenAnalyserIsCalled_ThenSitemapIsEmpty() {
		SitemapAnalyser analyzer = new SitemapAnalyser(lufthansaUrl);
		assertTrue(analyzer.getSitemap().isEmpty());
	}
	
	@Test
	void GivenPageWithNormalSitemapInRoot_WhenAnalyserIsCalled_ThenSitemapIsCreated() {
		Optional<Document> sitemap = new SitemapAnalyser(governmentUrl).getSitemap();
		assertTrue(sitemap.isPresent());
	}
	
	@Test
	void GivenPageWithSpecialSitemapWithPathInRobots_WhenAnalyzerIsCalled_ThenSitemapIsCreated() {
		Optional<Document> sitemap = new SitemapAnalyser(sapUrl).getSitemap();
		assertTrue(sitemap.isPresent());
	}

}
