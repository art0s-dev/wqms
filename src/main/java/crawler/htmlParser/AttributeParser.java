package crawler.htmlParser;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import crawler.website.Website;

public final class AttributeParser implements HtmlParser {
	
	Website website;
	
	String keywordsAttribute = "keywords";
	String markupBeforeValue = "content=";
	String doubleQuotes = new StringBuilder() 
			.append('"')
			.toString();
	
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
		var html = this.website.body;
		int from = html.indexOf(attribute);
		
		var attributeDoesNotExist = from == -1;
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
		
		var valueAndDoubleQuotes = valueAndTagRest.split(doubleQuotes);
		final int positionOfValue = 1;
		
		return valueAndDoubleQuotes[positionOfValue];
	}

	private String createWholeMetaTag(String attribute, String html) {
		var pageParts = html.split(attribute);
		var first = pageParts[0]; 
		var last = pageParts[1];
		
		int positionOfStartTag = first.lastIndexOf('<');
		
		int offsetForEndTag = 1;
		int positionOfEndTag = last.indexOf('>') + offsetForEndTag;
		
		var tagFromStartToSplit = first.substring(positionOfStartTag);
		
		var fromSplit = 0;
		var tagFromSplitToEnd = last.substring(fromSplit, positionOfEndTag);
		
		return tagFromStartToSplit + tagFromSplitToEnd;
	}

}
