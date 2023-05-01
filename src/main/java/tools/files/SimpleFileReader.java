package tools.files;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.stream.Collectors;

public final class SimpleFileReader {
	
	private String url;
	private String lines;
	
	public static String read(String url) throws FileNotFoundException, IOException {
		var fileReader = new SimpleFileReader();
		{
			fileReader.setUrl(url);
		}
		return fileReader.read();
	}
	
	private void setUrl(String url) { this.url = url; }
	
	private String read() throws FileNotFoundException,IOException {
		var file = this.url;
		try (var bufferedReader = createBufferedReader(file))
		{
			return bufferedReader.lines()
					.collect(Collectors.toList())
					.stream()
					.reduce("", (carry, item) -> carry + item );
		} 
	}
	
	private BufferedReader createBufferedReader(String path) 
	throws FileNotFoundException 
	{
		var fileReader = new FileReader(path);
		return new BufferedReader(fileReader);
	}

	
}
