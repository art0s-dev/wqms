package unit.tools;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import static tools.files.SimpleFileReader.read;

import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.fail;

class SimpleFileReaderTest {

	@Test
	void GivenFileWithPath_WhenLoad_ThenContentsOfFileAreReturned() {
		var path = "sites/snippets/helloWorld.html";
		try 
		{
			var lines = read(path);
			var contentIsHelloWorld = lines.contentEquals("<h1>Hello World</h1>"); 
			assertTrue(contentIsHelloWorld);
		}
		
		catch(FileNotFoundException e) 
		{
			fail(e.getLocalizedMessage());
		}
		catch(IOException e) 
		{
			fail(e.getLocalizedMessage());
		}
		
	}

}
