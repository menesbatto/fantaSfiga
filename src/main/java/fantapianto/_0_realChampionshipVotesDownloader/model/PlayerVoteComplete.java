package fantapianto._0_realChampionshipVotesDownloader.model;

import java.io.Serializable;

import fantapianto._1_realChampionshipAnalyzer.model.RoleEnum;

public class PlayerVoteComplete implements Serializable{
	
	private static final long serialVersionUID = -8182568610565154395L;

	private String name;
	private String team;
	private RoleEnum role;
	private Double vote;

	private Boolean yellowCard;
	private Boolean redCard;
	private Double scoredGoals;
	private Double scoredPenalties;
	private Double movementAssists;
	private Double stationaryAssists;

	private Double autogoals;
	private Double missedPenalties;
	
	private Double savedPenalties;
	private Double takenGoals;
	
	
	private Boolean winGoal;
	private Boolean evenGoal;
	private Boolean subIn;
	private Boolean subOut;
	
	private Double fantavote;
	
	
	
	public PlayerVoteComplete(String name, String team, RoleEnum role, Double vote, Boolean yellowCard, Boolean redCard,
			Double scoredGoals, Double scoredPenalties, Double movementAssists,  Double stationaryAssists, Double autogoals, Double missedPenalties,
			Double savedPenalties,  Double takenGoals, Boolean winGoal, Boolean evenGoal, Boolean subIn, Boolean subOut ) {
		this.role = role;
		this.name = name;
		this.team = team;
		this.yellowCard = yellowCard;
		this.redCard = redCard;
		this.scoredGoals = scoredGoals;
		this.scoredPenalties = scoredPenalties;
		this.savedPenalties = savedPenalties;
		this.missedPenalties = missedPenalties;
		this.vote = vote;
		this.takenGoals = takenGoals;
		this.autogoals = autogoals;
		this.movementAssists = movementAssists;
		this.stationaryAssists = stationaryAssists;
		this.winGoal = winGoal;
		this.evenGoal = evenGoal;
		this.subIn = subIn;
		this.subOut = subOut;
	}

	public Boolean getYellowCard() {
		return yellowCard;
	}

	public void setYellowCard(Boolean yellowCard) {
		this.yellowCard = yellowCard;
	}

	public Boolean getRedCard() {
		return redCard;
	}

	public void setRedCard(Boolean redCard) {
		this.redCard = redCard;
	}

	public Double getScoredGoals() {
		return scoredGoals;
	}

	public void setScoredGoals(Double scoredGoals) {
		this.scoredGoals = scoredGoals;
	}

	public Double getScoredPenalties() {
		return scoredPenalties;
	}

	public void setScoredPenalties(Double scoredPenalties) {
		this.scoredPenalties = scoredPenalties;
	}

	public Double getSavedPenalties() {
		return savedPenalties;
	}

	public void setSavedPenalties(Double savedPenalties) {
		this.savedPenalties = savedPenalties;
	}

	public Double getMissedPenalties() {
		return missedPenalties;
	}

	public void setMissedPenalties(Double missedPenalties) {
		this.missedPenalties = missedPenalties;
	}

	public Double getVote() {
		return vote;
	}

	public void setVote(Double vote) {
		this.vote = vote;
	}

	public Double getTakenGoals() {
		return takenGoals;
	}

	public void setTakenGoals(Double takenGoals) {
		this.takenGoals = takenGoals;
	}

	public Double getAutogoals() {
		return autogoals;
	}

	public void setAutogoals(Double autogoals) {
		this.autogoals = autogoals;
	}

	public Double getFantavote() {
		return fantavote;
	}

	public void setFantavote(Double fantavote) {
		this.fantavote = fantavote;
	}

	public RoleEnum getRole() {
		return role;
	}

	public void setRole(RoleEnum role) {
		this.role = role;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTeam() {
		return team;
	}

	public void setTeam(String team) {
		this.team = team;
	}

	public Double getMovementAssists() {
		return movementAssists;
	}

	public void setMovementAssists(Double movementAssists) {
		this.movementAssists = movementAssists;
	}

	public Double getStationaryAssists() {
		return stationaryAssists;
	}

	public void setStationaryAssists(Double stationaryAssists) {
		this.stationaryAssists = stationaryAssists;
	}

	@Override
	public String toString() {
		return "PlayerVoteComplete [role=" + role + "\t name=" + name + "\t team=" + team + "\t vote=" + vote
				+ "\t yellowCard=" + yellowCard + "\t redCard=" + redCard + "\t scoredGoals=" + scoredGoals
				+ "\t scoredPenalties=" + scoredPenalties + "\t savedPenalties=" + savedPenalties + "\t missedPenalties="
				+ missedPenalties + "\t takenGoals=" + takenGoals + "\t autogoals=" + autogoals + "\t movementAssists="
				+ movementAssists + "\t stationaryAssists=" + stationaryAssists + "\t fantavote=" + fantavote + "]\n";
	}

	public Boolean getWinGoal() {
		return winGoal;
	}

	public void setWinGoal(Boolean winGoal) {
		this.winGoal = winGoal;
	}

	public Boolean getEvenGoal() {
		return evenGoal;
	}

	public void setEvenGoal(Boolean evenGoal) {
		this.evenGoal = evenGoal;
	}

	public Boolean getSubIn() {
		return subIn;
	}

	public void setSubIn(Boolean subIn) {
		this.subIn = subIn;
	}

	public Boolean getSubOut() {
		return subOut;
	}

	public void setSubOut(Boolean subOut) {
		this.subOut = subOut;
	}
	
	
	
	
	
	
}
