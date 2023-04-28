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

class StandardExtractorTest {
	
	static String pathToSites = "src/test/java/sites";
	static String pathToNormalSites = "/normal/";
	static String pathToEmptySites = "/empty/";
	static String pathToMalformedWebsites = "/malformed/";
	
	static List<Website> normalSites;
	static List<Website> sitesWithoutMeta;
	static List<Website> emptySites;
	static List<Website> malformedSites;
	
	
	
	@BeforeAll
	static void beforeAll(){
		normalSites = createDummyWebsites(pathToSites + pathToNormalSites);
		emptySites = createDummyWebsites(pathToSites + pathToEmptySites);
	}
	

	@ParameterizedTest
	@MethodSource("getNormalSites")
	void GivenNormalSite_WhenExtracting_ThenTitleIsPresent(
		Website website
	) {
		var meta = createMeta(website);
		Boolean titleIsPresent = !meta.title.isBlank();
	
		assertTrue(titleIsPresent);
	}
	
	@ParameterizedTest
	@MethodSource("getEmptySites")
	void GivenEmptySite_WhenExtracting_ThenTitleIsBlank(
		Website website
	) {
		var meta = createMeta(website);
		Boolean titleIsBlank = meta.title.isBlank();
	
		assertTrue(titleIsBlank);
	}
	
	@Test
	void GivenSiteWasNotSet_WhenExtracting_ThenEmptyMetaReturns()
	{
		var extractor = new StandardMetaExtractor();
		var metaIsEmpty = extractor.build().isEmpty();
		
		assertTrue(metaIsEmpty);
	}
	
	
	private Meta createMeta(Website website) {
		var extractor = new StandardMetaExtractor();
		extractor.setWebsite(website);
		return extractor.build().orElseThrow();
	}
	
	private static List<Website> getNormalSites(){ return normalSites; }
	private static List<Website> getEmptySites(){ return emptySites; }
	
}
