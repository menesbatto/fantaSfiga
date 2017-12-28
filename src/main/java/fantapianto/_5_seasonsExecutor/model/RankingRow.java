package fantapianto._5_seasonsExecutor.model;

import java.io.Serializable;

public class RankingRow implements Serializable{

	private static final long serialVersionUID = -8498184099900310703L;

	private String name;
	private int points;					// 56, 60, 58
	private double sumAllVotes;			// 1345, 1200, 1240.5, 
	private int scoredGoals;			// 50, 30, 23
	private int takenGoals;			// 50, 30, 23
	private int rankingPosition;		// 1,2,3,4,5,6,7,8
	
	
	public RankingRow() {
	}


	public RankingRow(String name) {
		this.name = name;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public int getPoints() {
		return points;
	}


	public void setPoints(int points) {
		this.points = points;
	}


	public double getSumAllVotes() {
		return sumAllVotes;
	}


	public void setSumAllVotes(double sumAllVotes) {
		this.sumAllVotes = sumAllVotes;
	}


	public int getScoredGoals() {
		return scoredGoals;
	}


	public void setScoredGoals(int scoredGoals) {
		this.scoredGoals = scoredGoals;
	}


	public int getTakenGoals() {
		return takenGoals;
	}


	public void setTakenGoals(int takenGoals) {
		this.takenGoals = takenGoals;
	}


	public int getRankingPosition() {
		return rankingPosition;
	}


	public void setRankingPosition(int rankingPosition) {
		this.rankingPosition = rankingPosition;
	}


	@Override
	public String toString() {
		return  getNameToString() + "\t" + points + "\t" + sumAllVotes + "\t scoredGoals=\t"
				+ scoredGoals + "\t takenGoals=\t" + takenGoals + "\t" + rankingPosition;
	}


	private String getNameToString() {
		return this.name.length() > 11 ? this.name.substring(0,10) : this.name;
	}

	

}
