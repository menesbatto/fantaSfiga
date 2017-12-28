package fantapianto._2_seasonPatternExtractor.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SeasonDay  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 9121439474561854857L;
	private String name;
	private Integer nameNumber;
	private List<Match> matches;
	private Integer serieANumber;
	
	
	

	public SeasonDay(String name) {
		this.matches = new ArrayList<Match>();
		this.setName(name);
		Integer nameNumber = Integer.valueOf(name.substring(name.indexOf(" ")+1));
		this.nameNumber = nameNumber;
	}

	public SeasonDay() {
		//Usato solo in un caso inutile
	}

	public List<Match> getMatches() {
		return matches;
	}

	public void setMatches(List<Match> matches) {
		this.matches = matches;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name + "\t" + serieANumber + "\n" + matches + "\n";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((matches == null) ? 0 : matches.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SeasonDay other = (SeasonDay) obj;
		if (matches == null) {
			if (other.matches != null)
				return false;
		} else if (!matches.equals(other.matches))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	public Integer getNameNumber() {
		return nameNumber;
	}

	public void setNameNumber(Integer nameNumber) {
		this.nameNumber = nameNumber;
	}

	public Integer getSerieANumber() {
		return serieANumber;
	}

	public void setSerieANumber(Integer serieANumber) {
		this.serieANumber = serieANumber;
	}
	
	
	
	
	
	
}
