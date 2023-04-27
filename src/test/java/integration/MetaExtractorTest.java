package integration;

import static org.junit.jupiter.api.Assertions.*;
import static unit.crawler.factory.TestSites.*;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.StringUtils;
import crawler.MetaExtractor;

import java.util.List;


class MetaExtractorTest {
	
	//TODO: stuff this integration test with the website scanner

	@Test @Disabled
	void GivenWikipediaUrlAndextractor_WhenextractorIsInstanciated_ThenextractorContainsADocumentWithABody() {
		var extractor = new MetaExtractor(wikipediaUrl);
		extractor.start();
		
		var body = extractor.getMeta().get().getBody();
		assertFalse(body.isBlank());
	}
	
	@Test  @Disabled
	void GivenErrorUrlAndextractor_WhenextractorIsInstanciated_ThenTheextractorHasNoDocument() {
		var extractor = new MetaExtractor(errorUrl);
		extractor.start();
		
		assertTrue(extractor.getMeta().isEmpty());
	}
	
	@Test  @Disabled
	void GivenWikipediaUrlAndextractor_WhenextractorIsInstanciated_ThenextractorContainsADocumentWithAValidTitle() {
		var extractor = new MetaExtractor(wikipediaUrl);
		extractor.start();
		
		assertEquals("Wikipedia", extractor.getMeta().get().getTitle());
	}
	
	@Test   @Disabled
	void GivenWikipediaUrlAndextractor_WhenextractorIsInstanciated_ThenextractorContainsADocumentWithADescription() {
		var extractor = new MetaExtractor(wikipediaUrl);
		extractor.start();
		
		var description = "Wikipedia is a free online encyclopedia, "
				+ "created and edited by volunteers around the world"
				+ " and hosted by the Wikimedia Foundation.";
		
		assertEquals(description, extractor.getMeta().get().getDescription());
	}
	
	@Test  @Disabled
	void GivenOracleUrlAndextractor_WhenextractorIsInstanciated_ThenextractorContainsADocumentWithListtOfKeywords() {
		var extractor = new MetaExtractor(oracleUrl);
		var lookFor = "Software";
		extractor.start();
		
		List<String> keywordsList = extractor.getMeta().get().getKeywords();
		boolean listContainsLookFor = keywordsList.stream()
				.filter(keyword -> keyword.contentEquals(lookFor))
				.findFirst()
				.isPresent();
		

		assertTrue(listContainsLookFor);
	}
	
	@Test  @Disabled
	void GivenSiteWithoutMetaData_WhenextractorIsInstanciated_ThenextractorContainsADocumentWithAnEmpyKeywordList() {
		var extractor = new MetaExtractor(siteWithoutMetadata);
		extractor.start();
		
		List<String> list = extractor.getMeta().get().getKeywords();
		assertTrue(list.isEmpty());
	}
}
