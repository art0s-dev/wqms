package unit.crawler.extractor;

import static org.junit.jupiter.api.Assertions.*;
import static unit.crawler.extractor.ExtractorWebsiteTestFactory.createDummyWebsites;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import crawler.extractor.Meta;
import crawler.website.Website;
import crawler.extractor.StandardMetaExtractor;

class StandardMetaExtractorTest {
	
	static String pathToSites = "sites";
	static String pathToNormalSites = "/normal/";
	static String pathToEmptySites = "/empty/";
	
	String markupForDescription = "<meta name=\"description\" content=\"test\">";
	String markupForDescriptionReversed = "<meta content=\"test\" name=\"description\">";
	
	static List<Website> normalSites;
	static List<Website> emptySites;
	
	@BeforeAll
	static void beforeAll(){
		normalSites = createDummyWebsites(pathToSites + pathToNormalSites);
		emptySites = createDummyWebsites(pathToSites + pathToEmptySites);
	}

	@ParameterizedTest
	@MethodSource("getNormalSites")
	void GivenNormalSite_WhenExtracting_ThenTitleIsPresent(Website website) {
		var meta = createMeta(website);
		var titleIsPresent = !meta.title.isBlank();
	
		assertTrue(titleIsPresent);
	}
	
	@ParameterizedTest
	@MethodSource("getEmptySites")
	void GivenEmptySite_WhenExtracting_ThenTitleIsBlank(Website website) {
		var meta = createMeta(website);
		var titleIsBlank = meta.title.isBlank();
	
		assertTrue(titleIsBlank);
	}

	@Test
	void GivenSiteWasNotSet_WhenExtracting_ThenEmptyMetaReturns() {
		var extractor = new StandardMetaExtractor();
		var metaIsEmpty = extractor.build().isEmpty();
		
		assertTrue(metaIsEmpty);
	}
	
	@Test
	void GivenDescription_WhenExtracting_ThenDescriptionIsSame() {
		var description = this.markupForDescription;
		var meta = createMeta(new Website(description));
		
		var descriptionIsTheSame = meta.description
				.contentEquals("test");
	
		assertTrue(descriptionIsTheSame);
	}

	@Test 
	void GivenDescriptionInReversedOrder_WhenExtracting_ThenDescriptionIsSame() {
		var description = this.markupForDescriptionReversed;
		var meta = createMeta(new Website(description));
		
		var descriptionIsTheSame = meta.description
				.contentEquals("test");
	
		assertTrue(descriptionIsTheSame);
	}
 
	
	private Meta createMeta(Website website) {
		var extractor = new StandardMetaExtractor();
		extractor.setWebsite(website);
		return extractor.build().orElseThrow();
	}
	
	private static List<Website> getNormalSites(){ return normalSites; }
	private static List<Website> getEmptySites(){ return emptySites; }
	
}
