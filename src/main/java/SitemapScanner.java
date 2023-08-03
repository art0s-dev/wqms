import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.function.Function;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class SitemapScanner {
    private URL baseUrl;
    private String searchForAllLinksOnAPage = "(?<=href=\").[^\"]*";

    public SitemapScanner(URL baseUrl){
        this.baseUrl = baseUrl;
    }

    public List<String> create() throws ParserConfigurationException, IOException, SAXException, SAXParseException {

        var url = this.baseUrl;
        List<String> linkList = new java.util.ArrayList<>(List.of());

        var documentIsHtml = !url.toString().contains("xml");
        if(documentIsHtml) {
            return searchDocumentForLinksAndSaveToLinkList(url);
        }

        var rootDocument = parseDocument(url);
        Function<String, Boolean> documentContains = (nodeName) -> {
            return rootDocument.getElementsByTagName(nodeName).getLength() > 0;
        };

        var linkIsSitemap = documentContains.apply("urlset");
        if (linkIsSitemap) {
            return parseSitemap(rootDocument);
        }

        var linkIsSiteindex = documentContains.apply("sitemapindex");
        if (linkIsSiteindex) {
            return parseSiteindex(parseSitemap(rootDocument));
        }

        return linkList;
    }

    private Document parseDocument(URL url) throws IOException, ParserConfigurationException, SAXException {
        InputStream stream = url.openStream();
        DocumentBuilderFactory instance = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = instance.newDocumentBuilder();
        {
            builder.setErrorHandler(null);
        }

        return builder.parse(stream);
    }

    private List<String> parseSitemap(Document document) {
        var listOfLocations = document.getElementsByTagName("loc");
        var numberOfNodes = listOfLocations.getLength();
        List<String> linkList = new java.util.ArrayList<>(List.of());

        for (int i = 0; i < numberOfNodes; ++i) {
            var node = listOfLocations.item(i).getTextContent();
            linkList.add(node);
        }

        return linkList;
    }

    private List<String> parseSiteindex(List<String> listOfSitemaps) {
        return listOfSitemaps.parallelStream()
                .map(this::convertSitemapLinklist)
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }

    private List<String> convertSitemapLinklist(String link) {
        try {
            var urlFromSiteindex = new URL(link);
            var sitemap = parseDocument(urlFromSiteindex);
            return parseSitemap(sitemap);
        } catch (Exception e) {
            return List.of();
        }
    }

    private List<String> searchDocumentForLinksAndSaveToLinkList(URL url) {
        List<String> linkList = new java.util.ArrayList<>(List.of());

		try(var stream = url.openStream()){
			var byteArrayOutputStream = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024];
			for (int length; (length = stream.read(buffer)) != -1; ) {
                byteArrayOutputStream.write(buffer, 0, length);
			}

			var page = byteArrayOutputStream.toString(StandardCharsets.UTF_8);
			var pattern = Pattern.compile(searchForAllLinksOnAPage, Pattern.CASE_INSENSITIVE);
			var matcher = pattern.matcher(page);

            linkList.addAll(matcher.results().parallel()
                    .map(MatchResult::group)
                    .filter(result -> !result.contains("css"))
                    .filter(result -> !result.contains("#"))
                    .filter(result -> !result.contains("http"))
                    .filter(result -> !result.contains("woff"))
                    .filter(result -> result.contains("/"))
                    .map(uri -> baseUrl.toString() + uri)
                    .toList()
            );

            return linkList;

		} catch (Exception e){
			return new java.util.ArrayList<>(List.of());
		}
    }
}
