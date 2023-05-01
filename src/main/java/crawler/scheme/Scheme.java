package crawler.scheme;

import javax.xml.validation.Schema;

public class Scheme {
	public String name;
	public String path;
	public Schema document;
	
	public Scheme(String name, String path, Schema document) {
		this.name = name;
		this.path = path;
		this.document = document;
	}
}
