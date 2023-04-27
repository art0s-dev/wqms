package unit;

import static org.junit.jupiter.api.Assertions.*;
import static unit.crawler.factory.TestSites.*;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import crawler.WebsiteScanner;

class WebsiteScannerTest {

	@Test @Disabled
	void GivenBadUrl_WhenWebsiteScannerIsCalled_ThenResponseIsEmpty() {
		var scanner = new WebsiteScanner(errorUrl);
		var response = scanner.scan();
		
		assertTrue(response.isEmpty());
	}
	
	@Test @Disabled
	void GivenGoodUrl_WhenWebsiteScannerIsCalled_ThenResponseIsPresent() {
		var scanner = new WebsiteScanner(oracleUrl);
		var response = scanner.scan();
		
		assertTrue(response.isPresent());
	}

}
