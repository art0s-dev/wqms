package crawler.htmlParser;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import crawler.website.Website;

public final class AttributeParser implements HtmlParser {
	
	Website website;	
	String keywordsAttribute = "keywords";
	String markupBeforeValue = "content=";
	
	public AttributeParser(Website website) {
		this.website = website;
	}

	public String extract(String information) {
		return searchForAttribute(information);
	}
	
	public List<String> extractKeywords(){
		var keywords = searchForAttribute(keywordsAttribute);
		return createKeywordList(keywords);
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
	
	private String searchForAttribute(String attribute) {
		var html = website.body;
		var attributeDoesNotExist = html.indexOf(attribute) == -1;
		if(attributeDoesNotExist) {
			return "";
		}
		
		var wholeMetaTag = createWholeMetaTag(attribute, html);
		
		var positionOfMarkup = wholeMetaTag.indexOf(markupBeforeValue);
		var valueIsNotSet = positionOfMarkup == -1;
		if(valueIsNotSet) {
			return ""; 
		}
		
		return extractValueFromMetaTag(wholeMetaTag);
	}

	private String extractValueFromMetaTag(String metaTag) {
		var markupAndValue = metaTag.split(markupBeforeValue);
		var valueAndTagRest = markupAndValue[markupAndValue.length -1];
		
		var doubleQuotes = "\"";
		var valueAndDoubleQuotes = valueAndTagRest.split(doubleQuotes);
		final int positionOfValue = 1;
		
		return valueAndDoubleQuotes[positionOfValue];
	}

	private String createWholeMetaTag(String attribute, String html) {
		var pageParts = html.split(attribute);
		var first = pageParts[0]; 
		var last = pageParts[1];
		
		return createTagFromStartToSplit(first)
			+ createTagFromSplitToEnd(last);
	}

	private String createTagFromStartToSplit(String first) {
		int positionOfStartTag = first.lastIndexOf('<');
		return first.substring(positionOfStartTag);
	}

	private String createTagFromSplitToEnd(String last) {
		int offsetForEndTag = 1;
		var fromSplit = 0;
		
		int positionOfEndTag = last.indexOf('>') + offsetForEndTag;
		return last.substring(fromSplit, positionOfEndTag);
	}

}
