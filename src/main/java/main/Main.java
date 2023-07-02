package main;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;

public class Main {
	public static void main(String[] args) throws MalformedURLException{
		URL url = new URL(args[0]);
		List<String> pageList = Sitemap.create(url);
		
		HashMap<String, List<String>> problems = new HashMap<>();
		pageList.parallelStream().forEach(
			(website) -> problems.put(website, Assignment.getPossibleProblems(website))
		);
		
		System.out.print(problems.toString());
	}
}

