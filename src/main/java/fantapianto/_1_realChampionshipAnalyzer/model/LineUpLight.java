package fantapianto._1_realChampionshipAnalyzer.model;

import java.io.Serializable;


public class LineUpLight implements Serializable {
	private String teamName;
	
	private Double middlefieldersVariation;

	private Double goalkeeperModifier;
	
	private Double totalWithoutGoalkeeperAndMiddlefielderModifiers;

	private Double sumTotalPoints;		//punti fatti 66, 75.5;

	private int goals;					//Gol fatti 1,2,3,4,5,		

	private int takenGoals;					//Gol Presi 1,2,3,4,5,		

	private int rankingPoints;			//0, 1, 3
										//X, V, P
	
	
	
	
	public LineUpLight(String teamName, Double middlefieldersVariation, Double totalWithoutModifiers) {
		this.teamName = teamName;
		this.middlefieldersVariation = middlefieldersVariation;
		this.totalWithoutGoalkeeperAndMiddlefielderModifiers = totalWithoutModifiers;
	}

	public LineUpLight() {
		// TODO Auto-generated constructor stub
	}

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public Double getMiddlefieldersVariation() {
		return middlefieldersVariation;
	}

	public void setMiddlefieldersVariation(Double middlefieldersVariation) {
		this.middlefieldersVariation = middlefieldersVariation;
	}

	public Double getTotalWithoutGoalkeeperAndMiddlefielderModifiers() {
		return totalWithoutGoalkeeperAndMiddlefielderModifiers;
	}

	public void setTotalWithoutGoalkeeperAndMiddlefielderModifiers(Double totalWithoutModifiers) {
		this.totalWithoutGoalkeeperAndMiddlefielderModifiers = totalWithoutModifiers;
	}

	public Double getSumTotalPoints() {
		return sumTotalPoints;
	}

	public void setSumTotalPoints(Double sumVotePoints) {
		this.sumTotalPoints = sumVotePoints;
	}

	public int getGoals() {
		return goals;
	}

	public void setGoals(int goals) {
		this.goals = goals;
	}

	public int getRankingPoints() {
		return rankingPoints;
	}

	public void setRankingPoints(int rankingPoints) {
		this.rankingPoints = rankingPoints;
	}

	@Override
	public String toString() {
		return "\n[teamName=" + getTeamName() + "\t sumTotalPoints="
				+ sumTotalPoints + "\t goals=" + goals + "\t rankingPoints=" + rankingPoints + "\t midVariation=" + middlefieldersVariation 
				+ "\t goalVariation=" + goalkeeperModifier
				+ "\t totalWithoutMidMod=" + totalWithoutGoalkeeperAndMiddlefielderModifiers + "]";
	}

	private String getTeamNameShort() {
		if (teamName.length()>10){
			return teamName.substring(0,10);
		}
		else if (teamName.length()<8){
			return teamName +"\t";
		} 
		else return teamName;
	}

	public int getTakenGoals() {
		return takenGoals;
	}

	public void setTakenGoals(int takenGoals) {
		this.takenGoals = takenGoals;
	}

	public Double getGoalkeeperModifier() {
		return goalkeeperModifier;
	}

	public void setGoalkeeperModifier(Double goalkeeperModifier) {
		this.goalkeeperModifier = goalkeeperModifier;
	}

	
	
}
