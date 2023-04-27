package unit.crawler.extractor;

import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import crawler.website.Website;

class ExtractorTest {
	static List<Website> websites;
	static String sitesFolder = "src/test/java/sites";
	
	@BeforeAll
	static void beforeAll(){
		websites = createDummyWebsites();
	}

	@ParameterizedTest
	@MethodSource("getWebsites")
	void GivenWebsite_WhenExtracting_Try(Website website) {
		var bodyIsNotEmpty = !website.body.isBlank();
		
		assertTrue(bodyIsNotEmpty);
	}
	
	private static List<Website> getWebsites(){ return websites; }
	
	private static List<Website> createDummyWebsites(){
		try {
			return Files.walk(Paths.get(sitesFolder))
				.filter(Files::isRegularFile)
				.map(file -> createDummy(file))
				.collect(Collectors.toList());
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private static String getFileContents(Path file){
		try {
			var bufferedReader = createBufferedReader(file);
			
			var lines = bufferedReader.lines()
					.collect(Collectors.toList())
					.stream()
					.reduce("", (carry, item) -> carry + item );
			
			bufferedReader.close();
			return lines;
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
		
	}
	
	private static Website createDummy(Path file) {
		return new Website(getFileContents(file));
	}

	private static BufferedReader createBufferedReader(Path file) throws FileNotFoundException {
		var path = file.toString();
		var fileReader = new FileReader(path);
		var bufferedReader = new BufferedReader(fileReader);
		return bufferedReader;
	}

}
