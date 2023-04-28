package crawler;

import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import crawler.extractor.Meta;

public final class MetaExtractor implements CanScanASingleWebPage,CanBeStarted {
	private final WebsiteScanner scanner;
	private Optional<Meta> meta = Optional.empty();

	public MetaExtractor(WebsiteScanner scanner) {
		this.scanner = scanner;
	}
	
	public void start() {
		
		var response = scanner.scan();
		var scanWasNotSuccessfull = response.isEmpty();
				
		if(scanWasNotSuccessfull) {
			return;
		}
		
		var responseContents = response.orElseThrow();
		//Meta meta = extractInformations(responseContents);
		//this.meta = Optional.of(meta);
	}
	
	//public Optional<Meta> getMeta() { return this.meta; }
	
	/*private Meta extractInformations(HttpResponse<String> response) {
		
		var body = response.body().toString();
		var title = this.searchForTag("title", body);
		var description = this.searchForAttribute("description", body);
		var keywords = this.searchForAttribute("keywords", body);
		List<String> keywordList = this.createKeywordList(keywords);
		
		//return new Meta(body,title,description,keywordList);
	}*/
	
	private String searchForTag(String tag, String html) {
		
		var startTag = "<" + tag + ">";
		int postionOfStartTag = html.indexOf(startTag); 
		int postionOfEndTag = html.indexOf("</" + tag + ">");
		
		String foundString = html.substring(postionOfStartTag, postionOfEndTag);
		String[] stringList = foundString.split(startTag);
		
		return stringList[stringList.length - 1];
	}
	
	private String searchForAttribute(String attribute, String html) {
		
		int postitionOfAttribute = html.indexOf(attribute);
		var attributeDoesNotExist = postitionOfAttribute == -1;
		
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
		return contentsLeft.replace(doubleQuotes,"");
	}
	
	private List<String> createKeywordList(String keywordString) {
		
		var thereAreNoKeywords = keywordString.isBlank();
		if(thereAreNoKeywords) {
			return Arrays.asList();
		}
		
		var list = Arrays.asList(keywordString.split(","));
		return list.parallelStream()
				.map(keyword -> keyword.strip())
				.collect(Collectors.toList());
	}
}
