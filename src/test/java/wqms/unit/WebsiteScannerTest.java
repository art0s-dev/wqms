package wqms.unit;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import controller.WebsiteScanner;

import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.StringUtils;

class WebsiteScannerTest {
	
	private String wikipediaUrl = "http://www.wikipedia.org";
	private String errorUrl = "hadsasdo3o2342ß00ß#ä#ä+";
	private String oracleUrl = "https://www.oracle.com/de/";
	

	@Test
	void GivenWikipediaUrlAndScanner_WhenScannerIsInstanciated_ThenScannerContainsADocumentWithABody() {
		var scanner = new WebsiteScanner(wikipediaUrl);
		var body = scanner.getDocument().get().getBody();
		assertTrue(StringUtils.isNotBlank(body));
	}
	
	@Test
	void GivenErrorUrlAndScanner_WhenScannerIsInstanciated_ThenTheScannerHasNoDocument() {
		var scanner = new WebsiteScanner(errorUrl);
		assertTrue(scanner.getDocument().isEmpty());
	}
	
	@Test
	void GivenWikipediaUrlAndScanner_WhenScannerIsInstanciated_ThenScannerContainsADocumentWithAValidTitle() {
		var scanner = new WebsiteScanner(wikipediaUrl);
		assertEquals("Wikipedia", scanner.getDocument().get().getTitle());
	}
	
	
	@Test 
	void GivenWikipediaUrlAndScanner_WhenScannerIsInstanciated_ThenScannerContainsADocumentWithADescription() {
		var scanner = new WebsiteScanner(wikipediaUrl);
		
		var description = "Wikipedia is a free online encyclopedia, "
				+ "created and edited by volunteers around the world"
				+ " and hosted by the Wikimedia Foundation.";
		
		assertEquals(description, scanner.getDocument().get().getDescription());
	}
	
	@Test
	void GivenOracleUrlAndScanner_WhenScannerIsInstanciated_ThenScannerContainsADocumentWithListtOfKeywords() {
		var scanner = new WebsiteScanner(oracleUrl);
		var lookFor = "Software";
		var keywordsList = scanner.getDocument().get().getKeywords();
		boolean listContainsLookFor = keywordsList.stream()
				.filter(keyword -> keyword.contentEquals(lookFor))
				.findFirst()
				.isPresent();

		assertTrue(listContainsLookFor);
	}
}
