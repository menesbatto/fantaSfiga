package fantapianto._2_seasonPatternExtractor.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Season implements Serializable{
	
	private static final long serialVersionUID = -5241896046608966295L;
	private String name;
	private List<SeasonDay> seasonDays;
	

	public Season() {
		this.seasonDays = new ArrayList<SeasonDay>();
	}

	public Season(String name) {
		this.name = name;
		this.seasonDays = new ArrayList<SeasonDay>();
	}

	public List<SeasonDay> getSeasonDays() {
		return seasonDays;
	}

	public void setSeasonDays(List<SeasonDay> seasonDays) {
		this.seasonDays = seasonDays;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "" + name + "\n, " + seasonDays + "\n\n";
	}
	
	

	
}
