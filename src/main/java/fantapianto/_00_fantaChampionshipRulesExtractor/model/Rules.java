package fantapianto._00_fantaChampionshipRulesExtractor.model;

import java.io.Serializable;

public class Rules  implements Serializable{

	private static final long serialVersionUID = -8460127535161295547L;

	private BonusMalus bonusMalus;
	private DataSources dataSource;
	private Points points;
	private Substitutions substitutions;
	private Modifiers modifiers;
	public BonusMalus getBonusMalus() {
		return bonusMalus;
	}
	public void setBonusMalus(BonusMalus bonusMalus) {
		this.bonusMalus = bonusMalus;
	}
	public DataSources getDataSource() {
		return dataSource;
	}
	public void setDataSource(DataSources dataSource) {
		this.dataSource = dataSource;
	}
	public Points getPoints() {
		return points;
	}
	public void setPoints(Points points) {
		this.points = points;
	}
	public Substitutions getSubstitutions() {
		return substitutions;
	}
	public void setSubstitutions(Substitutions substitution) {
		this.substitutions = substitution;
	}
	public Modifiers getModifiers() {
		return modifiers;
	}
	public void setModifiers(Modifiers modifiers) {
		this.modifiers = modifiers;
	}
	
	
	
		
}
