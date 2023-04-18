package wqms.unit;

import static org.junit.jupiter.api.Assertions.*;
import java.util.Optional;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import static wqms.unit.TestSites.*;
import crawler.SitemapAnalyzer;

class SitemapAnalyzerTest {

	@Test
	void GivenErrorUrl_WhenSitemapAnalyzerIsInstanciated_ThenSitemapIsEmpty() {
		SitemapAnalyzer analyzer = new SitemapAnalyzer(errorUrl);
		assertTrue(analyzer.getSitemap().isEmpty());
	}
	
	@Test
	void GivenPageWithdSitemapInRoot_WhenSitemapAnalyzerIsInstanciated_ThenSitemapIsCreated() {
		Optional<Document> sitemap = new SitemapAnalyzer(governmentUrl).getSitemap();
		assertTrue(sitemap.isPresent());
	}

}
