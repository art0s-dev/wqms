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
	@Disabled
	void GivenOracleUrl_WhenSitemapAnalyzerIsInstanciated() {
		SitemapAnalyzer analyzer = new SitemapAnalyzer(oracleUrl);
		Optional<Document> sitemap = analyzer.getSitemap();
		assertTrue(sitemap.isEmpty());
	}
	
	@Test
	void GivenErrorUrl_WhenSitemapAnalyzerIsInstanciated_ThenSizemapIsEmpty() {
		SitemapAnalyzer analyzer = new SitemapAnalyzer(errorUrl);
		assertTrue(analyzer.getSitemap().isEmpty());
	}

}
