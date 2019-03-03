package entrezSearch;

import java.util.Map;
import java.util.TreeMap;

public class Terms {

	public enum SearchTerms {
		JOURNAL, PUBLICATION_DATA;
	}
	
	Map<SearchTerms, String> termsDictionary = new TreeMap<SearchTerms, String>();
	
	public Terms(){
		termsDictionary.put(SearchTerms.JOURNAL, "[journal]");
		termsDictionary.put(SearchTerms.PUBLICATION_DATA, "[pdat]");
	}
	
	public String getTerm(SearchTerms st){
		return termsDictionary.get(st);
	}
}
