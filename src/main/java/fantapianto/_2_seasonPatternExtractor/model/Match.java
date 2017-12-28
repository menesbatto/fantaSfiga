package fantapianto._2_seasonPatternExtractor.model;

import java.io.Serializable;

import fantapianto._1_realChampionshipAnalyzer.model.LineUpLight;

public class Match  implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6875552801573617783L;
	private String homeTeam;
	private String awayTeam;
	private PlayerEnum homeTeamEnum;
	private PlayerEnum awayTeamEnum;
	
	private LineUpLight homeTeamResult;
	private LineUpLight awayTeamResult;
	
	
	
	
	
	
	
	public Match(String homeTeam, String awayTeam) {
		this.homeTeam = homeTeam;
		this.awayTeam = awayTeam;
	}
	public Match() {

	}
	
	public String getHomeTeam() {
		return homeTeam;
	}
	public void setHomeTeam(String homeTeam) {
		this.homeTeam = homeTeam;
	}
	public String getAwayTeam() {
		return awayTeam;
	}
	public void setAwayTeam(String awayTeam) {
		this.awayTeam = awayTeam;
	}
	public PlayerEnum getHomeTeamEnum() {
		return homeTeamEnum;
	}
	public void setHomeTeamEnum(PlayerEnum homeTeamEnum) {
		this.homeTeamEnum = homeTeamEnum;
	}
	public PlayerEnum getAwayTeamEnum() {
		return awayTeamEnum;
	}
	public void setAwayTeamEnum(PlayerEnum awayTeamEnum) {
		this.awayTeamEnum = awayTeamEnum;
	}
	public LineUpLight getHomeTeamResult() {
		if (homeTeamResult == null){
			homeTeamResult = new LineUpLight();
		}
		return homeTeamResult;
	}
	public void setHomeTeamResult(LineUpLight homeTeamResult) {
		this.homeTeamResult = homeTeamResult;
	}
	public LineUpLight getAwayTeamResult() {
		if (awayTeamResult == null){
			awayTeamResult = new LineUpLight();
		}
		return awayTeamResult;
	}
	public void setAwayTeamResult(LineUpLight awayTeamResult) {
		this.awayTeamResult = awayTeamResult;
	}
	@Override
	public String toString() {
		return "" + getHomeTeam().substring(0,7) + "\t " + getAwayTeam().substring(0,7) + "\t "
				+ getHomeTeamResult().getGoals() + "\t " + getAwayTeamResult().getGoals() + "\n";
//		return "Match [getHomeTeam()=" + getHomeTeam() + "\n getAwayTeam()=" + getAwayTeam() + "\n getHomeTeamEnum()="
//		+ getHomeTeamEnum() + "\n getAwayTeamEnum()=" + getAwayTeamEnum() + "\n getHomeTeamResult()="
//		+ getHomeTeamResult() + "\n getAwayTeamResult()=" + getAwayTeamResult() + "]";
	}


	
	
	
	
}
