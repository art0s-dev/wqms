package crawler.scheme;

import java.nio.file.Path;
import java.util.List;
import java.util.Optional;


import javax.xml.XMLConstants;
import javax.xml.validation.SchemaFactory;

import org.xml.sax.SAXException;

import static java.nio.file.Files.walk;
import static java.nio.file.Paths.get;
import static java.util.stream.Collectors.toList;
import static java.util.Optional.empty;

import java.io.File;
import java.io.IOException;

public final class StandardSchemeLoader implements SchemeLoader{
	private String pathToSchemes = "schemes/";

	public List<Scheme> load(){
	
		try {
			return walk(get(pathToSchemes)).parallel()
					.filter(file -> isXsd(file))
					.map(file -> createScheme(file))
					.filter(option -> option.isPresent())
					.map(option -> option.orElseThrow())
					.collect(toList());
		}
		
		catch(IOException e)
		{
			return List.of();
		}
		
	}
	
	private Optional<Scheme> createScheme(Path file) {
		var path = file.toString();
		var name = file.getFileName().toString();
		var asXSD = XMLConstants.W3C_XML_SCHEMA_NS_URI;
		
		try {
			var schema = SchemaFactory
					.newInstance(asXSD)
					.newSchema(new File(path));
			
			var scheme = new Scheme(name, path, schema);
			return Optional.of(scheme);
		}
		
		catch(SAXException e)
		{
			return empty();
		}
	}
	
	
	private boolean isXsd(Path file){
		return file.getFileName()
				.toString()
				.endsWith("xsd");
	}
	
}
