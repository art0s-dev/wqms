package integration;

import static org.junit.jupiter.api.Assertions.*;
import static unit.crawler.website.TestSites.*;

import java.util.Optional;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;

import crawler.SitemapAnalyser;

class SitemapAnalyserTest {

	@Test @Disabled
	void GivenMalformedUrl_WhenAnalyserIsCalled_ThenSitemapIsEmpty() {
		SitemapAnalyser analyzer = new SitemapAnalyser(errorUrl);
		assertTrue(analyzer.getSitemap().isEmpty());
	}
	
	@Test  @Disabled
	void GivenPageWithInvalidSitemap_WhenAnalyserIsCalled_ThenSitemapIsEmpty() {
		SitemapAnalyser analyzer = new SitemapAnalyser(lufthansaUrl);
		assertTrue(analyzer.getSitemap().isEmpty());
	}
	
	@Test @Disabled
	void GivenPageWithNormalSitemapInRoot_WhenAnalyserIsCalled_ThenSitemapIsCreated() {
		Optional<Document> sitemap = new SitemapAnalyser(governmentUrl).getSitemap();
		assertTrue(sitemap.isPresent());
	}
	
	@Test @Disabled
	void GivenPageWithSpecialSitemapWithPathInRobots_WhenAnalyzerIsCalled_ThenSitemapIsCreated() {
		Optional<Document> sitemap = new SitemapAnalyser(sapUrl).getSitemap();
		assertTrue(sitemap.isPresent());
	}

}
