package entrezRetrieval;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;


import entrezRetrieval.DataBase.DBType;

/*
 * This class uses uses the NCBI efetch for retrieving data in 3 different formats:
 * 1) LITERATURE - basic details and abstract of given article.
 * 2) PROTEIN - The sequence of given protein in fasta format.
 * 3) NUCLEOTIDE - The sequence of given gene in fasta format.
 * Note: The queries were given in class are still in the code for retrieving PROTEIN & NUCLEOTIDE data.
 */

public class RetrieveClient {
	
	public static void main(String[] args) throws Exception{
		
		// retrieving data from PubMed
		DataBase DB = new DataBase(DBType.LITERATURE);
		String[] ids = {"9705509","19745054"};
		
		String query = buildQuery(DB, ids);
		
		callEntrez(query);
		
		// retrieving sequences
		DataBase DB2 = new DataBase(DBType.NUCLEOTIDE);
		String[] ids2 = {"5"};
		
		String query2 = buildQuery(DB2, ids2);
		
		callEntrez(query2);
		
		// retrieving proteins
		DataBase DB3 = new DataBase(DBType.PROTEIN);
		String[] ids3 = {"28800982","28628843"};
		
		String query3 = buildQuery(DB3, ids3);
		
		callEntrez(query3);

		
    }
	
	public static String buildQuery(DataBase dBdiabetes, String[] ids){
		
		String query = "db=" + dBdiabetes.getPath() + "&id=";
		
		for (int i = 0; i < ids.length - 1; i++)
			query += ids[i] + ",";
		query += ids[ids.length - 1] + "&";
		
		if (dBdiabetes.getType().equals(DBType.LITERATURE))
			query += "retmode=text&rettype=abstract";
		else if(dBdiabetes.getType().equals(DBType.PROTEIN))
			query += "retmode=text&rettype=fasta";
		else if(dBdiabetes.getType().equals(DBType.NUCLEOTIDE))
			query += "retmode=text&rettype=fasta";
		else query += "&rettype=gp";
		
		return query;
	}
	
	public static void callEntrez(String id) throws IOException {
		final String  EntrezURL = "https://eutils.ncbi.nlm.nih.gov/entrez/eutils/efetch.fcgi?";
		
		String query = EntrezURL + id;
		
		System.out.println("Query URL: " + query);
		
		DataInputStream din= new DataInputStream(
				new URL(query).openStream());
		BufferedReader d = new BufferedReader(new InputStreamReader(din));
		String s = d.readLine();
		
		while (s != null){	
			s = d.readLine();
			System.out.println(s);
		}
	}

}

/* 
  -------------------Output-------------------
 Query URL: https://eutils.ncbi.nlm.nih.gov/entrez/eutils/efetch.fcgi?db=pubmed&id=9705509,19745054&retmode=text&rettype=abstract
1. Nucleic Acids Res. 1998 Sep 1;26(17):3986-90.

Protein sequence similarity searches using patterns as seeds.

Zhang Z(1), Schäffer AA, Miller W, Madden TL, Lipman DJ, Koonin EV, Altschul SF.

Author information: 
(1)Department of Computer Science and Engineering, Pennsylvania State University,
University Park, PA 16802, USA.

Protein families often are characterized by conserved sequence patterns or
motifs. A researcher frequently wishes to evaluate the significance of a specific
pattern within a protein, or to exploit knowledge of known motifs to aid the
recognition of greatly diverged but homologous family members. To assist in these
efforts, the pattern-hit initiated BLAST (PHI-BLAST) program described here takes
as input both a protein sequence and a pattern of interest that it contains.
PHI-BLAST searches a protein database for other instances of the input pattern,
and uses those found as seeds for the construction of local alignments to the
query sequence. The random distribution of PHI-BLAST alignment scores is studied 
analytically and empirically. In many instances, the program is able to detect
statistically significant similarity between homologous proteins that are not
recognizably related using traditional single-pass database search methods.
PHI-BLAST is applied to the analysis of CED4-like cell death regulators,
HS90-type ATPase domains, archaeal tRNA nucleotidyltransferases and archaeal
homologs of DnaG-type DNA primases.


PMCID: PMC147803
PMID: 9705509  [Indexed for MEDLINE]


2. Nucleic Acids Res. 2009 Nov;37(20):6799-810. doi: 10.1093/nar/gkp712. Epub 2009
Sep 10.

Selection for minimization of translational frameshifting errors as a factor in
the evolution of codon usage.

Huang Y(1), Koonin EV, Lipman DJ, Przytycka TM.

Author information: 
(1)National Center for Biotechnology Information, National Library of Medicine,
National Institutes of Health, Bethesda, MD 20894, USA.

In a wide range of genomes, it was observed that the usage of synonymous codons
is biased toward specific codons and codon patterns. Factors that are implicated 
in the selection for codon usage include facilitation of fast and accurate
translation. There are two types of translational errors: missense errors and
processivity errors. There is considerable evidence in support of the hypothesis 
that codon usage is optimized to minimize missense errors. In contrast, little is
known about the relationship between codon usage and frameshifting errors, an
important form of processivity errors, which appear to occur at frequencies
comparable to the frequencies of missense errors. Based on the recently proposed 
pause-and-slip model of frameshifting, we developed Frameshifting Robustness
Score (FRS). We used this measure to test if the pattern of codon usage indicates
optimization against frameshifting errors. We found that the FRS values of
protein-coding sequences from four analyzed genomes (the bacteria Bacillus
subtilis and Escherichia coli, and the yeasts Saccharomyces cerevisiae and
Schizosaccharomyce pombe) were typically higher than expected by chance. Other
properties of FRS patterns observed in B. subtilis, S. cerevisiae and S. pombe,
such as the tendency of FRS to increase from the 5'- to 3'-end of protein-coding 
sequences, were also consistent with the hypothesis of optimization against
frameshifting errors in translation. For E. coli, the results of different tests 
were less consistent, suggestive of a much weaker optimization, if any.
Collectively, the results fit the concept of selection against
mistranslation-induced protein misfolding being one of the factors shaping the
evolution of both coding and non-coding sequences.

DOI: 10.1093/nar/gkp712 
PMCID: PMC2777431
PMID: 19745054  [Indexed for MEDLINE]

null
Query URL: https://eutils.ncbi.nlm.nih.gov/entrez/eutils/efetch.fcgi?db=nucleotide&id=5&retmode=text&rettype=fasta
CCAGCGCTCGTCTTGCTGTTGGGGTTTCTCTGCCACGTTGCTATCGCAGGACGAACCTGCCCCAAGCCAG
ATGAGCTACCGTTTTCCACGGTGGTTCCACTGAAACGGACCTATGAGCCCGGGGAGCAGATAGTCTTCTC
CTGCCAGCCGGGCTACGTGTCCCGGGGAGGGATCCGGCGGTTTACATGCCCGCTCACAGGACTCTGGCCC
ATCAACACGCTGAAATGCATGCCCAGAGTATGTCCTTTTGCTGGGATCTTAGAAAACGGAACGGTACGCT
ATACAACGTTTGAGTATCCCAACACCATCAGCTTTTCTTGCCACACGGGGTTTTATCTGAAAGGAGCTAG
TTCTGCAAAATGCACTGAGGAAGGGAAGTGGAGCCCAGACCTTCCTGTCTGTGCCCCTATAACCTGCCCT
CCACCACCCATACCCAAGTTTGCAAGTCTCAGCGTTTACAAGCCGTTGGCTGGGAACAACTCCTTCTATG
GCAGCAAGGCAGTCTTTAAGTGCTTGCCACACCACGCGATGTTTGGAAATGACACCGTTACCTGCACGGA
ACATGGGAACTGGACGCAGTTGCCAGAATGCAGGGAAGTAAGATGCCCATTCCCATCAAGACCAGACAAT
GGGTTTGTGAACCATCCTGCAAATCCAGTGCTCTACTATAAGGACACCGCCACCTTTGGCTGCCATGAAA
CGTATTCCTTGGATGGACCGGAAGAAGTAGAATGCAGCAAATTCGGAAACTGGTCTGCACAGCCAAGCTG
TAAAGCATCTTGTAAGTTATCTATTAAAAGAGCTACTGTGATATATGAAGGAGAGAGAGTAGCTATCCAG
AACAAATTTAAGAATGGAATGCTGCATGGCCAAAAGGTTTCTTTCTTCTGCAAGCATAAGGAAAAGAAGT
GCAGCTACACAGAAGATGCTCAGTGCATAGACGGCACCATCGAGATTCCCAAATGCTTCAAGGAGCACAG
TTCTTTAGCTTTCTGGAAAACGGATGCATCTGACGTAAAACCATGCTAAGCTGGTTTTCACACTGAAAAT
TAAATGTCATGCTTATATGTGTCTGTCTGAGAATCTGATGGAAACGGAAAAATAAAGAGACTGAATTTAC
CGTGTCAAGAAAAAAA

null
Query URL: https://eutils.ncbi.nlm.nih.gov/entrez/eutils/efetch.fcgi?db=protein&id=28800982,28628843&retmode=text&rettype=fasta
SQMWLQLSQSLKGWDHMFTVDFWTIMENHNHSKESHTLQVILGCEMQEDNSTEGYWKYGYDGQDHLEFCP
DTLDWRAAEPRAWPTKLEWERHKIRARQNRAYLERDCPAQLQQLLELGRGVLDQQVPPLVKVTHHVTSSV
TTLRCRALNYYPQNITMKWLKDKQPMDAKEFEPKDVLPNGDGTYQGWITLAVPPGEEQRYTCQVEHPGLD
QPLIVIWEPSPSGTLVIGVISGIAVFVVILFIGILFIILRKRQGSRGAMGHYVLAERE

>AAO49381.1 erythroid associated factor [Homo sapiens]
MALLKANKDLISAGLKEFSVLLNQQVFNDPLVSEEDMVTVVEDWMNFYINYYRQQVTGEPQERDKALQEL
RQELNTLANPFLAKYRDFLKSHELPSHPPPSS

null

 */
