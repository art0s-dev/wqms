package wqms.unit;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.StringUtils;
import crawler.WebsiteScanner;
import static wqms.unit.TestSites.*;

import java.util.List;


class WebsiteScannerTest {

	@Test
	void GivenWikipediaUrlAndScanner_WhenScannerIsInstanciated_ThenScannerContainsADocumentWithABody() {
		var scanner = new WebsiteScanner(wikipediaUrl);
		var body = scanner.getMeta().get().getBody();
		assertTrue(StringUtils.isNotBlank(body));
	}
	
	@Test
	void GivenErrorUrlAndScanner_WhenScannerIsInstanciated_ThenTheScannerHasNoDocument() {
		var scanner = new WebsiteScanner(errorUrl);
		assertTrue(scanner.getMeta().isEmpty());
	}
	
	@Test
	void GivenWikipediaUrlAndScanner_WhenScannerIsInstanciated_ThenScannerContainsADocumentWithAValidTitle() {
		var scanner = new WebsiteScanner(wikipediaUrl);
		assertEquals("Wikipedia", scanner.getMeta().get().getTitle());
	}
	
	@Test 
	void GivenWikipediaUrlAndScanner_WhenScannerIsInstanciated_ThenScannerContainsADocumentWithADescription() {
		var scanner = new WebsiteScanner(wikipediaUrl);
		
		var description = "Wikipedia is a free online encyclopedia, "
				+ "created and edited by volunteers around the world"
				+ " and hosted by the Wikimedia Foundation.";
		
		assertEquals(description, scanner.getMeta().get().getDescription());
	}
	
	@Test
	void GivenOracleUrlAndScanner_WhenScannerIsInstanciated_ThenScannerContainsADocumentWithListtOfKeywords() {
		var scanner = new WebsiteScanner(oracleUrl);
		var lookFor = "Software";
		List<String> keywordsList = scanner.getMeta().get().getKeywords();
		boolean listContainsLookFor = keywordsList.stream()
				.filter(keyword -> keyword.contentEquals(lookFor))
				.findFirst()
				.isPresent();

		assertTrue(listContainsLookFor);
	}
	
	@Test 
	void GivenSiteWithoutMetaData_WhenScannerIsInstanciated_ThenScannerContainsADocumentWithAnEmpyKeywordList() {
		var scanner = new WebsiteScanner(siteWithoutMetadata);
		List<String> list = scanner.getMeta().get().getKeywords();
		assertTrue(list.isEmpty());
	}
}
