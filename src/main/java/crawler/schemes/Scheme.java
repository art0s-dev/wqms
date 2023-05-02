package crawler.schemes;

import javax.xml.validation.Schema;

public abstract class Scheme {
	
	public String path;
	public String name;
	public Schema document;
	
	public Scheme(String name, String path, Schema document) {
		this.name = name;
		this.path = path;
		this.document = document;
	}
}
