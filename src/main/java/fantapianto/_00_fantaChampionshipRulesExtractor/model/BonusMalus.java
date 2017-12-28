package fantapianto._00_fantaChampionshipRulesExtractor.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import fantapianto._1_realChampionshipAnalyzer.model.RoleEnum;

public class BonusMalus implements Serializable{
	private static final long serialVersionUID = 1791071872590063199L;

	public Map<RoleEnum, Double> redCard;
	public Map<RoleEnum, Double> yellowCard;
	public Map<RoleEnum, Double> scoredGoal;
	public Map<RoleEnum, Double> scoredPenalty;
	public Map<RoleEnum, Double> movementAssist;
	public Map<RoleEnum, Double> stationaryAssist;
	public Map<RoleEnum, Double> autogoal;
	public Map<RoleEnum, Double> missedPenalty;
	public Map<RoleEnum, Double> savedPenalty;
	public Map<RoleEnum, Double> takenGoal;
	public Map<RoleEnum, Double> evenGoal;
	public Map<RoleEnum, Double> winGoal;
	
	
	
	
	public BonusMalus() {
		redCard = new HashMap<RoleEnum, Double>();
		yellowCard = new HashMap<RoleEnum, Double>();
		scoredGoal = new HashMap<RoleEnum, Double>();
		scoredPenalty = new HashMap<RoleEnum, Double>();
		movementAssist = new HashMap<RoleEnum, Double>();
		stationaryAssist = new HashMap<RoleEnum, Double>();
		autogoal = new HashMap<RoleEnum, Double>();
		missedPenalty = new HashMap<RoleEnum, Double>();
		savedPenalty = new HashMap<RoleEnum, Double>();
		takenGoal = new HashMap<RoleEnum, Double>();
		evenGoal = new HashMap<RoleEnum, Double>();
		winGoal = new HashMap<RoleEnum, Double>();
	
	}
	public Map<RoleEnum, Double> getRedCard() {
		return redCard;
	}
	public void setRedCard(Map<RoleEnum, Double> redCard) {
		this.redCard = redCard;
	}
	public Map<RoleEnum, Double> getYellowCard() {
		return yellowCard;
	}
	public void setYellowCard(Map<RoleEnum, Double> yellowCard) {
		this.yellowCard = yellowCard;
	}
	public Map<RoleEnum, Double> getScoredGoal() {
		return scoredGoal;
	}
	public void setScoredGoal(Map<RoleEnum, Double> scoredGoal) {
		this.scoredGoal = scoredGoal;
	}
	public Map<RoleEnum, Double> getScoredPenalty() {
		return scoredPenalty;
	}
	public void setScoredPenalty(Map<RoleEnum, Double> scoredPenalty) {
		this.scoredPenalty = scoredPenalty;
	}
	public Map<RoleEnum, Double> getMovementAssist() {
		return movementAssist;
	}
	public void setMovementAssist(Map<RoleEnum, Double> movementsAssist) {
		this.movementAssist = movementsAssist;
	}
	public Map<RoleEnum, Double> getStationaryAssist() {
		return stationaryAssist;
	}
	public void setStationaryAssist(Map<RoleEnum, Double> stationaryAssist) {
		this.stationaryAssist = stationaryAssist;
	}
	public Map<RoleEnum, Double> getAutogoal() {
		return autogoal;
	}
	public void setAutogoal(Map<RoleEnum, Double> autogoal) {
		this.autogoal = autogoal;
	}
	public Map<RoleEnum, Double> getMissedPenalty() {
		return missedPenalty;
	}
	public void setMissedPenalty(Map<RoleEnum, Double> missedPenalty) {
		this.missedPenalty = missedPenalty;
	}
	public Map<RoleEnum, Double> getSavedPenalty() {
		return savedPenalty;
	}
	public void setSavedPenalty(Map<RoleEnum, Double> savedPenalty) {
		this.savedPenalty = savedPenalty;
	}
	public Map<RoleEnum, Double> getTakenGoal() {
		return takenGoal;
	}
	public void setTakenGoal(Map<RoleEnum, Double> takenGoals) {
		this.takenGoal = takenGoals;
	}
	public Map<RoleEnum, Double> getEvenGoal() {
		return evenGoal;
	}
	public void setEvenGoal(Map<RoleEnum, Double> evenGoal) {
		this.evenGoal = evenGoal;
	}
	public Map<RoleEnum, Double> getWinGoal() {
		return winGoal;
	}
	public void setWinGoal(Map<RoleEnum, Double> winGoal) {
		this.winGoal = winGoal;
	}
	@Override
	public String toString() {
		return "BonusMalus [redCard=" + redCard + "\n yellowCard=" + yellowCard + "\n scoredGoal=" + scoredGoal
				+ "\n scoredPenalty=" + scoredPenalty + "\n movementAssist=" + movementAssist + "\n stationaryAssist="
				+ stationaryAssist + "\n autogoal=" + autogoal + "\n missedPenalty=" + missedPenalty + "\n savedPenalty="
				+ savedPenalty + "\n takenGoal=" + takenGoal + "\n evenGoal=" + evenGoal + "\n winGoal=" + winGoal + "]";
	}
	
	
	
	
	
}
