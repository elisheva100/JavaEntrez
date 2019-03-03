package entrezSearch;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import entrezRetrieval.RetrieveClient;
import entrezSearch.DataBase.DBType;
import entrezSearch.Terms.SearchTerms;

/*
 * This program uses the NCBI eutils for searching and fetching data.
 * The program searches for all of the publications in the journal 
 * Science, which were published in 2009, containing the term: ‘diabetes’.
 * Afterwards it extracts the first 5 documents and using the efetch eutils
 *  to print the details of each of the Id’s found in question 3. Print their basic
 *  details and abstracts. 
 */

public class SearchClient {
	
	public static void main(String[] args) throws Exception{
		
		DataBase DB = new DataBase(DBType.LITERATURE);
		String[] terms = {"diabetes"}; //The given term 
		
		List<String> diabetesIds = new ArrayList<String>();	
		
		Map<SearchTerms, String> termsMap = new TreeMap<SearchTerms, String>();
		termsMap.put(SearchTerms.JOURNAL, "Science");
		termsMap.put(SearchTerms.PUBLICATION_DATA, "2009");
		
		String query = buildQuery(DB, terms, termsMap);
		
		diabetesIds = searchEntrez(query);
		diabetesIds.subList(0, 2).clear(); //Remove the headers.
		/*
		 * Trim all the tags: <> from the given Ids.
		 */
		for (int i = 0; i < diabetesIds.size(); i++) {
			diabetesIds.set(i, diabetesIds.get(i).replace("<Id>", ""));
			diabetesIds.set(i, diabetesIds.get(i).replace("</Id>", ""));		    
		}
		
		String[] ids = new String[diabetesIds.size()];
		diabetesIds.toArray(ids); //Convert the ArrayList into array;

		//Fetch the data - The abstracts of the articles with the given Ids.
		//Build DB of the entrezRetrieval for fetching data.
		entrezRetrieval.DataBase DBdiabetes = new entrezRetrieval.DataBase(entrezRetrieval.DataBase.DBType.LITERATURE);
		//Create the entrezRetrieval Query.
		String query_diabetesIds = RetrieveClient.buildQuery(DBdiabetes, ids);
		//Fetch the data - basic details and the abstracts of the articles with the given Ids.
		RetrieveClient.callEntrez(query_diabetesIds);
		
	}
	
	public static String buildQuery(DataBase db, String[] terms, Map<SearchTerms, String> termsMap){
		
		String query = "db=" + db.getPath() + "&term=";
		
		for(Map.Entry<SearchTerms, String> entry : termsMap.entrySet()) {
			String value = entry.getValue();
			query += value;
			query += new Terms().getTerm(entry.getKey()) + "+";
		}
		
		for (int i = 0; i < terms.length - 1; i++) {
			query += terms[i] + "+AND+";
		}
		query += terms[terms.length-1];
		
		return query;
	}
	
	public static List<String> searchEntrez(String id) throws IOException {
		final String entrezSearchURL = "https://eutils.ncbi.nlm.nih.gov/entrez/eutils/esearch.fcgi?";
		
		String query = entrezSearchURL + id;
		
		System.out.println("Query URL: " + query);
		
		DataInputStream din= new DataInputStream(
				new URL(query).openStream());
		
		BufferedReader d = new BufferedReader(new InputStreamReader(din));
		String s = d.readLine();
		
		int counter = 0; //Define a counter for getting the number of Ids on the query.
		int numOfIds = 5;
		List<String> queryIds = new ArrayList<String>(); //A list for the querie's Ids.
		
		 //counter < numOfIds + 2, since there are to information lines before the Ids.
		 while (s != null && counter < numOfIds + 2){			    
			s = d.readLine();
			//System.out.println(s);
			queryIds.add(s);
			counter ++;
		}
		
		return queryIds;
	}

}

/*   -------------------Output-------------------
 Query URL: https://eutils.ncbi.nlm.nih.gov/entrez/eutils/esearch.fcgi?db=pubmed&term=Science[journal]+2009[pdat]+diabetes
Query URL: https://eutils.ncbi.nlm.nih.gov/entrez/eutils/efetch.fcgi?db=pubmed&id=20007881,19965397,19965390,19797661,19608888&retmode=text&rettype=abstract
1. Science. 2009 Dec 11;326(5959):1478-9. doi: 10.1126/science.326.5959.1478.

Human evolution. What's for dinner? Researchers seek our ancestors' answers.

Gibbons A.

DOI: 10.1126/science.326.5959.1478 
PMID: 20007881  [Indexed for MEDLINE]


2. Science. 2009 Nov 20;326(5956):1049. doi: 10.1126/science.326.5956.1049-a.

Cell therapies. Clean pigs offer alternative to stem cell transplants.

Holden C.

DOI: 10.1126/science.326.5956.1049-a 
PMID: 19965397  [Indexed for MEDLINE]


3. Science. 2010 Jan 8;327(5962):217-20. doi: 10.1126/science.1176827. Epub 2009 Nov
19.

Overexpression of alpha2A-adrenergic receptors contributes to type 2 diabetes.

Rosengren AH(1), Jokubka R, Tojjar D, Granhall C, Hansson O, Li DQ, Nagaraj V,
Reinbothe TM, Tuncel J, Eliasson L, Groop L, Rorsman P, Salehi A, Lyssenko V,
Luthman H, RenstrÃ¶m E.

Author information: 
(1)Lund University Diabetes Centre, MalmÃ¶, SE-20502 MalmÃ¶, Sweden.

Several common genetic variations have been associated with type 2 diabetes, but 
the exact disease mechanisms are still poorly elucidated. Using congenic strains 
from the diabetic Goto-Kakizaki rat, we identified a 1.4-megabase genomic locus
that was linked to impaired insulin granule docking at the plasma membrane and
reduced beta cell exocytosis. In this locus, Adra2a, encoding the
alpha2A-adrenergic receptor [alpha(2A)AR], was significantly overexpressed.
Alpha(2A)AR mediates adrenergic suppression of insulin secretion. Pharmacological
receptor antagonism, silencing of receptor expression, or blockade of downstream 
effectors rescued insulin secretion in congenic islets. Furthermore, we
identified a single-nucleotide polymorphism in the human ADRA2A gene for which
risk allele carriers exhibited overexpression of alpha(2A)AR, reduced insulin
secretion, and increased type 2 diabetes risk. Human pancreatic islets from risk 
allele carriers exhibited reduced granule docking and secreted less insulin in
response to glucose; both effects were counteracted by pharmacological
alpha(2A)AR antagonists.

DOI: 10.1126/science.1176827 
PMID: 19965390  [Indexed for MEDLINE]


4. Science. 2009 Oct 2;326(5949):140-4. doi: 10.1126/science.1177221.

Ribosomal protein S6 kinase 1 signaling regulates mammalian life span.

Selman C(1), Tullet JM, Wieser D, Irvine E, Lingard SJ, Choudhury AI, Claret M,
Al-Qassab H, Carmignac D, Ramadani F, Woods A, Robinson IC, Schuster E, Batterham
RL, Kozma SC, Thomas G, Carling D, Okkenhaug K, Thornton JM, Partridge L, Gems D,
Withers DJ.

Author information: 
(1)Institute of Healthy Ageing, Centre for Diabetes and Endocrinology, Department
of Medicine, University College London, London WC1E 6JJ, UK.

Erratum in
    Science. 2011 Oct 7;334(6052):39.

Comment in
    Science. 2009 Oct 2;326(5949):55-6.

Caloric restriction (CR) protects against aging and disease, but the mechanisms
by which this affects mammalian life span are unclear. We show in mice that
deletion of ribosomal S6 protein kinase 1 (S6K1), a component of the
nutrient-responsive mTOR (mammalian target of rapamycin) signaling pathway, led
to increased life span and resistance to age-related pathologies, such as bone,
immune, and motor dysfunction and loss of insulin sensitivity. Deletion of S6K1
induced gene expression patterns similar to those seen in CR or with
pharmacological activation of adenosine monophosphate (AMP)-activated protein
kinase (AMPK), a conserved regulator of the metabolic response to CR. Our results
demonstrate that S6K1 influences healthy mammalian life-span and suggest that
therapeutic manipulation of S6K1 and AMPK might mimic CR and could provide broad 
protection against diseases of aging.

DOI: 10.1126/science.1177221 
PMCID: PMC4954603
PMID: 19797661  [Indexed for MEDLINE]


5. Science. 2009 Jul 17;325(5938):256-60. doi: 10.1126/science.325_256.

Insulin resistance. Prosperity's plague.

Taubes G.

DOI: 10.1126/science.325_256 
PMID: 19608888  [Indexed for MEDLINE]

null

 */
