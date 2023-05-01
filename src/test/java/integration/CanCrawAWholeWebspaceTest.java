package integration;

import static org.junit.jupiter.api.Assertions.*;
import static unit.crawler.website.TestSites.*;

import java.util.List;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import crawler.CanCrawlAWholeWebspace;
import crawler.extractor.Meta;

class CanCrawAWholeWebspaceTest {

	@Test @Disabled
	void GivenPageWithErrorUrl_WhenCrawlIsCalled_ThenMethodReturnsAnEmptyList() {
		List<Meta> list = CanCrawlAWholeWebspace.crawl(errorUrl);
		assertTrue(list.isEmpty());
	}
	
	@Test @Disabled
	void GivenPageWithMalformedSitemap_WhenCrawlIsCalled_ThenMethodReturnsAnEmptyList() {
		List<Meta> list = CanCrawlAWholeWebspace.crawl(lufthansaUrl);
		assertTrue(list.isEmpty());
	}
	
	@Test @Disabled
	void GivenPageWithNormalSitemap_WhenCrawlIsCalled_ThenMethodReturnsAListOfMetaObjects() {
		List<Meta> list = CanCrawlAWholeWebspace.crawl(oracleUrl);
		assertFalse(list.isEmpty());
	}

}
