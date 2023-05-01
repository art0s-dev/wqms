package crawler.scheme;

import org.w3c.dom.Document;

public class Scheme {
	public String name;
	public String path;
	public Document document;
	
	public Scheme(String name, String path, Document document) {
		this.name = name;
		this.path = path;
		this.document = document;
	}
}
