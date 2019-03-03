package entrezSearch;

public class DataBase {
	
	public enum DBType {
		LITERATURE, PROTEIN, NUCLEOTIDE;
	}
	
	private DBType _dbType;
	
	public DataBase (DBType dbType){
		_dbType = dbType;
	}
	
	public String getPath(){
		
		if (_dbType.equals(DBType.LITERATURE)) {return "pubmed";} 
		else if (_dbType.equals(DBType.PROTEIN)) {return "protein";}
		else if (_dbType.equals(DBType.NUCLEOTIDE)) {return "nucleotide";}
		else return null;
	}
	
	public DBType getType(){
		return _dbType;
	}

}
