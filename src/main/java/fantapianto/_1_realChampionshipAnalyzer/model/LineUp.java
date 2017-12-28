package fantapianto._1_realChampionshipAnalyzer.model;

import java.util.ArrayList;
import java.util.List;

public class LineUp {
	
	private String teamName;
	
	private List<PlayerVote> goalKeeper;
	private List<PlayerVote> defenders;
	private List<PlayerVote> midfielders;
	private List<PlayerVote> strikers;
	
	private List<PlayerVote> goalKeeperReserve;
	private List<PlayerVote> defendersReserves;
	private List<PlayerVote> midfieldersReserves;
	private List<PlayerVote> strikersReserves;
	
	private List<PlayerVote> reserves;
	
	
	private Double goalkeeperModifier;
	private Double defenderModifier;
	private Double strickerModifier;
	private Double middlefieldersVariation;
	private Double performanceModifier;
	private Double fairPlayModifier;
	
	private List<PlayerVote> finalLineUp;
	
	
	public LineUp() {
		this.goalKeeper = new ArrayList<PlayerVote>();
		this.defenders = new ArrayList<PlayerVote>();
		this.midfielders = new ArrayList<PlayerVote>();
		this.strikers = new ArrayList<PlayerVote>();
		
		this.goalKeeperReserve = new ArrayList<PlayerVote>();
		this.defendersReserves = new ArrayList<PlayerVote>();
		this.midfieldersReserves = new ArrayList<PlayerVote>();
		this.strikersReserves = new ArrayList<PlayerVote>();

		this.reserves = new ArrayList<PlayerVote>();

		this.finalLineUp = new ArrayList<PlayerVote>();
		
		this.defenderModifier = 0.0;
		this.strickerModifier = 0.0;
		this.performanceModifier = 0.0;
		this.fairPlayModifier = 0.0;
		
	}
	public List<PlayerVote> getGoalKeeper() {
		return goalKeeper;
	}
	public void setGoalKeeper(List<PlayerVote> goalKeeper) {
		this.goalKeeper = goalKeeper;
	}
	public List<PlayerVote> getDefenders() {
		return defenders;
	}
	public void setDefenders(List<PlayerVote> defenders) {
		this.defenders = defenders;
	}
	public List<PlayerVote> getMidfielders() {
		return midfielders;
	}
	public void setMidfielders(List<PlayerVote> midfielders) {
		this.midfielders = midfielders;
	}
	public List<PlayerVote> getStrikers() {
		return strikers;
	}
	public void setStrikers(List<PlayerVote> strikers) {
		this.strikers = strikers;
	}
	public List<PlayerVote> getGoalKeeperReserve() {
		return goalKeeperReserve;
	}
	public void setGoalKeeperReserve(List<PlayerVote> goalKeeperReserve) {
		this.goalKeeperReserve = goalKeeperReserve;
	}
	public List<PlayerVote> getDefendersReserves() {
		return defendersReserves;
	}
	public void setDefendersReserves(List<PlayerVote> defendersReserves) {
		this.defendersReserves = defendersReserves;
	}
	public List<PlayerVote> getMidfieldersReserves() {
		return midfieldersReserves;
	}
	public void setMidfieldersReserves(List<PlayerVote> midfieldersReserves) {
		this.midfieldersReserves = midfieldersReserves;
	}
	public List<PlayerVote> getStrikersReserves() {
		return strikersReserves;
	}
	public void setStrikersReserves(List<PlayerVote> strikersReserves) {
		this.strikersReserves = strikersReserves;
	}
	public String getTeamName() {
		return teamName;
	}
	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}
	public Double getDefenderModifier() {
		return defenderModifier;
	}
	public void setDefenderModifier(Double defenderModifier) {
		this.defenderModifier = defenderModifier;
	}
	public Double getStrickerModifier() {
		return strickerModifier;
	}
	public void setStrickerModifier(Double strickerModifier) {
		this.strickerModifier = strickerModifier;
	}
	public List<PlayerVote> getFinalLineUp() {
		return finalLineUp;
	}
	public void setFinalLineUp(List<PlayerVote> finalLineUp) {
		this.finalLineUp = finalLineUp;
	}
	@Override
	public String toString() {
		return "\n\nLineUp [teamName=" + teamName + "\n\n goalKeeper=" + goalKeeper + "\n\n defenders="
				+ defenders + "\n\n midfielders=" + midfielders + "\n\n strikers=" + strikers + "\n\n goalKeeperReserve="
				+ goalKeeperReserve + "\n\n defendersReserves=" + defendersReserves + "\n\n midfieldersReserves="
				+ midfieldersReserves + "\n\n strikersReserves=" + strikersReserves + "\n\n middlefieldersVariation="
				+ middlefieldersVariation + "\n\n defenderModifier=" + defenderModifier + "\n\n strickerModifier=" + strickerModifier + "\n\n finalLineUp=" + finalLineUp + "]";
	}
	public Double getMiddlefieldersVariation() {
		return middlefieldersVariation;
	}
	public void setMiddlefieldersVariation(Double middlefieldersVariation) {
		this.middlefieldersVariation = middlefieldersVariation;
	}
	public List<PlayerVote> getReserves() {
		return reserves;
	}
	public void setReserves(List<PlayerVote> reserves) {
		this.reserves = reserves;
	}
	public Double getGoalkeeperModifier() {
		return goalkeeperModifier;
	}
	public void setGoalkeeperModifier(Double goalkeeperModifier) {
		this.goalkeeperModifier = goalkeeperModifier;
	}
	public Double getPerformanceModifier() {
		return performanceModifier;
	}
	public void setPerformanceModifier(Double performanceModifier) {
		this.performanceModifier = performanceModifier;
	}
	public Double getFairPlayModifier() {
		return fairPlayModifier;
	}
	public void setFairPlayModifier(Double fairPlayModifier) {
		this.fairPlayModifier = fairPlayModifier;
	}

	
	
	
}
