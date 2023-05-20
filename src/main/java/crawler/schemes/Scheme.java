package crawler.schemes;

import javax.xml.validation.Schema;

public abstract class Scheme {
	
	public final String path;
	public final String name;
	public final Schema document;
	
	public Scheme(String name, String path, Schema document) {
		this.name = name;
		this.path = path;
		this.document = document;
	}
}
