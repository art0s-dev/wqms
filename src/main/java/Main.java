import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;

public class Main {
	public static void main(String[] args) throws MalformedURLException{
		var url = new URL(args[0]);
		List<URL> pageList = LinkList.create(url);
		
		HashMap<String, List<String>> problems = new HashMap<>();
		pageList.parallelStream().forEach(
			(website) -> problems.put(website.toString(), Assignment.assign(url))
		);
		
		System.out.print(problems.toString());
	}
}

