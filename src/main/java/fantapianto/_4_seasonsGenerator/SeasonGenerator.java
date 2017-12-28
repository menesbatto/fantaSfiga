package fantapianto._4_seasonsGenerator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import fantapianto._1_realChampionshipAnalyzerFINAL.MainSeasonAnalyzerFINAL;
import fantapianto._2_seasonPatternExtractor.model.Match;
import fantapianto._2_seasonPatternExtractor.model.PlayerEnum;
import fantapianto._2_seasonPatternExtractor.model.Season;
import fantapianto._2_seasonPatternExtractor.model.SeasonDay;
import fantapianto._2_seasonPatternExtractorNEW.MainSeasonPatternExtractorNEW;
import fantapianto._4_seasonsGenerator.model.PlayerOrder;

public class SeasonGenerator {
	
	private static ArrayList<String> players = MainSeasonPatternExtractorNEW.getPlayers();
	private static Season seasonPattern = MainSeasonPatternExtractorNEW.getSeasonPattern();
	private static Integer fantacalcioActualSeasonDay;
	
	public static Season execute(String combination, Integer seasonNumber){
		if (fantacalcioActualSeasonDay == null){
			Integer serieAActualSeasonDay = MainSeasonAnalyzerFINAL.getSerieAActualSeasonDay();
			Integer fantacalcioStartingSerieASeasonDay = MainSeasonAnalyzerFINAL.getFantacalcioStartingSerieASeasonDay();
			fantacalcioActualSeasonDay = serieAActualSeasonDay - fantacalcioStartingSerieASeasonDay + 1;
		}
		
		ArrayList<PlayerOrder> playerOrderList = new ArrayList<PlayerOrder>();
		
		for (int i = 0; i< players.size(); i++) {
			playerOrderList.add(new PlayerOrder(players.get(i), String.valueOf(combination.charAt(i))));
		}
		
		Collections.sort(playerOrderList, new Comparator<PlayerOrder>() {
			public int compare(PlayerOrder o1, PlayerOrder o2) {
				return o1.getLetter().compareTo(o2.getLetter());
			}
		});
		
		Season s = createSeason(seasonNumber + " - " + combination, playerOrderList); 
		
		return s;
		
	}

	private static Season createSeason(String seasonName, ArrayList<PlayerOrder> playerOrderList) {
		
		Season season = new Season(seasonName);
		SeasonDay seasonDay;
		Match m;
		PlayerEnum playerEnum;
		List<SeasonDay> seasonDays = seasonPattern.getSeasonDays();
		for (int i = 0; i < seasonDays.size(); i++) {
			if(i <= fantacalcioActualSeasonDay){
				SeasonDay currentSeasonDay = seasonDays.get(i);
				seasonDay = new SeasonDay(currentSeasonDay.getName());
				season.getSeasonDays().add(seasonDay);
				for (Match currentMatch : currentSeasonDay.getMatches()){
					m = new Match();
					for (PlayerOrder po : playerOrderList){
						playerEnum = PlayerEnum.valueOf(po.getLetter());
						if (currentMatch.getHomeTeamEnum().equals(playerEnum)) {
							m.setHomeTeam(po.getName());
						}
						if (currentMatch.getAwayTeamEnum().equals(playerEnum)) {
							m.setAwayTeam(po.getName());
						}
					}
					seasonDay.getMatches().add(m);
				}
			}
		}
		
		return season;
	}

}
