package unit.crawler.extractor;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import crawler.website.Website;

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
	
	private static String getFileContents(Path file){
		try 
		{
			var bufferedReader = createBufferedReader(file);
			
			var lines = bufferedReader.lines()
					.collect(Collectors.toList())
					.stream()
					.reduce("", (carry, item) -> carry + item );
			
			bufferedReader.close();
			
			return lines;
		} 
		
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		
		return null;
	}
	
	private static Website createDummy(Path file) {
		return new Website(getFileContents(file));
	}

	private static BufferedReader createBufferedReader(Path file) 
	throws FileNotFoundException 
	{
		var path = file.toString();
		var fileReader = new FileReader(path);
		var bufferedReader = new BufferedReader(fileReader);
		
		return bufferedReader;
	}
}
