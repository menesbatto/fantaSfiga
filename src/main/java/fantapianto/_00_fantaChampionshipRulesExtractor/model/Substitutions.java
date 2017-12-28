package fantapianto._00_fantaChampionshipRulesExtractor.model;

import java.io.Serializable;

public class Substitutions  implements Serializable{

	private static final long serialVersionUID = 5180169100397162838L;

	private Integer substitutionNumber;
	private SubstitutionsModeEnum substitutionMode;
	
	private Boolean yellowCardSvOfficeVoteActive;
	private Double yellowCardOfficeVote;
	
	private Boolean goalkeeperPlayerOfficeVoteActive;
	private Double goalkeeperPlayerOfficeVote;

	private Boolean movementsPlayerOfficeVoteActive;
	private Double movementsPlayerOfficeVote;
	
	
	public Integer getSubstitutionNumber() {
		return substitutionNumber;
	}
	public void setSubstitutionNumber(Integer substitutionNumber) {
		this.substitutionNumber = substitutionNumber;
	}
	public SubstitutionsModeEnum getSubstitutionMode() {
		return substitutionMode;
	}
	public void setSubstitutionMode(SubstitutionsModeEnum substitutionMode) {
		this.substitutionMode = substitutionMode;
	}
	public Double getYellowCardSvOfficeVote() {
		return yellowCardOfficeVote;
	}
	public void setYellowCardSvOfficeVote(Double yellowCardvOfficeVote) {
		this.yellowCardOfficeVote = yellowCardvOfficeVote;
	}
	public Double getGoalkeeperPlayerOfficeVote() {
		return goalkeeperPlayerOfficeVote;
	}
	public void setGoalkeeperPlayerOfficeVote(Double goalkeeperPlayerOfficeVote) {
		this.goalkeeperPlayerOfficeVote = goalkeeperPlayerOfficeVote;
	}
	public Double getMovementsPlayerOfficeVote() {
		return movementsPlayerOfficeVote;
	}
	public void setMovementsPlayerOfficeVote(Double movementsPlayerOfficeVote) {
		this.movementsPlayerOfficeVote = movementsPlayerOfficeVote;
	}
	public Boolean isYellowCardSvOfficeVoteActive() {
		return yellowCardSvOfficeVoteActive;
	}
	public void setYellowCardSvOfficeVoteActive(Boolean yellowCardSvOfficeVoteActive) {
		this.yellowCardSvOfficeVoteActive = yellowCardSvOfficeVoteActive;
	}
	public Boolean isGoalkeeperPlayerOfficeVoteActive() {
		return goalkeeperPlayerOfficeVoteActive;
	}
	public void setGoalkeeperPlayerOfficeVoteActive(Boolean goalkeeperPlayerOfficeVoteActive) {
		this.goalkeeperPlayerOfficeVoteActive = goalkeeperPlayerOfficeVoteActive;
	}
	public Boolean isMovementsPlayerOfficeVoteActive() {
		return movementsPlayerOfficeVoteActive;
	}
	public void setMovementsPlayerOfficeVoteActive(Boolean movementsPlayerOfficeVoteActive) {
		this.movementsPlayerOfficeVoteActive = movementsPlayerOfficeVoteActive;
	}
	@Override
	public String toString() {
		return "Substitutions [substitutionNumber=" + substitutionNumber + "\n substitutionMode=" + substitutionMode
				+ "\n yellowCardSvOfficeVoteActive=" + yellowCardSvOfficeVoteActive + "\n yellowCardOfficeVote="
				+ yellowCardOfficeVote + "\n goalkeeperPlayerOfficeVoteActive=" + goalkeeperPlayerOfficeVoteActive
				+ "\n goalkeeperPlayerOfficeVote=" + goalkeeperPlayerOfficeVote + "\n movementsPlayerOfficeVoteActive="
				+ movementsPlayerOfficeVoteActive + "\n movementsPlayerOfficeVote=" + movementsPlayerOfficeVote + "]";
	}
	
	
	
	
}
