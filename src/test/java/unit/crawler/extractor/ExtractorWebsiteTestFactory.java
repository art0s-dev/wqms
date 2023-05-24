package unit.crawler.extractor;

import java.io.IOException;
import static java.nio.file.Files.walk;
import static java.nio.file.Paths.get;
import static java.util.stream.Collectors.toList;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import crawler.website.Website;
import tools.files.SimpleFileReader;

public final class ExtractorWebsiteTestFactory {
	
	public static List<Website> createDummyWebsites(String sitesFolder){
		try 
		{
			return walk(get(sitesFolder))
				.filter(Files::isRegularFile)
				.map(file -> createDummy(file))
				.collect(toList());
		} 
		
		catch (IOException e) 
		{
			return null;
		}
	}
	
	private static Website createDummy(Path file) {
		var path = file.toString();
		
		try 
		{
			return new Website(SimpleFileReader.read(path));
		} 
		
		catch (IOException e) 
		{
			return null;
		}
	}
}
