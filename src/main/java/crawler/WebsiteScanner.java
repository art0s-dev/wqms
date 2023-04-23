package crawler;

import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class WebsiteScanner implements CanProcessWebPages {
	private Optional<Meta> meta;

	
	public WebsiteScanner(String url) {
		Optional<HttpResponse<String>> response = this.scan(url);
		this.meta = Optional.empty();
		
		if(response.isEmpty()) {
			return;
		}
		
		var responseContents = response.orElseThrow();
		Meta meta = this.extractInformations(responseContents);
		this.meta = Optional.of(meta);
	}
	
	private Meta extractInformations(HttpResponse<String> response) {
		var meta = new Meta();
		var body = response.body().toString();
		
		meta.setBody(body);
		meta.setTitle(this.searchForTag("title", body));
		meta.setDescription(this.searchForAttribute("meta", "description", body));
		
		String keywords = this.searchForAttribute("meta", "keywords", body);
		meta.setKeywords(this.createKeywordList(keywords));
		return meta;
	}
	
	private String searchForTag(String tag, String html) {
		var startTag = "<" + tag + ">";
		int postionOfStartTag = html.indexOf(startTag); 
		int postionOfEndTag = html.indexOf("</" + tag + ">");
		
		String foundString = html.substring(postionOfStartTag, postionOfEndTag);
		String[] stringList = foundString.split(startTag);
		String contentsRightFromStartTag = stringList[stringList.length - 1];
		return contentsRightFromStartTag;
	}
	
	private String searchForAttribute(String tag, String attribute, String html) {
		int postitionOfAttribute = html.indexOf(attribute);
		boolean attributeDoesNotExist = postitionOfAttribute == -1;
		if(attributeDoesNotExist) {
			return "";
		}
		
		String foundString = html.substring(postitionOfAttribute);
		String[] leftAndRightFromDeclaration = foundString.split("content=", 2);
		String contentsRight = leftAndRightFromDeclaration[leftAndRightFromDeclaration.length - 1];
		String[] leftAndRightAfterClosingTag = contentsRight.split(">");
		String contentsLeft = leftAndRightAfterClosingTag[0];
		
		var builder = new StringBuilder();
		var doubleQuotes = builder.append('"').toString();
		var valueOfAttribute = contentsLeft.replace(doubleQuotes,"");
		return valueOfAttribute;
	}
	
	private List<String> createKeywordList(String keywordString){
		if(keywordString.isBlank()) {
			return Arrays.asList();
		}
		
		var list = Arrays.asList(keywordString.split(","));
		return list.parallelStream()
		.map(keyword -> keyword.strip())
		.collect(Collectors.toList());
	}
	

	public Optional<Meta> getMeta() { return this.meta; }

}
