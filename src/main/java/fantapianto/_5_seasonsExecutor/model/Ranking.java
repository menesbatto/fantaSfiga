package fantapianto._5_seasonsExecutor.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Ranking implements Serializable{

	private static final long serialVersionUID = 8438190796515182835L;

	private int name;
	
	private List<RankingRow> rows;

	public Ranking() {
		this.rows = new ArrayList<RankingRow>();
	}

	public List<RankingRow> getRows() {
		return rows;
	}

	public void setRows(List<RankingRow> rows) {
		this.rows = rows;
	}

	@Override
	public String toString() {
		return rows.toString();
	}

	public int getName() {
		return name;
	}

	public void setName(int name) {
		this.name = name;
	}

	
	
	
	

}
