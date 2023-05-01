package unit.crawler.extractor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import crawler.website.Website;
import tools.files.SimpleFileReader;

public final class ExtractorWebsiteTestFactory {
	
	public static List<Website> createDummyWebsites(
		String sitesFolder
	){
		try 
		{
			return Files.walk(Paths.get(sitesFolder))
				.filter(Files::isRegularFile)
				.map(file -> createDummy(file))
				.collect(Collectors.toList());
		} 
		
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		
		return null;
	}
	
	private static Website createDummy(Path file) {
		var path = file.toString();
		try {
			return new Website(SimpleFileReader.read(path));
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

}
