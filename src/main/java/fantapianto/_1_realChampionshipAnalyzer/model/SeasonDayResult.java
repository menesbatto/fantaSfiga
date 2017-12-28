package fantapianto._1_realChampionshipAnalyzer.model;

import java.io.Serializable;
import java.util.List;

public class SeasonDayResult implements Serializable {
	
	private String name;
	private Integer nameNumber;
	private List<LineUpLight> linesUpLight;
	

	
	
	public SeasonDayResult(String name, List<LineUpLight> linesUpLight) {
		this.name = name;
		this.linesUpLight = linesUpLight;
		Integer nameNumber = Integer.valueOf(name.substring(name.indexOf(" ")+1));
		this.nameNumber = nameNumber;
	}

	public List<LineUpLight> getLinesUpLight() {
		return linesUpLight;
	}

	public void setLinesUpLight(List<LineUpLight> linesUpLight) {
		this.linesUpLight = linesUpLight;
	}

	public Integer getNameNumber() {
		return nameNumber;
	}

	public void setNameNumber(Integer nameNumber) {
		this.nameNumber = nameNumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "\n\nSeasonDayResult [name=" + name + "\n\n nameNumber=" + nameNumber + "\n\n linesUpLight=" + linesUpLight + "]";
	}
	
	
	
	
	
}

