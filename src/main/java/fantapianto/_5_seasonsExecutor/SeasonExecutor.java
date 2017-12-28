package fantapianto._5_seasonsExecutor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import fantapianto._00_fantaChampionshipRulesExtractor.RulesExpertMain;
import fantapianto._00_fantaChampionshipRulesExtractor.model.Rules;
import fantapianto._1_realChampionshipAnalyzer.model.LineUpLight;
import fantapianto._1_realChampionshipAnalyzer.model.SeasonDayResult;
import fantapianto._1_realChampionshipAnalyzerFINAL.MainSeasonAnalyzerFINAL;
import fantapianto._2_seasonPatternExtractor.model.Match;
import fantapianto._2_seasonPatternExtractor.model.Season;
import fantapianto._2_seasonPatternExtractor.model.SeasonDay;
import fantapianto._2_seasonPatternExtractorNEW.MainSeasonPatternExtractorNEW;
import fantapianto._5_seasonsExecutor.model.Ranking;
import fantapianto._5_seasonsExecutor.model.RankingRow;
import fantapianto._6_rankingAnalizer.model.Pair;
import fantapianto.utils.AppConstants;

public class SeasonExecutor {
	
	private static ArrayList<SeasonDayResult> realChampionshipResults = MainSeasonAnalyzerFINAL.getRealChampionshipResults();
	
	private static Ranking ranking;
	private static Ranking formulaUnoRanking;
	private static ArrayList<Integer> serieAseasonToWait;
	
	static Rules rules = RulesExpertMain.getRules();
	private static int i;
	
	
	public static Ranking execute(Season season) {
		List<SeasonDay> seasonDays = season.getSeasonDays();
		SeasonDay seasonDay = null;
		SeasonDayResult seasonDayResult;
		ranking = createRanking();
		formulaUnoRanking = createRanking();
		serieAseasonToWait = MainSeasonAnalyzerFINAL.getSeasonDaysToWait();
		
		for (int i = 0; i < realChampionshipResults.size(); i++) {
			Integer fantacalcioStartingSerieASeasonDay = MainSeasonAnalyzerFINAL.getFantacalcioStartingSerieASeasonDay();
			
			if (serieAseasonToWait.contains(i + fantacalcioStartingSerieASeasonDay)){
				continue;
			}
//			if (AppConstants.SEASON_DAYS_TO_WAIT.contains(i+1))
			seasonDay = seasonDays.get(i);
			seasonDayResult = realChampionshipResults.get(i);
			seasonDayResult = SeasonDayExecutor.execute(seasonDay, seasonDayResult);
//			System.out.println(seasonDayResult);
			updateRanking(seasonDayResult);	
			updateFormulaUnoRanking(seasonDayResult);
			
		}
		
		if(AppConstants.DEBUG_MODE)
			checkRealSeasonCorrect(realChampionshipResults);
		
		
//		System.out.println(ranking);
//		System.out.println("");
//		System.out.println(formulaUnoRanking);
//		System.out.println("");
		
//		System.out.println("numero sculate");
//		System.out.println(MatchExecutor.winOrDrewForHalfPointsList);
//		System.out.println("punti sculati");
//		System.out.println(MatchExecutor.rankingPointsWinOrDrewForHalfPointsList);
//		System.out.println("numero  sfigate");
//		System.out.println(MatchExecutor.loseOrDrewForHalfPointList);
//		System.out.println("punti sfigate");
//		System.out.println(MatchExecutor.rankingPointsLoseOrDrewForHalfPointList);
		return ranking;
		

	}

	
	private static void checkRealSeasonCorrect(ArrayList<SeasonDayResult> realChampionshipResults) {
		Season seasonPattern = MainSeasonPatternExtractorNEW.getSeasonPattern();
		
		List<SeasonDay> seasonDays = seasonPattern.getSeasonDays();
		for (int j = 0; j < seasonDays.size(); j++) {
			SeasonDay seasonDayFromWeb = seasonDays.get(j);
			if (j < realChampionshipResults.size()){
				SeasonDayResult seasonDayFromApp = realChampionshipResults.get(j);
				for (int k = 0; k < seasonDayFromWeb.getMatches().size(); k++){
					Match matchFromWeb = seasonDayFromWeb.getMatches().get(k);
					LineUpLight homeTeamResultFromWeb = matchFromWeb.getHomeTeamResult();
					LineUpLight awayTeamResultFromWeb = matchFromWeb.getAwayTeamResult();
					
					LineUpLight homeTeamFromApp = getTeamFromApp(seasonDayFromApp.getLinesUpLight(), matchFromWeb.getHomeTeam());
					LineUpLight awayTeamFromApp = getTeamFromApp(seasonDayFromApp.getLinesUpLight(), matchFromWeb.getAwayTeam());
					
					if (!homeTeamFromApp.getSumTotalPoints().equals(homeTeamResultFromWeb.getSumTotalPoints())){
						System.out.println("ERRORE \t Giornata:\t" + (Integer)(Integer.valueOf(j)+1) + "\tPartita:\t" +  matchFromWeb.getHomeTeam() + " - " + matchFromWeb.getAwayTeam() + "\tSquadra\t" +  matchFromWeb.getHomeTeam() + "\tPunteggio da web:\t" + homeTeamResultFromWeb.getSumTotalPoints() + "\tPunteggio da app:\t" +  homeTeamFromApp.getSumTotalPoints());
	
					}
	
					if (!awayTeamFromApp.getSumTotalPoints().equals(awayTeamResultFromWeb.getSumTotalPoints())){
						System.out.println("ERRORE \t Giornata:\t" + (Integer)(Integer.valueOf(j)+1) + "\tPartita:\t" +  matchFromWeb.getHomeTeam() + " - " + matchFromWeb.getAwayTeam() + "\tSquadra\t" +  matchFromWeb.getAwayTeam() + "\tPunteggio da web:\t" + awayTeamResultFromWeb.getSumTotalPoints() + "\tPunteggio da app:\t" +  awayTeamFromApp.getSumTotalPoints());
					}
				}
			}
		}
		
		
	}
	private static LineUpLight getTeamFromApp(List<LineUpLight> linesUpLight, String teamName) {
		for (LineUpLight lul : linesUpLight){
			if (lul.getTeamName().equals(teamName))
				return lul;
		}
		return null;
	}


	private static void updateFormulaUnoRanking(SeasonDayResult seasonDayResult) {
		List<Pair> seasonDayVoteSumRanking = new ArrayList<Pair>();
		Pair p;
		for (LineUpLight lul : seasonDayResult.getLinesUpLight()){
			p = new Pair(lul.getTeamName(), lul.getTotalWithoutGoalkeeperAndMiddlefielderModifiers());
			seasonDayVoteSumRanking.add(p);
		}
		Collections.sort(seasonDayVoteSumRanking, new Comparator<Pair>() {
			public int compare(Pair o1, Pair o2) {
				int compareTo = o2.getValue().compareTo(o1.getValue());
				return compareTo;
			}
			
		});
		
		List<Pair> seasonDayFormulaUnoRanking = new ArrayList<Pair>();
		Pair formulaUnoPair = new Pair(seasonDayVoteSumRanking.get(0).getName(), rules.getPoints().getFormulaUnoPoints().get(0));
		seasonDayFormulaUnoRanking.add(formulaUnoPair);
		for (int j = 1; j < seasonDayVoteSumRanking.size()-1; j++) {
			Pair voteSumPair = seasonDayVoteSumRanking.get(j);
			if (j > 0){
				Pair previousVoteSumPair = seasonDayVoteSumRanking.get(j - 1);
				if ( previousVoteSumPair.getValue() == voteSumPair.getValue() ){
					Pair previousFormulaUnoPair = seasonDayFormulaUnoRanking.get(j - 1);
					formulaUnoPair = new Pair(voteSumPair.getName(), previousFormulaUnoPair.getValue());
				}
				else
					formulaUnoPair = new Pair(voteSumPair.getName(),rules.getPoints().getFormulaUnoPoints().get(j));
			}
			seasonDayFormulaUnoRanking.add(formulaUnoPair);
		}
//		System.out.println(seasonDayFormulaUnoRanking);
		for( RankingRow rr : formulaUnoRanking.getRows()){
			for (Pair ps :seasonDayFormulaUnoRanking){
				if (rr.getName().equalsIgnoreCase((ps.getName()))){
					rr.setPoints(rr.getPoints() + ps.getValue().intValue());
					continue;
				}
			}
//			System.out.println(seasonDayFormulaUnoRanking);
		}
	
		Collections.sort(formulaUnoRanking.getRows(), new Comparator<RankingRow>() {
			public int compare(RankingRow o1, RankingRow o2) {
				int compareTo = o2.getPoints() - o1.getPoints();
				return compareTo;
			}
			
		});
		
	}



	private static void updateRanking(SeasonDayResult seasonDayResult) {
		for (LineUpLight lul : seasonDayResult.getLinesUpLight()){
			for (RankingRow rr : ranking.getRows()){
				if (lul.getTeamName().equalsIgnoreCase(rr.getName())){
					updateRankingRow(rr, lul);
				}
			}
		}
		
	}
	
	
	
	
	private static void updateRankingRow(RankingRow rr, LineUpLight lul) {
		rr.setPoints(rr.getPoints() + lul.getRankingPoints());
		rr.setScoredGoals(rr.getScoredGoals() + lul.getGoals());
		rr.setSumAllVotes(rr.getSumAllVotes() + lul.getSumTotalPoints());
		rr.setTakenGoals(rr.getTakenGoals() +  lul.getTakenGoals());
		//rr.setTakenGoals(takenGoals);
		//rr.setRankingPosition(rankingPosition);
		
	}



	


	private static Ranking createRanking() {
		Ranking ranking = new Ranking();
		ranking.setName(i++);
		ArrayList<String> players = MainSeasonPatternExtractorNEW.getPlayers();
		RankingRow rw; 
		for (String p : players){
			rw = new RankingRow(p);
			ranking.getRows().add(rw);
		}
		return ranking;
		
	}
	

	
	
}
