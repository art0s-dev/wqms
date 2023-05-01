package unit.crawler.scheme;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import crawler.scheme.Scheme;
import crawler.scheme.StandardSchemeLoader;

class StandardSchemeLoaderTest {

	@Test @Disabled
	void GivenSchemeLoader_WhenLoad_ThenListOfSchemesIsNotEmpty() {
		var loader = new StandardSchemeLoader();
		List<Scheme> list = loader.load();
		var listIsNotEmpty = !list.isEmpty();
		
		assertTrue(listIsNotEmpty);
	}

}
