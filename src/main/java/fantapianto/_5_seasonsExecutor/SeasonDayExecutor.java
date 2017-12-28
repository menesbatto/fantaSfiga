package fantapianto._5_seasonsExecutor;

import fantapianto._1_realChampionshipAnalyzer.model.LineUpLight;
import fantapianto._1_realChampionshipAnalyzer.model.SeasonDayResult;
import fantapianto._2_seasonPatternExtractor.model.Match;
import fantapianto._2_seasonPatternExtractor.model.SeasonDay;

public class SeasonDayExecutor {
	
	
	public static SeasonDayResult execute(SeasonDay seasonDay, SeasonDayResult seasonDayResult) {
		for (Match m : seasonDay.getMatches()){
			for (LineUpLight lul : seasonDayResult.getLinesUpLight()){
				if (m.getHomeTeam().equalsIgnoreCase(lul.getTeamName())){
					m.setHomeTeamResult(lul);
				}
				if (m.getAwayTeam().equalsIgnoreCase(lul.getTeamName())){
					m.setAwayTeamResult(lul);
				}
			}
			MatchExecutor.execute(m); // per riferimento ha modificato il SeasonDayResult
		}
		return seasonDayResult;
	}


	
}
