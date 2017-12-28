package fantapianto._1_realChampionshipAnalyzer.model;

public class Pair {
	private Integer rowIndex;
	private Integer columnIndex;
	private String columnString;
	
	
	
	public Pair(Integer rowIndex, Integer columnIndex) {
		this.rowIndex = rowIndex;
		this.columnIndex = columnIndex;
		this.columnString = columnIndex >= 0 && columnIndex < 26 ? String.valueOf((char)(columnIndex + 65)) : null; 
	}
	
	
	public Integer getRowIndex() {
		return rowIndex;
	}
	public void setRowIndex(Integer row) {
		this.rowIndex = row;
	}
	public Integer getColumnIndex() {
		return columnIndex;
	}
	public void setColumnIndex(Integer column) {
		this.columnIndex = column;
	}
	public String getColumnString() {
		return columnString;
	}
	public void setColumnString(String columnString) {
		this.columnString = columnString;
	}


	@Override
	public String toString() {
		return "Pair [rowIndex=" + rowIndex + ", columnIndex=" + columnIndex + ", columnString=" + columnString + "]";
	}
	
	
	
}
